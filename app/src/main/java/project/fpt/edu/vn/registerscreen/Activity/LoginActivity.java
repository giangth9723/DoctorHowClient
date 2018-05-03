package project.fpt.edu.vn.registerscreen.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventCheckLogin;
import project.fpt.edu.vn.registerscreen.Model.Patient;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Gson gson = new Gson();
    Button btnLog, btnReg;

    EditText etUser, etPass;
    CheckBox cbBox;
    TextView forgetPass;
    Session session;
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
        onCreateSocket();
        createControl();
        if (session.LoggedIn()) {
                JSONObject USP = new JSONObject();
                try {
                    USP.put("Username", session.getPatient().getUsername());
                    USP.put("Password", session.getPatient().getPassword());
                    socketApplication.getSocket().emit("patient_login", USP.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


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
    private void createControl(){
        btnLog = (Button) findViewById(R.id.BtnLog);
        btnReg= (Button) findViewById(R.id.BtnReg);
        etUser = (EditText)findViewById(R.id.EtUser);
        etPass = (EditText)findViewById(R.id.EtPass);
        cbBox = (CheckBox)findViewById(R.id.CbBox);
        btnLog.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        session = new Session(this);
    }
    private void Login(){
        JSONObject USP=new JSONObject();
        try {
            USP.put("Username",etUser.getText().toString());
            USP.put("Password",etPass.getText().toString());
            socketApplication.getSocket().emit("patient_login",USP.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BtnLog:
                Login();
                break;
            case R.id.BtnReg:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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
    public void onLoginEvent(EventCheckLogin event){
        Log.d("Login status2","gived");
        Patient patient = event.getPatient();
        if(event.isCheck()){
            session.setPatient(patient);
            if(!session.LoggedIn()) {
                session.setLoggedIn(true);
            }
            Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
            startActivity(intent);
            finish();
        }
        else if(!event.isCheck()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo !");
            builder.setMessage("Tài khoản hoặc mật khẩu không đúng ");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }
}
