package project.fpt.edu.vn.registerscreen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 2/5/2018.
 */

public class Session {
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    Context ctx;
    private int patient_id;
    private String username;
    private String password;
    private String patient_name;
    private String profile_picture;
    private String gender;
    private String birthday;
    private String phone_number;
    private String address;
    private float height;
    private float weight;
    private String id_number;


    public String getUser_name() {
        username = sharedPreferences.getString("username","");
        return username;
    }
    public String getPassword(){
        password = sharedPreferences.getString("password","");
        return password;
    }
    public int getPatient_id() {
        patient_id = sharedPreferences.getInt("patient_id",0);
        return patient_id;
    }
    public String getPatient_name(){
        patient_name = sharedPreferences.getString("patient_name","");
        return patient_name;
    }
    public String getProfile_picture() {
        profile_picture = sharedPreferences.getString("profile_picture","");
        return profile_picture;
    }
    public String getGender(){
        gender = sharedPreferences.getString("gender","");
        return gender;
    }
    public String getBirthday() {
        birthday = sharedPreferences.getString("birthday","");
        return birthday;
    }
    public String getPhone_number(){
        password = sharedPreferences.getString("phone_number","");
        return phone_number;
    }

    public String getAddress() {
        address = sharedPreferences.getString("address","");
        return address;
    }
    public double getHeight(){
        height = sharedPreferences.getFloat("height",0);
        return height;
    }

    public double getWeight() {
        weight = sharedPreferences.getFloat("weight",0);
        return weight;
    }
    public String getId_number(){
        password = sharedPreferences.getString("id_number","");
        return id_number;
    }

    public void setAddress(String address) {
        this.address = address;
        editor.putString("address", address).commit();
    }
    public void setHeight(float height) {
        this.height = height;
        editor.putFloat("height", height).commit();
    }
    public void setWeight(float weight) {
        this.weight = weight;
        editor.putFloat("weight", weight).commit();
    }
    public void setId_number(String id_number) {
        this.id_number = id_number;
        editor.putString("id_number", id_number).commit();
    }
    public void setUser_name(String username) {
        this.username = username;
        editor.putString("username", username).commit();
    }
    public void setPassword(String password) {
        this.password = password;
        editor.putString("password", password).commit();
    }
    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
        editor.putInt("patient_id", patient_id).commit();
    }
    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
        editor.putString("patient_name", patient_name).commit();
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
        editor.putString("profile_picture", profile_picture).commit();
    }
    public void setGender(String gender) {
        this.gender = gender;
        editor.putString("gender", gender).commit();
    }

    public void setBitrhday(String birthday) {
        this.birthday = birthday;
        editor.putString("birthday", birthday).commit();
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        editor.putString("phone_number", phone_number).commit();
    }
    public Session(Context ctx){
        this.ctx = ctx;
        sharedPreferences = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean loggedIn){
        editor.remove("LoggedInMode");
        editor.putBoolean("LoggedInMode", loggedIn);
        editor.commit();
    }

    public boolean LoggedIn(){
        return sharedPreferences.getBoolean("LoggedInMode", false);
    }
}
