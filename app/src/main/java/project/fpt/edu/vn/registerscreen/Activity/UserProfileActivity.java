package project.fpt.edu.vn.registerscreen.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;

public class UserProfileActivity extends AppCompatActivity {
    Session session;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageButton imgBtnBack;
    TextView txtUserName, txtGender, txtProfileBirthday, txtProfilePhone,
            txtWeight, txtCMT, txtAddressNumber, txtAddressRoad, txtAddressQuan, txtAddressCity, txtHeight;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        onCreateSocket();
        anhXa();
        session = new Session(this);
        txtUserName.setText(session.getPatient().getPatient_name());
        txtGender.setText(session.getPatient().getGender());
        txtProfileBirthday.setText(formatDate(session.getPatient().getBirthday()));
        txtAddressCity.setText(session.getPatient().getAddress_city());
        txtAddressNumber.setText(session.getPatient().getAddress_number());
        txtAddressRoad.setText(session.getPatient().getAddress_street());
        txtAddressQuan.setText(session.getPatient().getAddress_distric());
        txtProfilePhone.setText(session.getPatient().getPhone_number());
        txtCMT.setText(session.getPatient().getId_number());
        txtWeight.setText(String.valueOf(session.getPatient().getWeight()));
        txtHeight.setText(String.valueOf(session.getPatient().getHeight()));
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);


        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    public String formatDate(String date){
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
        txtHeight = (TextView)findViewById(R.id.txtHeight);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtGender = (TextView) findViewById(R.id.txtProfileGender);
        txtProfileBirthday = (TextView) findViewById(R.id.txtProfileBirthday);
        txtProfilePhone = (TextView) findViewById(R.id.txtProfilePhone);
        txtWeight = (TextView) findViewById(R.id.txtWeight);
        txtCMT = (TextView) findViewById(R.id.txtCMT);
        txtAddressNumber = (TextView) findViewById(R.id.txtAddressNumber);
        txtAddressRoad = (TextView) findViewById(R.id.txtAddressRoad);
        txtAddressQuan = (TextView) findViewById(R.id.txtAddressQuan);
        txtAddressCity = (TextView) findViewById(R.id.txtAddressCity);
    }
    private void doBindService() {
        bindService(new Intent(UserProfileActivity.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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

}
