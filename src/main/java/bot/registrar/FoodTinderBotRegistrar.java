package bot.registrar;

import bot.commands.ICommand;
import bot.commands.FoodMatchCommand;
import bot.data.FoodData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import util.RedactedClass;

import java.util.ArrayList;
import java.util.List;


@Deprecated
public class FoodTinderBotRegistrar extends TelegramLongPollingBot {



    private FoodData[] listOfFood = {
            new FoodData("Chicken Wings",
                    "https://preppykitchen.com/wp-content/uploads/2022/09/Chicken-Wings-Recipe-Card.jpg"),
            new FoodData("Saizeriya",
                    "https://burpple-3.imgix.net/foods/1660045160_review_image1947389_original.?w=645&dpr=1&fit=crop&q=80&auto=format"),



    };
    private List<ICommand> commands = new ArrayList<>();
    public FoodTinderBotRegistrar() {

        commands.add(new FoodMatchCommand());
    }


    @Override
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
