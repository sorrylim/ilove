package com.ilove.ilove.IntroActivity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //알림 채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var fcmPref=this.getSharedPreferences("FCM", Context.MODE_PRIVATE)
            if(fcmPref.getString("id","")==""){
                val notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notificationChannel = NotificationChannel(
                    "fcm_ilove",
                    "fcm_ilove",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationChannel.description = "ilove fcm channel"
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(true)
                notificationChannel.vibrationPattern = longArrayOf(100, 200, 100, 200)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                notificationChannel.setShowBadge(true)
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }
}