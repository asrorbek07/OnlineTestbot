package org.example.service;

import org.example.DataBase;
import org.example.model.User;

public class UserService implements BaseService<User, Boolean>{

    @Override
    public User add(User user) {
        return DataBase.users.put(user.getUserId(),user);
    }

    @Override
    public User get(int key) {
        return DataBase.users.get(key);
    }

    @Override
    public User upDate(User user, Long key) {
        if (DataBase.users.get(key)==null){
            return DataBase.users.put(key,user);
        }
        return null;
    }

    @Override
    public Boolean delete(int key) {
        DataBase.users.get(key).setActive(false);
        return true;
    }
}
