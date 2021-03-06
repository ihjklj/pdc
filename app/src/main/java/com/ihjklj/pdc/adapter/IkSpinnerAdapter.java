package com.ihjklj.pdc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ihjklj.pdc.R;
import com.ihjklj.pdc.model.IkSpinnerItem;
import java.util.List;

/**
 * Created by ihjklj on 2018/5/8.
 */

public class IkSpinnerAdapter extends BaseAdapter {

    private List<IkSpinnerItem> mList;
    private Context mContext;

    public IkSpinnerAdapter(Context context, List<IkSpinnerItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = null;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.spinner_item_layout, parent, false);
            viewHolder.textview = (TextView)view.findViewById(R.id.linechart_item_layout_text);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textview.setText(mList.get(position).getStr());
        return view;
    }

    public class ViewHolder {
        public TextView textview;
    }
}
