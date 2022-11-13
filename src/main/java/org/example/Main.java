package org.example;

import org.example.enums.QuestionLevel;
import org.example.enums.Variant;
import org.example.model.Question;
import org.example.model.Subject;
import org.example.model.User;
import org.example.service.QuestionService;
import org.example.service.SubjectService;
import org.example.service.TestService;
import org.example.service.UserService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.*;


public class Main {
    //2222
    static Scanner scannerInt = new Scanner(System.in);
    static Scanner scannerScr = new Scanner(System.in);
    static Scanner scannerLong=new Scanner(System.in);
    static QuestionService questionService = new QuestionService();
    static SubjectService subjectService = new SubjectService();
    static TestService testService = new TestService();
    static UserService userService = new UserService();

    public static void main(String[] args) throws IOException, TelegramApiException {
        DataBase.save();
        for (Map.Entry<Long, Question> longQuestionEntry : questionService.getAll()) {
            System.out.println(longQuestionEntry.getValue());
        }
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TelegramBot());
        DataBase.receive();
        adminPanel();
        }


    private static void adminPanel() throws IOException {
        int v = 2;
        while (v != 0) {
            System.out.println("1.AddSubject,2.show subject, 3.delete subject, 0.exit");
            v = scannerInt.nextInt();
            switch (v) {
                case 1 -> {
                    Subject subject = new Subject();
                    System.out.println("enter subject name: ");
                    subject.setName(scannerScr.nextLine());
                    subjectService.add(subject);
                        DataBase.receive();
                }
                case 2 -> {
                    for (Map.Entry<Long, Subject> longSubjectEntry : subjectService.getAll()) {
                        System.out.println(longSubjectEntry.getValue().getId()+" : "+longSubjectEntry.getValue().getName());
                    }
                    System.out.println("enter subject id: ");
                    Long subjectId = scannerLong.nextLong();
                    Subject subject = subjectService.get(subjectId);
                    if (subject != null) {
                        question(subject);
                    } else if (subjectId!=0)System.out.println("sorry not found subject !!! ");
                }
                case 3 -> {
                    System.out.println("enter subject key: ");
                    int key = scannerInt.nextInt();
                    subjectService.delete((long) key);
                    DataBase.receive();
                }

            }
        }
    }

    private static Subject question(Subject subject) throws IOException {
        int v1 = 2;
        while (v1 != 0) {
            System.out.println("1.addQuestion,2.showQuestions, 3.deleteQuestion, 0.exit");
            v1 = scannerInt.nextInt();
            switch (v1) {
                case 1 -> {
                    createNewQuestion(subject);
                }

                case 2 -> {
                    for (Long id : subject.getQuestionsId()) {
                        Question question = questionService.get(id);
                        if (question!=null) {
                            System.out.println("ID: " + question.getId());
                            System.out.println("QUESTION_UZ: " + question.getQuestionUz());
                            System.out.println("QUESTION_ENG: " + question.getQuestionEng());
                            System.out.println("VARIANT A_UZ: " + question.getUzVariantA());
                            System.out.println("VARIANT A_ENG: " + question.getEngVariantA());
                            System.out.println("VARIANT A_UZ: " + question.getUzVariantA());
                            System.out.println("VARIANT A_ENG: " + question.getEngVariantA());
                            System.out.println("VARIANT A_UZ: " + question.getUzVariantA());
                            System.out.println("VARIANT A_ENG: " + question.getEngVariantA());
                            System.out.println("VARIANT A_UZ: " + question.getUzVariantA());
                            System.out.println("VARIANT A_ENG: " + question.getEngVariantA());
                        }
                    }

                    Question currentQuestion=null;
                    long n=-1;
                    while (currentQuestion==null&&n!=0){
                        System.out.println("ENTER QUESTION ID TO UPDATE 0 TO BACK");
                        n= scannerLong.nextLong();
                        currentQuestion = questionService.get(n);
                        if (currentQuestion==null&&n!=0)
                        System.out.println("INCORRECT ID");
                    }
                }
                case 3 -> {
                    System.out.println("Enter quetsion id ????");
                    Long l=scannerLong.nextLong();
                    questionService.delete(l);
                    DataBase.receive();
                }
            }
        }
        return subject;
    }

    private static void createNewQuestion(Subject subject) throws IOException {
        QuestionLevel level = null;
        while (level == null) {
            System.out.println("select level:  1." + QuestionLevel.EASY.name() + ", 2." + QuestionLevel.MEDIUM.name() + ", 3." + QuestionLevel.HARD.name());
            int var = scannerInt.nextInt();
            if (var == 1) level = QuestionLevel.EASY;
            else if (var == 2) level = QuestionLevel.MEDIUM;
            else if (var == 3) level = QuestionLevel.HARD;
            else System.out.println("To'g'ri kiriting");
        }

        Long subjectId = subject.getId();
        QuestionLevel questionLevel = (level);

        System.out.println("ENTER QUESTION_UZ");
        String quas_uz = (scannerScr.nextLine());
        System.out.println("ENTER QUESTION_ENG");
        String quas_eng =(scannerScr.nextLine());

        System.out.println("A variantni kiriting");
        System.out.println("Uzb");
        String a_uz =(scannerScr.nextLine());
        System.out.println("Eng");
        String a_eng=(scannerScr.nextLine());

        System.out.println("B variantni kiriting");
        System.out.println("Uzb");
        String b_uz =(scannerScr.nextLine());
        System.out.println("Eng");
        String b_eng = (scannerScr.nextLine());

        System.out.println("C variantni kiriting");
        System.out.println("Uzb");
        String c_uz =(scannerScr.nextLine());
        System.out.println("Eng");
        String c_eng =(scannerScr.nextLine());

        System.out.println("D variantni kiriting");
        System.out.println("Uzb");
        String d_uz =(scannerScr.nextLine());
        System.out.println("Eng");
        String d_eng =(scannerScr.nextLine());

        String answer=null;
            System.out.println("ENTER CORRECT VARIANT 1.A 2.B 3.C 4.D");
            int n = scannerInt.nextInt();
            switch (n) {
                case 1 ->  answer = "A";
                case 2 ->  answer = "B";
                case 3 ->  answer = "C";
                case 4 ->  answer = "D";
            }



        Question question = new Question(subjectId,questionLevel,quas_uz,quas_eng,a_uz,b_uz,c_uz,d_uz,a_eng
                ,b_eng,c_eng,d_eng,answer);
        questionService.add(question);
        subject.addNewQuestionIdToArray(question.getId());
        DataBase.receive();
    }
}