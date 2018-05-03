package project.fpt.edu.vn.registerscreen.BusEvent;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Model.Doctor;

/**
 * Created by GIang on 3/8/2018.
 */

public class EventReloadDoctorOnline2 {
    ArrayList<Doctor> arrayDoctor;

    public EventReloadDoctorOnline2(ArrayList<Doctor> arrayDoctor) {
        this.arrayDoctor = arrayDoctor;
    }
    public ArrayList<Doctor> getArrayDoctor() {
        return arrayDoctor;
    }
}
