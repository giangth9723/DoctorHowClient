package project.fpt.edu.vn.registerscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by User on 2/5/2018.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private String[] tenBenh;
    private int[] hinhBenh;

    public GridViewAdapter(Context context, String[] tenBenh, int[] hinhBenh) {
        this.context = context;
        this.tenBenh = tenBenh;
        this.hinhBenh = hinhBenh;
    }

    @Override
    public int getCount() {
        return tenBenh.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.gridview_row, null);
        TextView textView = (TextView)view.findViewById(R.id.tvPicName);
        ImageView imageView = (ImageView)view.findViewById(R.id.imgPic);
        textView.setText(tenBenh[i]);
        imageView.setImageResource(hinhBenh[i]);
        return view;
    }
}