package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.enums.QuestionLevel;
import org.example.enums.Variant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class Question extends Base{
private Long subjectId;
private QuestionLevel level;
private String questionUz;
private String questionEng;
private Variant A;
private Variant B;
private Variant C;
private Variant D;
private Variant correctAnswer;
private int ball;

    public Question(Long subjectId, QuestionLevel level, String questionUz, String questionEng, Variant a, Variant b, Variant c, Variant d, Variant correctAnswer) {
        this.subjectId = subjectId;
        this.level = level;
        this.questionUz=questionUz;
        this.questionEng=questionEng;
        this.A = a;
        this.B = b;
        this.C = c;
        this.D = d;
        this.correctAnswer = correctAnswer;
        this.ball=level.equals(QuestionLevel.EASY)?5:(level.equals(QuestionLevel.MEDIUM)?10:15);
    }

}
