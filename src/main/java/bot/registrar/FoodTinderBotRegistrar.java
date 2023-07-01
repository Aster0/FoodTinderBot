package bot.registrar;

import bot.commands.ICommand;
import bot.commands.FoodMatchCommand;
import bot.data.FoodData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import util.RedactedClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Deprecated
public class FoodTinderBotRegistrar extends TelegramLongPollingBot {




    private List<ICommand> commands = new ArrayList<>();
    public FoodTinderBotRegistrar() {





        commands.add(new FoodMatchCommand());
    }


    @Override
    public void onUpdateReceived(Update update) {


        try {
            if(System.currentTimeMillis() / 1000 - update.getMessage().getDate() > 2)
                return;
        }
        catch(NullPointerException e) { // sometimes call back query

        }




        for(ICommand command : commands) {

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
