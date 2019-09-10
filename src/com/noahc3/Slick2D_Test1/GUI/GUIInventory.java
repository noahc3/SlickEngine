package com.noahc3.Slick2D_Test1.GUI;

import com.noahc3.Slick2D_Test1.Container.Container;
import com.noahc3.Slick2D_Test1.Container.ItemSlot;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Item.IItem;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import sun.java2d.pipe.DrawImage;

import java.util.ArrayList;

public class GUIInventory implements IGUIElement{

    String header;
    int width;
    int height;
    Container container;
    GUISlot[][] slots;

    int shouldPersist = 0;

    public GUIInventory(Container container, String header, int xPos, int yPos, int width, int height) {
        this.slots = new GUISlot[width][height];

        int i = 0;
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                this.slots[x][y] = new GUISlot("Slot " + i, container.GetSlot(i), new Point2D(xPos + (x * 74), yPos + (y * 74)), true);
                i++;
            }
        }

        this.width = width;
        this.height = height;
        this.header = header;
        this.container = container;
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        Color oc = g.getColor();
        g.setColor(new Color(Color.black.r, Color.black.g, Color.black.b, 0.5f));
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());

        g.setColor(Color.darkGray);
        g.fillRect(gc.getWidth() / 2 - (width * 64) / 2 - 40, gc.getHeight() / 2 - (height * 64) / 2 - 40, width * 64 + 80, height * 64 + 80);
        g.fillRect(gc.getWidth() / 2 - (width * 64) / 2 - 40, gc.getHeight() / 2 - (height * 64) / 2 - 60, Game.bigFont.getWidth(header) + 20, 20);

        g.setColor(Color.white);
        Game.bigFont.drawString(gc.getWidth() / 2 - (width * 64) / 2 - 30, gc.getHeight() / 2 - (height * 64) / 2 - 60, header);

        g.setColor(oc);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                slots[x][y].draw(gc, g);
            }
        }

        if (Game.player.mouseItem != null) {
            Game.player.mouseItem.drawOnScreen(g, gc.getInput().getMouseX() - 24, gc.getInput().getMouseY() - 24, 48, 48);
        }
    }

    @Override
    public boolean shouldUpdate() {
        return Game.paused;
    }

    @Override
    public int shouldPersist() {
        return shouldPersist;
    }

    @Override
    public void update(GameContainer gc, int delta) {
        for(GUISlot[] p : slots) {
            for (GUISlot q : p) {
                q.update(gc, delta);
            }
        }
    }

    public IItem[] getItems() {
        IItem[] items = new IItem[container.GetSize()];

        for(int i = 0; i < container.GetSize(); i++) {
            items[i] = container.GetSlot(i).GetItem();
        }

        return items;
    }

    public void flagDeletion() {
        shouldPersist = 2;
    }

}
