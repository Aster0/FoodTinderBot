package bot.registrar;

import bot.commands.CommandFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import util.RedactedClass;


@Deprecated
public class FoodTinderBotRegistrar extends TelegramLongPollingBot {




    // private List<ICommand> commands = new ArrayList<>();


    private CommandFactory commandFactory;
    public FoodTinderBotRegistrar() {



        commandFactory = new CommandFactory();

        //commands.add(new FoodMatchCommand());
    }


    @Override
    public void onUpdateReceived(Update update) {


        try {
            if(System.currentTimeMillis() / 1000 - update.getMessage().getDate() > 2)
                return;
        }
        catch(NullPointerException e) { // sometimes call back query

        }

        String commandPrefix;

        System.out.println("yea");


        boolean cameFromCallback = false;

        if(update.getMessage() != null) {
            commandPrefix = update.getMessage().getText().split(":")[0];
        }
        else {
            commandPrefix = update.getCallbackQuery().getData().split(":")[0];
            cameFromCallback = true;

        }



        commandFactory.fetchCommand(commandPrefix, cameFromCallback).onCommandReceived(update, this);




//        for(ICommand command : commands) {
//
//            command.onCommandReceived(update, this);
//        }

    }

    public String getBotUsername() {
        return "weareindecisivebot";
    }

    @Override
    public String getBotToken() {
        return RedactedClass.BOT_TOKEN;
    }
}
