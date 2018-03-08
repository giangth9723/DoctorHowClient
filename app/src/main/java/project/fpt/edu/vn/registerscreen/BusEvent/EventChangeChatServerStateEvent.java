package project.fpt.edu.vn.registerscreen.BusEvent;

/**
 * Created by GIang on 3/7/2018.
 */

public class EventChangeChatServerStateEvent {
    private String state;

    public EventChangeChatServerStateEvent(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public enum chatServerState {
        connectedToSocket,
        disconnectedFromSocket,
        flashConnectionIcon
    }
}
