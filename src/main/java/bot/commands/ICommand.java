package bot.commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ICommand {

    void onCommandReceived(Update update, TelegramLongPollingBot telegramBot);
}