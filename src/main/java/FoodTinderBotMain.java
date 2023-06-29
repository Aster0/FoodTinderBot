import bot.registrar.FoodTinderBotRegistrar;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class FoodTinderBotMain {

    public static void main(String[] args) {

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new FoodTinderBotRegistrar());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
