package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.enums.QuestionLevel;
import org.example.enums.QuestionType;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Question extends Base{
private QuestionType type;
private QuestionLevel level;
private String question;
private List<String> variants=new ArrayList<>();
private int answerId;

}
