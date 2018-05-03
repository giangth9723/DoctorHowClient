package project.fpt.edu.vn.registerscreen.Model;

import java.io.Serializable;

/**
 * Created by User on 3/1/2018.
 */

public class Appointment implements Serializable {
    private int appointment_id;
    private String date;
    private int patient_id;
    private int doctor_id;
    private String patient_name;
    private String doctor_name;
    private String time;
    private String status;
    private String content;
    private String emr_type;

    public Appointment() {
    }

    public Appointment(int appointment_id, String date, int patient_id, int doctor_id, String patient_name, String doctor_name, String time, String status, String content, String emr_type) {
        this.appointment_id = appointment_id;
        this.date = date;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.time = time;
        this.status = status;
        this.content = content;
        this.emr_type = emr_type;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmr_type() {
        return emr_type;
    }

    public void setEmr_type(String emr_type) {
        this.emr_type = emr_type;
    }
}
