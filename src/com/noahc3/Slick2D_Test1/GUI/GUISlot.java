package com.noahc3.Slick2D_Test1.GUI;

import com.noahc3.Slick2D_Test1.Container.ItemSlot;
import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.Entity.EntityPlayer;
import com.noahc3.Slick2D_Test1.Entity.IInteractable;
import com.noahc3.Slick2D_Test1.Entity.InteractionType;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Resources.ImageResource;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class GUISlot implements IGUIElement, IInteractable {

    Point2D location;
    ItemSlot slot;
    String displayName;
    Identifier baseDefault;
    Identifier baseHighlighted;
    Identifier overlay;

    boolean hover = false;
    boolean isInteractive = true;
    boolean wasClicking = false;

    public GUISlot(String displayName, ItemSlot slot, Point2D location, Identifier baseDefault, Identifier baseHighlighted) {
        this.location = location;
        this.slot = slot;
        this.displayName = displayName;
        this.baseDefault = baseDefault;
        this.baseHighlighted = baseHighlighted;
        this.overlay = null;
    }

    public GUISlot(String displayName, ItemSlot slot, Point2D location, Identifier baseDefault, Identifier baseHighlighted, Identifier overlay) {
        this.location = location;
        this.slot = slot;
        this.displayName = displayName;
        this.baseDefault = baseDefault;
        this.baseHighlighted = baseHighlighted;
        this.overlay = overlay;
    }

    public GUISlot(String displayName, ItemSlot slot, Point2D location, Identifier baseDefault, Identifier baseHighlighted, Identifier overlay, boolean isInteractive) {
        this.location = location;
        this.slot = slot;
        this.displayName = displayName;
        this.baseDefault = baseDefault;
        this.baseHighlighted = baseHighlighted;
        this.overlay = overlay;
        this.isInteractive = isInteractive;
    }


    @Override
    public void draw(GameContainer gc, Graphics g) {
        Rectangle mouse = new Rectangle(gc.getInput().getMouseX(), gc.getInput().getMouseY(), 2, 2);
        if (isInteractive && interactionArea().intersects(mouse)){
            Image texHighlighted = Registry.IMAGES.get(baseHighlighted).getImage();
            texHighlighted.draw(location.x, location.y, texHighlighted.getWidth(), texHighlighted.getHeight());
        } else {
            Image texDefault = Registry.IMAGES.get(baseDefault).getImage();
            texDefault.draw(location.x, location.y, texDefault.getWidth(), texDefault.getHeight());
        }

        if (overlay != null) {
            Image texOverlay = Registry.IMAGES.get(overlay).getImage();
            texOverlay.draw(location.x, location.y, texOverlay.getWidth(), texOverlay.getHeight());
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
