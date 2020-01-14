package com.noahc3.Slick2D_Test1.GUI;

import com.noahc3.Slick2D_Test1.Container.Container;
import com.noahc3.Slick2D_Test1.Container.ItemSlot;
import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Item.IItem;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import org.newdawn.slick.*;
import sun.java2d.pipe.DrawImage;

import java.util.ArrayList;

public class GUIPlayerInventory implements IGUIElement{

    protected String header;
    protected Container container;
    protected GUISlot[] slots;
    protected Image texture;

    protected Point2D location;

    protected int shouldPersist = 0;

    public GUIPlayerInventory(Container container, String header, Point2D location) throws SlickException {
        this.texture = new Image("assets/textures/gui/inventory.png");

        this.slots = new GUISlot[15];

        //hotbar slots
        for (int y = 0; y < 3; y++) {
            //HACK
            Identifier overlay;
            switch(y) {
                default: case 0: overlay = new Identifier("texture.gui.slot.overlay_j"); break;
                case 1: overlay = new Identifier("texture.gui.slot.overlay_k"); break;
                case 2: overlay = new Identifier("texture.gui.slot.overlay_l"); break;
            }
            this.slots[y] = new GUISlot("Slot " + y, container.GetSlot(y), new Point2D(location.x + 270, location.y + 129 + (y * 69)), new Identifier("texture.gui.slot.default"), new Identifier("texture.gui.slot.highlighted"), overlay);
        }

        //equip slots
        for (int y = 0; y < 3; y++) {
            this.slots[y + 3] = new GUISlot("Slot " + (y+3), container.GetSlot(y+3), new Point2D(location.x + 339, location.y + 129 + (y * 69)), new Identifier("texture.gui.slot.default"), new Identifier("texture.gui.slot.highlighted"), new Identifier("texture.gui.slot.overlay_equip"));
        }

        //storage slots
        for (int y = 0;  y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int i = y*3 + x + 6;
                this.slots[i] = new GUISlot("Slot " + i, container.GetSlot(i), new Point2D(location.x + 47 + (x * 69), location.y + 129 + (y * 69)), new Identifier("texture.gui.slot.default"), new Identifier("texture.gui.slot.highlighted"));
            }
        }

        this.header = header;
        this.container = container;
        this.location = location;
    }

    @Override
    public void draw(GameContainer gc, Graphics g) {
        Font headerfont = Registry.FONTS.get(new Identifier("font.header")).getFont();

        Color oc = g.getColor();
        g.setColor(new Color(Color.black.r, Color.black.g, Color.black.b, 0.5f));
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());

        texture.draw(location.x, location.y);

        g.setColor(Color.white);
        headerfont.drawString(location.x + (texture.getWidth() / 2) - (headerfont.getWidth(header) /2), location.y + 32 - (headerfont.getHeight(header)/2), header);

        g.setColor(oc);

        for (GUISlot k : slots) {
            k.draw(gc, g);
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
        for (GUISlot k : slots) {
            k.update(gc, delta);
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
