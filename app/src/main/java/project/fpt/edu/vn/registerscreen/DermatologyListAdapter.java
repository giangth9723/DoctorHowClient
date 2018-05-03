package project.fpt.edu.vn.registerscreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import project.fpt.edu.vn.registerscreen.Model.Emr_dermatology;

/**
 * Created by User on 4/2/2018.
 */

public class DermatologyListAdapter extends ArrayAdapter<Emr_dermatology> {

    public DermatologyListAdapter(Context context, int resource, List<Emr_dermatology> items){
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.content_examination_list, null);
        }
        Emr_dermatology el = getItem(position);

        if(el != null){
            TextView tv2 = (TextView)v.findViewById(R.id.txtDate);
            tv2.setText("Ngày : "+formatDate(el.getEmr_date()));

            TextView tv3 = (TextView)v.findViewById(R.id.txtTime);
            tv3.setText("Bác sĩ : "+el.getDoctor_name());

        }
        return v;
    }
    public String formatDate(String date){
        String[] part = date.split("-");
        String pp1 = part[0];
        String pp2 = part[1];
        String pp3 = part[2];
        String p2 = part[2]+"-"+part[1]+"-"+part[0];
        date = p2;
        return date;
    }

}