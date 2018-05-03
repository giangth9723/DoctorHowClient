package project.fpt.edu.vn.registerscreen.Activity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.fpt.edu.vn.registerscreen.Model.HistoryCall;
import project.fpt.edu.vn.registerscreen.R;

public class ActivityHistoryDoctorView extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageButton imgBtnBack;
    EditText edtPName, edtDocName, edtDate, edtDuration,
            edtSTime, edtETime, edtStatus;
    TextView txtInformation;
    LinearLayout lineHidden01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_doctor_view);
        anhXa();

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            HistoryCall historyCall = (HistoryCall) b.getSerializable("array_history");
            collapsingToolbarLayout.setTitle(" ");
            edtDocName.setText(historyCall.getDoctor_name());
            edtDuration.setText(historyCall.getDuration());
            edtSTime.setText(historyCall.getStart_time());
            edtETime.setText(historyCall.getEnd_time());
            edtStatus.setText(historyCall.getEmr_status());
            edtPName.setText(historyCall.getPatient_name());
            edtDate.setText(formatDate(historyCall.getDay()));
        }

        txtInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lineHidden01.getVisibility() != view.VISIBLE){
                    expand(lineHidden01);
                }else{
                    collapse(lineHidden01);
                }
            }
        });
    } public String formatDate(String date){
        String[] part = date.split("-");
        String pp1 = part[0];
        String pp2 = part[1];
        String pp3 = part[2];
        String p2 = part[2]+"-"+part[1]+"-"+part[0];
        date = p2;
        return date;
    }

    public void anhXa(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapToolbar);
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
//        txtDate = (TextView) findViewById(R.id.txtDate);
//        txtTime = (TextView) findViewById(R.id.txtTime);
        edtDate = (EditText) findViewById(R.id.edtDate);
        edtDuration = (EditText) findViewById(R.id.edtDuration);
        edtSTime = (EditText) findViewById(R.id.edtSTime);
        edtETime = (EditText) findViewById(R.id.edtETime);
        edtStatus = (EditText) findViewById(R.id.edtStatus);
        edtDocName = (EditText) findViewById(R.id.edtDoctorName);
        edtPName = (EditText) findViewById(R.id.edtPatientName);

        txtInformation = (TextView) findViewById(R.id.txtInformation);
        lineHidden01 = (LinearLayout) findViewById(R.id.lineHidden01);
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
