package com.noahc3.Slick2D_Test1.Utility;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;

import java.util.MissingFormatArgumentException;

public class NumberUtilities {

    public int wrap(int input, int min, int max) {
        if (input < min) return max;
        if (input > max) return min;
        return input;
    }

    public int clamp(int input, int min, int max) {
        if (input > max) return max;
        if (input < min) return min;
        return input;
    }

    public float clamp(float input, float min, float max) {
        if (input > max) return max;
        if (input < min) return min;
        return input;
    }

    public Point GetNormalizedDifference(Point a, Point b, float maxDistance) {
        float dx = b.getX() - a.getX();
        float dy = b.getY() - a.getY();

        float nx = clamp(dx / maxDistance, -1, 1);
        float ny = clamp(dy / maxDistance, -1, 1);

        return new Point(nx, ny);
    }
}
