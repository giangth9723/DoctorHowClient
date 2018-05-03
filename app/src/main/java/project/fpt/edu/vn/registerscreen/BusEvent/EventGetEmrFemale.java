package project.fpt.edu.vn.registerscreen.BusEvent;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Model.Emr_female;
import project.fpt.edu.vn.registerscreen.Model.Emr_female;

/**
 * Created by GIang on 4/29/2018.
 */

public class EventGetEmrFemale {
    private ArrayList<Emr_female> emr_femaleArrayList;

    public EventGetEmrFemale(ArrayList<Emr_female> emr_femaleArrayList) {
        this.emr_femaleArrayList = emr_femaleArrayList;
    }

    public ArrayList<Emr_female> getEmr_femaleArrayList() {
        return emr_femaleArrayList;
    }

    public void setEmr_femaleArrayList(ArrayList<Emr_female> emr_femaleArrayList) {
        this.emr_femaleArrayList = emr_femaleArrayList;
    }
}
