package com.ihjklj.pdc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.model.ImoocJson;

import java.util.List;

/**
 * Created by ihjklj on 2018/5/8.
 */

public class ImoocAdapter extends ArrayAdapter<ImoocJson.ImoocCourse> {

    private Context mContext;
    private int mResourceid;

    public ImoocAdapter(Context context, int resourceid, List<ImoocJson.ImoocCourse> data) {
        super(context, resourceid, data);
        mContext = context;
        mResourceid = resourceid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImoocJson.ImoocCourse course = getItem(position);
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResourceid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = view.findViewById(R.id.imooc_item_layout_title);
            viewHolder.student = view.findViewById(R.id.imooc_item_layout_student);
            viewHolder.arrow = view.findViewById(R.id.imooc_item_layout_arrow);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(course.getTitle());
        viewHolder.student.setText(String.valueOf(course.getStudent()));
        double indexSign = course.getIndex_sign();
        if (indexSign > 0) {
            viewHolder.arrow.setText(R.string.up_arrow);
            viewHolder.arrow.setTextColor(Color.RED);
        }
        else if (indexSign < 0) {
            viewHolder.arrow.setText(R.string.down_arrow);
            viewHolder.arrow.setTextColor(Color.BLACK);
        }
        return view;
    }

    public class ViewHolder {
        TextView title;
        TextView student;
        TextView arrow;
    }
}
