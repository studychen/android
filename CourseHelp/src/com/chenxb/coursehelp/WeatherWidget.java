package com.chenxb.coursehelp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class WeatherWidget extends AppWidgetProvider {
//	@Override
//	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
//			int[] appWidgetIds) {
//		super.onUpdate(context, appWidgetManager, appWidgetIds);
//		
//		//这样在第一次运行时也能响应用户的单击事件
//		getWeatherView(context);
//		
//		//启动一个自定义更新widget的后台服务
//		context.startService(new Intent(context,UpdateWidgetService.class));
//	}
//	
//	@Override //当删除最后一个Widget组件后调用
//	public void onDisabled(Context context) {
//		super.onDisabled(context);
//		//关闭后台服务
//		context.stopService(new Intent(context,UpdateWidgetService.class));
//	}
//
//	//返回widget中的布局视图对象
//	public static RemoteViews getWeatherView(Context context){
//		RemoteViews views=new RemoteViews(context.getPackageName(), R.layout.widget_layout);
//		
//		//当击widget的主体来启动MainActivity返回到天气精灵的天气显示界面
//		Intent intent = new Intent(context, MainActivity.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//		views.setOnClickPendingIntent(R.id.weather_rootLayout, pendingIntent);
//		
//		return views;
//	}
//	
//	public static void updateAppWidget(RemoteViews views, Context context, 
//			AppWidgetManager appWidgetManager, String cityCode) {
//		
//		SharedPreferences shared = context.getSharedPreferences(MainActivity.STORE_WEATHER, MainActivity.MODE_PRIVATE);
//		long currentTime = System.currentTimeMillis();
//		//得到天气缓冲文件中的有效期
//		long vaildTime = shared.getLong("validTime", currentTime);
//		//比较天气缓存文件中的有效期，如果超时了，则访问网络更新天气
//		if(vaildTime <= currentTime)
//			updateWeather(views, context, cityCode);
//		else
//			updateWeather(views, context);
//		
//		//更新时间
//		Date date = new Date();
//		SimpleDateFormat foramt = new SimpleDateFormat("hh:mm");
//		String timeText = foramt.format(date);
//		Log.i("widget", "===================update  time======"+timeText+"=====================");
//		views.setTextViewText(R.id.widget_time , timeText);
//	}
//
//	//由缓存文件来得到天气信息
//	public static void updateWeather(RemoteViews views, Context context) {
//		SharedPreferences sp = context.getSharedPreferences(MainActivity.STORE_WEATHER,MainActivity.MODE_PRIVATE);
//		
//		String info=sp.getString("city", "");
//		views.setTextViewText(R.id.widget_city, info);
//		
//		info=sp.getString("date_y", "");
//		views.setTextViewText(R.id.widget_data01, info);
//		
//		info= sp.getString("temp1", "");
//		views.setTextViewText(R.id.widget_temp, info);
//		
//		info= sp.getString("weather1", "");
//		views.setTextViewText(R.id.widget_weather, info);
//		
//		views.setImageViewResource(R.id.widget_icon, sp.getInt("img_title1", R.drawable.weathericon_condition_17));
//	}
//	
//	//从网络中更新天气文件和views中的显示数据
//	public static void updateWeather(RemoteViews views, Context context, String cityCode) {
//		//由城市码更新天气
//		StringBuffer str = new StringBuffer("http://m.weather.com.cn/data/");
//		str.append(cityCode);
//		str.append(".html");
//		try {
//			String info =new WebAccessTools(context).getWebContent(str.toString());
//			JSONObject json=new JSONObject(info).getJSONObject("weatherinfo");
//			int weather_icon = 0;
//			
//			//建立一个缓存天气的文件
//			SharedPreferences.Editor editor = context.getSharedPreferences(MainActivity.STORE_WEATHER,
//					MainActivity.MODE_PRIVATE).edit();
//			
//			//得到城市
//			info=json.getString("city");
//			editor.putString("city", info);
//			
//			views.setTextViewText(R.id.widget_city, info);
//			
//			//得到阳历日期
//			info= json.getString("date_y") ;
//			info= info+"("+json.getString("week")+")";
//			editor.putString("date_y", info);
//			
//			views.setTextViewText(R.id.widget_data01, info);
//			
//			//得到农历
//			info= json.getString("date");
//			editor.putString("date", info);
//			//得到温度
//			info= json.getString("temp1");
//			editor.putString("temp1", info);
//			
//			views.setTextViewText(R.id.widget_temp, info);
//			//得到天气
//			info= json.getString("weather1");
//			editor.putString("weather1", info);
//			
//			views.setTextViewText(R.id.widget_weather, info);
//			//天气图标
//			info= json.getString("img_title1");
//			weather_icon = MainActivity.getWeatherBitMapResource(info);
//			editor.putInt("img_title1", weather_icon);
//			
//			views.setImageViewResource(R.id.widget_icon, weather_icon);
//			//得到风向
//			info= json.getString("wind1");
//			editor.putString("wind1", info);
//			//得到建议
//			info= json.getString("index_d");
//			editor.putString("index_d", info);
//			
//			//得到明天的天气
//			info= json.getString("weather2");
//			editor.putString("weather2", info);
//			//明天的图标
//			info= json.getString("img_title2");
//			weather_icon = MainActivity.getWeatherBitMapResource(info);
//			editor.putInt("img_title2", weather_icon);
//			//明天的气温
//			info= json.getString("temp2");
//			editor.putString("temp2", info);
//			//明天的风力
//			info= json.getString("wind2");
//			editor.putString("wind2", info);
//			
//			//后天的天气
//			info= json.getString("weather3");
//			editor.putString("weather3", info);
//			//后天天气图标
//			info= json.getString("img_title3");
//			weather_icon = MainActivity.getWeatherBitMapResource(info);
//			editor.putInt("img_title3", weather_icon);
//			//后天的气温
//			info= json.getString("temp3");
//			editor.putString("temp3", info);
//			//后天的风力
//			info= json.getString("wind3");
//			editor.putString("wind3", info);
//			
//			//设置一个有效日期为5小时
//			long validTime = System.currentTimeMillis();
//			validTime = validTime + 5*60*60*1000;
//			editor.putLong("validTime", validTime);
//			
//			//保存
//			editor.commit();
//		}catch(JSONException e) {
//			e.printStackTrace();
//		}
//	}
}
