package bot.commands;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TestCommand extends AbstractCommand {




    public TestCommand() {
        allowPrefixReplies = true;
    }

    @Override
    public void onCommandReceived(Update update, TelegramLongPollingBot telegramBot) {


        System.out.println("test");


    }


}
