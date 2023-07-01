package util;


import bot.data.SessionData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder<E> {


    private E sendMessage;







    public MessageBuilder(String message, String thumbnail, long chatId, InlineKeyboardButton... buttons) {


        SendPhoto sendMessage = new SendPhoto();
        sendMessage.setChatId(chatId);

        sendMessage.setCaption(message);


        sendMessage.setPhoto(new InputFile(
                thumbnail));





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

        this.sendMessage = (E) sendMessage;


    }

    public MessageBuilder(String message, long chatId, InlineKeyboardButton... buttons) {


        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        sendMessage.setText(message);



        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1;


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        for(InlineKeyboardButton button : buttons) {

            row1 = new ArrayList<>();
            button.setCallbackData(button.getText());

            row1.add(button);

            rows.add(row1);
        }




        inlineKeyboardMarkup.setKeyboard(rows);
        // Add it to the message
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);


        this.sendMessage = (E) sendMessage;


    }

    public E build() {
        return this.sendMessage;
    }


    public EditMessageCaption editMessage(String message, int messageId, long chatId,
                                          TelegramLongPollingBot telegramBot, SessionData sessionData) {

        EditMessageCaption editMessageText = new EditMessageCaption();
        editMessageText.setChatId(chatId);

        editMessageText.setMessageId(messageId);
        editMessageText.setCaption(message);
        editMessageText.setReplyMarkup((InlineKeyboardMarkup) ((SendPhoto) this.sendMessage).getReplyMarkup());

        EditMessageMedia editMessageMedia = new EditMessageMedia();




        try {
            telegramBot.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return editMessageText;
    }
}
