package org.example.model;

import lombok.Data;
import lombok.Getter;
import org.example.enums.UserLang;
import org.example.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class User {
    private UserLang lang;
    private UserStatus status;
    private boolean isActive;
    private long userId;
    private String name;
    private int ball;
    private Date date;
    public User(long userId, String name) {
        this.userId = userId;
        this.name = name;
        this.date=new Date();
        this.isActive=true;
    }
}
