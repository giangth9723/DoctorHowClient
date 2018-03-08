package project.fpt.edu.vn.registerscreen.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.HistoryCall;
import project.fpt.edu.vn.registerscreen.R;

public class ActivityHistoryDoctorView extends AppCompatActivity {

    TextView tvName, tvDate, tvTime;
    ImageButton imgBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_doctor_view);

        tvName = (TextView) findViewById(R.id.TvDoctorName);
        tvDate = (TextView) findViewById(R.id.TvHisProDateCall);
        tvTime = (TextView) findViewById(R.id.TvHisProTime) ;

        imgBtn = (ImageButton) findViewById(R.id.ImgBtnBack) ;

        Bundle b = getIntent().getExtras();
        if (b != null) {
            HistoryCall historyCall = (HistoryCall) b.getSerializable("array_history");
            tvName.setText(historyCall.getHistoryName());
            tvDate.setText(historyCall.getHistoryDate());
            tvTime.setText(historyCall.getHistoryTime());
        }

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
