package bot.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements ICommand {




    public void onCommandReceived(Update update) {


        System.out.println(update.getMessage().getText());

    }
}
