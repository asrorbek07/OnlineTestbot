package org.example;

import org.example.model.Question;
import org.example.model.User;
import org.example.service.QuestionService;
import org.example.service.TestService;
import org.example.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class TelegramBot extends TelegramLongPollingBot {

//    SERVICES
    private QuestionService questionService = new QuestionService();
    private TestService testService = new TestService();
    private UserService userService = new UserService();
    private String botToken="5730104416:AAEd6dhguyo3pIQQSW_MxOwT47V1tdxh2P0";
    private String botUserName="t.me/pdp_b24_online_test_bot";
    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
        Message message = update.getMessage();
        Chat chat = message.getChat();
        System.out.println(chat.toString());
        if (message.hasText()) {
            if (message.getText().equals("/start")) {
                User currentUser = userService.get(chat.getId());
                if (currentUser == null) {
                    currentUser = signUp(chat);
                }
                if (currentUser.getLang()==null) {
                    Message langMessage= new Message();
                    message.setText("Tilni tanlang");
//                    InlineKeyboardMarkup<In>
                }
                signIn(currentUser);
            }
        }
        } else if (update.hasCallbackQuery()) {

        }
    }

    private void signIn(User currentUser) {
    }

    private User signUp(Chat chat) {
        return new User(chat.getId(),chat.getFirstName());
    }


}

