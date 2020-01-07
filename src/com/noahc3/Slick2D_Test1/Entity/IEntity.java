package com.noahc3.Slick2D_Test1.Entity;

import com.noahc3.Slick2D_Test1.Resources.IResource;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import com.noahc3.Slick2D_Test1.World.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.*;

public interface IEntity {

    //Generic IEntity

    //identifier used by the registry for static property lookups and stuff
    public Identifier getIdentifier();

    public String getDisplayName(); //get localised display name
    public boolean setDisplayName(String displayName); //set localised display name | true if successful

    public Point getPosition(); //get position in scene (without specifying the scene)
    public boolean setPosition(int x, int y); //set position in scene (without specifying the scene)
    public boolean setPosition(Point point);

    public Identifier getScene(); //get the scene of the entity
    public boolean setScene(Identifier scene); //set the scene of the entity

    public ScenePoint getScenePosition(); //get scene and position of the entity
    public boolean setScenePosition(int x, int y, Identifier scene); //set scene and pos of entity
    public boolean setScenePosition(Point point, Identifier scene);
    public boolean setScenePosition(ScenePoint scenePoint);

    public Shape getBoundingBox();

    public boolean getPersistence(Identifier scene); //does the entity continue processing in the given scene

    public boolean getDrawable(); //returns false if the entity cant be drawn

    public void drawInWorld(GameContainer gc, Graphics g, int x, int y); //draw in the world at the specified position in the scene
    public void drawInWorld(GameContainer gc, Graphics g, Point point);  //note: this method should check if it is visible before trying to draw

    public void drawOnScreen(GameContainer gc, Graphics g, int x, int y); //draw on the window; for IGUIElement stuff

    public void update(GameContainer gc, Identifier scene, int delta);
}
