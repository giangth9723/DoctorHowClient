package project.fpt.edu.vn.registerscreen.Activity.Medical;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Activity.LoginActivity;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventGetEmrDermatology;
import project.fpt.edu.vn.registerscreen.DermatologyListAdapter;
import project.fpt.edu.vn.registerscreen.Model.Emr_dermatology;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;


public class ActivityDermaExaminationHistory extends AppCompatActivity {
    ArrayList<Emr_dermatology> arrayExam = new ArrayList<Emr_dermatology>();
    DermatologyListAdapter adapterExam;
    ListView lstView;
    ImageButton imgBtnBack;
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
        setContentView(R.layout.activity_derma_examination_history);
        onCreateSocket();
        anhXa();
        socketApplication.getSocket().emit("patient_get_emr_dermatology",session.getPatient().getPatient_id());
        adapterExam = new DermatologyListAdapter(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                arrayExam
        );
        lstView.setAdapter(adapterExam);

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityDermaExaminationHistory.this,ActivityDermaMenu.class));
                finish();
            }
        });

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Emr_dermatology e = arrayExam.get(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("emr_dermatology",e);
                Intent intent = new Intent(ActivityDermaExaminationHistory.this,ActivityDermaContent.class);
                intent.putExtra("emr_dermatology",bundle);
                startActivity(intent);
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

    public void anhXa(){
        lstView = (ListView) findViewById(R.id.lstExamination);
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        session = new Session(this);
    }
    private void doBindService() {
        bindService(new Intent(ActivityDermaExaminationHistory.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventGetEmrDermatology event){
        arrayExam.clear();
        for(int i = 0;i<event.getEmr_dermatologyArrayList().size();i++){
            arrayExam.add(event.getEmr_dermatologyArrayList().get(i));
        }
        Log.d("EmrDermatology1",event.getEmr_dermatologyArrayList().get(0).getDoctor_name());
        adapterExam.notifyDataSetChanged();
    }

}
