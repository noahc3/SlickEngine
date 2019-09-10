package com.noahc3.Slick2D_Test1.World;

import com.noahc3.Slick2D_Test1.Config.ConfigDebug;
import com.noahc3.Slick2D_Test1.Entity.IEntity;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.TileUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;

public abstract class Scene {

    protected String registryName;
    protected String displayName;

    protected TiledMap tileMap;

    protected boolean blockedTiles[][];
    protected ArrayList<Shape> blockedColliders = new ArrayList<>();

    protected ArrayList<IEntity> entities = new ArrayList<>();

    public Scene(String registryName, String displayName) {
        this.registryName = registryName;
        this.displayName = displayName;
    }

    protected void generateCollisions(int tileSize, int colliderLayer, int colliderTileID) {
        blockedTiles = new boolean[tileMap.getWidth()][tileMap.getHeight()];
        for(int x = 0; x < tileMap.getWidth(); x++) {
            for (int y = 0; y < tileMap.getHeight(); y++) {
                if (tileMap.getTileId(x, y, colliderLayer) >= colliderTileID) {

                    int id = tileMap.getTileId(x, y, colliderLayer) % colliderTileID;

                    blockedTiles[x][y] = true;

                    if (id < 5) {

                        float a, b, h, k;

                        if (id == 0) {
                            a = 1; b = 1; h = 0; k = 0;
                        } else if (id == 1) {
                            a = 0.5f; b = 1; h = 0; k = 0;
                        } else if (id == 2) {
                            a = 1; b = 0.5f; h = 0.5f; k = 0;
                        } else if (id == 3) {
                            a = 0.5f; b = 1; h = 0; k = 0.5f;
                        } else{
                            a = 1; b = 0.5f; h = 0; k = 0;
                        }

                        blockedColliders.add(new Rectangle(((x * tileSize) + (h * tileSize)), (y * tileSize) + (k * tileSize), b * tileSize, a * tileSize));
                    } else {
                        //blockedColliders.add(TileUtils.GetTriangle((x * tileSize) - (10 / Game.scale), (y * tileSize) - (10 * Game.scale), tileSize, tileSize, id - 5));
                    }

                }
            }
        }
    }

    public ArrayList<Shape> getColliders() {
        return blockedColliders;
    }

    public String getRegistryName() {
        return registryName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean tryAddEntity(IEntity entity) {
        if (entities.contains(entity)) return false;
        else {
            entities.add(entity);
            return true;
        }
    }

    public void addEntity(IEntity entity) {
        entities.add(entity);
    }

    public boolean removeEntity(IEntity entity) {
        if (entities.contains(entity)) {
            entities.remove(entity);
            return true;
        } else return false;
    }

    public ArrayList<IEntity> getEntities() {
        return entities;
    }

    public TiledMap getTileMap() {
        return tileMap;
    }

    public void update(GameContainer gc, int delta) {

        for (int i = 0; i < entities.size(); i++) {
            //if (entities.get(i).getPersistence(this.registryName))
            entities.get(i).update(gc, this.registryName, delta);
        }
    }

    public void drawLayer(GameContainer gc, Graphics g, int centerOnX, int centerOnY, int layer) {

        int tileSize = 16 * Game.scale;

        tileMap.render(
                -(centerOnX % 16) -(16 - (((gc.getWidth()) / (2 * Game.scale)) % 16)),
                -(centerOnY % 16) -(16 - (((gc.getHeight()) / (2 * Game.scale)) % 16)),
                (centerOnX / 16) - ((gc.getWidth() / 2) / tileSize) - 1,
                (centerOnY / 16) - ((gc.getHeight() / 2) / tileSize) - 1,
                (gc.getWidth() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 3,
                (gc.getHeight() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 2,
                layer,
                false
        );
    }

    public void render(GameContainer gc, Graphics g, int centerOnX, int centerOnY) {

        int tileSize = 16 * Game.scale;

        drawLayer(gc, g, centerOnX, centerOnY, 1);
        drawLayer(gc, g, centerOnX, centerOnY, 2);

        //tileMap.render((-(centerOnX % 16) + ( gc.getWidth()) % 16), (-(centerOnY % 16) + (gc.getHeight() % 16)), (centerOnX / 16) - ((gc.getWidth() / 2) / tileSize), (centerOnY / 16) - ((gc.getHeight() / 2) / tileSize), (gc.getWidth() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 3, (gc.getHeight() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 2, 1, false);
        //tileMap.render(-(centerOnX % 16), -(centerOnY % 16), (centerOnX / 16) - ((gc.getWidth() / 2) / tileSize), (centerOnY / 16) - ((gc.getHeight() / 2) / tileSize), (gc.getWidth() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 3, (gc.getHeight() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 2, 2, false);


        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getScene() != null) {
                if (entities.get(i).getScene().equals(this.registryName)) {
                    entities.get(i).drawInWorld(gc, g, 0, 0);
                }
            }

        }

        drawLayer(gc, g, centerOnX, centerOnY, 3);
        drawLayer(gc, g, centerOnX, centerOnY, 4);

        //tileMap.render(-(centerOnX % 16), -(centerOnY % 16), (centerOnX / 16) - ((gc.getWidth() / 2) / tileSize), (centerOnY / 16) - ((gc.getHeight() / 2) / tileSize), (gc.getWidth() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 3, (gc.getHeight() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 2, 3, false);
        //tileMap.render(-(centerOnX % 16), -(centerOnY % 16), (centerOnX / 16) - ((gc.getWidth() / 2) / tileSize), (centerOnY / 16) - ((gc.getHeight() / 2) / tileSize), (gc.getWidth() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 3, (gc.getHeight() / tileSize) + ((gc.getHeight() / 2) / tileSize) + 2, 4, false);

        if (ConfigDebug.DRAW_WORLD_COLLIDERS) {
            Color oc = g.getColor();
            g.setColor(new Color(Color.red.r, Color.red.g, Color.red.b, 0.5f));
            for(Shape r : getColliders()) {
                if (r.intersects(TileUtils.GetScreenBoundingBox(gc))){
                    if (r.getPointCount() != 4) {
                        //special shape
                        Point2D screenPos = TileUtils.WorldToScreen(gc, (int) r.getX(), (int) r.getY());
                        r.setX(screenPos.x);
                        r.setY(screenPos.y);

                        g.drawString(Integer.toString(r.getPointCount()), 100, 100);

                        g.fill(r);
                    } else {
                        //probably rectangle
                        g.fillRect((r.getX()) - Game.player.getPosition().getX() + ((gc.getWidth() / 2) / Game.scale), (r.getY()) - Game.player.getPosition().getY() + ((gc.getHeight() / 2) / Game.scale), r.getWidth(), r.getHeight());

                    }
                }
            }
            g.setColor(oc);
        }

        if (ConfigDebug.DRAW_ENTITY_COLLIDERS) {
            Color oc = g.getColor();
            g.setColor(new Color(Color.cyan.r, Color.cyan.g, Color.cyan.b, 0.5f));
            for (IEntity e : entities) {
                Shape bb = e.getBoundingBox();

                if (bb.intersects(TileUtils.GetScreenBoundingBox(gc))){
                    Point2D screenPos = TileUtils.WorldToScreen(gc, (int) bb.getX(), (int) bb.getY());
                    g.fillRect(screenPos.x, screenPos.y, bb.getWidth(), bb.getHeight());
                }
            }

            g.setColor(oc);
        }
    }

    public void postRender(GameContainer gc, Graphics g, int centerOnX, int centerOnY) {

    }

}
