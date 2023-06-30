package util;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {


    private SendPhoto sendMessage;


    public MessageBuilder(String message, Update update, InlineKeyboardButton... buttons) {


        SendPhoto sendMessage = new SendPhoto();
        sendMessage.setChatId(update.getMessage().getChatId().toString());

        sendMessage.setCaption(message);


        sendMessage.setPhoto(new InputFile(
                "https://preppykitchen.com/wp-content/uploads/2022/09/Chicken-Wings-Recipe-Card.jpg"));




        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<InlineKeyboardButton>();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        for(InlineKeyboardButton button : buttons) {
            button.setCallbackData(button.getText());
            row1.add(button);
        }


        rows.add(row1);

        inlineKeyboardMarkup.setKeyboard(rows);
        // Add it to the message
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        this.sendMessage = sendMessage;


    }

    public SendPhoto build() {
        return this.sendMessage;
    }
}
