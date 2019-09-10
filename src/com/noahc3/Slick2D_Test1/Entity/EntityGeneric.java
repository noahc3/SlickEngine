package com.noahc3.Slick2D_Test1.Entity;

import com.noahc3.Slick2D_Test1.Core.SceneRegistry;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import com.noahc3.Slick2D_Test1.World.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.opengl.*;

public class EntityGeneric implements IEntity{

    private String registryName;
    private String displayName;

    protected int posX;
    protected int posY;

    protected String scene;


    public EntityGeneric(String registryName, String displayName) {
        this.registryName = registryName;

        setDisplayName(displayName);

    }

    @Override
    public String getRegistryName() {
        return registryName;
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
    public String getScene() {
        return scene;
    }

    @Override
    public boolean setScene(String scene) {
        this.scene = scene;
        return true;
    }

    @Override
    public ScenePoint getScenePosition() {
        return new ScenePoint(posX, posY, scene);
    }

    @Override
    public boolean setScenePosition(int x, int y, String scene) {
        return setScenePosition(new ScenePoint(x, y, scene));
    }

    @Override
    public boolean setScenePosition(Point point, String scene) {
        return setScenePosition(new ScenePoint(point, scene));
    }

    @Override
    public boolean setScenePosition(ScenePoint scenePoint) {

        if (scene != null) {
            SceneRegistry.GetScene(this.scene).removeEntity(this);
        }

        this.scene = scenePoint.scene;
        this.posX = (int) scenePoint.pos.getX();
        this.posY = (int) scenePoint.pos.getY();

        SceneRegistry.GetScene(this.scene).addEntity(this);

        return true;
    }

    @Override
    public boolean getPersistence(String scene) {
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
    public void update(GameContainer gc, String scene, int delta) {

    }
}
