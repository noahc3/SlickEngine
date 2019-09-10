package com.noahc3.Slick2D_Test1.GUI;

import com.noahc3.Slick2D_Test1.Utility.Point2D;
import org.newdawn.slick.*;

public class GUIButtonPrompt implements IGUIElement{

    public String displayText;
    public int button;
    public int anchor;
    public Point2D position;
    public int positionType;
    Font font;

    public GUIButtonPrompt(int button, String displayText, Font font, int anchor) {
        this.displayText = displayText;
        this.button = button;
        this.anchor = anchor;
        this.font = font;
        this.positionType = 0;
    }

    public GUIButtonPrompt(int button, String displayText, Font font, Point2D position) {
        this.displayText = displayText;
        this.button = button;
        this.position = position;
        this.font = font;
        this.positionType = 1;
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        if (positionType == 0) {
            displayText = "[" + Input.getKeyName(button) + "]" + " " + displayText;
            int width = font.getWidth(displayText) + 10;
            int height = font.getHeight(displayText) + 10;

            Point2D drawAt = GUIAnchorUtility.GetAnchoredScreenPosition(gc, width, height, 10, 10, anchor);

            Color oc = g.getColor();
            g.setColor(Color.black);

            g.fillRect(drawAt.x, drawAt.y, width, height);
            font.drawString(drawAt.x + 5, drawAt.y, displayText);
        }
    }

    @Override
    public boolean shouldUpdate() {
        return false;
    }

    @Override
    public int shouldPersist() {
        return 1;
    }

    @Override
    public void update(GameContainer gc, int delta) {

    }
}
