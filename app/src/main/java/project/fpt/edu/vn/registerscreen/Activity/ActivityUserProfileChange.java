package project.fpt.edu.vn.registerscreen.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

import project.fpt.edu.vn.registerscreen.R;

/**
 * Created by User on 2/12/2018.
 */

public class ActivityUserProfileChange extends AppCompatActivity {

    private static final String TAG = "Fragment Profile";
    AlertDialog alertDialog1;
    DatePickerDialog.OnDateSetListener DateListener;
    TextView tvBirth, tvGender;
    ImageButton ImgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_change);

        tvBirth = (TextView) findViewById(R.id.tvUserProBirth);
        tvGender = (TextView) findViewById(R.id.tvUserProGender);
        ImgBtn = (ImageButton) findViewById(R.id.ImgBtnBack);

        ImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        DatePicker();
        OnClickAlertRadio();


    }

    public void DatePicker(){
        tvBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ActivityUserProfileChange.this,
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
                tvBirth.setText(birth);
            }
        };
    }

    public void AlertRadio(){
        CharSequence[] values = {" Nam "," Nữ "};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityUserProfileChange.this);
        builder.setTitle("Chọn giới tính");
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item) {
                    case 0:
                        tvGender.setText("Nam");
                        break;
                    case 1:
                        tvGender.setText("Nữ");
                        break;
                }
                alertDialog1.dismiss();
            }
        });

        alertDialog1 = builder.create();
        alertDialog1.show();

    }

    public void OnClickAlertRadio(){
        tvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertRadio();
            }
        });
    }

    public void hideKey(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}
