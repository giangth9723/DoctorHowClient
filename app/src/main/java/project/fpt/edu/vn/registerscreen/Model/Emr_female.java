package project.fpt.edu.vn.registerscreen.Model;

import java.io.Serializable;

/**
 * Created by GIang on 4/28/2018.
 */

public class Emr_female implements Serializable {
    private int emr_id;
    private int patient_id;
    private int doctor_id;
    private String doctor_name;
    private String patient_name;
    private String emr_date;
    private String pathology;
    private String history_disease;
    private String family;
    private int periods_year;
    private int periods_age;
    private String periods_nature;
    private int periods_cycle;
    private int periods_noofdate;
    private String periods_amount;
    private String periods_lastdate;
    private int stomachache;
    private int marriage_year;
    private int marriage_age;
    private int periods_endyear;
    private int periods_endage;
    private String periods_treatment;
    private float vascular;
    private float temperature;
    private float blood_pressure;
    private float breathing;
    private float weight;
    private String body;
    private String cyclic;
    private String respiratory;
    private String digest;
    private String nerve;
    private String bone;
    private String kidney;
    private String others_o;
    private String secondary_signs;
    private String big_lips;
    private String baby_lips;
    private String clitoris;
    private String vulva;
    private String hymen;
    private String perineal;
    private String vagina;
    private String cervical;
    private String the_uterus;
    private String extra;
    private String douglas;
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

