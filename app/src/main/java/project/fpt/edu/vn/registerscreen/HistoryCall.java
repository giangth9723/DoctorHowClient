package project.fpt.edu.vn.registerscreen;

import java.io.Serializable;

/**
 * Created by User on 2/7/2018.
 */

public class HistoryCall implements Serializable {
    public String historyName, historyDate, historyTime, historyMoney;

    public HistoryCall(String historyName, String historyDate, String historyTime, String historyMoney) {
        this.historyName = historyName;
        this.historyDate = historyDate;
        this.historyTime = historyTime;
        this.historyMoney = historyMoney;
    }

    public String getHistoryName() {
        return historyName;
    }

    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }

    public String getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(String historyDate) {
        this.historyDate = historyDate;
    }

    public String getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(String historyTime) {
        this.historyTime = historyTime;
    }

    public String getHistoryMoney() {
        return historyMoney;
    }

    public void setHistoryMoney(String historyMoney) {
        this.historyMoney = historyMoney;
    }
}
