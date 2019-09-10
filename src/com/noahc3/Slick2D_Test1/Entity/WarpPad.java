package com.noahc3.Slick2D_Test1.Entity;

import com.noahc3.Slick2D_Test1.Config.ConfigControls;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.opengl.Texture;

public class WarpPad extends EntityGeneric implements IInteractable {

    ScenePoint destination;

    Image sprite;
    boolean invisible;

    int width = 16;
    int height = 16;

    boolean needsInteraction;

    String interactionText;

    public WarpPad(String registryName, String displayName, Point2D location, ScenePoint destination, boolean needsInteraction, String interactionText, boolean invisible) {
        super(registryName, displayName);

        this.destination = destination;
        this.invisible = invisible;

        this.setPosition(location.x, location.y);

        this.needsInteraction = needsInteraction;
        this.interactionText = interactionText;

    }

    public ScenePoint getDestination() {
        return destination;
    }

    @Override
    public Shape getBoundingBox() {
        return new Rectangle(this.getPosition().getX(), this.getPosition().getY(), width, height);
    }

    @Override
    public boolean getDrawable() {
        return !invisible;
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, int x, int y) {
        if (!this.invisible) {

        }
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, Point point) {
        drawInWorld(gc, g, (int) point.getX(), (int) point.getY());
    }

    @Override
    public void drawOnScreen(GameContainer gc, Graphics g, int x, int y) {
        super.drawOnScreen(gc, g, x, y);
    }

    @Override
    public void update(GameContainer gc, String scene, int delta) {

        //System.out.println(getBoundingBox().getX() + " " + getBoundingBox().getY() + " " + getBoundingBox().getWidth() + " " + getBoundingBox().getHeight());

        if (!Game.sceneChanging) {
            if (!needsInteraction) {
                if (getBoundingBox().intersects(Game.player.getBoundingBox())) {
                    Game.player.changeScene(destination);
                }
            }
        }
    }

    @Override
    public String interactionDisplayName() {
        return interactionText;
    }

    @Override
    public boolean canInteract(EntityPlayer e) {
        return true;
    }

    @Override
    public boolean needsInteraction(EntityPlayer e) {
        return needsInteraction;
    }

    @Override
    public InteractionType interactionType() {
        return InteractionType.INPUT_PRIMARY;
    }

    @Override
    public void interact(GameContainer gc, EntityPlayer e) {
        if (!Game.sceneChanging) {
            if (needsInteraction) {
                if (getBoundingBox().intersects(Game.player.getBoundingBox())) {
                    Game.player.changeScene(destination);
                }
            }
        }
    }

    @Override
    public Shape interactionArea() {
        return getBoundingBox();
    }

    @Override
    public int getInteractionKey() {
        if (needsInteraction) return ConfigControls.interact_Primary;
        else return -1;

    }
}
