package com.noahc3.Slick2D_Test1.Entity;

import com.noahc3.Slick2D_Test1.Config.ConfigControls;
import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Sound.SoundPlayer;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.opengl.Texture;

public class WarpPad extends EntityGeneric implements IInteractable {

    protected ScenePoint destination;

    protected Image sprite;
    protected boolean invisible;

    protected int width = 16;
    protected int height = 16;

    protected boolean needsInteraction;

    protected String interactionText;

    protected Identifier interactionSoundEffect;

    public WarpPad(Point2D location, ScenePoint destination, boolean needsInteraction, String interactionText, Identifier interactionSoundEffect, boolean invisible) {
        super(new Identifier("entityWarpPad"), "WARPPAD");

        this.destination = destination;
        this.invisible = invisible;

        this.setPosition(location.x, location.y);

        this.needsInteraction = needsInteraction;
        this.interactionText = interactionText;

        this.interactionSoundEffect = interactionSoundEffect;
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
    public void update(GameContainer gc, Identifier scene, int delta) {

        //System.out.println(getBoundingBox().getX() + " " + getBoundingBox().getY() + " " + getBoundingBox().getWidth() + " " + getBoundingBox().getHeight());

        if (!Game.sceneChanging) {
            if (!needsInteraction) {
                if (getBoundingBox().intersects(Game.player.getBoundingBox())) {
                    if (interactionSoundEffect != null) SoundPlayer.playEverywhere(interactionSoundEffect, 1.0f, 100.0f);
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
                    if (interactionSoundEffect != null) SoundPlayer.playEverywhere(interactionSoundEffect, 1.0f, 100.0f);
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
