package com.noahc3.Slick2D_Test1.Entity;

public interface IHealth {
    float getHealth();

    //returns the actual damage dealt after modifiers.
    float damage(Object sender, float rawDamage);
}
