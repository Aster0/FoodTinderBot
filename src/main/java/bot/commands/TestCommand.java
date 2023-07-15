package bot.commands;

import bot.data.FoodData;
import bot.data.SessionData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.MessageBuilder;

public class TestCommand extends ICommand {




    public TestCommand() {
        allowPrefixReplies = true;
    }

    @Override
    public void onCommandReceived(Update update, TelegramLongPollingBot telegramBot) {


        System.out.println("test");


    }


}
