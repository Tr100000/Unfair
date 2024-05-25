package io.github.tr100000.unfair;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class Unfair implements ModInitializer {
	public static ModContainer mod;
	public static boolean enabled = false;
	public static final String MODID = "unfair";
	public static final Logger LOGGER = LoggerFactory.getLogger("Unfair Mod");

	public static final Item JUNK_ITEM = Registry.register(Registries.ITEM, id("junk"), new Item(new Item.Settings().fireproof().maxCount(1).rarity(Rarity.EPIC)));

	@Override
	public void onInitialize(ModContainer mod) {
		Unfair.mod = mod;
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}
