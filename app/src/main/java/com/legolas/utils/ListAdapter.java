package com.legolas.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.legolas.VO.ListViewVO;
import com.legolas.activity.R;

import java.util.List;


/**
 * Created by legolas on 2016/4/7.
 */
public class ListAdapter extends ArrayAdapter<ListViewVO> {
    private int resourceId;
    public ListAdapter(Context context, int textViewResourceId, List<ListViewVO> objects){
        super(context , textViewResourceId , objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent ){
        ListViewVO listViewVO = getItem(position);
        View view ;
        ViewHolder viewHolder;
        if(convertView == null ){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder.id = (TextView)view.findViewById(R.id.id);
            viewHolder.name = (TextView)view.findViewById(R.id.name);
            viewHolder.date = (TextView)view.findViewById(R.id.date);
            viewHolder.audit = (TextView)view.findViewById(R.id.audit);
            view.setTag(viewHolder);
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.id.setText(String.valueOf(listViewVO.getId()));
        viewHolder.name.setText(listViewVO.getName());
        viewHolder.date.setText(listViewVO.getDate());
        if(listViewVO.getAudit() == 'N')
        {
        viewHolder.audit.setText("未审核");
        viewHolder.audit.setTextColor(Color.BLUE);
        }
        else
        {
        viewHolder.audit.setText("已审核");
        viewHolder.audit.setTextColor(Color.GRAY);
        }
        return view;
    }

    class ViewHolder{
        TextView id;
        TextView name;
        TextView date;
        TextView audit;
    }
}
