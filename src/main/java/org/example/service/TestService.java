package org.example.service;

import org.example.DataBase;
import org.example.model.Subject;
import org.example.model.Test;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TestService implements BaseService<Test,Boolean>{
    @Override
    public Test add(Test test) {
        return test!=null&& test.isActive() ? DataBase.tests.put(test.getId(),test):null;
    }

    @Override
    public Test get(Long key) {
        Test test = DataBase.tests.get(key);
        return test!=null&& test.isActive() ?test :null;
    }

    @Override
    public Test upDate(Test test, Long key) {
        Test previousTest = DataBase.tests.get(key);
        if (previousTest!=null&& previousTest.isActive()&&test!=null&& test.isActive()){
            return DataBase.tests.put(key, test);
        }
        return null;
    }

    @Override
    public Boolean delete(Long key) {
        Test test = DataBase.tests.get(key);
        if (test!= null){
            test.setActive(false);
            return true;
        }
        return false;
    }

    public Set<Map.Entry<Long, Test>> getAll(){
        return DataBase.tests.entrySet().stream().parallel().filter(i -> i.getValue().isActive()).collect(Collectors.toSet());
    }
}
