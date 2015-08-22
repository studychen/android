package com.chenxb.adapter;

import java.util.List;

import com.chenxb.coursebean.Card;
import com.chenxb.coursehelp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CardAdapter extends BaseAdapter {
	
	private List<Card> data;  
    private Context context;  
    private LayoutInflater mInflater;  
      
    public CardAdapter(List<Card> data, Context context) {  
        this.data = data;  
        this.context = context;  
        mInflater = LayoutInflater.from(context);  
    }  

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();  
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;  
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;  
        if(convertView==null){  
            convertView = mInflater.inflate(R.layout.list_item, null);  
              
            holder = new ViewHolder();  
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);  
            holder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);  
              
            convertView.setTag(holder);  
        }else{  
            holder = (ViewHolder) convertView.getTag();  
        }  
          
        Card card = data.get(position);  
        holder.tv_name.setText(card.getName());  
        holder.tv_desc.setText(card.getDesc());  
          
        return convertView;  
	}
	
	static class ViewHolder{  
        TextView tv_name;  
        TextView tv_desc;  
    }  

}
