package com.tomchen.smsbomb.view;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.tomchen.smsbomb.activity.R;
import com.tomchen.smsbomb.common.LevelLogUtils;
import com.tomchen.smsbomb.common.ToastHelper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class FastChangeTextView extends TextView {
	private double value;

	private double currentValue;

	private double rate;

	private static final int ADD_UPDATE = 0;
	private static final int MINUS_UPDATE = 1;
	private static final String tag = "bomb";

	static DecimalFormat df = new DecimalFormat("0.00");

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			// need add current value to reach value
			case ADD_UPDATE:
				if (currentValue < value) {
					setText(df.format(currentValue));
					currentValue = currentValue + rate;
					sendEmptyMessageDelayed(ADD_UPDATE, 10);
				} else {
					setText(df.format(value));
					// as value > current value, so value will not be 0
				}

				break;

			// need minus current value to reach value
			case MINUS_UPDATE:
				if (currentValue > value) {
					setText(df.format(currentValue));
					currentValue = currentValue + rate;
					sendEmptyMessageDelayed(MINUS_UPDATE, 10);
				} else {
					setText(df.format(value));
				}
				break;

			default:
				break;

			}
			super.handleMessage(msg);
		}

	};

	public FastChangeTextView(Context context) {
		super(context);
	}

	public FastChangeTextView(Context context, AttributeSet set) {
		super(context, set);
	}

	public FastChangeTextView(Context context, AttributeSet set, int defStyle) {
		super(context, set, defStyle);
	}

	public void setValue(double lastValue) {
		setValue(0, lastValue);
	}

	public void setValue(double originValue, double lastValue) {
		double oldRate = rate;
		currentValue = originValue;
		value = lastValue;
		// just negate to ensure add and minus have the same rate
		if (originValue < lastValue) {
			if (oldRate != 0) {
				rate = oldRate > 0 ? oldRate : -oldRate;
			} else {
				rate = (lastValue - originValue) / 500.0;
			}
		}

		if (originValue > lastValue) {
			if (oldRate != 0) {
				rate = oldRate < 0 ? oldRate : -oldRate;
			} else {
				rate = (lastValue - originValue) / 500.0;
			}
		}

		if (originValue < lastValue) {
			mHandler.sendEmptyMessage(ADD_UPDATE);

		}
		if (originValue > lastValue) {
			mHandler.sendEmptyMessage(MINUS_UPDATE);
		}
	}
}
