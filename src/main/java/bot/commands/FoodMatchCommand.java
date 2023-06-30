package bot.commands;

import bot.data.SessionData;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import util.MessageBuilder;

public class FoodMatchCommand implements ICommand {


    private Message message;
    private MessageBuilder messageBuilder;

    @Override
    public void onCommandReceived(Update update, TelegramLongPollingBot telegramBot) {

        if(update.getCallbackQuery() != null) {

            System.out.println(update.getCallbackQuery().getFrom().getFirstName() + " MESSAGE ID!");
            SessionData sessionData = SessionData.findSession(update.getCallbackQuery().getMessage().getMessageId());



            if(sessionData.currentMessageCount == 2 ||
                    sessionData.getUsernames().contains(update.getCallbackQuery().getFrom().getFirstName()))
                return;

            sessionData.currentMessageCount++;

            System.out.println(sessionData.currentMessageId + " SESSION DATA");

            sessionData.getUsernames().add(update.getCallbackQuery().getFrom().getFirstName());

            switch(update.getCallbackQuery().getData()) {
                case "Yes": {
                    System.out.println("ok");
                    break;
                }
                default: {
                    System.out.println("not ok");
                    sessionData.currentMessageAcceptedCount++;

                    break;
                }

            }

            messageBuilder.editMessage(update.getCallbackQuery().getMessage().getMessageId(),
                    update.getCallbackQuery().getMessage().getChatId(), telegramBot, sessionData);

            return;
        }

        System.out.println(update.getMessage().getText());




        messageBuilder = new MessageBuilder("Chicken Wings \n⌚ 0/0 have answered",
                update, new InlineKeyboardButton("Yes"),
                new InlineKeyboardButton("No"));

        SendPhoto sendMessage = messageBuilder.build();











        try {
            message = telegramBot.execute(sendMessage);
            System.out.println(message.getMessageId());


            SessionData.storeSession(message.getMessageId());

//            EditMessageText editMessageText = new EditMessageText();
//            editMessageText.setChatId(sendMessage.getChatId());
//            editMessageText.setMessageId(message.getMessageId());
//            editMessageText.setText("HI");
//            editMessageText.setReplyMarkup((InlineKeyboardMarkup) sendMessage.getReplyMarkup());
//
//
//
//            telegramBot.execute(editMessageText);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
}
