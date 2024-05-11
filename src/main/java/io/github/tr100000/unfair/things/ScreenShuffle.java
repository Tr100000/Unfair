package io.github.tr100000.unfair.things;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joml.Vector2i;

import net.minecraft.util.math.MathHelper;

public final class ScreenShuffle {
    private ScreenShuffle() {}
    public static float clientTickDelta;
    public static final int timeForEachButton = 30;
    public static int buttonTimeValue;

    public static List<Vector2i> positions = new ArrayList<>();

    public static void shufflePositions() {
        Collections.shuffle(positions);
    }

    private static float calculateRotation(float t) {
        return 2 * (float)Math.PI * t;
    }

    private static float calculateRotation() {
        return calculateRotation(buttonTimeValue / (float)timeForEachButton);
    }

    public static float getRotation() {
        return getRotation(clientTickDelta);
    }

    public static float getRotation(float customTickDelta) {
        return MathHelper.lerp(customTickDelta, calculateRotation((buttonTimeValue - 1) / (float)timeForEachButton), calculateRotation());
    }
}
