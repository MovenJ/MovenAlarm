package com.moven.ghostalarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * 闹钟工具类
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  j00240464
 * @version  [版本号, 2015-12-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class GhostAlarm
{
    private AlarmReceiver mReceiver = new AlarmReceiver();
    
    private static GhostAlarm instance = new GhostAlarm();
    
    private GhostAlarm()
    {
        //确保不能在外部被初始化
    }
    
    public static GhostAlarm getInstance()
    {
        return instance;
    }
    
    /**
     * 初始化
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    public void init(Context context)
    {
        registerReceiver(context.getApplicationContext());
    }
    
    /**
     * 设置每天重复的闹钟
     * <功能详细描述>
     * @param hour 24小时制
     * @param minute
     * @see [类、类#方法、类#成员]
     */
    public void setEveryDayRepeatAlarm(int hour, int minute, Context context)
    {
        setEveryDayRepeatAlarm(hour, minute, context, false);
    }
    
    public void setEveryDayRepeatAlarm(int hour, int minute, Context context, boolean isReset)
    {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(Constants.EVERYDAY_REPEAT_ALARM_ACTION);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.HOUR, hour);
        bundle.putInt(Constants.MINUTE, minute);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        if (isReset)
        {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        else 
        {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        
    }
    
    public void setRepeating()
    {
        
    }
    
    public void registerReceiver(Context context)
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.EVERYDAY_REPEAT_ALARM_ACTION);
        context.registerReceiver(mReceiver, filter);
    }
    
    public void unRegisterReceiver(Context context)
    {
        context.unregisterReceiver(mReceiver);
    }
}
