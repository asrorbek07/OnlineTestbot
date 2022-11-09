package org.example.service;

import org.example.DataBase;
import org.example.model.User;

public class UserService implements BaseService<User, Boolean>{

    @Override
    public User add(User user) {
        System.out.println("user add");
        System.out.println(user);
        return DataBase.users.put(user.getUserId(),user);
    }

    @Override
    public User get(Long key) {
        return DataBase.users.get(key);
    }

    @Override
    public User upDate(User user, Long key) {
        if (DataBase.users.get(key)!=null){
            return DataBase.users.put(key,user);
        }
        return null;
    }

    @Override
    public Boolean delete(Long key) {
        if (DataBase.users.get(key) != null){
            DataBase.users.get(key).setActive(false);
            return true;
        }
        return false;
    }
}
