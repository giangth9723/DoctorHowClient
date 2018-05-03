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
import project.fpt.edu.vn.registerscreen.BusEvent.EventAcceptCall;
import project.fpt.edu.vn.registerscreen.BusEvent.EventBookStatus;
import project.fpt.edu.vn.registerscreen.BusEvent.EventCancelCall;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventCheckLogin;
import project.fpt.edu.vn.registerscreen.BusEvent.EventCheckRegister;
import project.fpt.edu.vn.registerscreen.BusEvent.EventConnectCall;
import project.fpt.edu.vn.registerscreen.BusEvent.EventFinishCall;
import project.fpt.edu.vn.registerscreen.BusEvent.EventGetEmrDermatology;
import project.fpt.edu.vn.registerscreen.BusEvent.EventGetEmrFemale;
import project.fpt.edu.vn.registerscreen.BusEvent.EventGetEmrMental;
import project.fpt.edu.vn.registerscreen.BusEvent.EventGetHistoryCall;
import project.fpt.edu.vn.registerscreen.BusEvent.EventLoadDoctorOnline;
import project.fpt.edu.vn.registerscreen.BusEvent.EventReloadDoctorOnline1;
import project.fpt.edu.vn.registerscreen.BusEvent.EventReloadDoctorOnline2;
import project.fpt.edu.vn.registerscreen.BusEvent.EventReloadDoctorOnline3;
import project.fpt.edu.vn.registerscreen.Model.Doctor;
import project.fpt.edu.vn.registerscreen.Model.Emr_dermatology;
import project.fpt.edu.vn.registerscreen.Model.Emr_female;
import project.fpt.edu.vn.registerscreen.Model.Emr_mental;
import project.fpt.edu.vn.registerscreen.Model.HistoryCall;
import project.fpt.edu.vn.registerscreen.Model.Patient;
import project.fpt.edu.vn.registerscreen.Session;

/**
 * Created by GIang on 3/7/2018.
 */

public class SocketServiceProvider extends Service {
    Session session;
    private SocketApplication socketApplication;

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
        socketApplication = (SocketApplication) getApplication();

        if (socketApplication.getSocket() == null)
            socketApplication.CHAT_SOCKET = socketApplication.getSocket();

        socketApplication.getSocket().on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socketApplication.getSocket().on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socketApplication.getSocket().on(Socket.EVENT_CONNECT, onConnect);
        socketApplication.getSocket().on(Socket.EVENT_DISCONNECT, onDisconnect);
        socketApplication.getSocket().on("server_check_login_patient",onLoginStatus);
        socketApplication.getSocket().on("server_load_doctor_disease",onLoadDoctor);
        socketApplication.getSocket().on("server_reload_doctor_1",onReloadDoctor1);
        socketApplication.getSocket().on("server_reload_doctor_2",onReloadDoctor2);
        socketApplication.getSocket().on("server_reload_doctor_3",onReloadDoctor3);
        socketApplication.getSocket().on("server_check_register_patient",onRegisterStatus);
        socketApplication.getSocket().on("server_execute_call",onCall);
        socketApplication.getSocket().on("server_send_acception_call_to_patient",onAcceptCall);
        socketApplication.getSocket().on("server_send_cancelation_call_to_patient",onCancelCall);
        socketApplication.getSocket().on("server_send_finish_call_to_patient",onFinishCall);
        socketApplication.getSocket().on("server_response_emr_dermatology_for_patient",onLoadEmrDermatology);
        socketApplication.getSocket().on("server_response_emr_female_for_patient",onLoadEmrFemale);
        socketApplication.getSocket().on("server_response_emr_mental_for_patient",onLoadEmrMental);
        socketApplication.getSocket().on("server_load_history_call",onLoadHistoryCall);
        socketApplication.getSocket().on("server_send_book_result",onBookStatus);

//        socketApplication.getSocket().on("server_reload_doctor",onReloadDoctor);

