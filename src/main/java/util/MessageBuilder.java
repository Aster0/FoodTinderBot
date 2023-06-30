package util;


import bot.data.SessionData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {


    private SendPhoto sendMessage;


    private final String DEFAULT_FOOD_MESSAGE = "😋%foodname%\n⌚ %count%/2 have answered",
    MATCHED_FOOD_MESSAGE = "";




    public MessageBuilder(String message, Update update, InlineKeyboardButton... buttons) {


        SendPhoto sendMessage = new SendPhoto();
        sendMessage.setChatId(update.getMessage().getChatId().toString());

        sendMessage.setCaption(DEFAULT_FOOD_MESSAGE.replace("%count%", "0"));


        sendMessage.setPhoto(new InputFile(
                "https://preppykitchen.com/wp-content/uploads/2022/09/Chicken-Wings-Recipe-Card.jpg"));





        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1;


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        for(InlineKeyboardButton button : buttons) {

            row1 = new ArrayList<>();
            button.setCallbackData(button.getText());
            System.out.println(button.getText());
            row1.add(button);

            rows.add(row1);
        }




        inlineKeyboardMarkup.setKeyboard(rows);
        // Add it to the message
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        this.sendMessage = sendMessage;


    }

    public SendPhoto build() {
        return this.sendMessage;
    }


    public EditMessageCaption editMessage(int messageId, long chatId, TelegramLongPollingBot telegramBot, SessionData sessionData) {

            EditMessageCaption editMessageText = new EditMessageCaption();
            editMessageText.setChatId(chatId);

            editMessageText.setMessageId(messageId);
            editMessageText.setCaption(DEFAULT_FOOD_MESSAGE.replace("%count%",
                    String.valueOf(sessionData.currentMessageCount)));
            editMessageText.setReplyMarkup((InlineKeyboardMarkup) this.sendMessage.getReplyMarkup());



            try {
                telegramBot.execute(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            return editMessageText;
    }
}
