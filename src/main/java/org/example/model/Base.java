package org.example.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public abstract class Base {
    private Date date;
    protected long id;
    protected boolean isActive;
    protected static int idGeneration=0;

    public Base() {
        this.id = ++idGeneration;
        this.isActive=true;
        this.date=new Date();
    }


}
