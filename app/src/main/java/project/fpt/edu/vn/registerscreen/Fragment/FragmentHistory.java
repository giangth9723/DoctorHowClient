package project.fpt.edu.vn.registerscreen.Fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Activity.ActivityHistoryDoctorView;
import project.fpt.edu.vn.registerscreen.Activity.LoginActivity;
import project.fpt.edu.vn.registerscreen.Activity.MenuActivity;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.BusEvent.EventGetHistoryCall;
import project.fpt.edu.vn.registerscreen.Model.HistoryCall;
import project.fpt.edu.vn.registerscreen.ListAdapterHistory;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistory extends Fragment {
    ArrayList<HistoryCall> arrayHistory = new ArrayList<HistoryCall>();
    public static final String NAME = "NAME";
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";
    public static final String MONEY = "MONEY";
    ListAdapterHistory adapterHistory;
    Session session;
    ListView lv;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        lv = (ListView) view.findViewById(R.id.ListHistory);
        onCreateSocket();
        session = new Session(getContext());
        socketApplication.getSocket().emit("patient_load_history_call",session.getPatient().getPatient_id());
        adapterHistory = new ListAdapterHistory(
                getContext(),
                R.layout.fragment_history,
                arrayHistory
        );
        lv.setAdapter(adapterHistory);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ActivityHistoryDoctorView.class);
                intent.putExtra("array_history", arrayHistory.get(i));
                startActivity(intent);
            }
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    BottomNavigationView navigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
                    Menu menu = navigation.getMenu();
                    MenuItem menuItem = menu.getItem(0);
                    FragmentHome fragmentHome = new FragmentHome();
                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.RelLayoutMid, fragmentHome, "Fragment Home");
                    fragmentTransaction.commit();
                    menuItem.setChecked(true);
                    return true;
                }
                return false;
            }
        });
        return view;
    }
    private void onCreateSocket(){
        socketApplication = (SocketApplication) getActivity().getApplication();
        Log.d("Check", "Login created");
        if (socketApplication.getSocket() != null) {
            Log.d("Socket", " is not null");
            getActivity().startService(new Intent(getActivity(), SocketServiceProvider.class));
            doBindService();
        } else {
        }
    }
    private void doBindService() {
        getActivity().bindService(new Intent(getContext(), SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        if(mIsBound) {
            getActivity().unbindService(socketConnection);
            mIsBound = false;
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getActivity(), event.getState(), Toast.LENGTH_SHORT).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventGetHistoryCall event){
        arrayHistory.clear();
        for(int i=0;i<event.getHistoryCallArrayList().size();i++){
            arrayHistory.add(event.getHistoryCallArrayList().get(i));
        }
        adapterHistory.notifyDataSetChanged();
    }

}
