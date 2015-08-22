package com.tomchen.smsbomb.common;

import android.content.Context;
import android.os.AsyncTask;

public class CommonAsyncTask extends AsyncTask<Object, Void, Object> {

	private BaseAsyncTaskListener taskListener;

	public CommonAsyncTask(Context context, BaseAsyncTaskListener listener) {
		this.taskListener = listener;
	}

	@Override
	protected Object doInBackground(Object... params) {
		return this.taskListener.doInBackground(params);
	}

	@Override
	protected void onPostExecute(Object result) {
		this.taskListener.doAsyncTaskAfter(result);
		super.onPostExecute(result);
	}

	public interface BaseAsyncTaskListener {

		public Object doInBackground(Object... params);

		public void doAsyncTaskAfter(Object result);

	}

}