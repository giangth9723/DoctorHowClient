package project.fpt.edu.vn.registerscreen.Model;

import java.io.Serializable;

/**
 * Created by GIang on 4/28/2018.
 */

public class Emr_mental implements Serializable {
    private int emr_id;
    private int patient_id;
    private int doctor_id;
    private String patient_name;
    private String doctor_name;
    private String emr_date;
    private String pathology;
    private String history_disease;
    private String allergy;
    private String drug;
    private String alcohol;
    private String tobacco;
    private String pipe_tobacco;
    private String others_rf;
    private String family;
    private float vascular;
    private float temperature;
    private float blood_pressure;
    private float breathing;
    private float weight;
    private String body;
    private String cyclic;
    private String respiratory;
    private String digest;
    private String kidney;
    private String bone;
    private String ear_nose_throat;
    private String teeth;
    private String eye;
    private String endocrine;
    private String cranial_nerves;
    private String bottom_of_eye;
    private String motor;
    private String field_force;
    private String feel;
    private String reflex;
    private String general_expression;
    private String space;
    private String time;
    private String myself;
    private String affection;
    private String sense;
    private String form;
    private String content;
    private String spirit;
    private String instinct;
    private String mechanically;
    private String understandably;
    private String analytical;
    private String comprehensive;
    private String attention;
    private String clinical_test;
    private String summary;
    private String main_disease;
    private String secondary_disease;
    private String distinguish;
    private String prognosis;
    private String treatment_direction_dt;
    private String pathology_process;
    private String labs_result;
    private String treatments;
    private String patient_status;
    private String treatment_direction_s;
    private String file_pic;
    private int emr_status;

    public Emr_mental(int emr_id, int patient_id, int doctor_id, String patient_name, String doctor_name, String emr_date, String pathology, String history_disease, String allergy, String drug, String alcohol, String tobacco, String pipe_tobacco, String others_rf, String family, float vascular, float temperature, float blood_pressure, float breathing, float weight, String body, String cyclic, String respiratory, String digest, String kidney, String bone, String ear_nose_throat, String teeth, String eye, String endocrine, String cranial_nerves, String bottom_of_eye, String motor, String field_force, String feel, String reflex, String general_expression, String space, String time, String myself, String affection, String sense, String form, String content, String spirit, String instinct, String mechanically, String understandably, String analytical, String comprehensive, String attention, String clinical_test, String summary, String main_disease, String secondary_disease, String distinguish, String prognosis, String treatment_direction_dt, String pathology_process, String labs_result, String treatments, String patient_status, String treatment_direction_s, String file_pic, int emr_status) {
        this.emr_id = emr_id;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.patient_name = patient_name;
        this.doctor_name = doctor_name;
        this.emr_date = emr_date;
        this.pathology = pathology;
        this.history_disease = history_disease;
        this.allergy = allergy;
        this.drug = drug;
        this.alcohol = alcohol;
        this.tobacco = tobacco;
        this.pipe_tobacco = pipe_tobacco;
        this.others_rf = others_rf;
        this.family = family;
        this.vascular = vascular;
        this.temperature = temperature;
        this.blood_pressure = blood_pressure;
        this.breathing = breathing;
        this.weight = weight;
        this.body = body;
        this.cyclic = cyclic;
        this.respiratory = respiratory;
        this.digest = digest;
        this.kidney = kidney;
        this.bone = bone;
        this.ear_nose_throat = ear_nose_throat;
        this.teeth = teeth;
        this.eye = eye;
        this.endocrine = endocrine;
        this.cranial_nerves = cranial_nerves;
        this.bottom_of_eye = bottom_of_eye;
        this.motor = motor;
        this.field_force = field_force;
        this.feel = feel;
        this.reflex = reflex;
        this.general_expression = general_expression;
        this.space = space;
        this.time = time;
        this.myself = myself;
        this.affection = affection;
        this.sense = sense;
        this.form = form;
        this.content = content;
        this.spirit = spirit;
        this.instinct = instinct;
        this.mechanically = mechanically;
        this.understandably = understandably;
        this.analytical = analytical;
        this.comprehensive = comprehensive;
        this.attention = attention;
        this.clinical_test = clinical_test;
        this.summary = summary;
        this.main_disease = main_disease;
        this.secondary_disease = secondary_disease;
        this.distinguish = distinguish;
        this.prognosis = prognosis;
        this.treatment_direction_dt = treatment_direction_dt;
        this.pathology_process = pathology_process;
        this.labs_result = labs_result;
        this.treatments = treatments;
        this.patient_status = patient_status;
        this.treatment_direction_s = treatment_direction_s;
        this.file_pic = file_pic;
        this.emr_status = emr_status;
    }

    public int getEmr_id() {
        return emr_id;
    }

