package project.fpt.edu.vn.registerscreen.Activity.DoctorList;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Activity.LoginActivity;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventLoadDoctorOnline;
import project.fpt.edu.vn.registerscreen.DoctorProfileActivity;
import project.fpt.edu.vn.registerscreen.Fragment.FragmentHome;
import project.fpt.edu.vn.registerscreen.ListAdapter;
import project.fpt.edu.vn.registerscreen.Model.DoctorOnline;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;

/**
 * Created by GIang on 3/11/2018.
 */

public class ActivityDoctorList2 extends AppCompatActivity {
    ArrayList<DoctorOnline> arrayDoctorOnline = new ArrayList<DoctorOnline>();
    ListAdapter adapter;
    TextView tv;
    ListView lv;
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
        setContentView(R.layout.content_doctor_list);

        // Bind service
        socketApplication = (SocketApplication) getApplication();
        Log.d("Check", "Login created");
        if (socketApplication.getSocket() != null) {
            Log.d("Socket", " is not null");
            startService(new Intent(getBaseContext(), SocketServiceProvider.class));
            doBindService();
        } else {
        }
        // End bind service
        session = new Session(this);

        if(!session.LoggedIn()){
            Logout();
        }
        lv = (ListView) findViewById(R.id.ListDoctor);

        Intent intent = getIntent();
        String name = intent.getStringExtra(FragmentHome.NAME);
        if(name.equals("Bệnh da liễu")){
            socketApplication.getSocket().emit("patient_load_doctor","2");
        }
        adapter = new ListAdapter(
                ActivityDoctorList2.this,
                R.layout.activity_line_doctor,
                arrayDoctorOnline
        );
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ActivityDoctorList2.this, DoctorProfileActivity.class);
                intent.putExtra("array_doctor", arrayDoctorOnline.get(i));
                startActivity(intent);
            }
        });

    }
    private void Logout(){
        session.setLoggedIn(false);
        finish();
        startActivity(new Intent(ActivityDoctorList2.this, LoginActivity.class));
    }
    private void doBindService() {
        bindService(new Intent(ActivityDoctorList2.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getBaseContext(), event.getState(), Toast.LENGTH_SHORT).show();
    }
    protected void onDestroy() {
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventLoadDoctorOnline event) {
        Log.d("ListDoctorOnline","True");
        arrayDoctorOnline.clear();
        for(int i=0;i< event.getArrayDoctorOnline().size();i++){
            arrayDoctorOnline.add(event.getArrayDoctorOnline().get(i));
        }
        adapter.notifyDataSetChanged();
    }

}
