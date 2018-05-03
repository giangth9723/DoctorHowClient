package project.fpt.edu.vn.registerscreen.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListDermatology;
import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListFemale;
import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListMental;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventBookStatus;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.Model.Doctor;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;

import static project.fpt.edu.vn.registerscreen.Application.SocketApplication.getContext;

public class AppointmentActivity extends AppCompatActivity {

    TextView tvDocName;
    EditText edtDate, edtTime, edtContent;
    ImageButton imgBtnBack;
    DatePickerDialog.OnDateSetListener DateListener;
    private static final String TAG = "AppointmentActivity";
    AlertDialog alertDialog;
    Button btnBook;
    String date, time, content ;
    String birth = "";
    String Activity_name = "";
    Session session;
    Boolean mIsBound;
    SocketApplication socketApplication;
    SocketServiceProvider mBoundService;
    Doctor doctor;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        onCreateSocket();
        anhXa();
        datePicker();
        timePicker();
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("doctor_data");
        Activity_name = b.getString("activity_name");
        Log.d("appointment",Activity_name);
        if (b != null) {
            doctor= (Doctor) b.getSerializable("doctor");
            tvDocName.setText(doctor.getDoctor_name());
        }

        btnOnclick();
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
    public void btnOnclick(){

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
                if(validate()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentActivity.this);
                    builder.setTitle("Đồng ý xác nhận cuộc hẹn này: ");
                    String message = ("Ngày: " + birth + "\n Giờ: " + edtTime.getText().toString() + "\n Nội dung: " + edtContent.getText().toString());
                    builder.setMessage(message);
                    builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            JSONObject obj = new JSONObject();
                            try {
                                String Emr_type = "";
                                obj.put("Date", birth);
                                obj.put("Patient_id", session.getPatient().getPatient_id());
                                obj.put("Doctor_id", doctor.getDoctor_id());
                                obj.put("Time", edtTime.getText().toString());
                                obj.put("Content", edtContent.getText().toString());
                                if(Activity_name.equalsIgnoreCase(ActivityDoctorListFemale.class.getSimpleName())){
                                    Emr_type = "Bệnh phụ khoa";
                                }
                                if(Activity_name.equalsIgnoreCase(ActivityDoctorListMental.class.getSimpleName())){
                                    Emr_type = "Bệnh tâm thần";
                                }
                                if(Activity_name.equalsIgnoreCase(ActivityDoctorListDermatology.class.getSimpleName())){
                                    Emr_type = "Bệnh da liễu";
                                }
                                obj.put("Emr_type",Emr_type);
                                socketApplication.getSocket().emit("patient_book_appointment", obj.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentActivity.this);
                        builder.setTitle("Thông báo ");
                        String message= "Ngày hoặc giờ không được để trống!";
                        builder.setMessage(message);
                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.show();
                    }
            }
        });
    }
    public void anhXa(){
        tvDocName = (TextView) findViewById(R.id.tvDocName);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtTime = (EditText) findViewById(R.id.edtTime);
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        edtContent = (EditText) findViewById(R.id.edtContent);
        btnBook = (Button) findViewById(R.id.btnBook);
        session = new Session(this);
    }

    public void datePicker(){
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AppointmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, DateListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        DateListener = new DatePickerDialog.OnDateSetListener() {
            String sday, smonth;
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
                birth = "" + year + "-" + smonth + "-" + sday;
                edtDate.setText(formatDate(birth));
            }
        };
    }
    public String formatDate(String date){
        String[] part = date.split("-");
        String pp1 = part[0];
        String pp2 = part[1];
        String pp3 = part[2];
        String p2 = part[2]+"-"+part[1]+"-"+part[0];
        date = p2;
        return date;
    }
    public void timePicker(){

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(AppointmentActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String stime, shour, sminute;
                                if(i < 10){
                                    shour = "0" + i;
                                }else{
                                    shour = i + "";
                                }
                                if(i1 < 10){
                                    sminute = "0" + i1;
                                }else{
                                    sminute = i1 + "";
                                }
                                stime = shour + ":" + sminute +":00";
                                edtTime.setText(stime);
                            }
                        }, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });
    }

    public void init(){
        date = edtDate.getText().toString().trim();
        time = edtTime.getText().toString().trim();
        content = edtContent.getText().toString().trim();
    }

    public boolean validate(){
        boolean valid = true;
        if(date.equals("")){
            //Toast.makeText(this, "Nhập ngày", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(time.equals("")){
            //Toast.makeText(this, "Nhập thời gian", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(content.equals("")){
            //Toast.makeText(this, "Nhập nội dung", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    private void doBindService() {
        bindService(new Intent(AppointmentActivity.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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
    public void onMessageEvent(EventBookStatus event) {
        if(event.isCheck()){
            AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentActivity.this);
            builder.setTitle("Thông báo ");
            String message= "Đặt lịch hẹn thành công";
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();
        }
    }

}
