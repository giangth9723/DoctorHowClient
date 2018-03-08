package project.fpt.edu.vn.registerscreen.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

import java.net.Socket;
import java.net.URISyntaxException;

import io.socket.client.IO;
import project.fpt.edu.vn.registerscreen.Application.SocketApplication;
import project.fpt.edu.vn.registerscreen.BottomNavigationHelper;
import project.fpt.edu.vn.registerscreen.CustomTypefaceSpan;
import project.fpt.edu.vn.registerscreen.Fragment.FragmentHistory;
import project.fpt.edu.vn.registerscreen.Fragment.FragmentHome;
import project.fpt.edu.vn.registerscreen.Fragment.FragmentMedical;
import project.fpt.edu.vn.registerscreen.Fragment.FragmentSetting;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Service.SocketServiceProvider;
import project.fpt.edu.vn.registerscreen.Session;

public class MenuActivity extends AppCompatActivity {
    Session session;
    TextView tvShowName;
    FragmentManager fragmentManager = getSupportFragmentManager();
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentHome fragmentHome = new FragmentHome();
                    FragmentTransaction fragmentTransaction01 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction01.replace(R.id.RelLayoutMid, fragmentHome, "Fragment Home");
                    fragmentTransaction01.commit();
                    return true;
                case R.id.navigation_history:
                    FragmentHistory fragmentHistory = new FragmentHistory();
                    FragmentTransaction fragmentTransaction02 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction02.replace(R.id.RelLayoutMid, fragmentHistory, "Fragment History");
                    fragmentTransaction02.commit();
                    return true;
                case R.id.navigation_medical:
                    FragmentMedical fragmentMedical = new FragmentMedical();
                    FragmentTransaction fragmentTransaction03 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction03.replace(R.id.RelLayoutMid, fragmentMedical, "Fragment Medical");
                    fragmentTransaction03.commit();
                    return true;
                case R.id.navigation_setting:
                    FragmentSetting fragmentSetting = new FragmentSetting();
                    FragmentTransaction fragmentTransaction04 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction04.replace(R.id.RelLayoutMid, fragmentSetting, "Fragment Setting");
                    fragmentTransaction04.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationHelper.disableShiftMode(navigation);

        FragmentHome fragmentHome = new FragmentHome();
        FragmentTransaction fragmentTransaction01 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction01.replace(R.id.RelLayoutMid, fragmentHome, "Fragment Home");
        fragmentTransaction01.commit();

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);

        session = new Session(this);

        if(!session.LoggedIn()){
            Logout();
        }
        tvShowName = (TextView)findViewById(R.id.txtShowName);
        tvShowName.setText(session.getName());


    }

    private void Logout(){
        session.setLoggedIn(false);
        finish();
        startActivity(new Intent(MenuActivity.this, LoginActivity.class));
    }

    @Override
    public void onBackPressed(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       /*if(!menu.getItem(0).isChecked()){
           FragmentHome fragmentHome = new FragmentHome();
           FragmentTransaction fragmentTransaction01 = getSupportFragmentManager().beginTransaction();
           fragmentTransaction01.replace(R.id.RelLayoutMid, fragmentHome, "Fragment Home");
           fragmentTransaction01.commit();
           menuItem.setChecked(true);
       }
       else if(menu.getItem(0).isChecked()){
           finish();
       }*/

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
