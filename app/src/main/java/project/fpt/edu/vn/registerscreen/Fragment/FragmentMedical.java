package project.fpt.edu.vn.registerscreen.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import project.fpt.edu.vn.registerscreen.Activity.Medical.ActivityDermaMenu;
import project.fpt.edu.vn.registerscreen.Activity.Medical.ActivityFemaleMenu;
import project.fpt.edu.vn.registerscreen.Activity.Medical.ActivityMentalMenu;
import project.fpt.edu.vn.registerscreen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMedical extends Fragment {


    CardView cvDermatology, cvFemale, cvMental;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical, container, false);
        cvDermatology = (CardView) view.findViewById(R.id.cvDermatology);
        cvFemale = (CardView) view.findViewById(R.id.cvFemale);
        cvMental = (CardView) view.findViewById(R.id.cvMental);

        cvDermatology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityDermaMenu.class);
                //intent.putExtra(NAME, "Dermatology");
                startActivity(intent);
                //startActivity(new Intent(getContext(), ActivityMedicalMenu.class));
            }
        });

        cvFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getContext(), ActivityMedicalMenu.class));
                Intent intent = new Intent(getContext(), ActivityFemaleMenu.class);
                //intent.putExtra(NAME, "Female");
                startActivity(intent);
            }
        });

        cvMental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getContext(), ActivityMedicalMenu.class));
                Intent intent = new Intent(getContext(), ActivityMentalMenu.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
