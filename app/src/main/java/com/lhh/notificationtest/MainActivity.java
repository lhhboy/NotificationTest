package com.lhh.notificationtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendNotice = findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_notice:
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //高版本需要渠道
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel("1", "name", NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(notificationChannel);

                }
                Intent intent = new Intent(this, NotificationActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
                Notification notification = new NotificationCompat.Builder(this, "1")
                        .setContentTitle("这是内容标题")
                        .setContentText("这是文本内容")
                        .setWhen(System.currentTimeMillis())//时间
                        .setSmallIcon(R.drawable.ic_launcher_background)//小图
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))//大图
                        .setContentIntent(pi)//跳转页面
                        .setAutoCancel(true)//点击后取消通知
                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))//播放音频
                        .setVibrate(new long[]{0,1000,1000,1000})//振动，需要加入权限VIBRATE
                        .setLights(Color.RED,1000,1000)//呼吸灯
                       // .setDefaults(NotificationCompat.DEFAULT_ALL)//手机环境默认
                        .build();
                manager.notify(1, notification);
                break;
            default:
                break;
        }
    }
}
