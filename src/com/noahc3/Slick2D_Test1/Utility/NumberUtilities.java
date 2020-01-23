package com.noahc3.Slick2D_Test1.Utility;

import com.sun.xml.internal.ws.wsdl.writer.document.StartWithExtensionsType;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import java.util.Random;
import org.newdawn.slick.geom.Vector2f;

import java.util.MissingFormatArgumentException;

public class NumberUtilities {

    private static Random rng = new Random();

    //TODO: doesnt actually wrap.
    public static int wrap(int input, int min, int max) {
        if (input < min) return max;
        if (input > max) return min;
        return input;
    }

    public static int clamp(int input, int min, int max) {
        if (input > max) return max;
        if (input < min) return min;
        return input;
    }

    public static float clamp(float input, float min, float max) {
        if (input > max) return max;
        if (input < min) return min;
        return input;
    }

    public static float distance(Point a, Point b) {
        Line line = new Line(a.getX(), a.getY(), b.getX(), b.getY());
        return line.length();
    }

    public static Point randomPointOffset(Point start, float minDistance, float maxDistance, boolean round) {
        float radius = (float) (rng.nextDouble() * (maxDistance - minDistance) + minDistance);
        float theta = ((2*rng.nextFloat())) * (float) Math.PI; //random angle between 0 and 2pi
        return pointTowardsAngle(start, theta, radius, round);
    }

    public static Point pointTowardsPoint(Point start, Point target, float radius, boolean round, boolean exceedTarget) {
        if (!exceedTarget) radius = NumberUtilities.clamp(radius, 0, NumberUtilities.distance(start, target));
        return pointTowardsPoint(start, target, radius, round);
    }

    public static Point pointTowardsPoint(Point start, Point target, float radius, boolean round) {
        float theta = (float) (Math.atan2(target.getY() - start.getY(), target.getX() - start.getX()));
        return pointTowardsAngle(start, theta, radius, round);
    }

    public static Point pointAwayFromPoint(Point start, Point target, float radius, boolean round, boolean exceedTarget) {
        if (!exceedTarget) radius = NumberUtilities.clamp(radius, 0, NumberUtilities.distance(start, target));
        return pointAwayFromPoint(start, target, radius, round);
    }

    public static Point pointAwayFromPoint(Point start, Point target, float radius, boolean round) {
        float theta = (float) (Math.atan2(target.getY() - start.getY(), target.getX() - start.getX()) + Math.PI);
        return pointTowardsAngle(start, theta, radius, round);
    }

    public static Point pointTowardsAngle(Point start, float angle, float radius, boolean round) {
        double dx = start.getX() + radius * Math.cos(angle);
        double dy = start.getY() + radius * Math.sin(angle);
        if (round) {
            dx = Math.round(dx);
            dy = Math.round(dy);
        }
        return new Point((float) dx, (float) dy);
    }
}