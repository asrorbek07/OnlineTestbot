package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Question;
import org.example.model.Subject;
import org.example.model.Test;
import org.example.model.User;

import java.io.*;
import java.net.Inet4Address;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class DataBase {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static BufferedWriter bufferedWriter;
    public static HashMap<Long, Question> questions;
    public static HashMap<Long, User> users;
    public static HashMap<Long, Subject> subjects;
    public static HashMap<Long, Test> tests;
    public static Integer idGeneration=0;

    public static void save() throws IOException {
//        SUBJECTS
        File subjectsFile = new File("src/main/java/org/example/files/subjects.json");
        subjectsFile.createNewFile();
        FileReader subjectsFileReader = new FileReader(subjectsFile);
        subjects = gson.fromJson(subjectsFileReader, new TypeToken<HashMap<Long, Subject>>() {
        }.getType());
        subjectsFileReader.close();
        if (subjects == null) {
            subjects = new HashMap<>();
        }

//        idGeneration
        File idGenerationFile = new File("src/main/java/org/example/files/idGeneration.json");
        idGenerationFile.createNewFile();
        FileReader idGenerationFileReader = new FileReader(idGenerationFile);
        idGeneration = gson.fromJson(idGenerationFileReader, new TypeToken<Integer>() {
        }.getType());

        idGenerationFileReader.close();
        if (idGeneration == null){
            idGeneration =0;
        }

//        Question
        File questionsFile = new File("src/main/java/org/example/files/questions.json");
        questionsFile.createNewFile();
        FileReader questionsFileReader = new FileReader(questionsFile);
        questions = gson.fromJson(questionsFileReader, new TypeToken<HashMap<Long, Question>>() {
        }.getType());
        questionsFileReader.close();
        if (questions == null) {
            questions = new HashMap<>();
        }

//      USERS
        File usersFile = new File("src/main/java/org/example/files/users.json");
        usersFile.createNewFile();
        FileReader userFileReader = new FileReader(usersFile);
        users = gson.fromJson(userFileReader, new TypeToken<HashMap<Long, User>>() {
        }.getType());
        userFileReader.close();
        if (users == null) {
            users = new HashMap<>();
        }

        //        Test
        File testsFile = new File("src/main/java/org/example/files/tests.json");
        testsFile.createNewFile();
        FileReader testsFileReader = new FileReader(testsFile);
        tests = gson.fromJson(testsFileReader, new TypeToken<HashMap<Long, Test>>() {
        }.getType());
        testsFileReader.close();
        if (tests == null) {
            tests = new HashMap<>();
        }

    }

    public static void receive() throws IOException {
        //        subjects
        File subjectsFile = new File("src/main/java/org/example/files/subjects.json");
        subjectsFile.createNewFile();
        String subjectsString = gson.toJson(subjects);
        bufferedWriter = new BufferedWriter(new FileWriter(subjectsFile));
        bufferedWriter.write(subjectsString);
        bufferedWriter.close();

        //        idGeneration
        File idGenerationFile = new File("src/main/java/org/example/files/idGeneration.json");
        idGenerationFile.createNewFile();
        String idGeneration1 = gson.toJson(idGeneration);
        bufferedWriter = new BufferedWriter(new FileWriter(idGenerationFile));
        bufferedWriter.write(idGeneration1);
        bufferedWriter.close();

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
