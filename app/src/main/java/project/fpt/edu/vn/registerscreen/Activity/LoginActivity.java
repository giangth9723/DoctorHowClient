package project.fpt.edu.vn.registerscreen.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventCheckLogin;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLog, btnReg;
    Session session;
    EditText etUser, etPass;
    CheckBox cbBox;
    TextView forgetPass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String USERNAME_KEY = "user";
    String PASSWORD_KEY = "password";
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
        setContentView(R.layout.activity_login);
        btnLog = (Button) findViewById(R.id.BtnLog);
        btnReg= (Button) findViewById(R.id.BtnReg);
        etUser = (EditText)findViewById(R.id.EtUser);
        etPass = (EditText)findViewById(R.id.EtPass);
        cbBox = (CheckBox)findViewById(R.id.CbBox);
        forgetPass = (TextView)findViewById(R.id.TvForgetPass);

        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        btnLog.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        forgetPass.setOnClickListener(this);


        session = new Session(this);

        if(session.LoggedIn()){
            JSONObject USP=new JSONObject();
            try {
                USP.put("Username",session.getUser_name());
                USP.put("Password",session.getPassword());
                socketApplication.getSocket().emit("patient_login",USP.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        socketApplication = (SocketApplication) getApplication();
        Log.d("Check", "Login created");
        if (socketApplication.getSocket() != null) {
            Log.d("Socket", " is not null");
            startService(new Intent(getBaseContext(), SocketServiceProvider.class));
            doBindService();
        } else {
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BtnLog:
                JSONObject USP=new JSONObject();
                try {
                    USP.put("Username",etUser.getText().toString());
                    USP.put("Password",etPass.getText().toString());
                    socketApplication.getSocket().emit("patient_login",USP.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.BtnReg:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.TvForgetPass:
                startActivity(new Intent(LoginActivity.this, ActivityForgotPass.class));
                break;
            default:
        }
    }
    private void doBindService() {
        bindService(new Intent(LoginActivity.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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
    @Subscribe
    public void onMessageEvent(EventCheckLogin event){
        Log.d("Login status2","gived");
        if(event.isKet_qua()){
            if(!session.LoggedIn()) {
                session.setUser_name(event.getUsername());
                session.setPassword(event.getPassword());
                session.setPatient_id(event.getPatient_id());
                session.setProfile_picture(event.getProfile_picture());
                session.setAddress(event.getAddress());
                session.setBitrhday(event.getBirthday());
                session.setHeight(event.getHeight());
                session.setWeight(event.getWeight());
                session.setGender(event.getGender());
                session.setId_number(event.getId_number());
                session.setPatient_name(event.getPatient_name());
                session.setPhone_number(event.getPhone_number());
                session.setLoggedIn(true);
            }
            Intent intent = new Intent(getBaseContext(),MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
