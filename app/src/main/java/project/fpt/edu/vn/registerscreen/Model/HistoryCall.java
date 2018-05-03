package project.fpt.edu.vn.registerscreen.Model;

import java.io.Serializable;

/**
 * Created by User on 4/2/2018.
 */

public class HistoryCall implements Serializable {
    private int call_id;
    private String day;
    private String start_time;
    private String end_time;
    private String duration;
    private int patient_id;
    private int doctor_id;
    private String doctor_name;
    private String patient_name;
    private String emr_type;
    private int emr_id;
    private String emr_status;

    public HistoryCall(int call_id, String day, String start_time, String end_time, String duration, int patient_id, int doctor_id, String doctor_name, String patient_name, String emr_type, int emr_id, String emr_status) {
        this.call_id = call_id;
        this.day = day;
        this.start_time = start_time;
        this.end_time = end_time;
        this.duration = duration;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.patient_name = patient_name;
        this.emr_type = emr_type;
        this.emr_id = emr_id;
        this.emr_status = emr_status;
    }

    public int getCall_id() {
        return call_id;
    }

    public void setCall_id(int call_id) {
        this.call_id = call_id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getEmr_type() {
        return emr_type;
    }

    public void setEmr_type(String emr_type) {
        this.emr_type = emr_type;
    }

    public int getEmr_id() {
        return emr_id;
    }

    public void setEmr_id(int emr_id) {
        this.emr_id = emr_id;
    }

    public String getEmr_status() {
        return emr_status;
    }

    public void setEmr_status(String emr_status) {
        this.emr_status = emr_status;
    }
}
