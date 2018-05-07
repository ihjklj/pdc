package com.ihjklj.pdc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.model.ImoocItem;
import java.util.List;

/**
 * Created by ihjklj on 2018/5/8.
 */

public class ImoocAdapter extends ArrayAdapter<ImoocItem> {

    private Context mContext;
    private int mResourceid;

    public ImoocAdapter(Context context, int resourceid, List<ImoocItem> data) {
        super(context, resourceid, data);
        mContext = context;
        mResourceid = resourceid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImoocItem item = getItem(position);
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResourceid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = view.findViewById(R.id.imooc_item_layout_image);
            viewHolder.title = view.findViewById(R.id.imooc_item_layout_title);
            viewHolder.student = view.findViewById(R.id.imooc_item_layout_student);
            viewHolder.arrow = view.findViewById(R.id.imooc_item_layout_arrow);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.image.setImageResource(item.getImageId());
        viewHolder.title.setText(item.getTitle());
        viewHolder.student.setText(item.getStudent());
        viewHolder.arrow.setImageResource(item.getArrow());
        return view;
    }

    public class ViewHolder {
        ImageView image;
        TextView title;
        TextView student;
        ImageView arrow;
    }
}
