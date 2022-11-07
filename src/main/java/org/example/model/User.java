package org.example.model;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
@Data
public class User {
    long userId;
    String name;
    int testCount;
    int ball;
    LocalDateTime dateTime;

    public User(long userId, String name) {
        this.userId = userId;
        this.name = name;
        this.dateTime=LocalDateTime.now();
    }
}
