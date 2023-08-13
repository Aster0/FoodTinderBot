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

    private final String DEFAULT_FOOD_MESSAGE = "😋 %foodname%\n %count%/2 have answered\n📰%details%",
            MATCHED_FOOD_MESSAGE = "💖 It's a match for %foodname%"+
                    "!\n\nIf you wish to continue to explore more, " +
                    "click \"Continue\".";


    private final int MAX_COUNT = 2;
    private Message message;
    private MessageBuilder<SendPhoto> messageBuilder;

    private FoodData[] listOfFood = {
            new FoodData("Chicken Wings",
                    "https://preppykitchen.com/wp-content/uploads/2022/09/Chicken-Wings-Recipe-Card.jpg"),
            new FoodData("Saizeriya",
                    "https://burpple-3." +
                            "imgix.net/foods/1660045160_review_image1947389_original.?w=645&dpr=1&fit=crop&q=80&auto=format"),
            new FoodData("Ayam Penyet",
                    "https://eatbook.sg/wp-content/uploads/2023/01/uncle-penyet-ayam-penyet-set.jpg"),
            new FoodData("Bak Chor Mee",
                    "https://eatbook.sg/wp-content/uploads/2022/08/10-best-bak-chor-mee-tai-hwa-pork-noodles.jpg"),
            new FoodData("Bak Kut Teh",
                    "https://eatbook.sg/wp-content/uploads/2020/02/Joo-Siah-Bak-Koot-Teh-Bak-Kut-Teh.jpg"),
            new FoodData("Ban Mian",
                    "https://eatbook.sg/wp-content/uploads/2021/02/OrganicL32-Handmade-Noodlesby-Yi-En-16.jpg"),
            new FoodData("Carrot Cake",
                    "https://eatbook.sg/wp-content/uploads/2023/02/chey-sua-carrot-cake-plating.jpg"),
            new FoodData("Ramen",
                    "https://www.foodandwine.com/thmb/" +
                            "0AXGLeY6dYnY8sEXFqxBa8opDrs=/1500x0/filter" +
                            "s:no_upscale():max_bytes(150000):strip_icc()/Tonkotsu-Ramen-FT-BLO" +
                            "G1122-8fe6c12d609a4fd4ab246bea3aae140e.jpg"),
            


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



            if(sessionData.currentMessageCount == MAX_COUNT) // make it so the count cant go above 2.
                return;



//            if( sessionData.getUsernames().contains(update.getCallbackQuery().getFrom().getFirstName()))
//                return;


            final String username = update.getCallbackQuery().getFrom().getFirstName();

            if(sessionData.getUsernames().get(username) != null) // no double voting
                return;

            sessionData.currentMessageCount++;



            final String answer = update.getCallbackQuery().getData().split(":")[1];



            sessionData.getUsernames().put(username, answer);

            StringBuilder detailsStringBuilder = new StringBuilder();

            for(String key : sessionData.getUsernames().keySet()) {

                detailsStringBuilder.append(key +
                        " has voted " + sessionData.getUsernames().get(key));

            }

            messageBuilder.editMessage(DEFAULT_FOOD_MESSAGE.replace("%count%",
                            String.valueOf(sessionData.currentMessageCount)).replace(
                                    "%foodname%", sessionData.currentFoodData.getName()).replace("%details%",
                            detailsStringBuilder.toString()),
                    update.getCallbackQuery().getMessage().getMessageId(),
                    update.getCallbackQuery().getMessage().getChatId(), telegramBot, sessionData);

            switch(answer) {
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
