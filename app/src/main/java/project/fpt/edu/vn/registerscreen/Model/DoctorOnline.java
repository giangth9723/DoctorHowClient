package project.fpt.edu.vn.registerscreen.Model;

/**
 * Created by GIang on 3/8/2018.
 */

public class DoctorOnline {
    int doctorID;
    String username;
    String status;
    String socketID;

    public DoctorOnline(int doctorID, String username, String status, String socketID) {
        this.doctorID = doctorID;
        this.username = username;
        this.status = status;
        this.socketID = socketID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSocketID() {
        return socketID;
    }

    public void setSocketID(String socketID) {
        this.socketID = socketID;
    }
}
