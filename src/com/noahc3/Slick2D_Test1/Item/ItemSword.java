package com.noahc3.Slick2D_Test1.Item;

import com.noahc3.Slick2D_Test1.Core.Registry;
import com.noahc3.Slick2D_Test1.Entity.EntityPlayer;
import com.noahc3.Slick2D_Test1.Entity.EntitySword;
import com.noahc3.Slick2D_Test1.Entity.IEntity;
import com.noahc3.Slick2D_Test1.GUI.GUITextDialogue;
import com.noahc3.Slick2D_Test1.Game;
import com.noahc3.Slick2D_Test1.Resources.Identifier;
import com.noahc3.Slick2D_Test1.Utility.NumberUtilities;
import com.noahc3.Slick2D_Test1.World.Scene;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import sun.util.resources.cldr.et.CurrencyNames_et;

public class ItemSword extends BasicItem {

    protected EntitySword currentSword;

    protected float defaultCooldown = 800.0f;
    protected float cooldown = 0.0f;

    public ItemSword(String displayName) {
        super (displayName, new Identifier("texture.item.sword"), new Identifier("sound.effect.pickup"));
        this.identifier = new Identifier("item.sword");
    }

    @Override
    public void update(GameContainer gc, int delta) {
        cooldown = NumberUtilities.clamp(cooldown - delta, 0, defaultCooldown);
    }

    @Override
    public boolean canInteract(EntityPlayer e) {
        return cooldown <= 0;
    }

    @Override
    public void interact(GameContainer gc, EntityPlayer e) {
        if (currentSword != null) currentSword.cancelSword();
        currentSword = new EntitySword(getDisplayName());
        Registry.SCENES.get(Game.player.getScene()).addEntity(currentSword);
        this.cooldown = defaultCooldown;
    }
}
