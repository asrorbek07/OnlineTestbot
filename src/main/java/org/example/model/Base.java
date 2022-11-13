package org.example.model;

import lombok.Data;
import org.example.DataBase;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public abstract class Base {
    private Date date;
    protected long id;
    protected boolean isActive;


    public Base() {
        this.id = ++DataBase.idGeneration;
        this.isActive=true;
        this.date= new Date();
    }


}
