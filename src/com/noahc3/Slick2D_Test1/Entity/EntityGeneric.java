package com.noahc3.Slick2D_Test1.Entity;

import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.Resources.IResource;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;

public class EntityGeneric implements IEntity, IResource {

    private Identifier identifier;
    private String displayName;

    protected int posX;
    protected int posY;

    protected Identifier scene;


    public EntityGeneric(Identifier identifier, String displayName) {
        this.identifier = identifier;

        setDisplayName(displayName);

    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Shape getBoundingBox() {
        return null;
    };

    @Override
    public boolean setDisplayName(String displayName) {
        this.displayName = displayName;
        return true;
    }

    @Override
    public Point getPosition() {
        return new Point(posX, posY);
    }

    @Override
    public boolean setPosition(int x, int y) {
        setPosition(new Point(x, y));
        return true;
    }

    @Override
    public boolean setPosition(Point point) {
        this.posX = (int) point.getX();
        this.posY = (int) point.getY();
        return true;
    }

    @Override
    public Identifier getScene() {
        return scene;
    }

    @Override
    public boolean setScene(Identifier scene) {
        this.scene = scene;
        return true;
    }

    @Override
    public ScenePoint getScenePosition() {
        return new ScenePoint(posX, posY, scene);
    }

    @Override
    public boolean setScenePosition(int x, int y, Identifier scene) {
        return setScenePosition(new ScenePoint(x, y, scene));
    }

    @Override
    public boolean setScenePosition(Point point, Identifier scene) {
        return setScenePosition(new ScenePoint(point, scene));
    }

    @Override
    public boolean setScenePosition(ScenePoint scenePoint) {

        if (scene != null) {
            Registry.SCENES.get(this.scene).removeEntity(this);

        }

        this.scene = scenePoint.scene;
        this.posX = (int) scenePoint.pos.getX();
        this.posY = (int) scenePoint.pos.getY();

        Registry.SCENES.get(this.scene).addEntity(this);

        return true;
    }

    @Override
    public boolean getPersistence(Identifier scene) {
        return false;
    }

    @Override
    public boolean getDrawable() {
        return false;
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, int x, int y) {
        drawInWorld(gc, g, new Point(x,y));
    }

    @Override
    public void drawInWorld(GameContainer gc, Graphics g, Point point) {

    }

    @Override
    public void drawOnScreen(GameContainer gc, Graphics g, int x, int y) {

    }

    @Override
    public void update(GameContainer gc, Identifier scene, int delta) {

    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public EntityCategory getEntityCategory() {
        return EntityCategory.INANIMATE;
    }

    @Override
    public EntityCollisionType getEntityCollisionType() {
        return EntityCollisionType.INCORPOREAL;
    }
}
