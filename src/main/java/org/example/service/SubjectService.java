package org.example.service;

import org.example.DataBase;
import org.example.model.Subject;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SubjectService implements BaseService<Subject,Boolean>{

    @Override
    public Subject add(Subject subject) {
        return subject!=null&& subject.isActive()? DataBase.subjects.put(subject.getId(), subject):null;
    }

    @Override
    public Subject get(Long key) {
        Subject subject = DataBase.subjects.get(key);
        return subject!=null&& subject.isActive()?subject:null;
    }

    @Override
    public Subject upDate(Subject subject, Long key) {
        Subject previousSubject = DataBase.subjects.get(key);
        if (previousSubject!=null&& previousSubject.isActive()&&subject!=null&& subject.isActive()){
            return DataBase.subjects.put(key,subject);
        }
        return null;
    }

    @Override
    public Boolean delete(Long key) {
        Subject subject = DataBase.subjects.get(key);
        if ( subject!= null){
            subject.setActive(false);
            return true;
        }
        return false;
    }
    public Set<Map.Entry<Long, Subject>> getAll(){
        return DataBase.subjects.entrySet().stream().parallel().filter(i -> i.getValue().isActive()).collect(Collectors.toSet());


    }
}
