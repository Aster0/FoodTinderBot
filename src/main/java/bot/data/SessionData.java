package bot.data;

import com.google.inject.internal.util.Lists;
import util.MessageBuilder;

import java.util.*;

public class SessionData {



    private static List<SessionData> sessions;

    private Map<String, String> usernames = new HashMap();



    public int currentMessageId = 0;
    public boolean sessionStarted;
    public long chatId = 0;

    public int currentMessageCount = 0, currentMessageAcceptedCount = 0;

    public FoodData currentFoodData;

    public List<FoodData> foodList;

    public SessionData(int currentMessageId, long chatId) {

        this.currentMessageId = currentMessageId;
        this.chatId = chatId;
    }


    private void resetCount() {
        currentMessageCount = 0;
        currentMessageAcceptedCount = 0;
        usernames.clear();
    }



    public FoodData chooseRandomFood(FoodData[] foodData) {

        FoodData tempFoodData = null;

        try
        {
            if(foodList == null) {
                foodList = Lists.newArrayList(foodData);
            }

            Random random = new Random();



            int randIndex = random.nextInt(foodList.size());


            tempFoodData = foodList.get(randIndex);

            foodList.remove(randIndex);




            this.currentFoodData = tempFoodData;
        }
        catch (IllegalArgumentException e) {

        }

        return tempFoodData;

    }

    public Map<String, String> getUsernames() {
        return usernames;
    }

    public static SessionData storeSession(int messageId, long chatId) {

        SessionData sessionData = findSessionByChatId(chatId);

        if(sessionData == null) {

            sessionData = new SessionData(messageId, chatId);

            sessions.add(sessionData);


        }
        else
        {
            sessionData.resetCount();
            sessionData.currentMessageId = messageId;
        }


        return sessionData;



    }

    public static void deleteSession(SessionData sessionData) {

        for(int i = 0; i < sessions.size(); i++) {

            if(sessions.get(i) == sessionData) {
                sessions.remove(i);
                break;
            }
        }

    }

    public static SessionData storeSession(long chatId) { // only for storing initial session

        SessionData sessionData = new SessionData(0, chatId);
        sessions.add(sessionData);

        return sessionData;

    }



    public static SessionData findSessionByMessageId(int messageId) {

        if(sessions == null)
            sessions = new ArrayList<>();


        SessionData returnedSession = null;

        for(SessionData session : sessions) {

            if(session.currentMessageId == messageId) {
                returnedSession = session;
                break;
            }


        }

        return returnedSession;


    }

    public static SessionData findSessionByChatId(long chatId) {

        if(sessions == null)
            sessions = new ArrayList<>();


        SessionData returnedSession = null;

        for(SessionData session : sessions) {

            if(session.chatId == chatId) {
                returnedSession = session;
                break;
            }


        }

        return returnedSession;


    }
}
