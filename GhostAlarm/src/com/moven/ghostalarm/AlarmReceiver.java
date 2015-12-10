package com.moven.ghostalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Constants.EVERYDAY_REPEAT_ALARM_ACTION))
        {
            
            Toast.makeText(context, "µΩ ±º‰¿≤", Toast.LENGTH_SHORT).show();
            Bundle bundle = intent.getExtras();
            int hour = bundle.getInt(Constants.HOUR);
            int minute = bundle.getInt(Constants.MINUTE);
            GhostAlarm.getInstance().setEveryDayRepeatAlarm(hour, minute, context, true);
        }
    }
    
}