        //@formatter:off
        //@formatter:on
        session = new Session(getBaseContext());
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
        return START_NOT_STICKY;
    }
    private Emitter.Listener onBookStatus = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        Log.d("Register status","da nhan");
                        boolean pass = object.getBoolean("ketqua");
                        EventBus.getDefault().post(new EventBookStatus(pass));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onLoadHistoryCall = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        JSONArray danhsach_lichsu = (JSONArray)object.getJSONArray("historyList");
                        ArrayList<HistoryCall> historyCallArrayList = new ArrayList<>();
                        for(int i=0;i<danhsach_lichsu.length();i++){
                            JSONObject obj = (JSONObject)danhsach_lichsu.get(i);
                            int Call_id = obj.getInt("Call_id");
                            String Day = obj.getString("Day");
                            String Start_time = obj.getString("Start_time");
                            String End_time = obj.getString("End_time");
                            String Duration = obj.getString("Duration");
                            int Patient_id = obj.getInt("Patient_id");
                            int Doctor_id = obj.getInt("Doctor_id");
                            String Doctor_name = obj.getString("Doctor_name");
                            String Patient_name = obj.getString("Patient_name");
                            String Emr_type = obj.getString("Emr_type");
                            String Emr_status = String.valueOf(obj.getInt("Emr_status"));
                            int Emr_id = obj.getInt("Emr_id");
                            HistoryCall historyCall = new HistoryCall(Call_id,Day,Start_time,End_time,Duration,Patient_id,Doctor_id,Doctor_name,Patient_name,Emr_type,Emr_id,Emr_status);
                            historyCallArrayList.add(historyCall);
                        }
                        Log.d("EmrDermatology","true");
                        EventBus.getDefault().post(new EventGetHistoryCall(historyCallArrayList));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    // LOAD EMR EVENT
    private Emitter.Listener onLoadEmrDermatology = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        JSONArray danhsach_benhan = (JSONArray)object.getJSONArray("danhsach_benhan");
                        ArrayList<Emr_dermatology> emr_dermatologyArrayList = new ArrayList<>();
                        for(int i=0;i<danhsach_benhan.length();i++){
                            JSONObject obj = (JSONObject)danhsach_benhan.get(i);
                            int Emr_id = obj.getInt("Emr_id");
                            int Doctor_id = obj.getInt("Doctor_id");
                            int Patient_id = obj.getInt("Patient_id");
                            String Patient_name = obj.getString("Patient_name");
                            String Doctor_name = obj.getString("Doctor_name");
                            String Emr_date = obj.getString("Emr_date");
                            String Pathology = obj.getString("Pathology");
                            String History_disease = obj.getString("History_disease");
                            String Family = obj.getString("Family");
                            String Allergy = obj.getString("Allergy");
                            String Drug = obj.getString("Drug");
                            String Alcohol = obj.getString("Alcohol");
                            String Tobacco = obj.getString("Tobacco");
                            String Pipe_tobacco = obj.getString("Pipe_tobacco");
                            String Others_rf = obj.getString("Others_rf");
                            float Vascular = Float.parseFloat(String.valueOf(obj.getDouble("Vascular")));
                            float Temperature = Float.parseFloat(String.valueOf(obj.getDouble("Temperature")));
                            float Blood_pressure = Float.parseFloat(String.valueOf(obj.getDouble("Blood_pressure")));
                            float Breathing = Float.parseFloat(String.valueOf(obj.getDouble("Breathing")));
                            float Weight = Float.parseFloat(String.valueOf(obj.getDouble("Weight")));
                            String Body = obj.getString("Body");
                            String Functional_symtoms = obj.getString("Functional_symtoms");
                            String Basic_injury = obj.getString("Basic_injury");
                            String Clinical_test = obj.getString("Clinical_test");
                            String Summary = obj.getString("Summary");
                            String Cyclic = obj.getString("Cyclic");
                            String Respiratory = obj.getString("Respiratory");
                            String Digest = obj.getString("Digest");
                            String Kug = obj.getString("Kug");
                            String Peripheral_neuropathy = obj.getString("Peripheral_neuropathy");
                            String Others_o = obj.getString("Others_o");
                            String Main_disease = obj.getString("Main_disease");
                            String Secondary_disease = obj.getString("Secondary_disease");
                            String Distinguish = obj.getString("Distinguish");
                            String Prognosis = obj.getString("Prognosis");
                            String Treatment_direction_dt = obj.getString("Treatment_direction_dt");
                            String Pathology_process = obj.getString("Pathology_process");
                            String Labs_result = obj.getString("Labs_result");
                            String Treatments = obj.getString("Treatments");
                            String Patient_status = obj.getString("Patient_status");
                            String Treatment_direction_s = obj.getString("Treatment_direction_s");
                            String File_pic = obj.getString("File_pic");
                            int Emr_status = obj.getInt("Emr_status");
                            Emr_dermatology emr_dermatology = new Emr_dermatology(Emr_id,Patient_id,Doctor_id,Doctor_name,Patient_name,Emr_date,Pathology,History_disease,Family,Allergy,Drug,Alcohol,Tobacco,Pipe_tobacco,Others_rf,Vascular,Temperature,Blood_pressure,Breathing,Weight,Body,Functional_symtoms,Basic_injury,Clinical_test,Summary,Cyclic,Respiratory,Digest,Kug,Peripheral_neuropathy,Others_o,Main_disease,Secondary_disease,Distinguish,Prognosis,Treatment_direction_dt,Pathology_process,Labs_result,Treatments,Patient_status,Treatment_direction_s,File_pic,Emr_status);
                            emr_dermatologyArrayList.add(emr_dermatology);
                        }
                        Log.d("EmrDermatology","true");
                        EventBus.getDefault().post(new EventGetEmrDermatology(emr_dermatologyArrayList) );
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onLoadEmrFemale = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        JSONArray danhsach_benhan = (JSONArray)object.getJSONArray("danhsach_benhan");
                        ArrayList<Emr_female> emr_femaleArrayList = new ArrayList<>();
                        Log.d("EmrFemale","true");
                        for(int i=0;i<danhsach_benhan.length();i++){
                            JSONObject obj = (JSONObject)danhsach_benhan.get(i);
                            int Emr_id = obj.getInt("Emr_id");
                            int Patient_id = obj.getInt("Patient_id");
                            int Doctor_id = obj.getInt("Doctor_id");

                            int Periods_year = obj.getInt("Periods_year");
                            int Periods_age = obj.getInt("Periods_age");
                            int Periods_cycle = obj.getInt("Periods_cycle");
                            int Periods_noofdate = obj.getInt("Periods_noofdate");
                            int Stomachache = obj.getInt("Stomachache");
                            int Marriage_year = obj.getInt("Marriage_year");
                            int Marriage_age = obj.getInt("Marriage_age");
                            int Periods_endyear = obj.getInt("Periods_endyear");
                            int Periods_endage = obj.getInt("Periods_endage");
                            int Emr_status = obj.getInt("Emr_status");

                            float Vascular = Float.parseFloat(String.valueOf(obj.getDouble("Vascular")));
                            float Temperature = Float.parseFloat(String.valueOf(obj.getDouble("Temperature")));
                            float Blood_pressure = Float.parseFloat(String.valueOf(obj.getDouble("Blood_pressure")));
                            float Breathing = Float.parseFloat(String.valueOf(obj.getDouble("Breathing")));
                            float Weight = Float.parseFloat(String.valueOf(obj.getDouble("Weight")));

                            String Doctor_name = obj.getString("Doctor_name");
                            String Patient_name = obj.getString("Patient_name");
                            String Emr_date = obj.getString("Emr_date");
                            String Pathology = obj.getString("Pathology");
                            String History_disease = obj.getString("History_disease");
                            String Family = obj.getString("Family");
                            String Periods_nature = obj.getString("Periods_nature");
                            String Periods_amount = obj.getString("Periods_amount");
                            String Periods_lastdate = obj.getString("Periods_lastdate");
                            String Periods_treatment = obj.getString("Periods_treatment");
                            String Body = obj.getString("Body");
                            String Cyclic = obj.getString("Cyclic");
                            String Respiratory = obj.getString("Respiratory");
                            String Digest = obj.getString("Digest");
                            String Nerve = obj.getString("Nerve");
                            String Bone = obj.getString("Bone");
                            String Kidney = obj.getString("Kidney");
                            String Others_o = obj.getString("Others_o");
                            String Secondary_signs = obj.getString("Secondary_signs");
                            String Big_lips = obj.getString("Big_lips");
                            String Baby_lips = obj.getString("Baby_lips");
                            String Clitoris = obj.getString("Clitoris");
                            String Vulva = obj.getString("Vulva");
                            String Hymen = obj.getString("Hymen");
                            String Perineal = obj.getString("Perineal");
                            String Vagina = obj.getString("Vagina");
                            String Cervical = obj.getString("Cervical");
                            String The_uterus = obj.getString("The_uterus");
                            String Extra = obj.getString("Extra");
                            String Douglas = obj.getString("Douglas");
                            String Clinical_test = obj.getString("Clinical_test");
                            String Summary = obj.getString("Summary");
                            String Main_disease = obj.getString("Main_disease");
                            String Secondary_disease = obj.getString("Secondary_disease");
                            String Distinguish = obj.getString("Distinguish");
                            String Prognosis = obj.getString("Prognosis");
                            String Treatment_direction_dt = obj.getString("Treatment_direction_dt");
                            String Pathology_process = obj.getString("Pathology_process");
                            String Labs_result = obj.getString("Labs_result");
                            String Treatments = obj.getString("Treatments");
                            String Patient_status = obj.getString("Patient_status");
                            String Treatment_direction_s = obj.getString("Treatment_direction_s");
                            String File_pic = obj.getString("File_pic");
                            Emr_female emr_female = new Emr_female(Emr_id,Patient_id,Doctor_id,Doctor_name,Patient_name,Emr_date,Pathology,History_disease,Family,Periods_year,Periods_age,Periods_nature,Periods_cycle,Periods_noofdate,Periods_amount,Periods_lastdate,Stomachache,Marriage_year,Marriage_age,Periods_endyear,Periods_endage,Periods_treatment,Vascular,Temperature,Blood_pressure,Breathing,Weight,Body,Cyclic,Respiratory,Digest,Nerve,Bone,Kidney,Others_o,Secondary_signs,Big_lips,Baby_lips,Clitoris,Vulva,Hymen,Perineal,Vagina,Cervical,The_uterus,Extra,Douglas,Clinical_test,Summary,Main_disease,Secondary_disease,Distinguish,Prognosis,Treatment_direction_dt,Pathology_process,Labs_result,Treatments,Patient_status,Treatment_direction_s,File_pic,Emr_status);
                            emr_femaleArrayList.add(emr_female);
                        }
                        EventBus.getDefault().post(new EventGetEmrFemale(emr_femaleArrayList));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener onLoadEmrMental = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        JSONArray danhsach_benhan = (JSONArray)object.getJSONArray("danhsach_benhan");
                        ArrayList<Emr_mental> emr_mentalArrayList = new ArrayList<>();
                        Log.d("EmrMental","true");
                        for(int i=0;i<danhsach_benhan.length();i++){
                            JSONObject obj = (JSONObject)danhsach_benhan.get(i);
                            int Emr_id = obj.getInt("Emr_id");
                            int Doctor_id = obj.getInt("Doctor_id");
                            int Patient_id = obj.getInt("Patient_id");

                            String Patient_name = obj.getString("Patient_name");
                            String Doctor_name = obj.getString("Doctor_name");
                            String Emr_date = obj.getString("Emr_date");
                            String Pathology = obj.getString("Pathology");
                            String History_disease = obj.getString("History_disease");
                            String Family = obj.getString("Family");
                            String Allergy = obj.getString("Allergy");
                            String Drug = obj.getString("Drug");
                            String Alcohol = obj.getString("Alcohol");
                            String Tobacco = obj.getString("Tobacco");
                            String Pipe_tobacco = obj.getString("Pipe_tobacco");
                            String Others_rf = obj.getString("Others_rf");
                            float Vascular = Float.parseFloat(String.valueOf(obj.getDouble("Vascular")));
                            float Temperature = Float.parseFloat(String.valueOf(obj.getDouble("Temperature")));
                            float Blood_pressure = Float.parseFloat(String.valueOf(obj.getDouble("Blood_pressure")));
                            float Breathing = Float.parseFloat(String.valueOf(obj.getDouble("Breathing")));
                            float Weight = Float.parseFloat(String.valueOf(obj.getDouble("Weight")));
                            String Body = obj.getString("Body");
                            String Cyclic = obj.getString("Cyclic");
                            String Respiratory = obj.getString("Respiratory");
                            String Digest = obj.getString("Digest");
                            String Kidney = obj.getString("Kidney");
                            String Bone = obj.getString("Bone");
                            String Ear_nose_throat = obj.getString("Ear_nose_throat");
                            String Teeth = obj.getString("Teeth");
                            String Eye = obj.getString("Eye");
                            String Endocrine = obj.getString("Endocrine");
                            String Cranial_nerves = obj.getString("Cranial_nerves");
                            String Bottom_of_eye = obj.getString("Bottom_of_eye");
                            String Motor = obj.getString("Motor");
                            String Field_force = obj.getString("Field_force");
                            String Feel = obj.getString("Feel");
                            String Reflex = obj.getString("Reflex");
                            String General_expression = obj.getString("General_expression");
                            String Space = obj.getString("Space");
                            String Time = obj.getString("Time");
                            String Myself = obj.getString("Myself");
                            String Affection = obj.getString("Affection");
                            String Sense = obj.getString("Sense");
                            String Form = obj.getString("Form");
                            String Content = obj.getString("Content");
                            String Spirit = obj.getString("Spirit");
                            String Instinct = obj.getString("Instinct");
                            String Mechanically = obj.getString("Mechanically");
                            String Understandably = obj.getString("Understandably");
                            String Analytical = obj.getString("Analytical");
                            String Comprehensive = obj.getString("Comprehensive");
                            String Attention = obj.getString("Attention");
                            String Clinical_test = obj.getString("Clinical_test");
                            String Summary = obj.getString("Summary");
                            String Main_disease = obj.getString("Main_disease");
                            String Secondary_disease = obj.getString("Secondary_disease");
                            String Distinguish = obj.getString("Distinguish");
                            String Prognosis = obj.getString("Prognosis");
                            String Treatment_direction_dt = obj.getString("Treatment_direction_dt");
                            String Pathology_process = obj.getString("Pathology_process");
                            String Labs_result = obj.getString("Labs_result");
                            String Treatments = obj.getString("Treatments");
                            String Patient_status = obj.getString("Patient_status");
                            String Treatment_direction_s = obj.getString("Treatment_direction_s");
                            String File_pic = obj.getString("File_pic");
                            int Emr_status = obj.getInt("Emr_status");
                            Emr_mental emr_mental = new Emr_mental(Emr_id,Patient_id,Doctor_id,Patient_name,Doctor_name,Emr_date,Pathology,History_disease,Allergy,Drug,Alcohol,Tobacco,Pipe_tobacco,Others_rf,Family,Vascular,Temperature,Blood_pressure,Breathing,Weight,Body,Cyclic,Respiratory,Digest,Kidney,Bone,Ear_nose_throat,Teeth,Eye,Endocrine,Cranial_nerves,Bottom_of_eye,Motor,Field_force,Feel,Reflex,General_expression,Space,Time,Myself,Affection,Sense,Form,Content,Spirit,Instinct,Mechanically,Understandably,Analytical,Comprehensive,Attention,Clinical_test,Summary,Main_disease,Secondary_disease,Distinguish,Prognosis,Treatment_direction_dt,Pathology_process,Labs_result,Treatments,Patient_status,Treatment_direction_s,File_pic,Emr_status);
                            emr_mentalArrayList.add(emr_mental);
                        }
                        Log.d("EmrMental","true");
                        EventBus.getDefault().post(new EventGetEmrMental(emr_mentalArrayList));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };



    // END LOAD EMR EVENT

    // CALL EVENT
    private Emitter.Listener onFinishCall = new Emitter.Listener() {
        @Override
        public void call( final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventFinishCall("FinishCall"));
                }
            });
        }
    };
    private Emitter.Listener onCancelCall = new Emitter.Listener() {
        @Override
        public void call( final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventCancelCall("CancelCall"));
                }
            });
        }
    };
    private Emitter.Listener onAcceptCall = new Emitter.Listener() {
        @Override
        public void call( final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventAcceptCall("AcceptCall"));
                }
            });
        }
    };
    private Emitter.Listener onCall = new Emitter.Listener() {
        @Override
        public void call( final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj = (JSONObject)args[0];
                    try {
                        JSONArray connectionAu = obj.getJSONArray("call_info");
                        String sessionID ;
                        String token;
                        sessionID = connectionAu.getString(0);
                        token = connectionAu.getString(1);
                        EventBus.getDefault().post(new EventConnectCall(sessionID,token));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

    // END CALL EVENT

    // SERVER EVENT

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
                            new EventChangeChatServerStateEvent("connect error")
                    );
                }
            });
        }
    };

    // END SERVER EVENT

    // LOGIN ACTIVITY EVENT

    private Emitter.Listener onRegisterStatus = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        Log.d("Register status","da nhan");
                        boolean pass = object.getBoolean("ket_qua");
                        EventBus.getDefault().post(new EventCheckRegister(pass));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                        JSONArray patient_check = object.getJSONArray("ket_qua");
                        boolean pass = (Boolean)patient_check.get(1);
                        if(pass) {
                            JSONObject patient_info = (JSONObject) patient_check.get(0);
                            int patient_id = patient_info.getInt("Patient_id");
                            String username = patient_info.getString("Username");
                            String password = patient_info.getString("Password");
                            String patient_name = patient_info.getString("Patient_name");
                            String profile_picture = patient_info.getString("Profile_picture");
                            int gender = patient_info.getInt("Gender");
                            String sGender = "";
                            if (gender == 0) {
                                sGender = "Nữ";
                            } else if (gender == 1) {
                                sGender = "Nam";
                            }
                            String birthday = patient_info.getString("Birthday");
                            String phone_number = patient_info.getString("Phone_number");
                            String address_number = patient_info.getString("Address_number");
                            String address_street = patient_info.getString("Address_street");
                            String address_distric = patient_info.getString("Address_distric");
                            String address_city = patient_info.getString("Address_city");
                            float weight = Float.parseFloat(String.valueOf(patient_info.getDouble("Weight")));
                            float height = Float.parseFloat(String.valueOf(patient_info.getDouble("Height")));
                            String id_number = patient_info.getString("Id_number");
                            String socket_id = patient_info.getString("Socket_id");
                            String online_status = patient_info.getString("Online_status");
                            Log.d("Weight", String.valueOf(weight));
                            Patient patient = new Patient(patient_id, username, password, patient_name, profile_picture, sGender, birthday, phone_number, weight, height, id_number, address_number, address_street, address_distric, address_city, socket_id, online_status);
                            EventBus.getDefault().post(new EventCheckLogin(patient,pass));
                        }else if(!pass){
                            Patient patient = new Patient();
                            EventBus.getDefault().post(new EventCheckLogin(patient,pass));
                        }

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    // LOAD DOCTOR EVENT
    private Emitter.Listener onLoadDoctor = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        ArrayList<Doctor> arrayDoctorOnline = new ArrayList<>();
                        Log.d("Load Doctor","da nhan");
                        JSONArray arrayDoctor = object.getJSONArray("arrayDoctor");
                        for ( int i = 0 ; i< arrayDoctor.length();i++){
                            JSONObject obj = (JSONObject) arrayDoctor.get(i);
                            int doctor_id = obj.getInt("Doctor_id");
                            String username = obj.getString("Username");
                            String password = obj.getString("Password");
                            String doctor_name = obj.getString("Doctor_name");
                            String profile_picture = obj.getString("Profile_picture");
                            int gender = obj.getInt("Gender");
                            String sGender = "";
                            if(gender == 0) {
                                sGender = "Nữ";
                            }else if(gender == 1){
                                sGender = "Nam";
                            }
                            String clinic = obj.getString("Clinic");
                            String birthday = obj.getString("Birthday");
                            String phone_number = obj.getString("Phone_number");
                            String id_number = obj.getString("ID_Number");
                            String degree = obj.getString("Degree");
                            String address_number = obj.getString("Address_number");
                            String address_street = obj.getString("Address_street");
                            String address_distric = obj.getString("Address_distric");
                            String address_city = obj.getString("Address_city");
                            String description = obj.getString("Description");
                            String socket_id = obj.getString("Socket_id");
                            String online_status = obj.getString("Online_status");
                            Doctor doctor = new Doctor(doctor_id,username,password,doctor_name,profile_picture,sGender,clinic,degree,birthday,phone_number,id_number,address_number,address_street,address_distric,address_city,description,socket_id,online_status);
                            arrayDoctorOnline.add(doctor);
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
                        ArrayList<Doctor> arrayDoctorOnline = new ArrayList<>();
                        Log.d("Load Doctor","da nhan");
                        JSONArray arrayDoctor = object.getJSONArray("arrayDoctor");
                        for ( int i = 0 ; i< arrayDoctor.length();i++){
                            JSONObject obj = (JSONObject) arrayDoctor.get(i);
                            int doctor_id = obj.getInt("Doctor_id");
                            String username = obj.getString("Username");
                            String password = obj.getString("Password");
                            String doctor_name = obj.getString("Doctor_name");
                            String profile_picture = obj.getString("Profile_picture");
                            int gender = obj.getInt("Gender");
                            String sGender = "";
                            if(gender == 0) {
                                sGender = "Nữ";
                            }else if(gender == 1){
                                sGender = "Nam";
                            }
                            String clinic = obj.getString("Clinic");
                            String birthday = obj.getString("Birthday");
                            String phone_number = obj.getString("Phone_number");
                            String id_number = obj.getString("ID_Number");
                            String degree = obj.getString("Degree");
                            String address_number = obj.getString("Address_number");
                            String address_street = obj.getString("Address_street");
                            String address_distric = obj.getString("Address_distric");
                            String address_city = obj.getString("Address_city");
                            String description = obj.getString("Description");
                            String socket_id = obj.getString("Socket_id");
                            String online_status = obj.getString("Online_status");
                            Doctor doctor = new Doctor(doctor_id,username,password,doctor_name,profile_picture,sGender,clinic,degree,birthday,phone_number,id_number,address_number,address_street,address_distric,address_city,description,socket_id,online_status);
                            arrayDoctorOnline.add(doctor);
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
                        ArrayList<Doctor> arrayDoctorOnline = new ArrayList<>();
                        Log.d("Load Doctor","da nhan");
                        JSONArray arrayDoctor = object.getJSONArray("arrayDoctor");
                        for ( int i = 0 ; i< arrayDoctor.length();i++){
                            JSONObject obj = (JSONObject) arrayDoctor.get(i);
                            int doctor_id = obj.getInt("Doctor_id");
                            String username = obj.getString("Username");
                            String password = obj.getString("Password");
                            String doctor_name = obj.getString("Doctor_name");
                            String profile_picture = obj.getString("Profile_picture");
                            int gender = obj.getInt("Gender");
                            String sGender = "";
                            if(gender == 0) {
                                sGender = "Nữ";
                            }else if(gender == 1){
                                sGender = "Nam";
                            }
                            String clinic = obj.getString("Clinic");
                            String birthday = obj.getString("Birthday");
                            String phone_number = obj.getString("Phone_number");
                            String id_number = obj.getString("ID_Number");
                            String degree = obj.getString("Degree");
                            String address_number = obj.getString("Address_number");
                            String address_street = obj.getString("Address_street");
                            String address_distric = obj.getString("Address_distric");
                            String address_city = obj.getString("Address_city");
                            String description = obj.getString("Description");
                            String socket_id = obj.getString("Socket_id");
                            String online_status = obj.getString("Online_status");
                            Doctor doctor = new Doctor(doctor_id,username,password,doctor_name,profile_picture,sGender,clinic,degree,birthday,phone_number,id_number,address_number,address_street,address_distric,address_city,description,socket_id,online_status);
                            arrayDoctorOnline.add(doctor);
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
                        ArrayList<Doctor> arrayDoctorOnline = new ArrayList<>();
                        Log.d("Load Doctor","da nhan");
                        JSONArray arrayDoctor = object.getJSONArray("arrayDoctor");
                        for ( int i = 0 ; i< arrayDoctor.length();i++){
                            JSONObject obj = (JSONObject) arrayDoctor.get(i);
                            int doctor_id = obj.getInt("Doctor_id");
                            String username = obj.getString("Username");
                            String password = obj.getString("Password");
                            String doctor_name = obj.getString("Doctor_name");
                            String profile_picture = obj.getString("Profile_picture");
                            int gender = obj.getInt("Gender");
                            String sGender = "";
                            if(gender == 0) {
                                sGender = "Nữ";
                            }else if(gender == 1){
                                sGender = "Nam";
                            }
                            String clinic = obj.getString("Clinic");
                            String birthday = obj.getString("Birthday");
                            String phone_number = obj.getString("Phone_number");
                            String id_number = obj.getString("ID_Number");
                            String degree = obj.getString("Degree");
                            String address_number = obj.getString("Address_number");
                            String address_street = obj.getString("Address_street");
                            String address_distric = obj.getString("Address_distric");
                            String address_city = obj.getString("Address_city");
                            String description = obj.getString("Description");
                            String socket_id = obj.getString("Socket_id");
                            String online_status = obj.getString("Online_status");
                            Doctor doctor = new Doctor(doctor_id,username,password,doctor_name,profile_picture,sGender,clinic,degree,birthday,phone_number,id_number,address_number,address_street,address_distric,address_city,description,socket_id,online_status);
                            arrayDoctorOnline.add(doctor);
                        }
                        EventBus.getDefault().post(new EventReloadDoctorOnline3(arrayDoctorOnline));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    // END LOAD DOCTOR EVENT
    private void connectConnection() {
        instance = this;
        socketApplication.getSocket().connect();
    }

    private void disconnectConnection() {
        instance = null;
        socketApplication.getSocket().disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        socketApplication.getSocket().off(Socket.EVENT_CONNECT, onConnect);
        socketApplication.getSocket().off(Socket.EVENT_DISCONNECT, onDisconnect);
        socketApplication.getSocket().off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socketApplication.getSocket().off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socketApplication.getSocket().off("server_check_login_patient",onLoginStatus);
        socketApplication.getSocket().off("server_load_doctor_disease",onLoadDoctor);
        socketApplication.getSocket().off("server_reload_doctor_1",onReloadDoctor1);
        socketApplication.getSocket().off("server_reload_doctor_2",onReloadDoctor2);
        socketApplication.getSocket().off("server_reload_doctor_3",onReloadDoctor3);
        socketApplication.getSocket().off("server_check_register_patient",onRegisterStatus);
        socketApplication.getSocket().off("server_execute_call",onCall);
        socketApplication.getSocket().off("server_send_acception_call_to_patient",onAcceptCall);
        socketApplication.getSocket().off("server_send_cancelation_call_to_patient",onCancelCall);
        socketApplication.getSocket().off("server_send_finish_call_to_patient",onFinishCall);
        socketApplication.getSocket().off("server_response_emr_dermatology_for_patient",onLoadEmrDermatology);
        socketApplication.getSocket().off("server_response_emr_female_for_patient",onLoadEmrFemale);
        socketApplication.getSocket().off("server_response_emr_mental_for_patient",onLoadEmrMental);
        socketApplication.getSocket().off("server_load_history_call",onLoadHistoryCall);
        socketApplication.getSocket().on("server_send_book_result",onBookStatus);
//        socketApplication.getSocket().on("server_server_reload_doctor",onReloadDoctor);
        EventBus.getDefault().unregister(this);
        Log.d("disconnected","true");
        disconnectConnection();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getBaseContext(), event.getState(), Toast.LENGTH_SHORT).show();
    }

}
