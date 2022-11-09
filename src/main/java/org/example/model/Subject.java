package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
@Data
@NoArgsConstructor
public class Subject extends Base{
    private String name;
    private ArrayList<Long> questionsId=new ArrayList<>();
    public boolean addNewQuestionIdToArray(Long questionId){
        return  this.questionsId.add(questionId);
    }

    public Subject(String name, ArrayList<Long> questionsId) {
        this.name = name;
        this.questionsId = questionsId;
    }
}
