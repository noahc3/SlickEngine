package com.noahc3.Slick2D_Test1;

import com.noahc3.Slick2D_Test1.Config.ConfigControls;
import com.noahc3.Slick2D_Test1.Config.ConfigDebug;
import com.noahc3.Slick2D_Test1.Core.SceneRegistry;
import com.noahc3.Slick2D_Test1.Entity.EntityPlayer;
import com.noahc3.Slick2D_Test1.GUI.GUIButtonPrompt;
import com.noahc3.Slick2D_Test1.GUI.GUIInventory;
import com.noahc3.Slick2D_Test1.GUI.GUITextDialogue;
import com.noahc3.Slick2D_Test1.GUI.IGUIElement;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import com.noahc3.Slick2D_Test1.Utility.TileUtils;
import com.sun.media.jfxmedia.events.PlayerEvent;
import javafx.scene.Scene;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.Arrays;

import com.noahc3.Slick2D_Test1.World.*;
import org.newdawn.slick.geom.Shape;
import sun.security.krb5.Config;

import javax.swing.text.Style;

public class Game extends BasicGame {

    //private ArrayList<com.noahc3.Slick2D_Test1.World.Scene> scenes = new ArrayList<>();

    public static final int tickRate = 50; //updates per second
    public static final int scale = 2;

    private static String initScene = "sceneTest";

    public static EntityPlayer player;

    public boolean rerender = true;

    public static Font smallFont;
    public static Font bigFont;
    public static Font dialogueFont;

    //scene changing properties

    public static boolean sceneChanging = false;
    private static int fadeValue = 0;
    private static boolean fadeDirection = true;
    public static ScenePoint sceneChangeStorage;

    public static ArrayList<IGUIElement> GUIRenderQueue = new ArrayList<>();

    private static boolean menuKeyHeld = false;

    public static boolean paused = false;
    private static GUIInventory inventory;


    public Game(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        player = new EntityPlayer(gc,"entityPlayer", "Player1");

        SceneRegistry.RegisterScene(new SceneTest("sceneTest", "Test Scene"));
        SceneRegistry.RegisterScene(new SceneHouseGeneric1("sceneHouseGeneric1", "Villiage House"));

        player.setScenePosition((int) 550, (int) 250, initScene);

        gc.setMinimumLogicUpdateInterval(1000/tickRate);
        gc.setMaximumLogicUpdateInterval(1000/tickRate);
        gc.setVSync(true);

        smallFont = new TrueTypeFont(new java.awt.Font("Segoe UI", 0, 14), true);
        bigFont = new TrueTypeFont(new java.awt.Font("Segoe UI", 0, 30), true);
        dialogueFont = new TrueTypeFont(new java.awt.Font("Manaspace", 0, 30), false);

    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        AppGameContainer agc = (AppGameContainer) gc;
        agc.setTitle("SLICK2D TEST GAME | " + SceneRegistry.GetScene(player.getScene()).getDisplayName());

        if (gc.getInput().isKeyDown(ConfigControls.menuKey) && !menuKeyHeld) {
            paused = !paused;
            if (paused) {
                inventory = new GUIInventory(player.inventory, "Inventory", gc.getWidth() / 2 - 180, gc.getHeight() / 2 - 106, 5, 3);
                GUIRenderQueue.add(inventory);
            }
            else inventory.flagDeletion();
        }

        if (!paused) {
            boolean updateEntities = true;
            for (IGUIElement k : GUIRenderQueue) {
                if (k instanceof GUITextDialogue) {
                    updateEntities = false;
                    break;
                }
            }

            if (updateEntities) {
                SceneRegistry.GetScene(player.getScene()).update(gc, delta);
            }

            if (sceneChanging) {
                if (fadeValue == 0) {
                    fadeDirection = true;
                }
                if (fadeDirection) {
                    fadeValue++;
                } else {
                    fadeValue--;
                }
                if (fadeValue == 25) {

                    player.setScenePosition(sceneChangeStorage);

                    fadeDirection = false;
                }
                if (fadeValue == 0) {
                    Game.sceneChanging = false;
                }
            }

            for (IGUIElement k : GUIRenderQueue) {
                if (k.shouldUpdate()) k.update(gc, delta);
            }
        } else {

            for (IGUIElement k : GUIRenderQueue) {
                if (k.shouldUpdate()) k.update(gc, delta);
            }

            if (!Arrays.equals(inventory.getItems(), player.inventory.GetItems())) {
                inventory.flagDeletion();
                inventory = new GUIInventory(player.inventory, "Inventory", gc.getWidth() / 2 - 180, gc.getHeight() / 2 - 106, 5, 3);
                GUIRenderQueue.add(inventory);
            }
        }

        if (gc.getInput().isKeyDown(ConfigControls.menuKey)) menuKeyHeld = true;
        else menuKeyHeld = false;

    }

