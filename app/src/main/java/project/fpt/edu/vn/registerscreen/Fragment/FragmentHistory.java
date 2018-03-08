package project.fpt.edu.vn.registerscreen.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import project.fpt.edu.vn.registerscreen.Activity.ActivityDoctorList;
import project.fpt.edu.vn.registerscreen.Activity.ActivityHistoryDoctorView;
import project.fpt.edu.vn.registerscreen.HistoryCall;
import project.fpt.edu.vn.registerscreen.ListAdapterHistory;
import project.fpt.edu.vn.registerscreen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistory extends Fragment {

    public static final String NAME = "NAME";
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";
    public static final String MONEY = "MONEY";

    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        lv = (ListView) view.findViewById(R.id.ListHistory);
        final ArrayList<HistoryCall> arrayHistory = new ArrayList<HistoryCall>();
        arrayHistory.add(new HistoryCall("Doctor A", "08/02/2017", "230 giây", "30 coin"));
        arrayHistory.add(new HistoryCall("Doctor B", "18/04/2017", "230 giây", "30 coin"));
        arrayHistory.add(new HistoryCall("Doctor C", "01/12/2017", "130 giây", "20 coin"));
        arrayHistory.add(new HistoryCall("Doctor D", "09/03/2017", "30 giây", "10 coin"));

        ListAdapterHistory adapterHistory = new ListAdapterHistory(
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

}
