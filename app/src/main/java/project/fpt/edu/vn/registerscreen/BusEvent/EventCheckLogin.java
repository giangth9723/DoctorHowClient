package project.fpt.edu.vn.registerscreen.BusEvent;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Model.Patient;

/**
 * Created by GIang on 3/7/2018.
 */

public class EventCheckLogin {
    private Patient patient;
    private boolean check;

    public EventCheckLogin(Patient patient, boolean check) {
        this.patient = patient;
        this.check = check;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
