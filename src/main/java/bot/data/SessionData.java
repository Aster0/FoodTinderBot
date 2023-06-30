package bot.data;

import java.util.ArrayList;
import java.util.List;

public class SessionData {



    private static List<SessionData> sessions;



    public int currentMessageId = 0;

    public int currentMessageCount = 0, currentMessageAcceptedCount = 0;

    public SessionData(int currentMessageId) {

        this.currentMessageId = currentMessageId;
    }


    private void resetCount() {
        currentMessageCount = 0;
        currentMessageAcceptedCount = 0;
    }

    public static void storeSession(int messageId) {

        SessionData sessionData = findSession(messageId);

        if(sessionData == null)
            sessions.add(new SessionData(messageId));
        else {
            sessionData.resetCount();
            sessionData.currentMessageId = messageId;
        }


    }

    public static SessionData findSession(int messageId) {

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
}
