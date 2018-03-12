package project.fpt.edu.vn.registerscreen.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventCheckLogin;
import project.fpt.edu.vn.registerscreen.BusEvent.EventLoadDoctorOnline;
import project.fpt.edu.vn.registerscreen.BusEvent.EventReloadDoctorOnline1;
import project.fpt.edu.vn.registerscreen.BusEvent.EventReloadDoctorOnline2;
import project.fpt.edu.vn.registerscreen.BusEvent.EventReloadDoctorOnline3;
import project.fpt.edu.vn.registerscreen.BusEvent.EventReloadDoctorOnline4;
import project.fpt.edu.vn.registerscreen.Model.DoctorOnline;

/**
 * Created by GIang on 3/7/2018.
 */

public class SocketServiceProvider extends Service {
    private SocketApplication signalApplication;

    public static SocketServiceProvider instance = null;

    public static boolean isInstanceCreated() {
        return instance == null ? false : true;
    }

    private final Binder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public SocketServiceProvider getService() {
            return SocketServiceProvider.this;
        }
    }

    public void IsBendable() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        if (isInstanceCreated()) {
            return;
        }
        super.onCreate();
        Log.d("service","Service created");
        signalApplication = (SocketApplication) getApplication();

        if (signalApplication.getSocket() == null)
            signalApplication.CHAT_SOCKET = signalApplication.getSocket();

        signalApplication.getSocket().on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        signalApplication.getSocket().on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        signalApplication.getSocket().on(Socket.EVENT_CONNECT, onConnect);
        signalApplication.getSocket().on("server_check_login_patient",onLoginStatus);
        signalApplication.getSocket().on("server_load_doctor_list",onLoadDoctor);
        signalApplication.getSocket().on("server_reload_doctor_1",onReloadDoctor1);
        signalApplication.getSocket().on("server_reload_doctor_2",onReloadDoctor2);
        signalApplication.getSocket().on("server_reload_doctor_3",onReloadDoctor3);
        signalApplication.getSocket().on("server_reload_doctor_4",onReloadDoctor4);
