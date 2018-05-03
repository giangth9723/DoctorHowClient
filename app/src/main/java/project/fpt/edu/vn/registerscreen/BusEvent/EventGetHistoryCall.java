package project.fpt.edu.vn.registerscreen.BusEvent;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Model.HistoryCall;

/**
 * Created by GIang on 5/1/2018.
 */

public class EventGetHistoryCall {
    ArrayList<HistoryCall> historyCallArrayList;

    public EventGetHistoryCall(ArrayList<HistoryCall> historyCallArrayList) {
        this.historyCallArrayList = historyCallArrayList;
    }

    public ArrayList<HistoryCall> getHistoryCallArrayList() {
        return historyCallArrayList;
    }

    public void setHistoryCallArrayList(ArrayList<HistoryCall> historyCallArrayList) {
        this.historyCallArrayList = historyCallArrayList;
    }
}
