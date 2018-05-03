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

import project.fpt.edu.vn.registerscreen.Model.HistoryCall;

/**
 * Created by User on 2/7/2018.
 */

public class ListAdapterHistory extends ArrayAdapter<HistoryCall> {
    public ListAdapterHistory(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ListAdapterHistory(Context context, int resource, List<HistoryCall> items){
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_line_history, null);
        }
       // Doctor d = getItem(position);
        HistoryCall hc = getItem(position);

        if(hc != null){
            TextView tv1 = (TextView)v.findViewById(R.id.TvHisDocName);
            tv1.setText("Bác sĩ : "+hc.getDoctor_name());

            TextView tv2 = (TextView)v.findViewById(R.id.TvHisDateCall);
            tv2.setText("Ngày : "+hc.getDay());

            TextView tv3 = (TextView)v.findViewById(R.id.TvHisTime);
            tv3.setText("Giờ : "+hc.getStart_time());

            /*TextView tv4 = (TextView)v.findViewById(R.id.TvMoney);
            tv4.setText(hc.historyMoney);*/
        }
        return v;
    }
}
