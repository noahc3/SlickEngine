package com.noahc3.Slick2D_Test1.Container;

import com.noahc3.Slick2D_Test1.Item.IItem;
import com.noahc3.Slick2D_Test1.Item.ItemDefinition;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;

public class ItemSlot {

    protected IItem item = null;
    protected ArrayList<ItemDefinition> validItems;

    public ItemSlot() {

    }

    public ItemSlot(ArrayList<ItemDefinition> validItems) {
        this.validItems = validItems;
    }

    public IItem Swap(IItem in) {
        if (!CanHold(in)) return in;

        IItem hold = item;
        item = in;
        return hold;
    }

    public IItem TrySet(IItem in) {
        if (!CanHold(in)) return in;

        item = in;
        return null;
    }

    public IItem GetItem() {
        return item;
    }


    public boolean CanHold(IItem i) {
        if (validItems == null || validItems.isEmpty()) return true;

        ItemDefinition item = i.getItemDefinition();

        for(ItemDefinition k : validItems) {
            if (k.fits(item)) return true;
        }

        return false;
    }



}
