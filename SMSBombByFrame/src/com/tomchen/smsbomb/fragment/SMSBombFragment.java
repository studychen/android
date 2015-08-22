package com.tomchen.smsbomb.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tomchen.smsbomb.activity.R;
import com.tomchen.smsbomb.common.CommonAsyncTask;
import com.tomchen.smsbomb.common.LevelLogUtils;
import com.tomchen.smsbomb.common.NetworkHelper;
import com.tomchen.smsbomb.common.ToastHelper;
import com.tomchen.smsbomb.request.BombManager;
import com.tomchen.smsbomb.view.FastChangeTextView;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SMSBombFragment extends Fragment {
	private FastChangeTextView singleNumberField;
	private FastChangeTextView totalNumberField;

	private Integer singleSuccess = 0;
	private Integer totalSuccess = 0;

	private EditText phoneField;
	private EditText timesField;

	private Button fireupButton;
	private Button clearButton;

	private String phoneString;
	private int timesInt;

	private static final int TEMP_CHANGE_FAST = 0;

	private static final int TOAST_REMINDER = 2;

	private static final String tag = "bomb";

	private Handler handleSuccess = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TEMP_CHANGE_FAST:
				singleNumberField.setValue(timesInt);
				double originTotal = Double.valueOf(totalNumberField.getText().toString());
				totalNumberField.setValue(originTotal, originTotal + timesInt);
				LevelLogUtils.getInstance().i(tag, "in while handler");
				break;
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_bomb, container, false);

		initView(v);
		setButtonListener();

		return v;
	}

	/**
	 * initial component in the XML file
	 */
	private void initView(View v) {

		singleNumberField = (FastChangeTextView) v.findViewById(R.id.single_bomb_number);
		totalNumberField = (FastChangeTextView) v.findViewById(R.id.total_bomb_number);

		phoneField = (EditText) v.findViewById(R.id.phone);
		timesField = (EditText) v.findViewById(R.id.times);

		fireupButton = (Button) v.findViewById(R.id.fireup);
		clearButton = (Button) v.findViewById(R.id.clear);
	}

	private void setButtonListener() {
		fireupButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// check network
				if (!NetworkHelper.isNetworkAvailable(getActivity())) {
					ToastHelper.showToastLong(getActivity(), R.string.network_not_avaiable);
					return;
				}

				initValue();
				
				if (phoneString.length() != 11) {
					ToastHelper.showToastLong(getActivity(), R.string.phone_format_reminder);
					return;
				}
				// qu na er web get
				// hu pu get request

				List<RequestGetPostTask> taskList = new ArrayList<RequestGetPostTask>();
				for (int i = 0; i < timesInt; i++) {
					RequestGetPostTask singleTask = new RequestGetPostTask(getActivity(), bobmResultListener);
					taskList.add(singleTask);
					singleTask.execute();
				}

				new UpdateAccountTask().execute(taskList);

				handleSuccess.sendEmptyMessage(TEMP_CHANGE_FAST);

			}
		});

		clearButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				singleNumberField.setText(R.string.bomb_origin_value);
				totalNumberField.setText(R.string.bomb_origin_value);

			}
		});
	}

	/**
	 * initial phone number and times
	 */
	private void initValue() {
		singleSuccess = 0;

		phoneString = phoneField.getText().toString().trim();
		
		String TimesEditTextInput = timesField.getText().toString().trim();
		if (TimesEditTextInput == null || TimesEditTextInput.equals("")) {
			timesInt = 3;
		} else {
			timesInt = Integer.valueOf(TimesEditTextInput);
		}

	}

	private class UpdateAccountTask extends AsyncTask<List<RequestGetPostTask>, Void, Void> {

		@Override
		protected Void doInBackground(List<RequestGetPostTask>... params) {
			List<RequestGetPostTask> taskList = params[0];

			while (true) {
				if (taskList == null || taskList.size() == 0) {
					break;
				}
				Iterator<RequestGetPostTask> it = taskList.iterator();
				while (it.hasNext()) {
					if (it.next().getStatus().equals(AsyncTask.Status.FINISHED)) {
						it.remove();
					}
				}
				if (taskList == null || taskList.size() == 0) {
					break;
				}
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Double originVaule = Double.valueOf(singleNumberField.getText().toString());
			singleNumberField.setValue(originVaule, Double.valueOf(singleSuccess));

			originVaule = Double.valueOf(totalNumberField.getText().toString());
			totalNumberField.setValue(originVaule, Double.valueOf(totalSuccess));

			super.onPostExecute(result);
		}

	}

	/**
	 * need update the amount of successful bomb message update UI in main
	 * thread use method
	 * 
	 * @author tomchen
	 *
	 */
	private class RequestGetPostTask extends CommonAsyncTask {

		public RequestGetPostTask(Context context, BaseAsyncTaskListener listener) {
			super(context, listener);
		}

	}

	private CommonAsyncTask.BaseAsyncTaskListener bobmResultListener = new CommonAsyncTask.BaseAsyncTaskListener() {

		@Override
		public Object doInBackground(Object... params) {
			return new BombManager(phoneString).randomSingleBomb();
		}

		@Override
		public void doAsyncTaskAfter(Object result) {
			synchronized (totalSuccess) {
				if (result instanceof Boolean) {
					if ((Boolean) result) {
						++totalSuccess;
					}
				}
			}

			synchronized (singleSuccess) {
				if (result instanceof Boolean) {
					if ((Boolean) result) {
						++singleSuccess;
					}
				}
			}
		}
	};

}
