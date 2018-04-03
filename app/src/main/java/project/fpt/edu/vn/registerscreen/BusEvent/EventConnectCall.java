package project.fpt.edu.vn.registerscreen.BusEvent;

/**
 * Created by GIang on 3/30/2018.
 */

public class EventConnectCall {
    String sessionID;
    String token;

    public EventConnectCall(String sessionID, String token) {
        this.sessionID = sessionID;
        this.token = token;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getToken() {
        return token;
    }
}
