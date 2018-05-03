package project.fpt.edu.vn.registerscreen.BusEvent;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Model.Emr_dermatology;

/**
 * Created by GIang on 4/29/2018.
 */

public class EventGetEmrDermatology {
    private ArrayList<Emr_dermatology> emr_dermatologyArrayList;

    public EventGetEmrDermatology(ArrayList<Emr_dermatology> emr_dermatologyArrayList) {
        this.emr_dermatologyArrayList = emr_dermatologyArrayList;
    }

    public ArrayList<Emr_dermatology> getEmr_dermatologyArrayList() {
        return emr_dermatologyArrayList;
    }

    public void setEmr_dermatologyArrayList(ArrayList<Emr_dermatology> emr_dermatologyArrayList) {
        this.emr_dermatologyArrayList = emr_dermatologyArrayList;
    }
}
