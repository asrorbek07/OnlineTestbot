package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Test extends Base{
    private int correctAnswerAmount;
    private Long userId;
    private Question[] questions;
    private int answeredQuestionId;

    public Test(Long userId,int questionsAmount) {
        this.userId=userId;
        this.questions = new Question[questionsAmount];
        this.answeredQuestionId = -1;
    }
    public Question getNextQuestion(){
        if (answeredQuestionId+1==questions.length) return null;
       return questions[++answeredQuestionId];
    }
    public void increaseCorrectAnswerAmount(){
        correctAnswerAmount++;
    }

}
