package project.fpt.edu.vn.registerscreen.Activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListFemale;
import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListDermatology;
import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListMental;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventFinishCall;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class CallActivity extends AppCompatActivity implements  Session.SessionListener,PublisherKit.PublisherListener {
    private static String API_KEY = "46082492";
    private static String SESSION_ID = "";
    private static String TOKEN = "";
    private static final String LOG_TAG = CallActivity.class.getSimpleName();
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    private static final int RC_VIDEO_APP_PERM = 124;
    private Session mSession;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Publisher mPublisher;
    private Subscriber mSubscriber;
    private String Activity_before = "";
    private String start_time;
    private String end_time;
    private String duration;
    final DateFormat df = new SimpleDateFormat("HH:mm:ss"); //format time
    project.fpt.edu.vn.registerscreen.Session session;
    FloatingActionButton fabCancel;
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
        setContentView(R.layout.activity_call);
        onConnectSocket();
        session = new project.fpt.edu.vn.registerscreen.Session(this);
        fabCancel = (FloatingActionButton)findViewById(R.id.fabCancel);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieu");
        SESSION_ID = bundle.getString("SESSION_ID");
        TOKEN = bundle.getString("TOKEN");
        Activity_before = bundle.getString("Activity_name");
        start_time= df.format(Calendar.getInstance().getTime());
        fabCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patient_finish_call();
            }
        });
        requestPermissions();
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
    public void patient_finish_call(){
        mSession.disconnect();
        socketApplication.getSocket().emit("patient_finish_call",session.getDoctorOpp().getSocket_id());
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CallActivity.this);
        alertDialog.setTitle("Thông báo !");
        alertDialog.setMessage("Cuộc gọi kết thúc ! Bạn có muốn chia sẻ bệnh án cho bác sĩ không?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 socketApplication.getSocket().emit("patient_accept_request_emr",session.getDoctorOpp().getSocket_id());
                 finish();
                 getBack_activity();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                socketApplication.getSocket().emit("patient_decline_request_emr",session.getDoctorOpp().getSocket_id());
                finish();
                getBack_activity();
            }
        });
        alertDialog.show();

    }
    public void getBack_activity(){
        if(Activity_before.equalsIgnoreCase(ActivityDoctorListFemale.class.getSimpleName())) {
            Intent intent = new Intent(CallActivity.this, ActivityDoctorListFemale.class);
            startActivity(intent);
        }
        else if(Activity_before.equalsIgnoreCase(ActivityDoctorListDermatology.class.getSimpleName())){
            Intent intent = new Intent(CallActivity.this, ActivityDoctorListDermatology.class);
            startActivity(intent);
        }else if(Activity_before.equalsIgnoreCase(ActivityDoctorListMental.class.getSimpleName())){
            Intent intent = new Intent(CallActivity.this, ActivityDoctorListMental.class);
            startActivity(intent);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };
        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);
            mSubscriberViewContainer = (FrameLayout)findViewById(R.id.subscriber_container);

            // initialize and connect to the session
            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(this);
            mSession.connect(TOKEN);

        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    // SessionListener methods
    @Override
    public void onConnected(Session session) {
        Log.i(LOG_TAG, "Session Connected");
        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());
        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {
        Log.i(LOG_TAG, "Session Disconnected");
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Received");

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
        Log.i(LOG_TAG, "Stream Dropped");

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.e(LOG_TAG, "Session error: " + opentokError.getMessage());
    }

    // PublisherListener methods
    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamCreated");
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {
        Log.i(LOG_TAG, "Publisher onStreamDestroyed");
    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.e(LOG_TAG, "Publisher error: " + opentokError.getMessage());
    }
    private void doBindService() {
        bindService(new Intent(CallActivity.this, SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
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
    private String secToTime(int sec){
        int seconds = sec % 60;
        int minutes = sec / 60;
        if (minutes >= 60) {
            int hours = minutes / 60;
            minutes %= 60;
            if( hours >= 24) {
                int days = hours / 24;
                return String.format("%d days %02d:%02d:%02d", days,hours%24, minutes, seconds);
            }
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return String.format("00:%02d:%02d", minutes, seconds);
    }

    private String timeToSec(String time1, String time2){
        String[] unit1 = time1.split(":");
        int hour1 = Integer.parseInt(unit1[0])*3600;
        int minute1 = Integer.parseInt(unit1[1])*60;
        int second1 = Integer.parseInt(unit1[2]);
        int total1 = hour1 + minute1 + second1;

        String[] unit2 = time2.split(":");
        int hour2 = Integer.parseInt(unit2[0])*3600;
        int minute2 = Integer.parseInt(unit2[1])*60;
        int second2 = Integer.parseInt(unit2[2]);
        int total2 = hour2 + minute2 + second2;

        return String.valueOf(total2 - total1);
    }
    @Override
    protected void onDestroy() {
        end_time= df.format(Calendar.getInstance().getTime());
        String sduration = timeToSec(start_time,end_time);
        duration = secToTime(Integer.parseInt(sduration));
        JSONObject obj = new JSONObject();
        try {
            Calendar cal = Calendar.getInstance();
            obj.put("Doctor_id",session.getDoctorOpp().getDoctor_id());
            obj.put("Patient_id",session.getPatient().getPatient_id());
            obj.put("Start_time",start_time);
            obj.put("End_time",end_time);
            obj.put("Duration",duration);
            if(Activity_before.equalsIgnoreCase(ActivityDoctorListFemale.class.getSimpleName())) {
                obj.put("Emr_type","female");
            }
            else if(Activity_before.equalsIgnoreCase(ActivityDoctorListDermatology.class.getSimpleName())){
                obj.put("Emr_type","dermatology");
            }else if(Activity_before.equalsIgnoreCase(ActivityDoctorListMental.class.getSimpleName())){
                obj.put("Emr_type","mental");
            }
            obj.put("Day",String.valueOf(cal.get(Calendar.YEAR))+"-"+cal.get(Calendar.MONTH)+"-"+cal.get(Calendar.DAY_OF_MONTH));
            socketApplication.getSocket().emit("patient_save_history_call",obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getBaseContext(), event.getState(), Toast.LENGTH_SHORT).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventFinishCall event){
        patient_finish_call();
    }

}
