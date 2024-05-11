package io.github.tr100000.unfair.things;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joml.Vector2i;

public final class ScreenShuffle {
    private ScreenShuffle() {}
    public static int timeForEachButton = 20;
    public static int otherTimeThing;

    public static List<Vector2i> positions = new ArrayList<>();

    public static void shufflePositions() {
        Collections.shuffle(positions);
    }
}
