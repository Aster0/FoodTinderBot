package bot.commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class ICommand {

    protected boolean allowPrefixReplies = false; // whether the bot will do something when the prefix is said by the user.
    public void onCommandReceived(Update update, TelegramLongPollingBot telegramBot) {}
}
