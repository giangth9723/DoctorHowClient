package project.fpt.edu.vn.registerscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import project.fpt.edu.vn.registerscreen.Activity.ActivityAppoint;
import project.fpt.edu.vn.registerscreen.Activity.WaitingCallActivity;
import project.fpt.edu.vn.registerscreen.Model.DoctorOnline;

/**
 * Created by User on 2/6/2018.
 */

public class ListAdapter extends ArrayAdapter<DoctorOnline> {
    public ListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ListAdapter(Context context, int resource, List<DoctorOnline> items){
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_line_doctor, null);
        }
        final DoctorOnline d = getItem(position);
        final ImageButton ib = (ImageButton)v.findViewById(R.id.imgCall);
        final ImageView iv = (ImageView)v.findViewById(R.id.imgStatus);
        final ImageButton ib2 = (ImageButton) v.findViewById(R.id.imgAppoint);

        if(d != null){
            TextView tv1 = (TextView)v.findViewById(R.id.tvListDocName);
            tv1.setText("Bác sĩ "+d.getDoctorName());
            if(d.getStatus() == "online"){
                iv.setColorFilter(getContext().getResources().getColor(R.color.colorAccent));
                ib.setBackgroundResource(R.drawable.not_available_border);
                ib.setColorFilter(getContext().getResources().getColor(R.color.colorGray));
                ib.setEnabled(false);
                ib2.setBackgroundResource(R.drawable.not_available_border);
                ib2.setColorFilter(getContext().getResources().getColor(R.color.colorGray));
                ib2.setEnabled(false);
            }

        }

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Drawable thumb = ContextCompat.getDrawable(getContext(), R.drawable.green_button);
                //ib.setBackgroundResource(R.drawable.green_button);
                Toast.makeText(view.getContext(), "Button Working", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(getContext().getApplicationContext(), WaitingCallActivity.class);
                bundle.putString("Socket_id",d.getSocketID());
                bundle.putString("Doctor_name",d.getDoctorName());
                bundle.putString("Activity_name",getContext().getClass().getSimpleName());
                intent.putExtra("Doctor_data",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                getContext().getApplicationContext().startActivity(intent);
                ((Activity)getContext()).finish();
            }
        });

        ib2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), ActivityAppoint.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                getContext().getApplicationContext().startActivity(intent);
            }
        });

        return v;
    }
}
