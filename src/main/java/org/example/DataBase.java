package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Test;
import org.example.model.User;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class DataBase {
    static Gson gson =  new GsonBuilder().setPrettyPrinting().create();
    static BufferedWriter bufferedWriter;
    public static ConcurrentHashMap<Integer, Test> tests;
    public static ConcurrentHashMap<Long, User> users;
    public static void save() throws IOException {
//        TEST
        File testsFile = new File("src/main/java/org/example/files/tests.json");
        testsFile.createNewFile();
        FileReader testsFileReader = new FileReader(testsFile);
        tests = gson.fromJson(testsFileReader, new TypeToken<LinkedHashMap<Integer, Test>>() {
        }.getType());
        testsFileReader.close();
        if (tests == null) {
            tests = new ConcurrentHashMap<>();
        }

//      USERS
        File usersFile = new File("src/main/java/org/example/files/users.json");
        usersFile.createNewFile();
        FileReader userFileReader = new FileReader(testsFile);
        users = gson.fromJson(userFileReader, new TypeToken<LinkedHashMap<Integer, Test>>() {
        }.getType());
        userFileReader.close();
        if (users == null) {
            users = new ConcurrentHashMap<>();
        }
    }

    public static void receive() throws IOException {
//        TESTS
        File testsFile = new File("src/main/java/org/example/files/tests.json");
        testsFile.createNewFile();
        String testsString = gson.toJson(tests);
        bufferedWriter=new BufferedWriter(new FileWriter(testsFile));
        bufferedWriter.write(testsString);
        bufferedWriter.close();

//        USERS

        File usersFile = new File("src/main/java/org/example/files/users.json");
        usersFile.createNewFile();
        String usersString = gson.toJson(users);
        bufferedWriter=new BufferedWriter(new FileWriter(usersFile));
        bufferedWriter.write(usersString);
        bufferedWriter.close();
    }
}
