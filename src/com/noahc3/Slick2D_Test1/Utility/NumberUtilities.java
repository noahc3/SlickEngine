package com.noahc3.Slick2D_Test1.Utility;

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


}
