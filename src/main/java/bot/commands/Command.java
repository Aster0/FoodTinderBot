package bot.commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Command {

    protected boolean allowPrefixReplies = false; // whether the bot will do something when the prefix is said by the user. e.g., matching-test
    public abstract void onCommandReceived(Update update, TelegramLongPollingBot telegramBot);

}
