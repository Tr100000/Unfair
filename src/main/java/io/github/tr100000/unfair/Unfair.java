package io.github.tr100000.unfair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Unfair implements ModInitializer {
	public static boolean enabled = false;
	public static final String MODID = "unfair";
	public static final Logger LOGGER = LoggerFactory.getLogger("Unfair Mod");

	@Override
	public void onInitialize() {}

	public static Identifier id(String path) {
		return Identifier.of(MODID, path);
	}
}
