package org.example.model;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
@Data
public class User {
    private boolean isActive;
    private long userId;
    private String name;
    private int testCount;
    private int ball;
    private LocalDateTime dateTime;

    public User(long userId, String name) {
        this.userId = userId;
        this.name = name;
        this.dateTime=LocalDateTime.now();
        this.isActive=true;
    }
}