package io.github.tr100000.unfair;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;

import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandBuildContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class Unfair implements ModInitializer {
	public static ModContainer mod;
	public static boolean enabled = false;
	public static final Path CONFIG_PATH = QuiltLoader.getConfigDir().resolve("unfair.txt");
	public static final String MODID = "unfair";
	public static final Logger LOGGER = LoggerFactory.getLogger("Unfair Mod");

	public static Identifier SYNC_STATE_PACKET = new Identifier(MODID, "sync_unfair");

	@Override
	public void onInitialize(ModContainer mod) {
		Unfair.mod = mod;
		enabled = true;
		try {
			if (Files.exists(CONFIG_PATH)) {
				String text = Files.readString(CONFIG_PATH);
				if (text.equalsIgnoreCase("false")) {
					enabled = false;
				}
			}
			else {
				LOGGER.info("making config file");
				Files.writeString(CONFIG_PATH, "true", Charset.defaultCharset());
			}
		}
		catch (Exception e) {
			LOGGER.info("failed to load config", e);
			MinecraftClient.getInstance().close();
		}
		if (enabled) {
			LOGGER.info("your game is unfair now");
		}
		else {
			LOGGER.info("game is normal");
		}
		CommandRegistrationCallback.EVENT.register(Unfair::registerCommand);
		ClientPlayNetworking.registerGlobalReceiver(SYNC_STATE_PACKET, (client, handler, buf, responseSender) -> {
			enabled = buf.readBoolean();
			LOGGER.info("Recieved changes from server.");
		});
	}

	private static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandBuildContext buildContext, CommandManager.RegistrationEnvironment environment) {
		dispatcher.register(
			literal("unfair")
				.executes(context -> {
					Text activeText;
					if (enabled) {
						activeText = Text.literal("Mod Enabled").formatted(Formatting.GREEN);
					}
					else {
						activeText = Text.literal("Mod Disabled").formatted(Formatting.RED);
					}
					context.getSource().sendFeedback(() -> Text.literal("Unfair Mod " + mod.metadata().version() + " (").append(activeText).append(")"), false);
					return 0;
				})
				.then(literal("enabled")
					.executes(context -> {
						context.getSource().sendFeedback(() -> Text.literal(String.valueOf(enabled)), false);
						return 0;
					})
					.then(argument("enabled", BoolArgumentType.bool())
						.executes(context -> {
							enabled = BoolArgumentType.getBool(context, "enabled");
							if (!enabled) {
								enabled = true;
								context.getSource().sendFeedback(() -> Text.literal("you cant do that"), false);
								return 1;
							}
							context.getSource().sendFeedback(() -> Text.literal(enabled ? "Unfair Mod now enabled." : "Unfair Mod now disabled"), false);

							sendSyncPacket(context.getSource().getServer(), enabled);
							return 0;
						})
					)
				).then(literal("saveConfig")
					.executes(context -> {
						try {
							Files.writeString(CONFIG_PATH, String.valueOf(enabled), Charset.defaultCharset());
							context.getSource().sendFeedback(() -> Text.literal("Successfully saved config."), false);
							return 0;
						}
						catch (IOException e) {
							context.getSource().sendFeedback(() -> Text.literal("Failed to save config.").formatted(Formatting.RED), false);
							return 1;
						}
					})
				)
			);
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}

	private static void sendSyncPacket(MinecraftServer server, boolean enabled) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeBoolean(enabled);
		ServerPlayNetworking.send(server.getPlayerManager().getPlayerList(), SYNC_STATE_PACKET, buf);
	}
}
