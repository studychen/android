package com.monitor.ui;

import java.util.List;
import java.util.Map;

import com.monitor.main.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpandAdapter extends BaseAdapter {

	private List<Map<String, String>> listData;
	private LayoutInflater mlayoutInflater;
	private int expandPosition = -1;

	public ExpandAdapter(Context context, List<Map<String, String>> listData) {
		this.listData = listData;
		mlayoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return null == listData ? 0 : listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = mlayoutInflater.inflate(R.layout.web_info, null);

		TextView nameZh = (TextView) convertView.findViewById(R.id.nameZh);
		ImageView isDown = (ImageView) convertView.findViewById(R.id.isDown);
		Map<String, String> singleInfo = listData.get(position);

		nameZh.setText(singleInfo.get("nameZh"));
		if (singleInfo.get("isDown").equals("false")) {
			isDown.setImageResource(R.drawable.green_middle);
		} else {
			isDown.setImageResource(R.drawable.red_middle);
		}

		RelativeLayout expand = (RelativeLayout) convertView.findViewById(R.id.bar_layout);
		LinearLayout item = (LinearLayout) convertView.findViewById(R.id.item);		

		if (null != singleInfo) {
			expand.setOnClickListener(new ExpandClickListener(
					position));
			// 如果点击的是当前项，则将其展开，否则将其隐藏
			if (expandPosition == position) {
				item.setVisibility(View.VISIBLE);
			} else {
				item.setVisibility(View.GONE);
			}
		}

		return convertView;
	}

	class ExpandClickListener implements OnClickListener {
		private int position;

		public ExpandClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// 如果当前项为展开，则将其置为-1，目的是为了让其隐藏，如果当前项为隐藏，则将当前位置设置给全局变量，让其展开，这也就是借助于中间变量实现布局的展开与隐藏
			if (expandPosition == position) {
				expandPosition = -1;
			} else {
				expandPosition = position;
			}
			notifyDataSetChanged();
		}

	}

}
