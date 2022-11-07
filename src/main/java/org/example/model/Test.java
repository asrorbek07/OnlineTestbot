package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Test extends Base{
private TestType type;
private String question;
private List<String> variants=new ArrayList<>();
private int answerId;

}
