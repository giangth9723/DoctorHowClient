package project.fpt.edu.vn.registerscreen.Activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

import project.fpt.edu.vn.registerscreen.Doctor;
import project.fpt.edu.vn.registerscreen.R;

public class ActivityAppoint extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener DateListener;
    ImageButton imgBtnback;
    TextView tvDate, tvDocName;
    private static final String TAG = "ActivityAppoint";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint);

        tvDate = (TextView)findViewById(R.id.etDate);
        tvDocName = (TextView)findViewById(R.id.tvAppDocName);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            Doctor doctor = (Doctor) b.getSerializable("array_doctor");
            tvDocName.setText(doctor.getName());
        }

        imgBtnback = (ImageButton) findViewById(R.id.ImgBtnBack);
        imgBtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DatePicker();

    }

    public void DatePicker(){
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(ActivityAppoint.this,
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
                tvDate.setText(birth);
            }
        };
    }
}
