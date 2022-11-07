package org.example.model;

public abstract class Base {
    protected int id;
    protected static int idGeneration=0;

    public Base() {
        this.id = ++idGeneration;
    }
}
