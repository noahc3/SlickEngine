package com.noahc3.Slick2D_Test1.GUI;

import com.noahc3.Slick2D_Test1.Config.ConfigControls;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Utility.StringUtilities;
import org.newdawn.slick.*;

import javax.xml.soap.Text;
import java.util.ArrayList;

public class GUITextDialogue implements IGUIElement  {

    Image dialogueBackground;

    int totalDeltaMillis = 0;
    int textSpeedMillis;
    String text;
    String[] textLines;

    Font font;

    int width = 740;
    int height = 240;
    int line = 0;

    int shouldPersist = 0;
    boolean canExit = false;

    boolean wasKeyDown = true;

    public GUITextDialogue(String text, int textSpeedMillis, Font font) {
        this.font = font;
        this.text = text;
        this.textLines = StringUtilities.DivideByLines(width -20, text, font);
        this.textSpeedMillis = textSpeedMillis;


        try {
            dialogueBackground = new Image("assets/textures/dialogue.png");
            dialogueBackground.setFilter(0);
        } catch (SlickException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        Color oc = g.getColor();
        Font of = g.getFont();
        g.setColor(Color.black);
        g.setFont(font);

        //g.fillRect(gc.getWidth() / 2 - width / 2 - 20, gc.getHeight() - height - 20, width + 20, height);
        dialogueBackground.draw(gc.getWidth() / 2 - width / 2, gc.getHeight() - height - 30, width, height);

        //g.drawString(text.substring(0, Math.min(text.length(), (totalDeltaMillis / textSpeedMillis))), gc.getWidth() / 2 - width / 2, gc.getHeight() - height - 20);
        //font.drawString(gc.getWidth() / 2 - width / 2, gc.getHeight() - height - 20, text.substring(0, Math.min(text.length(), (totalDeltaMillis / textSpeedMillis))));

        //int pos = 0;
        //for (String k : textLines) {
        //    pos++;
        //    font.drawString(gc.getWidth() / 2 - width / 2, gc.getHeight() - height - 20 + font.getHeight(k) * pos, k);
        //}

        String[] linesToDraw = new String[textLines.length];
        int pos = 0;
        int line = 0;
        int charsToDraw = totalDeltaMillis / textSpeedMillis;
        int totalChars = text.length();

        if (charsToDraw > 0) {
            for(String h : textLines) {
                for(char k : h.toCharArray()) {
                    pos++;
                    if (pos < charsToDraw) linesToDraw[line] += k;
                }
                line++;
            }

            //for(String k : linesToDraw) k.replace(null, "");

            pos = 0;
            for (String k : linesToDraw) {
                if (k != null) {
                    pos++;
                    font.drawString(gc.getWidth() / 2 - (width) / 2 + 20, gc.getHeight() - height + font.getHeight(k) * (pos - 1) + (3 * (pos - 1)), k.substring(4), Color.darkGray);
                }

            }
        }


        g.setColor(oc);
        g.setFont(of);
    }

    @Override
    public boolean shouldUpdate() {
        return !Game.paused;
    }

    @Override
    public int shouldPersist() {
        return shouldPersist;
    }

    @Override
    public void update(GameContainer gc, int delta) {
        if (totalDeltaMillis < textSpeedMillis * text.length()) {
            int multiplier = 1;
            if (gc.getInput().isKeyDown(ConfigControls.interact_Primary)) multiplier = 4;
            totalDeltaMillis += delta * multiplier;
        } else {
            canExit = true;
        }

        if (gc.getInput().isKeyDown(ConfigControls.interact_Primary)) {
            if (!wasKeyDown) {
                wasKeyDown = true;
                if (canExit) {
                    shouldPersist = 1;
                } else {
                    //totalDeltaMillis = textSpeedMillis * text.length();
                }
            }
        } else {
            wasKeyDown = false;
        }


    }
}
