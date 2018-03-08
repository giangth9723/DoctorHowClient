package project.fpt.edu.vn.registerscreen.Model;

import java.io.Serializable;

/**
 * Created by User on 2/6/2018.
 */

public class Doctor implements Serializable {
    private int doctorID;
    private String username;
    private String password;
    private String name;
    private int gender;
    private String clinic;
    private String degree;
    private String birthday;
    private String id_card;
    private String address;

    public Doctor(int doctorID, String username, String password, String name, int gender, String clinic, String degree, String birthday, String id_card, String address) {
        this.doctorID = doctorID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.clinic = clinic;
        this.degree = degree;
        this.birthday = birthday;
        this.id_card = id_card;
        this.address = address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
