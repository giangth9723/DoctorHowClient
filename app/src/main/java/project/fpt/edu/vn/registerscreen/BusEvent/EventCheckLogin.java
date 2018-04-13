package project.fpt.edu.vn.registerscreen.BusEvent;

/**
 * Created by GIang on 3/7/2018.
 */

public class EventCheckLogin {
    private boolean ket_qua;
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

    public EventCheckLogin(boolean ket_qua, int patient_id, String username, String password, String patient_name, String profile_picture, String gender, String birthday, String phone_number, String address, float height, float weight, String id_number) {
        this.ket_qua = ket_qua;
        this.patient_id = patient_id;
        this.username = username;
        this.password = password;
        this.patient_name = patient_name;
        this.profile_picture = profile_picture;
        this.gender = gender;
        this.birthday = birthday;
        this.phone_number = phone_number;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.id_number = id_number;
    }

    public boolean isKet_qua() {
        return ket_qua;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public String getId_number() {
        return id_number;
    }
}
