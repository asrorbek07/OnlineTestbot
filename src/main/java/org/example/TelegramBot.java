package org.example;

import org.example.enums.ReplayOption;
import org.example.enums.UserLang;
import org.example.enums.UserStatus;
import org.example.model.User;
import org.example.service.QuestionService;
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

import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

    //    SERVICES
    private QuestionService questionService = new QuestionService();
    private TestService testService = new TestService();
    private UserService userService = new UserService();
    private String botToken = "5730104416:AAEd6dhguyo3pIQQSW_MxOwT47V1tdxh2P0";
    private String botUserName = "t.me/pdp_b24_online_test_bot";

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
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Chat chat = message.getChat();
            if (message.hasText()) {
                if (message.getText().equals("/start")) {
                    User currentUser = userService.get(chat.getId());

                    if (currentUser == null) {
                        currentUser = signUp(chat);
                        userService.add(currentUser);
                    }
                    if (currentUser.getLang()==null) {
                        currentUser.setStatus(UserStatus.START);
                        myExecute("Tilni tanlang", chat.getId(), inlineLenguageButton(),null);
                    } else {
                        currentUser.setStatus(UserStatus.SELECT_LANG);
                        myExecute(currentUser.getLang()==UserLang.Uz ?"Xush Kelibsiz":"Welcome", chat.getId(), null,replyKeyboardMarkupOption());

                    }

                    signIn(currentUser);
                }
            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long userId = update.getCallbackQuery().getFrom().getId();
            User currentUser = userService.get(userId);
            if (currentUser.getStatus().equals(UserStatus.START)&&(data.equals(UserLang.Uz.name())||data.equals(UserLang.Eng.name()))) {
                setLang(data, currentUser);
            }
            if (currentUser.getStatus() == UserStatus.SELECT_LANG) {
                myExecute(currentUser.getLang()==UserLang.Uz ?"Xush Kelibsiz":"Welcome",userId,null,replyKeyboardMarkupOption());
            }

        }
    }


//    private void selectReplayOption(String data, User currentUser) {
//
//    }

    private static void setLang(String data, User currentUser) {
        if (data.equals(UserLang.Uz.name()))
            currentUser.setLang(UserLang.Uz);
        else if (data.equals(UserLang.Eng.name())) {
            currentUser.setLang(UserLang.Eng);
        }
        currentUser.setStatus(UserStatus.SELECT_LANG);

    }

    private void signIn(User currentUser) {
    }

    private User signUp(Chat chat) {
        return new User(chat.getId(), chat.getFirstName());
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
    public void myExecute(String text,Long userId,InlineKeyboardMarkup iMarkup, ReplyKeyboardMarkup rMarkup){
        SendMessage sendMessageReplayOption =new SendMessage();
        sendMessageReplayOption.setChatId(userId);
        sendMessageReplayOption.setText(text);
        sendMessageReplayOption.setReplyMarkup(iMarkup==null? rMarkup:iMarkup);
        try {
            execute(sendMessageReplayOption);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

