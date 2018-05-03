package project.fpt.edu.vn.registerscreen.BusEvent;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Model.Emr_mental;
import project.fpt.edu.vn.registerscreen.Model.Emr_mental;

/**
 * Created by GIang on 4/29/2018.
 */

public class EventGetEmrMental {
    private ArrayList<Emr_mental> emr_mentalArrayList;

    public EventGetEmrMental(ArrayList<Emr_mental> emr_mentalArrayList) {
        this.emr_mentalArrayList = emr_mentalArrayList;
    }

    public ArrayList<Emr_mental> getEmr_mentalArrayList() {
        return emr_mentalArrayList;
    }

    public void setEmr_mentalArrayList(ArrayList<Emr_mental> emr_mentalArrayList) {
        this.emr_mentalArrayList = emr_mentalArrayList;
    }
}
