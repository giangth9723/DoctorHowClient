package project.fpt.edu.vn.registerscreen.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import project.fpt.edu.vn.registerscreen.R;

public class RegisterActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener DateListener;
    ImageButton ImgBtn;
    TextView tvbirth;

    EditText fname, lname, uname, pass, cpass, phone, birthdate;
    String sfname, slname, suname, spass, scpass, sphone, sbirthdate;
    Button btnreg;
    RadioGroup rg;

    private static final String TAG = "ActivityRegister";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname = (EditText) findViewById(R.id.Etfname);
        lname = (EditText) findViewById(R.id.Etlname);
        uname = (EditText) findViewById(R.id.Etuser);
        pass = (EditText) findViewById(R.id.Etpass);
        cpass = (EditText) findViewById(R.id.Etcpass);
        phone = (EditText) findViewById(R.id.Etphone);
        btnreg = (Button) findViewById(R.id.Btnreg);
        birthdate = (EditText) findViewById(R.id.Etbirthday);
        rg = (RadioGroup) findViewById(R.id.Radgender);

        ImgBtn = (ImageButton)findViewById(R.id.ImgBtnBack);

        DatePicker();

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        ImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void inialize(){
        sfname = fname.getText().toString().trim();
        slname = lname.getText().toString().trim();
        suname = uname.getText().toString().trim();
        spass = pass.getText().toString().trim();
        scpass = cpass.getText().toString().trim();
        sphone = phone.getText().toString().trim();
        sbirthdate = birthdate.getText().toString().trim();
    }

    public void register(){
        inialize();
        if(!validate()){
            Toast.makeText(this, "Đăng kí không thành công", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
        }
    }
    public void onRegisterSuccess(String firstname){

    }

    public boolean validate(){
        boolean valid = true;
        if(sfname.isEmpty()){
            fname.setError("Hãy nhập tên");
            valid = false;
        }
        if(slname.isEmpty()){
            lname.setError("Hãy nhập họ");
            valid = false;
        }
        if(suname.isEmpty()){
            uname.setError("Hãy nhập tên tài khoản");
            valid = false;
        }
        if(spass.isEmpty()){
            pass.setError("Hãy nhập mật khẩu");
            valid = false;
        }
        if(scpass.isEmpty() || !spass.equals(scpass)){
            cpass.setError("Hãy nhập mật khẩu giống nhau");
            cpass.setText(null);
            valid = false;
        }
        if(sphone.isEmpty() || !Patterns.PHONE.matcher(sphone).matches()){
            phone.setError("Hãy nhập đúng số điện thoại");
            //phone.setText(null);
            valid = false;
        }
        if(sbirthdate.equals("")){
            Toast.makeText(getApplicationContext(), "Hãy nhập ngày tháng năm sinh", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(rg.getCheckedRadioButtonId()==-1){
            Toast.makeText(getApplicationContext(), "Hãy chọn giới tính", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    public void DatePicker(){

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        DateListener = new DatePickerDialog.OnDateSetListener() {
            String sday, smonth, birth;
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG,"onDateSet: date: mm/dd/yyy: " + month + "/" + day + "/" + year);
                int dday = day;
                if(dday < 10){
                    sday = "0" + dday;
                }else{
                    sday = dday + "";
                }
                int dmonth = month;
                if(dmonth < 10){
                    smonth = "0" + dmonth;
                }else{
                    smonth = dmonth + "";
                }
                birth = sday + "/" + smonth + "/" + year;
                birthdate.setText(birth);
            }
        };
    }

    public void hideKey(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
