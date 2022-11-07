package org.example.service;

import org.example.DataBase;
import org.example.model.Test;

public class TestService implements BaseService<Test,Boolean>{
    @Override
    public Test add(Test test) {
       return DataBase.tests.put(test.getId(),test);
    }

    @Override
    public Test get(int key) {
        return DataBase.tests.get(key);
    }

    @Override
    public Test upDate(Test test, Long key) {
        if (DataBase.tests.get(key)==null){
        return DataBase.tests.put(key,test);
        }
        return null;
    }

    @Override
    public Boolean delete(int key) {
        DataBase.tests.get(key).setActive(false);
        return true;
    }
}
