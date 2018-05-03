package project.fpt.edu.vn.registerscreen.Activity;

import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventCheckRegister;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;

public class RegisterActivity extends AppCompatActivity {

    ImageButton imgBtnBack;
    DatePickerDialog.OnDateSetListener DateListener;
    EditText edtName, edtUser, edtPass, edtCpass, edtPhone, edtBirth,
            edtSoNha, edtDuong, edtQuan, edtThanhPho, edtWeight, edtHeight, edtIdNumber; //moi
    Button btnReg;
    RadioGroup radGender;
    RadioButton radMale,radFemale;
    String name, user, pass, cpass, phone, birth,
            so, duong, quan, thanhpho, cannang, chieucao; //moi
    private static final String TAG = "ActivityRegister";
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
    public void anhXa(){
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        edtName = (EditText) findViewById(R.id.edtName); //moi
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtCpass = (EditText) findViewById(R.id.edtCpass);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtBirth = (EditText) findViewById(R.id.edtBirth);
        radGender = (RadioGroup) findViewById(R.id.radGender);
        radMale = (RadioButton)findViewById(R.id.radMale);
        radFemale = (RadioButton)findViewById(R.id.radFemale);

        //moi
        edtSoNha = (EditText) findViewById(R.id.edtAddressNumber);
        edtDuong = (EditText) findViewById(R.id.edtAddressRoad);
        edtQuan = (EditText) findViewById(R.id.edtAddressQuan);
        edtThanhPho = (EditText) findViewById(R.id.edtAddressCity);
        edtWeight = (EditText) findViewById(R.id.edtWeight);
        edtHeight = (EditText) findViewById(R.id.edtHeight);
        edtIdNumber = (EditText) findViewById(R.id.edtIdNumber);

        btnReg = (Button) findViewById(R.id.btnReg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        onCreateSocket();
        anhXa();
        DatePicker();

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    private void onCreateSocket(){
        socketApplication = (SocketApplication) getApplication();
        Log.d("Check", "Login created");
        if (socketApplication.getSocket() != null) {
            Log.d("Socket", " is not null");
            startService(new Intent(getBaseContext(), SocketServiceProvider.class));
            doBindService();
        } else {
        }
    }
    public void initialize(){
        name = edtName.getText().toString().trim();
        user = edtUser.getText().toString().trim();
        pass = edtPass.getText().toString().trim();
        cpass = edtCpass.getText().toString().trim();
        phone = edtPhone.getText().toString().trim();
        birth = edtBirth.getText().toString().trim();
        so = edtSoNha.getText().toString().trim();
        duong = edtDuong.getText().toString().trim();
        quan = edtQuan.getText().toString().trim();
        thanhpho = edtThanhPho.getText().toString().trim();
        cannang = edtWeight.getText().toString().trim();
        chieucao = edtHeight.getText().toString().trim();
    }

    public void DatePicker(){

        edtBirth.setOnClickListener(new View.OnClickListener() {
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
                String birthShow = sday + "-" + smonth + "-" + year;
                birth = year + "-" + smonth + "-" + sday;
                edtBirth.setText(birthShow);
            }
        };
    }

    public boolean validate(){
        boolean valid = true;
        if(name.isEmpty()){
            edtName.setError("Hãy nhập tên");
            valid = false;
        }
        if(user.isEmpty()){
            edtUser.setError("Hãy nhập tên tài khoản");
            valid = false;
        }
        if(pass.isEmpty()){
            edtPass.setError("Hãy nhập mật khẩu");
            valid = false;
        }
        if(cpass.isEmpty() || !pass.equals(cpass)){
            edtCpass.setError("Hãy nhập mật khẩu giống nhau");
            valid = false;
        }
        if(phone.isEmpty()){
            edtPhone.setError("Hãy nhập số điện thoại");
            valid = false;
        }
        if(birth.equals("")){
            Toast.makeText(getApplicationContext(), "Hãy nhập ngày tháng năm sinh", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(radGender.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Hãy chọn giới tính", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        //moi
        if(edtIdNumber.getText().toString().trim().isEmpty()){
            edtIdNumber.setError("Hãy nhập địa chỉ");
            valid = false;
        }
        if(so.isEmpty()){
            edtSoNha.setError("Hãy nhập địa chỉ");
            valid = false;
        }
        if(duong.isEmpty()){
            edtDuong.setError("Hãy nhập địa chỉ");
            valid = false;
        }
        if(quan.isEmpty()){
            edtQuan.setError("Hãy nhập địa chỉ");
            valid = false;
        }
        if(thanhpho.isEmpty()){
            edtThanhPho.setError("Hãy nhập địa chỉ");
            valid = false;
        }
        if(cannang.isEmpty()){
            edtWeight.setError("Hãy nhập cân nặng");
            valid = false;
        }
        if(chieucao.isEmpty()){
            edtHeight.setError("Hãy nhập chiều cao");
            valid = false;
        }
        return valid;
    }

    public void register(){
        initialize();
        if(!validate()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo !");
            builder.setMessage("Đăng kí không thành công,vui lòng điền đầy đủ thông tin! ");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }else{
            JSONObject object = new JSONObject();
            try {
                object.put("Username",edtUser.getText().toString().trim());
                object.put("Password",edtPass.getText().toString().trim());
                object.put("Patient_name",edtName.getText().toString().trim());
                if(radMale.isChecked() ){
                    object.put("Gender",1);
                }else if(radFemale.isChecked()){
                    object.put("Gender",0);
                }
                object.put("Birthday",birth);
                object.put("Phone_number",edtPhone.getText().toString().trim());
                object.put("Height",Float.parseFloat(edtHeight.getText().toString().trim()));
                object.put("Weight",Float.parseFloat(edtWeight.getText().toString().trim()));
                object.put("Id_number",edtIdNumber.getText().toString().trim());
                object.put("Address_number",edtSoNha.getText().toString().trim());
                object.put("Address_street",edtDuong.getText().toString().trim());
                object.put("Address_distric",edtQuan.getText().toString().trim());
                object.put("Address_city",edtThanhPho.getText().toString().trim());
                socketApplication.getSocket().emit("patient_register",object.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private void doBindService() {
        bindService(new Intent(RegisterActivity.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getBaseContext(), event.getState(), Toast.LENGTH_SHORT).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventCheckRegister event){
        if(event.isKet_qua()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo !");
            builder.setMessage("Đăng kí thành công ! ");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.show();
        }else if(!event.isKet_qua()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo !");
            builder.setMessage("Đăng kí không thành công, Tên đăng nhập đã có người sử dụng");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }
}