    public Emr_female(int emr_id, int patient_id, int doctor_id, String doctor_name, String patient_name, String emr_date, String pathology, String history_disease, String family, int periods_year, int periods_age, String periods_nature, int periods_cycle, int periods_noofdate, String periods_amount, String periods_lastdate, int stomachache, int marriage_year, int marriage_age, int periods_endyear, int periods_endage, String periods_treatment, float vascular, float temperature, float blood_pressure, float breathing, float weight, String body, String cyclic, String respiratory, String digest, String nerve, String bone, String kidney, String others_o, String secondary_signs, String big_lips, String baby_lips, String clitoris, String vulva, String hymen, String perineal, String vagina, String cervical, String the_uterus, String extra, String douglas, String clinical_test, String summary, String main_disease, String secondary_disease, String distinguish, String prognosis, String treatment_direction_dt, String pathology_process, String labs_result, String treatments, String patient_status, String treatment_direction_s, String file_pic, int emr_status) {
        this.emr_id = emr_id;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.patient_name = patient_name;
        this.emr_date = emr_date;
        this.pathology = pathology;
        this.history_disease = history_disease;
        this.family = family;
        this.periods_year = periods_year;
        this.periods_age = periods_age;
        this.periods_nature = periods_nature;
        this.periods_cycle = periods_cycle;
        this.periods_noofdate = periods_noofdate;
        this.periods_amount = periods_amount;
        this.periods_lastdate = periods_lastdate;
        this.stomachache = stomachache;
        this.marriage_year = marriage_year;
        this.marriage_age = marriage_age;
        this.periods_endyear = periods_endyear;
        this.periods_endage = periods_endage;
        this.periods_treatment = periods_treatment;
        this.vascular = vascular;
        this.temperature = temperature;
        this.blood_pressure = blood_pressure;
        this.breathing = breathing;
        this.weight = weight;
        this.body = body;
        this.cyclic = cyclic;
        this.respiratory = respiratory;
        this.digest = digest;
        this.nerve = nerve;
        this.bone = bone;
        this.kidney = kidney;
        this.others_o = others_o;
        this.secondary_signs = secondary_signs;
        this.big_lips = big_lips;
        this.baby_lips = baby_lips;
        this.clitoris = clitoris;
        this.vulva = vulva;
        this.hymen = hymen;
        this.perineal = perineal;
        this.vagina = vagina;
        this.cervical = cervical;
        this.the_uterus = the_uterus;
        this.extra = extra;
        this.douglas = douglas;
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

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public int getPeriods_year() {
        return periods_year;
    }

    public void setPeriods_year(int periods_year) {
        this.periods_year = periods_year;
    }

    public int getPeriods_age() {
        return periods_age;
    }

    public void setPeriods_age(int periods_age) {
        this.periods_age = periods_age;
    }

    public String getPeriods_nature() {
        return periods_nature;
    }

    public void setPeriods_nature(String periods_nature) {
        this.periods_nature = periods_nature;
    }

    public int getPeriods_cycle() {
        return periods_cycle;
    }

    public void setPeriods_cycle(int periods_cycle) {
        this.periods_cycle = periods_cycle;
    }

    public int getPeriods_noofdate() {
        return periods_noofdate;
    }

    public void setPeriods_noofdate(int periods_noofdate) {
        this.periods_noofdate = periods_noofdate;
    }

    public String getPeriods_amount() {
        return periods_amount;
    }

    public void setPeriods_amount(String periods_amount) {
        this.periods_amount = periods_amount;
    }

    public String getPeriods_lastdate() {
        return periods_lastdate;
    }

    public void setPeriods_lastdate(String periods_lastdate) {
        this.periods_lastdate = periods_lastdate;
    }

    public int getStomachache() {
        return stomachache;
    }

    public void setStomachache(int stomachache) {
        this.stomachache = stomachache;
    }

    public int getMarriage_year() {
        return marriage_year;
    }

    public void setMarriage_year(int marriage_year) {
        this.marriage_year = marriage_year;
    }

    public int getMarriage_age() {
        return marriage_age;
    }

    public void setMarriage_age(int marriage_age) {
        this.marriage_age = marriage_age;
    }

    public int getPeriods_endyear() {
        return periods_endyear;
    }

    public void setPeriods_endyear(int periods_endyear) {
        this.periods_endyear = periods_endyear;
    }

    public int getPeriods_endage() {
        return periods_endage;
    }

    public void setPeriods_endage(int periods_endage) {
        this.periods_endage = periods_endage;
    }

    public String getPeriods_treatment() {
        return periods_treatment;
    }

    public void setPeriods_treatment(String periods_treatment) {
        this.periods_treatment = periods_treatment;
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

    public String getNerve() {
        return nerve;
    }

    public void setNerve(String nerve) {
        this.nerve = nerve;
    }

    public String getBone() {
        return bone;
    }

    public void setBone(String bone) {
        this.bone = bone;
    }

    public String getKidney() {
        return kidney;
    }

    public void setKidney(String kidney) {
        this.kidney = kidney;
    }

    public String getOthers_o() {
        return others_o;
    }

    public void setOthers_o(String others_o) {
        this.others_o = others_o;
    }

    public String getSecondary_signs() {
        return secondary_signs;
    }

    public void setSecondary_signs(String secondary_signs) {
        this.secondary_signs = secondary_signs;
    }

    public String getBig_lips() {
        return big_lips;
    }

    public void setBig_lips(String big_lips) {
        this.big_lips = big_lips;
    }

    public String getBaby_lips() {
        return baby_lips;
    }

    public void setBaby_lips(String baby_lips) {
        this.baby_lips = baby_lips;
    }

    public String getClitoris() {
        return clitoris;
    }

    public void setClitoris(String clitoris) {
        this.clitoris = clitoris;
    }

    public String getVulva() {
        return vulva;
    }

    public void setVulva(String vulva) {
        this.vulva = vulva;
    }

    public String getHymen() {
        return hymen;
    }

    public void setHymen(String hymen) {
        this.hymen = hymen;
    }

    public String getPerineal() {
        return perineal;
    }

    public void setPerineal(String perineal) {
        this.perineal = perineal;
    }

    public String getVagina() {
        return vagina;
    }

    public void setVagina(String vagina) {
        this.vagina = vagina;
    }

    public String getCervical() {
        return cervical;
    }

    public void setCervical(String cervical) {
        this.cervical = cervical;
    }

    public String getThe_uterus() {
        return the_uterus;
    }

    public void setThe_uterus(String the_uterus) {
        this.the_uterus = the_uterus;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getDouglas() {
        return douglas;
    }

    public void setDouglas(String douglas) {
        this.douglas = douglas;
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
