package com.noahc3.Slick2D_Test1.Resources;

public class Identifier {
    public String name;

    public Identifier(String name) {
        this.name = name;
    }

    public boolean equals(Identifier i) {
        return nameEquals(i);
    }

    public boolean nameEquals(Identifier i) {
        return name.equals(i.name);
    }

    public boolean nameEquals(String s) {
        return name.equals(s);
    }

    @Override
    public String toString() {
        return name;
    }
}
