package project.fpt.edu.vn.registerscreen.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import project.fpt.edu.vn.registerscreen.R;

public class UserProfileChangeActivity extends AppCompatActivity {

    private static final String TAG = "Date";
    ImageButton imgBtnBack;
    TextView txtGender, txtBirth, txtSave;
    DatePickerDialog.OnDateSetListener DateListener;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_change);
        anhXa();

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertRadio();
            }
        });

        DatePicker();

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileChangeActivity.this);
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn muốn lưu thay đổi?");
                builder.setNegativeButton("Không", null);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(UserProfileChangeActivity.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public void anhXa(){
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        txtGender = (TextView) findViewById(R.id.txtGender);
        txtBirth = (TextView) findViewById(R.id.txtBirth);
        txtSave = (TextView) findViewById(R.id.txtSave);
    }

    public void DatePicker(){
        txtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UserProfileChangeActivity.this,
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
                txtBirth.setText(birth);
            }
        };
    }

    public void AlertRadio(){
        CharSequence[] values = {" Nam "," Nữ "};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserProfileChangeActivity.this);
        builder.setTitle("Chọn giới tính");
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item) {
                    case 0:
                        txtGender.setText("Nam");
                        break;
                    case 1:
                        txtGender.setText("Nữ");
                        break;
                }
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();

    }
}
