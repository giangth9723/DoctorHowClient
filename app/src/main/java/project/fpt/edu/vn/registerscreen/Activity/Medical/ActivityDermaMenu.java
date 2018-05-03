package project.fpt.edu.vn.registerscreen.Activity.Medical;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import project.fpt.edu.vn.registerscreen.Model.Patient;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Session;


public class ActivityDermaMenu extends AppCompatActivity {
    Session session;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView txtInformation, txtHistory;
    LinearLayout lineHidden01;
    ImageButton imgBtnBack;

    EditText edtName, edtBirth, edtAge, edtPhone, edtAddress, edtWeight, edtCMT,edtHeight;
    String birth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derma_menu);
        anhXa();
        calAge();

        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        txtInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lineHidden01.getVisibility() != view.VISIBLE) {
                    expand(lineHidden01);
                } else {
                    collapse(lineHidden01);
                }
            }
        });

        txtHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityDermaMenu.this, ActivityDermaExaminationHistory.class));
                finish();
            }
        });

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void anhXa(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapToolbar);
        txtInformation = (TextView) findViewById(R.id.txtInformation);
        lineHidden01 = (LinearLayout) findViewById(R.id.lineHidden01);
        txtHistory = (TextView) findViewById(R.id.txtHistory);
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);

        edtName = (EditText) findViewById(R.id.edtName);
        edtBirth = (EditText) findViewById(R.id.edtBirth);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtWeight = (EditText) findViewById(R.id.edtWeight);
        edtCMT = (EditText) findViewById(R.id.edtCMT);
        edtHeight = (EditText)findViewById(R.id.edtHeight);
        session = new Session(this);
        Patient patient = session.getPatient();

        // Assign value for patient
        edtName.setText(patient.getPatient_name());
        edtBirth.setText(formatDate(patient.getBirthday()));
        edtPhone.setText(patient.getPhone_number());
        edtAddress.setText("Số nhà "+patient.getAddress_number()+" đường "+patient.getAddress_street()+ " quận "+patient.getAddress_distric()+" thành phố "+patient.getAddress_city() );
        edtWeight.setText(String.valueOf(patient.getWeight()));
        edtHeight.setText(String.valueOf(patient.getHeight()));
        edtCMT.setText(patient.getId_number());
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
    public void calAge(){
        if(!edtBirth.getText().toString().trim().isEmpty()){
            birth = edtBirth.getText().toString().trim();
            String[] part = birth.split("-");
            String p1 = part[0];
            String p2 = part[1];
            String p3 = part[2];

            int birthYear = Integer.parseInt(p3);
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int age = year - birthYear;
            edtAge.setText(String.valueOf(age));
        }
    }

    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
