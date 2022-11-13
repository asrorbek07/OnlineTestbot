package org.example.model;

import lombok.Data;
import org.example.enums.QuestionLevel;
import org.example.enums.UserStatus;

@Data
public class Test extends Base{
    private int correctAnswerAmount;
    private Long userId;
    private QuestionLevel level;
    private Question[] questions;
    private int answeredQuestionId;
    private String subjectName;
    private int ball=0;


    public Test(Long userId,String subjectName) {
        this.userId=userId;
        this.answeredQuestionId = -1;
        this.subjectName=subjectName;
    }
    public Question getNextQuestion(){
        if (answeredQuestionId+1==questions.length) return null;
       return questions[++answeredQuestionId];
    }

    public void increaseCorrectAnswerAmount(){
        correctAnswerAmount++;
    }
   public void increaceBall(int ball){
        this.ball+=ball;
    }

}
