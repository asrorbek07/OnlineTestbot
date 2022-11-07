package org.example.model;

import lombok.Data;

@Data
public abstract class Base {
    protected long id;
    protected boolean isActive;
    protected static int idGeneration=0;

    public Base() {
        this.id = ++idGeneration;
        this.isActive=true;
    }
}
