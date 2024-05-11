package io.github.tr100000.unfair;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class Unfair implements ModInitializer {
	public static ModContainer mod;
	public static boolean enabled = false;
	public static final Path CONFIG_PATH = QuiltLoader.getConfigDir().resolve("unfair.txt");
	public static final String MODID = "unfair";
	public static final Logger LOGGER = LoggerFactory.getLogger("Unfair Mod");

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
			LOGGER.info("game is unfair");
		}
		else {
			LOGGER.info("game is normal :(");
		}
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
