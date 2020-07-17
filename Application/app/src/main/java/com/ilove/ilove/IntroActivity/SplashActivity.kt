package com.ilove.ilove.IntroActivity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.MainActivity.MainActivity
import com.ilove.ilove.Object.VolleyService
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

        var userPref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        UserInfo.ID = userPref.getString("ID", "")!!
        UserInfo.PW = userPref.getString("PW", "")!!
        if(UserInfo.ID != "") {
            VolleyService.loginReq(UserInfo.ID, UserInfo.PW, this, { success->
                when(success.getInt("code")) {
                    0 -> {
                        //통신 실패
                        Handler().postDelayed({
                            //var intent: Intent = Intent(this,LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },2000)
                        Toast.makeText(this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
                        //로그인 성공
                        //프리퍼런스 저장
                        var user = success.getJSONObject("user")
                        UserInfo.ID=user.getString("user_id")
                        UserInfo.PW=user.getString("user_pw")
                        UserInfo.NICKNAME=user.getString("user_nickname")
                        UserInfo.BIRTHDAY=user.getString("user_birthday")
                        UserInfo.GENDER=user.getString("user_gender")
                        UserInfo.AUTHORITY=user.getString("user_authority")
                        UserInfo.PHONE=user.getString("user_phone")
                        UserInfo.BLOCKING=user.getInt("user_blocking")
                        UserInfo.ALARMLIKE=user.getInt("user_alarmlike")
                        UserInfo.ALARMMEET=user.getInt("user_alarmmeet")
                        UserInfo.ALARMCHECKPROFILE=user.getInt("user_alarmcheckprofile")
                        UserInfo.ALARMUPDATEPROFILE=user.getInt("user_alarmupdateprofile")
                        UserInfo.ALARMMESSAGE=user.getInt("user_alarmmessage")
                        UserInfo.CANDYCOUNT=user.getInt("user_candycount")
                        UserInfo.LIKECOUNT=user.getInt("user_likecount")
                        UserInfo.MESSAGETICKET=user.getInt("user_messageticket")
                        UserInfo.VIP = user.getInt("user_vip")


                        var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        var editor=pref.edit()
                        editor.putString("ID",UserInfo.ID)
                            .putString("PW",UserInfo.PW)
                            .putString("NICKNAME",UserInfo.NICKNAME)
                            .putString("BIRTHDAY",UserInfo.BIRTHDAY)
                            .putString("PHONE",UserInfo.PHONE)
                            .putString("GENDER",UserInfo.GENDER)
                            .putString("AUTHORITY",UserInfo.AUTHORITY)
                            .putInt("BLOCKING",UserInfo.BLOCKING)
                            .putInt("ALARMLIKE", UserInfo.ALARMLIKE)
                            .putInt("ALARMMEET", UserInfo.ALARMMEET)
                            .putInt("ALARMCHECKPROFILE", UserInfo.ALARMCHECKPROFILE)
                            .putInt("ALARMUPDATEPROFILE", UserInfo.ALARMUPDATEPROFILE)
                            .putInt("ALARMMESSAGE", UserInfo.ALARMMESSAGE)
                            .putInt("CANDYCOUNT", UserInfo.CANDYCOUNT)
                            .putInt("LIKECOUNT", UserInfo.LIKECOUNT)
                            .putInt("MESSAGETICKET", UserInfo.MESSAGETICKET)
                            .putInt("VIP", UserInfo.VIP)
                            .apply()

                        Handler().postDelayed({
                            var intent: Intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },2000)
                    }
                }
            })
        }
        else {
            Handler().postDelayed({
                //var intent= Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

}