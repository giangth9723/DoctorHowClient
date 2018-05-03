package project.fpt.edu.vn.registerscreen.Model;

import java.io.Serializable;

/**
 * Created by User on 2/6/2018.
 */

public class Doctor implements Serializable {
    private int doctor_id;
    private String username;
    private String password;
    private String doctor_name;
    private String profile_picture;
    private String gender;
    private String clinic;
    private String degree;
    private String birthday;
    private String phone_number;
    private String id_number;
    private String address_number;
    private String address_street;
    private String address_distric;
    private String address_city;
    private String description;
    private String socket_id;
    private String online_status;
    public Doctor() {
    }

    public Doctor(int doctor_id, String username, String password, String doctor_name, String profile_picture, String gender, String clinic, String degree, String birthday, String phone_number, String id_number, String address_number, String address_street, String address_distric, String address_city, String description, String socket_id, String online_status) {
        this.doctor_id = doctor_id;
        this.username = username;
        this.password = password;
        this.doctor_name = doctor_name;
        this.profile_picture = profile_picture;
        this.gender = gender;
        this.clinic = clinic;
        this.degree = degree;
        this.birthday = birthday;
        this.phone_number = phone_number;
        this.id_number = id_number;
        this.address_number = address_number;
        this.address_street = address_street;
        this.address_distric = address_distric;
        this.address_city = address_city;
        this.description = description;
        this.socket_id = socket_id;
        this.online_status = online_status;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
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

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
