package com.noahc3.Slick2D_Test1.Container;

import com.noahc3.Slick2D_Test1.GUI.GUISlot;
import com.noahc3.Slick2D_Test1.Item.IItem;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Arrays;

public class Container {

    ItemSlot[] slots;

    public Container(int size) {
        slots = new ItemSlot[size];

        for(int i = 0; i < size; i++) {
            slots[i] = new ItemSlot();
        }
    }

    public int GetSize() {
        return slots.length;
    }

    public IItem[] GetItems() {
        IItem[] items = new IItem[slots.length];

        for(int i = 0; i < slots.length; i++) {
            items[i] = slots[i].GetItem();
        }

        return items;
    }

    public ItemSlot[] GetSlots() {
        return slots;
    }

    public ItemSlot GetSlot(int index) {
        if (index < slots.length) return slots[index];
        else return null;
    }

    public IItem TryInsert(IItem in) {
        for (ItemSlot k : slots) {
            if (k.GetItem() == null) {
                if (k.CanHold(in)) {
                    if (k.TrySet(in) == null) {
                        return null;
                    }
                }
            }
        }

        return in;
    }



}
