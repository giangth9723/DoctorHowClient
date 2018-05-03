package project.fpt.edu.vn.registerscreen;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;

import project.fpt.edu.vn.registerscreen.Model.Doctor;
import project.fpt.edu.vn.registerscreen.Model.Patient;

/**
 * Created by User on 2/5/2018.
 */

public class Session {
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;
    Context ctx;
    Gson gson = new Gson();
    Patient patient;
    String sPatient;
    Doctor doctor_opp;
    String sDoctor_opp;
    public Patient getPatient(){
        sPatient = sharedPreferences.getString("jsonPatient","");
        patient = gson.fromJson(sPatient,Patient.class);
        return patient;
    }
    public void setPatient(Patient patient){
          this.patient = patient;
          String jsonPatient = gson.toJson(patient);
          editor.putString("jsonPatient",jsonPatient).commit();
    }
    public Doctor getDoctorOpp(){
        sDoctor_opp = sharedPreferences.getString("jsonDoctorOpp","");
        doctor_opp = gson.fromJson(sDoctor_opp,Doctor.class);
        return doctor_opp;
    }
    public void setDoctor_opp(Doctor doctor_opp){
        this.doctor_opp = doctor_opp;
        String jsonDoctorOpp = gson.toJson(doctor_opp);
        editor.putString("jsonDoctorOpp",jsonDoctorOpp).commit();
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
