package org.example.service;

import org.example.DataBase;
import org.example.model.Question;

public class QuestionService implements BaseService<Question,Boolean>{
    @Override
    public Question add(Question question) {
       return (question!=null&&question.isActive())?DataBase.questions.put(question.getId(), question):
        null;

    }

    @Override
    public Question get(Long key) {
        Question question = DataBase.questions.get(key);
        return (question!=null&&question.isActive())?question:null;
    }

    @Override
    public Question upDate(Question question, Long key) {
        Question previousQuestion = DataBase.questions.get(key);
        if (previousQuestion!=null&&previousQuestion.isActive()&&question!=null&&question.isActive()){
        return DataBase.questions.put(key, question);
        }
        return null;
    }

    @Override
    public Boolean delete(Long key) {
        Question question = DataBase.questions.get(key);
        if(question!=null){
            question.setActive(false);
            return true;
        }else return false;

    }
}
