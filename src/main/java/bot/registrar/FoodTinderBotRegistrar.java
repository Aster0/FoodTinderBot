package bot.registrar;

import bot.commands.ICommand;
import bot.commands.StartCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import util.RedactedClass;

import java.util.ArrayList;
import java.util.List;


@Deprecated
public class FoodTinderBotRegistrar extends TelegramLongPollingBot {


    private List<ICommand> commands = new ArrayList<>();
    public FoodTinderBotRegistrar() {

        commands.add(new StartCommand());
    }


    public void onUpdateReceived(Update update) {





        for(ICommand command : commands) {
            System.out.println("Finding");
            command.onCommandReceived(update, this);
        }

    }

    public String getBotUsername() {
        return "weareindecisivebot";
    }

    @Override
    public String getBotToken() {
        return RedactedClass.BOT_TOKEN;
    }
}
