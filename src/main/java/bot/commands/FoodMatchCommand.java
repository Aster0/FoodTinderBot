package bot.commands;

import bot.data.FoodData;
import bot.data.SessionData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.MessageBuilder;

public class FoodMatchCommand extends Command {

    private final String DEFAULT_FOOD_MESSAGE = "😋 %foodname%\n⌚ %count%/2 have answered",
            MATCHED_FOOD_MESSAGE = "💖 It's a match for %foodname%"+
                    "!\n\nIf you wish to continue to explore more, " +
                    "click \"Continue\".";
    private Message message;
    private MessageBuilder<SendPhoto> messageBuilder;

    private FoodData[] listOfFood = {
            new FoodData("Chicken Wings",
                    "https://preppykitchen.com/wp-content/uploads/2022/09/Chicken-Wings-Recipe-Card.jpg"),
            new FoodData("Saizeriya",
                    "https://burpple-3.imgix.net/foods/1660045160_review_image1947389_original.?w=645&dpr=1&fit=crop&q=80&auto=format"),
    };



    @Override
    public void onCommandReceived(Update update, TelegramLongPollingBot telegramBot) {

        if(update.getCallbackQuery() != null) {


            if(update.getCallbackQuery().getData().split(":")[1].equals("Continue")) {
                System.out.println("CONTINUE!!!");
                generateFoodChoice(update.getCallbackQuery().getMessage().getChatId(), telegramBot, false);

                return;
            }

            SessionData sessionData = SessionData.findSessionByMessageId(
                    update.getCallbackQuery().getMessage().getMessageId());


            if(sessionData == null)
                return;

            if(sessionData.currentMessageCount == 2)
                return;



//            if( sessionData.getUsernames().contains(update.getCallbackQuery().getFrom().getFirstName()))
//                return;


            sessionData.currentMessageCount++;



            sessionData.getUsernames().add(update.getCallbackQuery().getFrom().getFirstName());

            messageBuilder.editMessage(DEFAULT_FOOD_MESSAGE.replace("%count%",
                            String.valueOf(sessionData.currentMessageCount)).replace(
                                    "%foodname%", sessionData.currentFoodData.getName()),
                    update.getCallbackQuery().getMessage().getMessageId(),
                    update.getCallbackQuery().getMessage().getChatId(), telegramBot, sessionData);

            switch(update.getCallbackQuery().getData().split(":")[1]) {
                case "Yes": {

                    sessionData.currentMessageAcceptedCount++;

                    if(sessionData.currentMessageAcceptedCount == 2) { // it's a match!

                        try {
                            telegramBot.execute(new MessageBuilder<SendPhoto>(
                                    MATCHED_FOOD_MESSAGE.replace("%foodname%",
                                            sessionData.currentFoodData.getName()),
                                    "https://cdn.dribbble.com/users/485616/screenshots/6022245/heart_icon.gif",
                                    update.getCallbackQuery().getMessage().getChatId(),
                                    CommandFactory.COMMANDS.START.getCommandPrefix(),
                                    new InlineKeyboardButton("Continue")).build());



                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                        return; // pause here for the user to decide to continue or not.
                    }

                    break;
                }
            }



            if(sessionData.currentMessageCount == 2)
                generateFoodChoice(update.getCallbackQuery().getMessage().getChatId(), telegramBot, false);

            return;
        }


        generateFoodChoice(update.getMessage().getChatId(), telegramBot, true);

    }

    private void generateFoodChoice(long chatId, TelegramLongPollingBot telegramBot, boolean checkIfStarted) {


        SessionData sessionData = SessionData.findSessionByChatId(chatId);


        if(checkIfStarted) { // if already started, we dont start again

            System.out.println(sessionData);
            if(sessionData != null && sessionData.sessionStarted) {
                return;
            }

        }



        if(sessionData == null) {
            sessionData = SessionData.storeSession(chatId);
            sessionData.sessionStarted = true;
        }







        FoodData foodData = sessionData.chooseRandomFood(listOfFood);



        if(foodData == null) {


            try {
                telegramBot.execute(new MessageBuilder<SendMessage>(
                        "We have come to the end of the food list.", chatId, null).build());

                SessionData.deleteSession(sessionData);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }






        messageBuilder = new MessageBuilder(DEFAULT_FOOD_MESSAGE.replace("%count%",
                "0").replace("%foodname%", foodData.getName()), foodData.getThumbnail(),
                chatId, CommandFactory.COMMANDS.START.getCommandPrefix(), new InlineKeyboardButton("Yes"),
                new InlineKeyboardButton("No"));

        SendPhoto sendMessage = messageBuilder.build();

        try {
            message = telegramBot.execute(sendMessage);



            SessionData.storeSession(message.getMessageId(), message.getChatId());


        } catch (TelegramApiException e) {
            e.printStackTrace();
        }













    }
}
