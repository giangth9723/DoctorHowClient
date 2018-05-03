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

import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventGetEmrMental;
import project.fpt.edu.vn.registerscreen.MentalListAdapter;
import project.fpt.edu.vn.registerscreen.Model.Emr_mental;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;


public class ActivityMentalExaminationHistory extends AppCompatActivity {
    ArrayList<Emr_mental> arrayExam = new ArrayList<Emr_mental>();
    MentalListAdapter adapterExam;
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
        setContentView(R.layout.activity_mental_examination_history);
        onCreateSocket();
        anhXa();
        socketApplication.getSocket().emit("patient_get_emr_mental",session.getPatient().getPatient_id());
        adapterExam = new MentalListAdapter(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                arrayExam
        );
        lstView.setAdapter(adapterExam);

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMentalExaminationHistory.this,ActivityMentalMenu.class));
                finish();
            }
        });

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Emr_mental e = arrayExam.get(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable("emr_mental",e);
                Intent intent = new Intent(ActivityMentalExaminationHistory.this,ActivityMentalContent.class);
                intent.putExtra("emr_mental",bundle);
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
        bindService(new Intent(ActivityMentalExaminationHistory.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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
    public void onEvent(EventGetEmrMental event){
        arrayExam.clear();
        for(int i = 0;i<event.getEmr_mentalArrayList().size();i++){
            arrayExam.add(event.getEmr_mentalArrayList().get(i));
        }
        Log.d("EmrDermatology1",event.getEmr_mentalArrayList().get(0).getDoctor_name());
        adapterExam.notifyDataSetChanged();
    }
}
