package project.fpt.edu.vn.registerscreen.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListFemale;
import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListDermatology;
import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListMental;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventAcceptCall;
import project.fpt.edu.vn.registerscreen.BusEvent.EventCancelCall;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventConnectCall;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;

public class WaitingCallActivity extends AppCompatActivity {
    private String SESSION_ID = "";
    private String TOKEN = "";
    private String Activity_before="";
    private String Doctor_Socket_id = "";
    Gson gson = new Gson();
    TextView txtCallDoctorName;
    FloatingActionButton fabCancelCall;
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
        setContentView(R.layout.activity_waiting_call);
        txtCallDoctorName = (TextView)findViewById(R.id.txtCallDoctorName);
        fabCancelCall = (FloatingActionButton)findViewById(R.id.fabCancelCall);
        onConnectSocket();
        session = new Session(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("activity_name");
//        Doctor_Socket_id = bundle.getString("Socket_id");
//        String Doctor_name = bundle.getString("Doctor_name");
        Activity_before = bundle.getString("activity_name");
        txtCallDoctorName.setText(session.getDoctorOpp().getDoctor_name());
        if(!session.LoggedIn()){
            Logout();
        }
        fabCancelCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socketApplication.getSocket().emit("patient_cancel_call",session.getDoctorOpp());
                if(Activity_before.equalsIgnoreCase(ActivityDoctorListFemale.class.getSimpleName())) {
                    Intent intent = new Intent(WaitingCallActivity.this, ActivityDoctorListFemale.class);
                    startActivity(intent);
                }
                else if(Activity_before.equalsIgnoreCase(ActivityDoctorListDermatology.class.getSimpleName())){
                    Intent intent = new Intent(WaitingCallActivity.this, ActivityDoctorListDermatology.class);
                    startActivity(intent);
                }else if(Activity_before.equalsIgnoreCase(ActivityDoctorListMental.class.getSimpleName())){
                    Intent intent = new Intent(WaitingCallActivity.this, ActivityDoctorListMental.class);
                    startActivity(intent);
                }
                finish();
            }
        });
        Call();
    }
    private void onConnectSocket(){
        socketApplication = (SocketApplication) getApplication();
        Log.d("Check", "Login created");
        if (socketApplication.getSocket() != null) {
            Log.d("Socket", " is not null");
            doBindService();
        } else {
        }
    }
    private void Call(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("Patient_info",gson.toJson(session.getPatient()));
            obj.put("Doctor_socket_id",session.getDoctorOpp().getSocket_id());
            obj.put("Activity_name",Activity_before);
            socketApplication.getSocket().emit("patient_call",obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void doBindService() {
        bindService(new Intent(WaitingCallActivity.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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
    private void Logout(){
        session.setLoggedIn(false);
        finish();
        startActivity(new Intent(WaitingCallActivity.this, LoginActivity.class));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getBaseContext(), event.getState(), Toast.LENGTH_SHORT).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventAcceptCall event) {
        Bundle bundle = new Bundle();
        bundle.putString("SESSION_ID",SESSION_ID);
        bundle.putString("TOKEN",TOKEN);
        bundle.putString("Activity_name",Activity_before);
        bundle.putString("Doctor_socket_id",session.getDoctorOpp().getSocket_id());
        Intent intent = new Intent(WaitingCallActivity.this,CallActivity.class);
        finish();
        intent.putExtra("dulieu",bundle);
        startActivity(intent);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventConnectCall event){
        SESSION_ID = event.getSessionID();
        TOKEN = event.getToken();
        Toast.makeText(getBaseContext(), event.getSessionID(), Toast.LENGTH_SHORT).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventCancelCall event) {
        finish();
        if(Activity_before.equalsIgnoreCase(ActivityDoctorListFemale.class.getSimpleName())) {
            Intent intent = new Intent(WaitingCallActivity.this, ActivityDoctorListFemale.class);
            startActivity(intent);
        }
        else if(Activity_before.equalsIgnoreCase(ActivityDoctorListDermatology.class.getSimpleName())){
            Intent intent = new Intent(WaitingCallActivity.this, ActivityDoctorListDermatology.class);
            startActivity(intent);
        }else if(Activity_before.equalsIgnoreCase(ActivityDoctorListMental.class.getSimpleName())){
            Intent intent = new Intent(WaitingCallActivity.this, ActivityDoctorListMental.class);
            startActivity(intent);
        }

    }
}
