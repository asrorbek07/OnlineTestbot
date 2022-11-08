package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Question;
import org.example.model.Test;
import org.example.model.User;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataBase {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static BufferedWriter bufferedWriter;
    public static ConcurrentHashMap<Long, Question> questions;
    public static ConcurrentHashMap<Long, User> users;
    public static ConcurrentHashMap<Long, Test> tests;

    public static void save() throws IOException {
//        Question
        File questionsFile = new File("src/main/java/org/example/files/questions.json");
        questionsFile.createNewFile();
        FileReader questionsFileReader = new FileReader(questionsFile);
        questions = gson.fromJson(questionsFileReader, new TypeToken<ConcurrentHashMap<Long, Question>>() {
        }.getType());
        questionsFileReader.close();
        if (questions == null) {
            questions = new ConcurrentHashMap<>();
        }

//      USERS
        File usersFile = new File("src/main/java/org/example/files/users.json");
        usersFile.createNewFile();
        FileReader userFileReader = new FileReader(questionsFile);
        users = gson.fromJson(userFileReader, new TypeToken<ConcurrentHashMap<Long, Question>>() {
        }.getType());
        userFileReader.close();
        if (users == null) {
            users = new ConcurrentHashMap<>();
        }

        //        Test
        File testsFile = new File("src/main/java/org/example/files/tests.json");
        testsFile.createNewFile();
        FileReader testsFileReader = new FileReader(testsFile);
        tests = gson.fromJson(testsFileReader, new TypeToken<ConcurrentHashMap<Long, Test>>() {
        }.getType());
        testsFileReader.close();
        if (tests == null) {
            tests = new ConcurrentHashMap<>();
        }

    }

    public static void receive() throws IOException {
//        questions
        File questionsFile = new File("src/main/java/org/example/files/questions.json");
        questionsFile.createNewFile();
        String questionsString = gson.toJson(questions);
        bufferedWriter = new BufferedWriter(new FileWriter(questionsFile));
        bufferedWriter.write(questionsString);
        bufferedWriter.close();

//        USERS

        File usersFile = new File("src/main/java/org/example/files/users.json");
        usersFile.createNewFile();
        String usersString = gson.toJson(users);
        bufferedWriter = new BufferedWriter(new FileWriter(usersFile));
        bufferedWriter.write(usersString);
        bufferedWriter.close();


//    TESTS
        File testsFile = new File("src/main/java/org/example/files/tests.json");
        testsFile.createNewFile();
        String testsString = gson.toJson(tests);
        bufferedWriter = new BufferedWriter(new FileWriter(testsFile));
        bufferedWriter.write(testsString);
        bufferedWriter.close();
    }
}
