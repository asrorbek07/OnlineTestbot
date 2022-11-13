package org.example;

import org.example.enums.*;
import org.example.model.Question;
import org.example.model.Subject;
import org.example.model.Test;
import org.example.model.User;
import org.example.service.QuestionService;
import org.example.service.SubjectService;
import org.example.service.TestService;
import org.example.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;

public class TelegramBot extends TelegramLongPollingBot {

    //    SERVICES
    private final QuestionService questionService = new QuestionService();
    private final TestService testService = new TestService();
    private final UserService userService = new UserService();
    private final SubjectService subjectService = new SubjectService();

    @Override
    public String getBotUsername() {
        return "t.me/pdp_b24_online_test_bot";
    }

    @Override
    public String getBotToken() {
        return "5730104416:AAEd6dhguyo3pIQQSW_MxOwT47V1tdxh2P0";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Chat chat = message.getChat();
            if (message.hasText()) {
                message(message, chat);
            }
        } else if (update.hasCallbackQuery()) {
            callBackQuery(update);
        }
    }

    private void message(Message message, Chat chat) {
        User currentUser = userService.get(chat.getId());
        if (message.getText().equals("/start")) {
            start(chat, currentUser);
        } else if (message.getText().equals("TEST")) {
            currentUser.setStatus(UserStatus.TEST);
            myExecute("CHOOSE SUBJECT", chat.getId(), getSubjectInlineMarkup(currentUser), null);
        }
    }

    private void callBackQuery(Update update) {
        String data = update.getCallbackQuery().getData();
        Long userId = update.getCallbackQuery().getFrom().getId();
        User currentUser = userService.get(userId);
        if (currentUser.getStatus().equals(UserStatus.START) && (data.equals(UserLang.Uz.name()) || data.equals(UserLang.Eng.name()))) {
            setLang(data, currentUser);
        }
        if (data.equals(UserLang.Uz.name()) || data.equals(UserLang.Eng.name())) {
            myExecute(currentUser.getLang() == UserLang.Uz ? "Xush Kelibsiz" : "Welcome", userId, null, replyKeyboardMarkupOption());
        } else if (data.startsWith(UserStatus.TEST.name()) && currentUser.getStatus().equals(UserStatus.TEST)) {
            currentUser.setStatus(UserStatus.SUBJECT);
            for (Map.Entry<Long, Subject> longSubjectEntry : subjectService.getAll()) {
                Subject value = longSubjectEntry.getValue();

                if (value.getName().equals(data.substring(4))) {
                    Test test = new Test(userId, value.getName());
                    currentUser.setTest(test);
                    testService.add(test);
                    try {
                        DataBase.receive();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    myExecute(currentUser.getLang().equals(UserLang.Uz) ? "TESTLAR DARAJASINI TANLANG !" : "CHOOSE LEVEL OF TEST !", userId, inlineLevelButton(currentUser), null);
                }
            }
        } else if (data.startsWith(UserStatus.SUBJECT.name()) && currentUser.getStatus().equals(UserStatus.SUBJECT)) {

            Test test = currentUser.getTest();
            test.setLevel(QuestionLevel.valueOf(data.substring(7)));
            currentUser.setStatus(UserStatus.QUASTION_LEVEL);
            myExecute(currentUser.getLang().equals(UserLang.Uz) ? "TESTLAR SONINI TANLANG !" : "CHOOSE COUNT OF TEST !", userId, inlileKeyboardTestAmount(currentUser), null);

        } else if (data.startsWith(UserStatus.QUASTION_LEVEL.name()) && currentUser.getStatus().equals(UserStatus.QUASTION_LEVEL)) {

            Test test = currentUser.getTest();
            currentUser.setStatus(UserStatus.START_TEST);
            try {
                DataBase.receive();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String substring = data.substring(14);
            int cut = 0;
            if (substring.equals(TestAmount.FIVE.name())) cut = 5;
            else if (substring.equals(TestAmount.TEN.name())) cut = 10;
            else if (substring.equals(TestAmount.FIFTEEN.name())) cut = 15;
            else if (substring.equals(TestAmount.TWENTY.name())) cut = 20;
            Question[] questions = new Question[cut];
            int n = 0;
            for (Map.Entry<Long, Question> longQuestionEntry : questionService.getAll()) {
                Question value = longQuestionEntry.getValue();
                if (value.getLevel().equals(test.getLevel())) {
                    if (cut != n) {
                        questions[n++] = value;
                    } else break;
                }
            }
            test.setQuestions(questions);
            try {
                DataBase.receive();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //            myExecute("n",userId,inlineQuastions(currentUser,n,),null);
            Question nextQuestion = test.getNextQuestion();
            int answeredQuestionId = test.getAnsweredQuestionId();
            myExecute(currentUser.getLang().equals(UserLang.Uz) ? nextQuestion.getQuestionUz() : nextQuestion.getQuestionEng(), userId, inlineQuastions(currentUser, answeredQuestionId, nextQuestion), null);

        } else if (currentUser.getStatus().equals(UserStatus.START_TEST) && data.startsWith(UserStatus.START_TEST.name())) {
            Test test = currentUser.getTest();
            String substring = data.substring(10);
            String answer = substring.substring(substring.length() - 1);
            int questionId = Integer.parseInt(substring.substring(0, substring.length() - 1));
            System.out.println(answer);
            System.out.println(questionId);
            if (test.getAnsweredQuestionId() == questionId) {
                if (answer.equals(test.getQuestions()[test.getAnsweredQuestionId()].getCorrectAnswer())) {
                    test.increaseCorrectAnswerAmount();
                    test.increaceBall(test.getQuestions()[test.getAnsweredQuestionId()].getBall());
                    System.out.println("test ball "+test.getQuestions()[test.getAnsweredQuestionId()].getBall());
                    try {
                        DataBase.receive();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            Question nextQuestion = test.getNextQuestion();
            if (nextQuestion == null) {
                testService.delete(test.getId());
                try {
                    DataBase.receive();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(test.getBall());
                String s = currentUser.getLang().equals(UserLang.Uz) ? "Sizning to'plagan ballaringiz :  " + test.getBall() : "your accumulated points :  " + test.getBall();
                currentUser.setTest(null);
                myExecute(s, userId, null, null);
            } else {
                int answeredQuestionId = test.getAnsweredQuestionId();
                myExecute(currentUser.getLang().equals(UserLang.Uz) ? nextQuestion.getQuestionUz() : nextQuestion.getQuestionEng(), userId, inlineQuastions(currentUser, answeredQuestionId, nextQuestion), null);
            }


        }

    }

    private void start(Chat chat, User currentUser) {
        if (currentUser == null) {
            currentUser = signUp(chat);
            userService.add(currentUser);
            try {
                DataBase.receive();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (currentUser.getLang() == null) {
            currentUser.setStatus(UserStatus.START);
            myExecute("Tilni tanlang", chat.getId(), inlineLenguageButton(), null);
        } else {
            currentUser.setStatus(UserStatus.SELECT_LANG);
            myExecute(currentUser.getLang() == UserLang.Uz ? "Xush Kelibsiz" : "Welcome", chat.getId(), null, replyKeyboardMarkupOption());
        }
    }

    private static void setLang(String data, User currentUser) {
        if (data.equals(UserLang.Uz.name()))
            currentUser.setLang(UserLang.Uz);
        else if (data.equals(UserLang.Eng.name())) {
            currentUser.setLang(UserLang.Eng);
        }
        currentUser.setStatus(UserStatus.SELECT_LANG);
        try {
            DataBase.receive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void signIn(User currentUser) {
    }

    private User signUp(Chat chat) {
        User user = new User(chat.getId(), chat.getFirstName());
        user.setStatus(UserStatus.START);
        return user;
    }

    private InlineKeyboardMarkup inlineLenguageButton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButtonUz = new InlineKeyboardButton();
        inlineKeyboardButtonUz.setText(UserLang.Uz.name());
        inlineKeyboardButtonUz.setCallbackData(UserLang.Uz.name());

        InlineKeyboardButton inlineKeyboardButtonEng = new InlineKeyboardButton();
        inlineKeyboardButtonEng.setCallbackData(UserLang.Eng.name());
        inlineKeyboardButtonEng.setText(UserLang.Eng.name());

        buttons.add(inlineKeyboardButtonEng);
        buttons.add(inlineKeyboardButtonUz);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(buttons);
        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup inlineQuastions(User user, int queId, Question questions) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButtonEasy = new InlineKeyboardButton();
        String s = user.getLang().equals(UserLang.Eng) ? questions.getEngVariantA() : questions.getUzVariantA();
        inlineKeyboardButtonEasy.setCallbackData(UserStatus.START_TEST + String.valueOf(queId) + "A");
        inlineKeyboardButtonEasy.setText("A : " + s);

        InlineKeyboardButton inlineKeyboardButtonEasy1 = new InlineKeyboardButton();
        String s1 = user.getLang().equals(UserLang.Eng) ? questions.getEngVariantB() : questions.getUzVariantB();
        inlineKeyboardButtonEasy1.setCallbackData(UserStatus.START_TEST + String.valueOf(queId) + "B");
        inlineKeyboardButtonEasy1.setText("B : " + s1);

        InlineKeyboardButton inlineKeyboardButtonEasy2 = new InlineKeyboardButton();
        String s2 = user.getLang().equals(UserLang.Eng) ? questions.getEngVariantC() : questions.getUzVariantC();
        inlineKeyboardButtonEasy2.setCallbackData(UserStatus.START_TEST + String.valueOf(queId) + "C");
        inlineKeyboardButtonEasy2.setText("C : " + s2);

        InlineKeyboardButton inlineKeyboardButtonEasy3 = new InlineKeyboardButton();
        String s3 = user.getLang().equals(UserLang.Eng) ? questions.getEngVariantD() : questions.getUzVariantD();
        inlineKeyboardButtonEasy3.setCallbackData(UserStatus.START_TEST + String.valueOf(queId) + "D");
        inlineKeyboardButtonEasy3.setText("D : " + s3);

        buttons.add(inlineKeyboardButtonEasy);
        buttons.add(inlineKeyboardButtonEasy1);
        buttons.add(inlineKeyboardButtonEasy2);
        buttons.add(inlineKeyboardButtonEasy3);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(buttons);
        inlineKeyboardMarkup.setKeyboard(rows);
        System.out.println(inlineKeyboardMarkup);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup inlineLevelButton(User user) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButtonEasy = new InlineKeyboardButton();
        inlineKeyboardButtonEasy.setCallbackData(UserStatus.SUBJECT + QuestionLevel.EASY.name());
        inlineKeyboardButtonEasy.setText(user.getLang().equals(UserLang.Uz) ? " OSON " : " EASY ");

        InlineKeyboardButton inlineKeyboardButtonMedium = new InlineKeyboardButton();
        inlineKeyboardButtonMedium.setCallbackData(UserStatus.SUBJECT + QuestionLevel.MEDIUM.name());
        inlineKeyboardButtonMedium.setText(user.getLang().equals(UserLang.Uz) ? " O'RTA " : " MEDIUM ");

        InlineKeyboardButton inlineKeyboardButtonHard = new InlineKeyboardButton();
        inlineKeyboardButtonHard.setCallbackData(UserStatus.SUBJECT + QuestionLevel.HARD.name());
        inlineKeyboardButtonHard.setText(user.getLang().equals(UserLang.Uz) ? " QIYIN " : " HARD ");

        buttons.add(inlineKeyboardButtonMedium);
        buttons.add(inlineKeyboardButtonEasy);
        buttons.add(inlineKeyboardButtonHard);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(buttons);
        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup inlileKeyboardTestAmount(User user) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setCallbackData(UserStatus.QUASTION_LEVEL + TestAmount.FIVE.name());
        inlineKeyboardButton5.setText(" 5 ");

        InlineKeyboardButton inlineKeyboardButton10 = new InlineKeyboardButton();
        inlineKeyboardButton10.setCallbackData(UserStatus.QUASTION_LEVEL + TestAmount.TEN.name());
        inlineKeyboardButton10.setText(" 10 ");

        InlineKeyboardButton inlineKeyboardButton15 = new InlineKeyboardButton();
        inlineKeyboardButton15.setCallbackData(UserStatus.QUASTION_LEVEL + TestAmount.FIFTEEN.name());
        inlineKeyboardButton15.setText(" 15 ");

        InlineKeyboardButton inlineKeyboardButton20 = new InlineKeyboardButton();
        inlineKeyboardButton20.setCallbackData(UserStatus.QUASTION_LEVEL + TestAmount.TWENTY.name());
        inlineKeyboardButton20.setText(" 20 ");


        buttons.add(inlineKeyboardButton5);
        buttons.add(inlineKeyboardButton10);
        buttons.add(inlineKeyboardButton15);
        buttons.add(inlineKeyboardButton20);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(buttons);
        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup getSubjectInlineMarkup(User currentUser) {
        Set<Map.Entry<Long, Subject>> entries = subjectService.getAll();
        InlineKeyboardMarkup subjectInlineMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonsRow = new ArrayList<>();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        int n = 0;
        for (Map.Entry<Long, Subject> entry : entries) {
            Subject value = entry.getValue();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(value.getName());
            button.setCallbackData(currentUser.getStatus().toString() + value.getName());
            buttons.add(button);
            n++;
            if (n == 3) {
                n = 0;
                buttonsRow.add(buttons);
                buttons = new ArrayList<>();
            }
        }
        if (n > 0) {
            buttonsRow.add(buttons);
        }
        subjectInlineMarkup.setKeyboard(buttonsRow);
        return subjectInlineMarkup;
    }

    private ReplyKeyboardMarkup replyKeyboardMarkupOption() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();

        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(ReplayOption.PROFILE.name());

        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setText(ReplayOption.TEST.name());

        keyboardRow.add(keyboardButton);
        keyboardRow.add(keyboardButton1);
        rows.add(keyboardRow);

        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public void myExecute(String text, Long userId, InlineKeyboardMarkup iMarkup, ReplyKeyboardMarkup rMarkup) {
        System.out.println("enter");
        SendMessage sendMessageReplayOption = new SendMessage();
        sendMessageReplayOption.setChatId(userId);
        sendMessageReplayOption.setText(text);

        sendMessageReplayOption.setReplyMarkup(iMarkup == null ? rMarkup : iMarkup);
        try {
            execute(sendMessageReplayOption);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

