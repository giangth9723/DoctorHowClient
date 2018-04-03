package project.fpt.edu.vn.registerscreen.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventCheckRegister;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;

public class RegisterActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener DateListener;
    ImageButton ImgBtn;
    TextView tvbirth;

    EditText fname, lname, uname, pass, cpass, phone, birthdate ,address,idnumber ;
    String sfname, slname, suname, spass, scpass, sphone, sbirthdate, saddress,sidnumber;
    Button btnreg;
    RadioGroup rg;
    Boolean mIsBound;
    SocketApplication socketApplication;
    SocketServiceProvider mBoundService;
    protected ServiceConnection socketConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("serviceConnection", "connected");
            mBoundService = ((SocketServiceProvider.LocalBinder) service).getService();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            mIsBound = false;
        }
    };
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
        address = (EditText)findViewById(R.id.Etaddress);
        idnumber = (EditText)findViewById(R.id.Etidnumber);

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
        socketApplication = (SocketApplication) getApplication();
        Log.d("Check", "Login created");
        if (socketApplication.getSocket() != null) {
            Log.d("Socket", " is not null");
            startService(new Intent(getBaseContext(), SocketServiceProvider.class));
            doBindService();
        } else {
        }
    }
    private void doBindService() {
        bindService(new Intent(RegisterActivity.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    public void inialize(){
        sfname = fname.getText().toString().trim();
        slname = lname.getText().toString().trim();
        suname = uname.getText().toString().trim();
        spass = pass.getText().toString().trim();
        scpass = cpass.getText().toString().trim();
        sphone = phone.getText().toString().trim();
        sbirthdate = birthdate.getText().toString().trim();
        saddress = address.getText().toString().trim();
        sidnumber = idnumber.getText().toString().trim();
    }

    public void register(){
        inialize();
        if(validate()) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("Name", slname + " " + sfname);
                obj.put("Username", suname);
                obj.put("Password", spass);
                obj.put("PhoneNumber", sphone);
                obj.put("Address", saddress);
                obj.put("ID_Number", sidnumber);
                if (rg.getCheckedRadioButtonId() == 0) {
                    obj.put("Gender", 1);
                } else {
                    obj.put("Gender", 0);
                }
                obj.put("Birthday", sbirthdate);
                socketApplication.getSocket().emit("patient_register", obj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{

        }

    }
    public void onRegisterSuccess(String firstname){

    }

    public boolean validate(){
        boolean valid = true;
        if(sfname.isEmpty()){
            fname.setError("Hãy nhập tên");
            valid = false;
        }else
        if(slname.isEmpty()){
            lname.setError("Hãy nhập họ");
            valid = false;
        }else
        if(suname.isEmpty()){
            uname.setError("Hãy nhập tên tài khoản");
            valid = false;
        }else
        if(spass.isEmpty()){
            pass.setError("Hãy nhập mật khẩu");
            valid = false;
        }else
        if(scpass.isEmpty() || !spass.equals(scpass)){
            cpass.setError("Hãy nhập mật khẩu giống nhau");
            cpass.setText(null);
            valid = false;
        }else
        if(sphone.isEmpty() || !Patterns.PHONE.matcher(sphone).matches()){
            phone.setError("Hãy nhập đúng số điện thoại");
            //phone.setText(null);
            valid = false;
        }else
        if(saddress.isEmpty()){
            address.setError("Hãy nhập địa chỉ");
            valid = false;
        }else
        if(sidnumber.isEmpty()){
            idnumber.setError("Hãy nhập số chứng minh thư");
            valid = false;
        }else
        if(sbirthdate.equals("")){
            Toast.makeText(getApplicationContext(), "Hãy nhập ngày tháng năm sinh", Toast.LENGTH_SHORT).show();
            valid = false;
        }else
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
                birth = year + "-" + smonth + "-" + sday;
                birthdate.setText(birth);
            }
        };
    }

    public void hideKey(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        if(mIsBound) {
            unbindService(socketConnection);
            mIsBound = false;
        }
        super.onStop();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getBaseContext(), event.getState(), Toast.LENGTH_SHORT).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventCheckRegister event) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo !!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        if(event.isKet_qua()){
            alertDialog.setMessage("Đăng kí thành công!");
            alertDialog.show();
        }else{
            alertDialog.setMessage("Tên đăng nhập đã có người sử dụng!");
            alertDialog.show();
        }
    }
}
