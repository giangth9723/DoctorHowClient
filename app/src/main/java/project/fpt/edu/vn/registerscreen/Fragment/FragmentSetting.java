package project.fpt.edu.vn.registerscreen.Fragment;


import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import project.fpt.edu.vn.registerscreen.Activity.UserPasswordChangeActivity;
import project.fpt.edu.vn.registerscreen.Activity.UserProfileActivity;
import project.fpt.edu.vn.registerscreen.Activity.LoginActivity;
import project.fpt.edu.vn.registerscreen.Activity.UserProfileChangeActivity;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BusEvent.EventChangeChatServerStateEvent;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetting extends Fragment {

    public FragmentSetting() {
        // Required empty public constructor
    }

    private TextView tvName;
    private Session session;
    private Button BtnLogout;
    private ListView listView;
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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        EventBus.getDefault().register(this);

        socketApplication = (SocketApplication) getActivity().getApplication();
        Log.d("Check", "FragmentSetting created");
        if (socketApplication.getSocket() != null) {
            Log.d("Socket", " is not null");
            getActivity().startService(new Intent(getActivity(), SocketServiceProvider.class));
            doBindService();
        } else {
        }

        //  tvName = (TextView)view.findViewById(R.id.YourName);
      //  BtnLogout = (Button)view.findViewById(R.id.BtnLogout);
        session = new Session(getContext());
       // tvName.setText(session.getName());
        if(!session.LoggedIn()){
            Logout();
        }

        /*BtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });*/
        listView = (ListView)view.findViewById(R.id.ListSetting);
        final String[] option = {"Thông tin cá nhân", "Chỉnh sửa thông tin cá nhân","Đổi mật khẩu",
                "Điều khoản", "Hỏi đáp", "Đăng xuất"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.my_simple_list_item01, option);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        startActivity(new Intent(getContext(), UserProfileActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), UserProfileChangeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getContext(), UserPasswordChangeActivity.class));
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Đăng xuất");
                        builder.setMessage("Bạn muốn đăng xuất không?");
                        builder.setNegativeButton("Không", null);
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Logout();

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
                }
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
    private void doBindService() {
        getActivity().bindService(new Intent(getActivity(), SocketServiceProvider.class), socketConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }
    private void Logout(){
        socketApplication.getSocket().emit("patient_logout",session.getPatient().getUsername());
        getActivity().stopService(new Intent(getActivity(),SocketServiceProvider.class));
        session.setLoggedIn(false);
        getActivity().finish();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if(mIsBound) {
            Log.d("Unbind Service","true");
            getActivity().unbindService(socketConnection);
            mIsBound = false;
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventChangeChatServerStateEvent event) {
        Toast.makeText(getActivity(), event.getState(), Toast.LENGTH_SHORT).show();
    }
}
