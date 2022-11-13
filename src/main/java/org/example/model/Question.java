package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.enums.QuestionLevel;
import org.example.enums.Variant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class Question extends Base {
    private Long subjectId;
    private QuestionLevel level;
    private String questionUz;
    private String questionEng;
    private String uzVariantA;
    private String uzVariantB;
    private String uzVariantC;
    private String uzVariantD;
    private String engVariantA;
    private String engVariantB;
    private String engVariantC;
    private String engVariantD;
    private String correctAnswer;
    private int ball;

    public Question(Long subjectId, QuestionLevel level, String questionUz, String questionEng, String uzVariantA, String uzVariantB, String uzVariantC, String uzVariantD, String engVariantA, String engVariantB, String engVariantC, String engVariantD, String correctAnswer) {
        this.subjectId = subjectId;
        this.level = level;
        this.questionUz = questionUz;
        this.questionEng = questionEng;
        this.uzVariantA = uzVariantA;
        this.uzVariantB = uzVariantB;
        this.uzVariantC = uzVariantC;
        this.uzVariantD = uzVariantD;
        this.engVariantA = engVariantA;
        this.engVariantB = engVariantB;
        this.engVariantC = engVariantC;
        this.engVariantD = engVariantD;
        this.correctAnswer = correctAnswer;
        this.ball = level.equals(QuestionLevel.EASY) ? 5 : (level.equals(QuestionLevel.MEDIUM) ? 10 : 15);
    }
}
