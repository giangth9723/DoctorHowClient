package project.fpt.edu.vn.registerscreen;

import java.io.Serializable;

/**
 * Created by User on 2/6/2018.
 */

public class Doctor implements Serializable {
    public String Name;
    public boolean DoctorStatus;

    public Doctor(String name, boolean doctorStatus) {
        Name = name;
        DoctorStatus = doctorStatus;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isDoctorStatus() {
        return DoctorStatus;
    }

    public void setDoctorStatus(boolean doctorStatus) {
        DoctorStatus = doctorStatus;
    }
}
