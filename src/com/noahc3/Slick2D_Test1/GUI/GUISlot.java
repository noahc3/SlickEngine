package com.noahc3.Slick2D_Test1.GUI;

import com.noahc3.Slick2D_Test1.Container.ItemSlot;
import com.noahc3.Slick2D_Test1.Entity.EntityPlayer;
import com.noahc3.Slick2D_Test1.Entity.IInteractable;
import com.noahc3.Slick2D_Test1.Entity.InteractionType;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Item.IItem;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class GUISlot implements IGUIElement, IInteractable {

    Point2D location;
    ItemSlot slot;
    String displayName;
    Image texture;

    boolean hover = false;
    boolean isInventory = false;
    boolean wasClicking = false;

    public GUISlot(String displayName, ItemSlot slot, Point2D location) {
        this.location = location;
        this.slot = slot;
        this.displayName = displayName;

        try {
            texture = new Image("assets/textures/slot.png");
        } catch (SlickException e) {
            System.err.println(e.getMessage());
        }
    }

    public GUISlot(String displayName, ItemSlot slot, Point2D location, boolean isInventory) {
        this.isInventory = isInventory;
        this.location = location;
        this.slot = slot;
        this.displayName = displayName;

        try {
            texture = new Image("assets/textures/slot.png");
        } catch (SlickException e) {
            System.err.println(e.getMessage());
        }
    }



    @Override
    public void draw(GameContainer gc, Graphics g) {
        texture.draw(location.x, location.y, 64, 64);
        Rectangle mouse = new Rectangle(gc.getInput().getMouseX(), gc.getInput().getMouseY(), 8, 8);
        if (interactionArea().intersects(mouse)){
            //g.fill(interactionArea());
            Color oc = g.getColor();
            g.setColor(new Color(Color.black.r, Color.black.g, Color.black.b, 0.5f));
            g.fillRect(location.x + 8, location.y + 8, 48, 48);
            g.setColor(oc);
        }

        if (slot.GetItem() != null) {
            slot.GetItem().drawOnScreen(g, location.x + 8, location.y + 8, 48, 48);
        }


    }

    @Override
    public boolean shouldUpdate() {
        //return Game.paused && isInventory;
        return true;
    }

    @Override
    public int shouldPersist() {
        return 1;
    }

    @Override
    public void update(GameContainer gc, int delta) {
        Rectangle mouse = new Rectangle(gc.getInput().getMouseX(), gc.getInput().getMouseY(), 8, 8);
        if (interactionArea().intersects(mouse)) {
            if (gc.getInput().isMouseButtonDown(0) && !wasClicking) {
                Game.player.mouseItem = slot.Swap(Game.player.mouseItem);
            }
        }

        if (gc.getInput().isMouseButtonDown(0)) wasClicking = true;
        else wasClicking = false;
    }

    @Override
    public String interactionDisplayName() {
        return null;
    }

    @Override
    public boolean canInteract(EntityPlayer e) {
        return true;
    }

    @Override
    public boolean needsInteraction(EntityPlayer e) {
        return true;
    }

    @Override
    public InteractionType interactionType() {
        return InteractionType.MOUSE;
    }

    @Override
    public void interact(GameContainer gc, EntityPlayer e) {
        //hover = true;
    }

    @Override
    public Shape interactionArea() {
        return new Rectangle(location.x, location.y, 64, 64);
    }

    @Override
    public int getInteractionKey() {
        return 0;
    }
}