    public void setEmr_id(int emr_id) {
        this.emr_id = emr_id;
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

    public String getEmr_date() {
        return emr_date;
    }

    public void setEmr_date(String emr_date) {
        this.emr_date = emr_date;
    }

    public String getPathology() {
        return pathology;
    }

    public void setPathology(String pathology) {
        this.pathology = pathology;
    }

    public String getHistory_disease() {
        return history_disease;
    }

    public void setHistory_disease(String history_disease) {
        this.history_disease = history_disease;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public String getTobacco() {
        return tobacco;
    }

    public void setTobacco(String tobacco) {
        this.tobacco = tobacco;
    }

    public String getPipe_tobacco() {
        return pipe_tobacco;
    }

    public void setPipe_tobacco(String pipe_tobacco) {
        this.pipe_tobacco = pipe_tobacco;
    }

    public String getOthers_rf() {
        return others_rf;
    }

    public void setOthers_rf(String others_rf) {
        this.others_rf = others_rf;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public float getVascular() {
        return vascular;
    }

    public void setVascular(float vascular) {
        this.vascular = vascular;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getBlood_pressure() {
        return blood_pressure;
    }

    public void setBlood_pressure(float blood_pressure) {
        this.blood_pressure = blood_pressure;
    }

    public float getBreathing() {
        return breathing;
    }

    public void setBreathing(float breathing) {
        this.breathing = breathing;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCyclic() {
        return cyclic;
    }

    public void setCyclic(String cyclic) {
        this.cyclic = cyclic;
    }

    public String getRespiratory() {
        return respiratory;
    }

    public void setRespiratory(String respiratory) {
        this.respiratory = respiratory;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getKidney() {
        return kidney;
    }

    public void setKidney(String kidney) {
        this.kidney = kidney;
    }

    public String getBone() {
        return bone;
    }

    public void setBone(String bone) {
        this.bone = bone;
    }

    public String getEar_nose_throat() {
        return ear_nose_throat;
    }

    public void setEar_nose_throat(String ear_nose_throat) {
        this.ear_nose_throat = ear_nose_throat;
    }

    public String getTeeth() {
        return teeth;
    }

    public void setTeeth(String teeth) {
        this.teeth = teeth;
    }

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getEndocrine() {
        return endocrine;
    }

    public void setEndocrine(String endocrine) {
        this.endocrine = endocrine;
    }

    public String getCranial_nerves() {
        return cranial_nerves;
    }

    public void setCranial_nerves(String cranial_nerves) {
        this.cranial_nerves = cranial_nerves;
    }

    public String getBottom_of_eye() {
        return bottom_of_eye;
    }

    public void setBottom_of_eye(String bottom_of_eye) {
        this.bottom_of_eye = bottom_of_eye;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getField_force() {
        return field_force;
    }

    public void setField_force(String field_force) {
        this.field_force = field_force;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public String getReflex() {
        return reflex;
    }

    public void setReflex(String reflex) {
        this.reflex = reflex;
    }

    public String getGeneral_expression() {
        return general_expression;
    }

    public void setGeneral_expression(String general_expression) {
        this.general_expression = general_expression;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMyself() {
        return myself;
    }

    public void setMyself(String myself) {
        this.myself = myself;
    }

    public String getAffection() {
        return affection;
    }

    public void setAffection(String affection) {
        this.affection = affection;
    }

    public String getSense() {
        return sense;
    }

    public void setSense(String sense) {
        this.sense = sense;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpirit() {
        return spirit;
    }

    public void setSpirit(String spirit) {
        this.spirit = spirit;
    }

    public String getInstinct() {
        return instinct;
    }

    public void setInstinct(String instinct) {
        this.instinct = instinct;
    }

    public String getMechanically() {
        return mechanically;
    }

    public void setMechanically(String mechanically) {
        this.mechanically = mechanically;
    }

    public String getUnderstandably() {
        return understandably;
    }

    public void setUnderstandably(String understandably) {
        this.understandably = understandably;
    }

    public String getAnalytical() {
        return analytical;
    }

    public void setAnalytical(String analytical) {
        this.analytical = analytical;
    }

    public String getComprehensive() {
        return comprehensive;
    }

    public void setComprehensive(String comprehensive) {
        this.comprehensive = comprehensive;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getClinical_test() {
        return clinical_test;
    }

    public void setClinical_test(String clinical_test) {
        this.clinical_test = clinical_test;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMain_disease() {
        return main_disease;
    }

    public void setMain_disease(String main_disease) {
        this.main_disease = main_disease;
    }

    public String getSecondary_disease() {
        return secondary_disease;
    }

    public void setSecondary_disease(String secondary_disease) {
        this.secondary_disease = secondary_disease;
    }

    public String getDistinguish() {
        return distinguish;
    }

    public void setDistinguish(String distinguish) {
        this.distinguish = distinguish;
    }

    public String getPrognosis() {
        return prognosis;
    }

    public void setPrognosis(String prognosis) {
        this.prognosis = prognosis;
    }

    public String getTreatment_direction_dt() {
        return treatment_direction_dt;
    }

    public void setTreatment_direction_dt(String treatment_direction_dt) {
        this.treatment_direction_dt = treatment_direction_dt;
    }

    public String getPathology_process() {
        return pathology_process;
    }

    public void setPathology_process(String pathology_process) {
        this.pathology_process = pathology_process;
    }

    public String getLabs_result() {
        return labs_result;
    }

    public void setLabs_result(String labs_result) {
        this.labs_result = labs_result;
    }

    public String getTreatments() {
        return treatments;
    }

    public void setTreatments(String treatments) {
        this.treatments = treatments;
    }

    public String getPatient_status() {
        return patient_status;
    }

    public void setPatient_status(String patient_status) {
        this.patient_status = patient_status;
    }

    public String getTreatment_direction_s() {
        return treatment_direction_s;
    }

    public void setTreatment_direction_s(String treatment_direction_s) {
        this.treatment_direction_s = treatment_direction_s;
    }

    public String getFile_pic() {
        return file_pic;
    }

    public void setFile_pic(String file_pic) {
        this.file_pic = file_pic;
    }

    public int getEmr_status() {
        return emr_status;
    }

    public void setEmr_status(int emr_status) {
        this.emr_status = emr_status;
    }
}
