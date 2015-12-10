package com.moven.ghostalarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * ���ӹ�����
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  j00240464
 * @version  [�汾��, 2015-12-4]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public class GhostAlarm
{
    private AlarmReceiver mReceiver = new AlarmReceiver();
    
    private static GhostAlarm instance = new GhostAlarm();
    
    private GhostAlarm()
    {
        //ȷ���������ⲿ����ʼ��
    }
    
    public static GhostAlarm getInstance()
    {
        return instance;
    }
    
    /**
     * ��ʼ��
     * <������ϸ����>
     * @see [�ࡢ��#��������#��Ա]
     */
    public void init(Context context)
    {
        registerReceiver(context.getApplicationContext());
    }
    
    /**
     * ����ÿ���ظ�������
     * <������ϸ����>
     * @param hour 24Сʱ��
     * @param minute
     * @see [�ࡢ��#��������#��Ա]
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
