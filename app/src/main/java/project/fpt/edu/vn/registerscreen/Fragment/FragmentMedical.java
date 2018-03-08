package project.fpt.edu.vn.registerscreen.Fragment;


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

import project.fpt.edu.vn.registerscreen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMedical extends Fragment {


    public FragmentMedical() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
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
