package project.fpt.edu.vn.registerscreen.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListFemale;
import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListDermatology;
import project.fpt.edu.vn.registerscreen.Activity.DoctorList.ActivityDoctorListMental;
import project.fpt.edu.vn.registerscreen.R;
import project.fpt.edu.vn.registerscreen.Session;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    Session session;
    TextView txtClientName;
    LinearLayout mental,female,dermatology;
    public FragmentHome() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mental = (LinearLayout)view.findViewById(R.id.mental);
        female = (LinearLayout)view.findViewById(R.id.female);
        dermatology = (LinearLayout)view.findViewById(R.id.dermatology);
        SetEvent();
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
        session = new Session(getContext());

        return view;
    }
    public void SetEvent(){
        mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ActivityDoctorListMental.class);
                startActivity(intent);
            }
        });
        female.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ActivityDoctorListFemale.class);
                startActivity(intent);
            }
        });
        dermatology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ActivityDoctorListDermatology.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
