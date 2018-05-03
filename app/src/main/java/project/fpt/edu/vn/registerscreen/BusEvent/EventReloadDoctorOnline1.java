package project.fpt.edu.vn.registerscreen.BusEvent;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Model.Doctor;

/**
 * Created by GIang on 3/8/2018.
 */

public class EventReloadDoctorOnline1 {
    ArrayList<Doctor> arrayDoctor;

    public EventReloadDoctorOnline1(ArrayList<Doctor> arrayDoctor) {
        this.arrayDoctor = arrayDoctor;
    }
    public ArrayList<Doctor> getArrayDoctor() {
        return arrayDoctor;
    }
}