    @Override
    public void render(GameContainer gc, Graphics g) {

        g.setFont(smallFont);

        g.scale(scale,scale);
        g.setBackground(org.newdawn.slick.Color.darkGray);
        SceneRegistry.GetScene(player.getScene()).render(gc, g, (int) player.getPosition().getX(), (int) player.getPosition().getY());


        if (Game.sceneChanging) {

            Color oc = g.getColor();
            g.setColor(new Color(0, 0, 0, fadeValue / 25f));

            g.fillRect(0, 0, gc.getWidth() / Game.scale, gc.getHeight() / Game.scale);
        }


        //debug screen boundary (needs scale)

        if (ConfigDebug.DRAW_SCREEN_BOUNDARY) {
            Color oc = g.getColor();
            g.setColor(new Color(Color.magenta.r, Color.magenta.g, Color.magenta.b, 0.5f));

            Shape bb = TileUtils.GetScreenBoundingBox(gc);

            Point2D screenPos = TileUtils.WorldToScreen(gc, (int) bb.getX(), (int) bb.getY());
            g.fillRect(screenPos.x, screenPos.y, bb.getWidth(), bb.getHeight());

            g.setColor(oc);
        }

        if (ConfigDebug.DRAW_ALIGNMENT_GRID) {
            Color oc = g.getColor();
            g.setColor(Color.blue);

            for (int x = (int) -(player.getPosition().getX() % 16) -(16 - (((gc.getWidth()) / (2 * Game.scale)) % 16)); x < gc.getWidth() / scale; x+= 16) {
                for (int y = (int) -(player.getPosition().getY() % 16) -(16 - (((gc.getHeight()) / (2 * scale)) % 16)); y < gc.getHeight() / scale; y+= 16) {
                    g.drawRect(x, y, 16,16);
                }
            }

            g.setColor(Color.red);

            g.drawRect(gc.getWidth() / (2 * scale) - (player.getPosition().getX() % 16), gc.getHeight() / (2 * scale) - (player.getPosition().getY() % 16), 16, 16);

            g.setColor(oc);
        }


        //unscale for IGUIElement drawing
        g.scale(1f / (float) scale, 1f / (float) scale);

        IGUIElement[] store = GUIRenderQueue.toArray(new IGUIElement[0]);
        for(IGUIElement k : store) {
            if (k.shouldPersist() == 2) GUIRenderQueue.remove(k);
        }

        for(IGUIElement k : GUIRenderQueue) {
            k.draw(gc, g);
        }

        store = GUIRenderQueue.toArray(new IGUIElement[0]);
        for(IGUIElement k : store) {
            if (k.shouldPersist() == 1) GUIRenderQueue.remove(k);
        }

        int debugDrawOffset = g.getFont().getHeight(Integer.toString(gc.getFPS()));
        String debugString;

        g.setColor(Color.white);

        if (ConfigDebug.DISPLAY_CURRENT_SCENE) {
            debugString = "SCENE: " + player.getScene();
            debugDrawOffset += g.getFont().getHeight(debugString);
            g.drawString(debugString, 10, debugDrawOffset);
        }

        debugDrawOffset += 10;

        if (ConfigDebug.DISPLAY_PLAYER_COORDINATES) {
            debugString = "PLAYER (ABS): X: " + player.getPosition().getX() + " Y: " + player.getPosition().getY();
            debugDrawOffset += g.getFont().getHeight(debugString);
            g.drawString(debugString, 10, debugDrawOffset);

            debugString = "PLAYER (BB ABS): X: " + player.getBoundingBox().getX() + " Y: " + player.getBoundingBox().getY();
            debugDrawOffset += g.getFont().getHeight(debugString);
            g.drawString(debugString, 10, debugDrawOffset);

            debugString = "PLAYER (TILE): X: " + Math.floor(player.getPosition().getX() / 16) + " Y: " + Math.floor(player.getPosition().getY() / 16);
            debugDrawOffset += g.getFont().getHeight(debugString);
            g.drawString(debugString, 10, debugDrawOffset);
        }

        debugDrawOffset += 10;

        if (ConfigDebug.DISPLAY_MOUSE_COORDINATES) {
            Point2D mp = TileUtils.ScreenToWorld(gc, gc.getInput().getMouseX(), gc.getInput().getMouseY());
            debugString = "MOUSE (WORLD ABS): X: " + mp.x + " Y: " + mp.y;
            debugDrawOffset += g.getFont().getHeight(debugString);
            g.drawString(debugString, 10, debugDrawOffset);

            debugString = "MOUSE (WORLD TILE): X: " + Math.floor(mp.x / 16) + " Y: " + Math.floor(mp.y / 16);
            debugDrawOffset += g.getFont().getHeight(debugString);
            g.drawString(debugString, 10, debugDrawOffset);

            debugString = "MOUSE (SCREEN): X: " + gc.getInput().getMouseX() + " Y: " + gc.getInput().getMouseY();
            debugDrawOffset += g.getFont().getHeight(debugString);
            g.drawString(debugString, 10, debugDrawOffset);

        }

    }


}