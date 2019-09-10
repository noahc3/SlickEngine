package com.noahc3.Slick2D_Test1.Utility;

import org.newdawn.slick.Font;

import java.util.ArrayList;

public class StringUtilities {

    //returns a string array where each line fits inside of the specified width
    public static String[] DivideByLines(int width, String text, Font font) {

        ArrayList<String> lines = new ArrayList<>();

        boolean done = false;

        int currentIndex = 0;
        int lastLineIndex = 0;
        while (!done) {

            if(font.getWidth(text.substring(lastLineIndex, currentIndex + 1)) < width) {
                currentIndex++;
            } else {
                while (text.charAt(currentIndex) != ' ') currentIndex--;
                lines.add(text.substring(lastLineIndex, currentIndex));
                lastLineIndex = currentIndex + 1;
                currentIndex++;
            }

            if (currentIndex == text.length() - 1) {
                lines.add(text.substring(lastLineIndex, currentIndex));
                done = true;
            }
        }

        return lines.toArray(new String[0]);
    }

}