//        signalApplication.getSocket().on("server_reload_doctor",onReloadDoctor);

        //@formatter:off
        //@formatter:on

        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isInstanceCreated()) {
            Log.d("SocketServiceProvider","Exist");
            return START_NOT_STICKY;
        }
        super.onStartCommand(intent, flags, startId);
        connectConnection();
        return START_STICKY;
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(
                            new EventChangeChatServerStateEvent("connected to Socket")
                    );
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(
                            new EventChangeChatServerStateEvent("disconnect from socket")
                    );

                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(
                            new EventChangeChatServerStateEvent("flash connection icon")
                    );
                }
            });
        }
    };
    private Emitter.Listener onLoginStatus = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        Log.d("Login status","da nhan");
                        boolean pass = object.getBoolean("ket_qua");
                        EventBus.getDefault().post(new EventCheckLogin(pass));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onLoadDoctor = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        ArrayList<DoctorOnline> arrayDoctorOnline = new ArrayList<>();
                        Log.d("Load Doctor","da nhan");
                        JSONArray arrayDoctor = object.getJSONArray("arrayDoctor");
                        for ( int i = 0 ; i< arrayDoctor.length();i++){
                            JSONObject obj = (JSONObject) arrayDoctor.get(i);
                            int doctorID = obj.getInt("DoctorID");
                            String username = obj.getString("Username");
                            String status = obj.getString("status");
                            String socketID = obj.getString("SocketID");
                            String doctorName = obj.getString("Name");
                            arrayDoctorOnline.add(new DoctorOnline(doctorName,doctorID,username,status,socketID));
                        }
                        EventBus.getDefault().post(new EventLoadDoctorOnline(arrayDoctorOnline));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onReloadDoctor1 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        ArrayList<DoctorOnline> arrayDoctorOnline = new ArrayList<>();
                        Log.d("Load Doctor 1","da nhan");
                        JSONArray arrayDoctor = object.getJSONArray("arrayDoctor");
                        for ( int i = 0 ; i< arrayDoctor.length();i++){
                            JSONObject obj = (JSONObject) arrayDoctor.get(i);
                            int doctorID = obj.getInt("DoctorID");
                            String username = obj.getString("Username");
                            String status = obj.getString("status");
                            String socketID = obj.getString("SocketID");
                            String doctorName = obj.getString("Name");
                            arrayDoctorOnline.add(new DoctorOnline(doctorName,doctorID,username,status,socketID));
                        }
                        EventBus.getDefault().post(new EventReloadDoctorOnline1(arrayDoctorOnline));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onReloadDoctor2 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        ArrayList<DoctorOnline> arrayDoctorOnline = new ArrayList<>();
                        Log.d("Load Doctor 2","da nhan");
                        JSONArray arrayDoctor = object.getJSONArray("arrayDoctor");
                        for ( int i = 0 ; i< arrayDoctor.length();i++){
                            JSONObject obj = (JSONObject) arrayDoctor.get(i);
                            int doctorID = obj.getInt("DoctorID");
                            String username = obj.getString("Username");
                            String status = obj.getString("status");
                            String socketID = obj.getString("SocketID");
                            String doctorName = obj.getString("Name");
                            arrayDoctorOnline.add(new DoctorOnline(doctorName,doctorID,username,status,socketID));
                        }
                        EventBus.getDefault().post(new EventReloadDoctorOnline2(arrayDoctorOnline));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onReloadDoctor3 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        ArrayList<DoctorOnline> arrayDoctorOnline = new ArrayList<>();
                        Log.d("Load Doctor 3","da nhan");
                        JSONArray arrayDoctor = object.getJSONArray("arrayDoctor");
                        for ( int i = 0 ; i< arrayDoctor.length();i++){
                            JSONObject obj = (JSONObject) arrayDoctor.get(i);
                            int doctorID = obj.getInt("DoctorID");
                            String username = obj.getString("Username");
                            String status = obj.getString("status");
                            String socketID = obj.getString("SocketID");
                            String doctorName = obj.getString("Name");
                            arrayDoctorOnline.add(new DoctorOnline(doctorName,doctorID,username,status,socketID));
                        }
                        EventBus.getDefault().post(new EventReloadDoctorOnline3(arrayDoctorOnline));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onReloadDoctor4 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        ArrayList<DoctorOnline> arrayDoctorOnline = new ArrayList<>();
                        Log.d("Load Doctor 4","da nhan");
                        JSONArray arrayDoctor = object.getJSONArray("arrayDoctor");
                        for ( int i = 0 ; i< arrayDoctor.length();i++){
                            JSONObject obj = (JSONObject) arrayDoctor.get(i);
                            int doctorID = obj.getInt("DoctorID");
                            String username = obj.getString("Username");
                            String status = obj.getString("status");
                            String socketID = obj.getString("SocketID");
                            String doctorName = obj.getString("Name");
                            arrayDoctorOnline.add(new DoctorOnline(doctorName,doctorID,username,status,socketID));
                        }
                        EventBus.getDefault().post(new EventReloadDoctorOnline4(arrayDoctorOnline));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private void connectConnection() {
        instance = this;
        signalApplication.getSocket().connect();
    }

    private void disconnectConnection() {
        instance = null;
        signalApplication.getSocket().disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        signalApplication.getSocket().off(Socket.EVENT_CONNECT, onConnect);
        signalApplication.getSocket().off(Socket.EVENT_DISCONNECT, onDisconnect);
        signalApplication.getSocket().off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        signalApplication.getSocket().off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        signalApplication.getSocket().off("server_check_login_patient",onLoginStatus);
        signalApplication.getSocket().off("server_load_doctor_list",onLoadDoctor);
        signalApplication.getSocket().off("server_reload_doctor_1",onReloadDoctor1);
        signalApplication.getSocket().off("server_reload_doctor_2",onReloadDoctor2);
        signalApplication.getSocket().off("server_reload_doctor_3",onReloadDoctor3);
        signalApplication.getSocket().off("server_reload_doctor_4",onReloadDoctor4);
//        signalApplication.getSocket().on("server_server_reload_doctor",onReloadDoctor);
        EventBus.getDefault().unregister(this);
        Log.d("disconnected","true");
        disconnectConnection();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getBaseContext(), event.getState(), Toast.LENGTH_SHORT).show();
    }

}
