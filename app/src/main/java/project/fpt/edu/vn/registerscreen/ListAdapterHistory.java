package project.fpt.edu.vn.registerscreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

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
            tv1.setText(hc.historyName);

            TextView tv2 = (TextView)v.findViewById(R.id.TvHisDateCall);
            tv2.setText(hc.historyDate);

            TextView tv3 = (TextView)v.findViewById(R.id.TvHisTime);
            tv3.setText(hc.historyTime);

            /*TextView tv4 = (TextView)v.findViewById(R.id.TvMoney);
            tv4.setText(hc.historyMoney);*/
        }
        return v;
    }
}
