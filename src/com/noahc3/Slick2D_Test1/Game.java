package com.noahc3.Slick2D_Test1;

import com.noahc3.Slick2D_Test1.Config.ConfigControls;
import com.noahc3.Slick2D_Test1.Config.ConfigDebug;
import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.Entity.EntityPlayer;
import com.noahc3.Slick2D_Test1.Event.Events;
import com.noahc3.Slick2D_Test1.GUI.FontResource;
import com.noahc3.Slick2D_Test1.GUI.GUIPlayerInventory;
import com.noahc3.Slick2D_Test1.GUI.GUITextDialogue;
import com.noahc3.Slick2D_Test1.GUI.IGUIElement;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Resources.ImageResource;
import com.noahc3.Slick2D_Test1.Sound.SoundCategory;
import com.noahc3.Slick2D_Test1.Sound.SoundResource;
import com.noahc3.Slick2D_Test1.Utility.Point2D;
import com.noahc3.Slick2D_Test1.Utility.ScenePoint;
import com.noahc3.Slick2D_Test1.Utility.TileUtils;
import org.newdawn.slick.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.noahc3.Slick2D_Test1.World.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.openal.SoundStore;

public class Game extends BasicGame {

    //private ArrayList<com.noahc3.Slick2D_Test1.World.Scene> scenes = new ArrayList<>();

    public static final int tickRate = 50; //updates per second
    public static final int scale = 2;

    private static Identifier initScene = new Identifier("sceneTest");

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
    private static GUIPlayerInventory inventory;


    public Game(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        SoundStore.get().setSoundVolume(0.1f);

        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.gui.slot.default"), "assets/textures/gui/slot/default.png"));
        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.gui.slot.highlighted"), "assets/textures/gui/slot/highlighted.png"));
        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.gui.slot.overlay_equip"), "assets/textures/gui/slot/overlay_equip.png"));
        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.gui.slot.overlay_j"), "assets/textures/gui/slot/overlay_j.png"));
        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.gui.slot.overlay_k"), "assets/textures/gui/slot/overlay_k.png"));
        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.gui.slot.overlay_l"), "assets/textures/gui/slot/overlay_l.png"));

        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.entity.sword"), "assets/textures/entity/sword.png"));
        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.entity.slime.idle"), "assets/textures/entity/slime/idle.png"));
        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.entity.slime.jump"), "assets/textures/entity/slime/jump.png"));

        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.debug"), "assets/textures/debug.png"));
        Registry.IMAGES.tryRegister(new ImageResource(new Identifier("texture.item.sword"), "assets/textures/item/sword.png"));

        Registry.SOUNDS.tryRegister(new SoundResource(new Identifier("sound.music.sceneTest"), SoundCategory.MUSIC));
        Registry.SOUNDS.tryRegister(new SoundResource(new Identifier("sound.music.sceneHouseGeneric1"), SoundCategory.MUSIC));
        Registry.SOUNDS.tryRegister(new SoundResource(new Identifier("sound.effect.fountain"), SoundCategory.EFFECTS));
        Registry.SOUNDS.tryRegister(new SoundResource(new Identifier("sound.effect.door"), SoundCategory.EFFECTS));
        Registry.SOUNDS.tryRegister(new SoundResource(new Identifier("sound.effect.pickup"), SoundCategory.EFFECTS));
        Registry.SOUNDS.tryRegister(new SoundResource(new Identifier("sound.effect.hurt"), SoundCategory.EFFECTS));
        Registry.SOUNDS.tryRegister(new SoundResource(new Identifier("sound.effect.slime"), SoundCategory.EFFECTS));

        player = new EntityPlayer(gc, "Player1");

        Registry.SCENES.tryRegister(new SceneTest(new Identifier("sceneTest"), "Test Scene"));
        Registry.SCENES.tryRegister(new SceneHouseGeneric1(new Identifier("sceneHouseGeneric1"), "Village House"));

        Registry.FONTS.tryRegister(new FontResource(new Identifier("font.header"), java.awt.Font.TRUETYPE_FONT, "assets/font/vecna.ttf", java.awt.Font.BOLD, 30.0f, false));

        player.setScenePosition((int) 550, (int) 250, initScene);

        gc.setMinimumLogicUpdateInterval(1000/tickRate);
        gc.setMaximumLogicUpdateInterval(1000/tickRate);
        gc.setVSync(true);

        smallFont = new TrueTypeFont(new java.awt.Font("Segoe UI", 0, 14), true);
        bigFont = new TrueTypeFont(new java.awt.Font("Vecna", java.awt.Font.BOLD, 24), true);
        dialogueFont = new TrueTypeFont(new java.awt.Font("Manaspace", 0, 30), false);

        Events.SceneChanged(Registry.SCENES.get(player.getScene()));

    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        AppGameContainer agc = (AppGameContainer) gc;
        agc.setTitle("SLICK2D TEST GAME | " + Registry.SCENES.get(player.getScene()).getDisplayName());

        if (gc.getInput().isKeyPressed(ConfigControls.debug_drawWorldColliders)) ConfigDebug.DRAW_WORLD_COLLIDERS = !ConfigDebug.DRAW_WORLD_COLLIDERS;
        if (gc.getInput().isKeyPressed(ConfigControls.debug_drawEntityColliders)) ConfigDebug.DRAW_ENTITY_COLLIDERS = !ConfigDebug.DRAW_ENTITY_COLLIDERS;
        if (gc.getInput().isKeyPressed(ConfigControls.debug_drawEntityPaths)) ConfigDebug.DRAW_ENTITY_PATHS = !ConfigDebug.DRAW_ENTITY_PATHS;


        if (gc.getInput().isKeyDown(ConfigControls.menuKey) && !menuKeyHeld) {
            paused = !paused;
            if (paused) {
                inventory = new GUIPlayerInventory(player.inventory, "INVENTORY", new Point2D(gc.getWidth() / 2 - 224, gc.getHeight() / 2 - 194));
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
                Registry.SCENES.get(player.getScene()).update(gc, delta);
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
                    Scene scene = Registry.SCENES.get(player.getScene());
                    scene.onFinishedLoading();
                    Events.SceneChanged(scene);
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
                inventory = new GUIPlayerInventory(player.inventory, "INVENTORY", new Point2D(gc.getWidth() / 2 - 224, gc.getHeight() / 2 - 194));
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
        Registry.SCENES.get(player.getScene()).render(gc, g, (int) player.getPosition().getX(), (int) player.getPosition().getY());


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

        debugDrawOffset += 10;

        if (ConfigDebug.DISPLAY_PLAYER_HEALTH) {
            debugString = "PLAYER HEALTH: " + player.getHealth();
            debugDrawOffset += g.getFont().getHeight(debugString);
            g.drawString(debugString, 10, debugDrawOffset);
        }

    }


}
