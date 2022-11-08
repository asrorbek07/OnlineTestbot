package org.example.service;

import org.example.DataBase;
import org.example.model.Question;

public class QuestionService implements BaseService<Question,Boolean>{
    @Override
    public Question add(Question question) {
       return DataBase.questions.put(question.getId(), question);
    }

    @Override
    public Question get(Long key) {
        return DataBase.questions.get(key);
    }

    @Override
    public Question upDate(Question question, Long key) {
        if (DataBase.questions.get(key)==null){
        return DataBase.questions.put(key, question);
        }
        return null;
    }

    @Override
    public Boolean delete(Long key) {
        DataBase.questions.get(key).setActive(false);
        return true;
    }
}
