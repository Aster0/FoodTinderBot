package bot.commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.games.CallbackGame;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;
import util.MessageBuilder;

import java.util.ArrayList;
import java.util.List;

public class StartCommand implements ICommand {




    public void onCommandReceived(Update update, TelegramLongPollingBot telegramBot) {

        if(update.getCallbackQuery() != null) {


            System.out.println(update.getCallbackQuery());
            return;
        }

        System.out.println(update.getMessage().getText());





        SendPhoto sendMessage = new MessageBuilder("Chicken Wings \n⌚ 0/0 have answered",
                update, new InlineKeyboardButton("Yes"),
                new InlineKeyboardButton("No")).build();











        try {
            Message message = telegramBot.execute(sendMessage);

//            EditMessageText editMessageText = new EditMessageText();
//            editMessageText.setChatId(sendMessage.getChatId());
//            editMessageText.setMessageId(message.getMessageId());
//            editMessageText.setText("HI");
//            editMessageText.setReplyMarkup((InlineKeyboardMarkup) sendMessage.getReplyMarkup());
//
//
//
//            telegramBot.execute(editMessageText);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
}
