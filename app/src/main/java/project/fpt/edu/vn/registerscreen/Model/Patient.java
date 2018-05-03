package project.fpt.edu.vn.registerscreen.Model;

import java.io.Serializable;

/**
 * Created by GIang on 4/19/2018.
 */

public class Patient implements Serializable {
    private int patient_id;
    private String username;
    private String password;
    private String patient_name;
    private String profile_picture;
    private String gender;
    private String birthday;
    private String phone_number;
    private float height;
    private float weight;
    private String id_number;
    private String address_number;
    private String address_street;
    private String address_distric;
    private String address_city;
    private String socket_id;
    private String online_status;

    public Patient() {
    }

    public Patient(int patient_id, String username, String password, String patient_name, String profile_picture, String gender, String birthday, String phone_number, float height, float weight, String id_number, String address_number, String address_street, String address_distric, String address_city, String socket_id, String online_status) {
        this.patient_id = patient_id;
        this.username = username;
        this.password = password;
        this.patient_name = patient_name;
        this.profile_picture = profile_picture;
        this.gender = gender;
        this.birthday = birthday;
        this.phone_number = phone_number;
        this.height = height;
        this.weight = weight;
        this.id_number = id_number;
        this.address_number = address_number;
        this.address_street = address_street;
        this.address_distric = address_distric;
        this.address_city = address_city;
        this.socket_id = socket_id;
        this.online_status = online_status;
    }

    public String getSocket_id() {
        return socket_id;
    }

    public void setSocket_id(String socket_id) {
        this.socket_id = socket_id;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
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

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getAddress_number() {
        return address_number;
    }

    public void setAddress_number(String address_number) {
        this.address_number = address_number;
    }

    public String getAddress_street() {
        return address_street;
    }

    public void setAddress_street(String address_street) {
        this.address_street = address_street;
    }

    public String getAddress_distric() {
        return address_distric;
    }

    public void setAddress_distric(String address_distric) {
        this.address_distric = address_distric;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }
}
