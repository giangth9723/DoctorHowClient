package project.fpt.edu.vn.registerscreen.BusEvent;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Model.DoctorOnline;

/**
 * Created by GIang on 3/8/2018.
 */

public class EventLoadDoctorOnline {
    ArrayList<DoctorOnline> arrayDoctorOnline;

    public EventLoadDoctorOnline(ArrayList<DoctorOnline> arrayDoctorOnline) {
        this.arrayDoctorOnline = arrayDoctorOnline;
    }

    public ArrayList<DoctorOnline> getArrayDoctorOnline() {
        return arrayDoctorOnline;
    }
}
