package com.moven.ghostalarm;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
    //小时值
    private EditText hourText;
    
    private EditText minuteText;
    
    private Button setAlarmBtn;
    
    AlarmReceiver mReceiver;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initListener();
    }
    
    private void init()
    {
        hourText = (EditText)findViewById(R.id.hour);
        minuteText = (EditText)findViewById(R.id.minute);
        setAlarmBtn = (Button)findViewById(R.id.set_alart_btn);
        GhostAlarm.getInstance().init(MainActivity.this);
    }
    
    private void initListener()
    {
        setAlarmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.set_alart_btn)
        {
            setAlarm();
        }
    }
    
    private void setAlarm()
    {
        int hour = Integer.parseInt(hourText.getText().toString());
        int minute = Integer.parseInt(minuteText.getText().toString());
        GhostAlarm.getInstance().setEveryDayRepeatAlarm(hour, minute, MainActivity.this);
        Toast.makeText(MainActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onDestroy()
    {                       
        super.onDestroy();
        GhostAlarm.getInstance().unRegisterReceiver(MainActivity.this.getApplicationContext());
    }
}
