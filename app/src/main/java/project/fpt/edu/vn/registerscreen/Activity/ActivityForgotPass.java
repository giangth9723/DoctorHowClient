package project.fpt.edu.vn.registerscreen.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import project.fpt.edu.vn.registerscreen.R;

public class ActivityForgotPass extends AppCompatActivity implements View.OnClickListener {

    TextView forgetReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        forgetReturn = (TextView)findViewById(R.id.TvForgetReturn);
        forgetReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.TvForgetReturn:
                finish();
                break;
        }
    }
}
