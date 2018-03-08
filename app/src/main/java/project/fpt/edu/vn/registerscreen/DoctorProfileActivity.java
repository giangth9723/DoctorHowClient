package project.fpt.edu.vn.registerscreen;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.fpt.edu.vn.registerscreen.Activity.ActivityAppoint;
import project.fpt.edu.vn.registerscreen.Model.Doctor;
import project.fpt.edu.vn.registerscreen.Model.DoctorOnline;

public class DoctorProfileActivity extends AppCompatActivity {

    LinearLayout lnDocInfor, lnDocPro;
    TextView tvDocInfor, tvDocSp, tvDocName;
    ImageButton imgBtnEp01, imgBtnEp02, imgBtnback;
    FloatingActionButton fabDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        tvDocInfor = (TextView) findViewById(R.id.tvDocProfile);
        tvDocSp = (TextView) findViewById(R.id.tvDocSp);
        imgBtnback = (ImageButton) findViewById(R.id.ImgBtnBack);
        tvDocName = (TextView)findViewById(R.id.tvProDocName);
        fabDate = (FloatingActionButton)findViewById(R.id.fabDate);

        fabDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorProfileActivity.this, ActivityAppoint.class);
                startActivity(intent);
            }
        });

        Bundle b = getIntent().getExtras();
        if (b != null) {
            DoctorOnline doctorOnline = (DoctorOnline) b.getSerializable("array_doctor");
            tvDocName.setText(doctorOnline.getDoctorName());
        }

        lnDocInfor = (LinearLayout)findViewById(R.id.LineDocProInfor);
        lnDocPro = (LinearLayout)findViewById(R.id.LineDocProSp);

        lnDocInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvDocInfor.getVisibility() != view.VISIBLE){
                    expand(tvDocInfor);
                }else{
                    collapse(tvDocInfor);
                }
            }
        });

        lnDocPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvDocSp.getVisibility() != view.VISIBLE){
                    expand(tvDocSp);
                }else{
                    collapse(tvDocSp);
                }
            }
        });

        imgBtnEp01 = (ImageButton)findViewById(R.id.imgBtnEp01);
        imgBtnEp01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvDocInfor.getVisibility() != view.VISIBLE){
                    expand(tvDocInfor);
                }else{
                    collapse(tvDocInfor);
                }
            }
        });

        imgBtnEp02 = (ImageButton)findViewById(R.id.imgBtnEp02);
        imgBtnEp02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvDocSp.getVisibility() != view.VISIBLE){
                    expand(tvDocSp);
                }else{
                    collapse(tvDocSp);
                }
            }
        });

        imgBtnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void expand(final View v) {
        v.measure(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
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
