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
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import project.fpt.edu.vn.registerscreen.Activity.ActivityDoctorList;
import project.fpt.edu.vn.registerscreen.Activity.LoginActivity;
import project.fpt.edu.vn.registerscreen.Activity.MenuActivity;
import project.fpt.edu.vn.registerscreen.GridViewAdapter;
import project.fpt.edu.vn.registerscreen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    public static final String NAME = "NAME";
    GridView gridView;
    private ListView lv;
    String[] ten = {
            "Anh 01","Anh 02","Anh 03","Anh 04"
    };
    int[] hinh = {
            R.drawable.logo01, R.drawable.logo02, R.drawable.logo03, R.drawable.logo04
    };
    String PicName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //gridView = (GridView)view.findViewById(R.id.GridView);

        //GridViewAdapter gridViewAdapter = new GridViewAdapter(getContext(),ten,hinh);
        //gridView.setAdapter(gridViewAdapter);

        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(), ten[i], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ActivityDoctorList.class);
                intent.putExtra(NAME, ten[i]);
                startActivity(intent);
            }
        });
        */
        lv = (ListView) view.findViewById(R.id.lstOption);
        final String[] option = {"Bệnh tâm lý", "Bệnh thai sản","Bệnh nam khoa", "Bệnh phụ khoa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.my_simple_list_item01, option);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(getActivity(), ActivityDoctorList.class);
                        intent.putExtra(NAME, option[i]);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getActivity(), ActivityDoctorList.class);
                        intent2.putExtra(NAME, option[i]);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getActivity(), ActivityDoctorList.class);
                        intent3.putExtra(NAME, option[i]);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(getActivity(), ActivityDoctorList.class);
                        intent4.putExtra(NAME, option[i]);
                        startActivity(intent4);
                        break;
                }
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
