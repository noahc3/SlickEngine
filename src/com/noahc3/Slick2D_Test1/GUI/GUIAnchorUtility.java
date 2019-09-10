package com.noahc3.Slick2D_Test1.GUI;

import com.noahc3.Slick2D_Test1.Utility.Point2D;
import org.newdawn.slick.GameContainer;

public class GUIAnchorUtility {

    public static Point2D GetAnchoredScreenPosition(GameContainer gc, int width, int height, int xPadding, int yPadding, int anchor) {
        /*
            0    1    2
            3    4    5
            6    7    8
        */

        switch (anchor) {
            case 0: return new Point2D(xPadding, yPadding);
            case 1: return new Point2D((gc.getWidth() / 2) - (width / 2), yPadding);
            case 2: return new Point2D(gc.getWidth() - width - xPadding, yPadding);
            case 3: return new Point2D(xPadding, (gc.getHeight() / 2) - (height / 2));
            case 4: return new Point2D((gc.getWidth() / 2) - (width / 2), (gc.getHeight() / 2) - (height / 2));
            case 5: return new Point2D(gc.getWidth() - width - xPadding, (gc.getHeight() / 2) - (height / 2));
            case 6: return new Point2D(xPadding, gc.getHeight() - height - yPadding);
            case 7: return new Point2D((gc.getWidth() / 2) - (width / 2), gc.getHeight() - height - yPadding);
            case 8: return new Point2D(gc.getWidth() - width - xPadding, gc.getHeight() - height - yPadding);
            default: return new Point2D(xPadding, yPadding);
        }
    }


}
