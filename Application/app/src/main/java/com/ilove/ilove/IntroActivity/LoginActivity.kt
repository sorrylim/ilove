package com.ilove.ilove.IntroActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.MainActivity.MainActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_chat_drawerlayout.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        text_signup.setOnClickListener {
            var intent: Intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }


        btn_login.setOnClickListener {
            VolleyService.loginReq(edit_loginid.text.toString(), edit_loginpw.text.toString(), this, {success->
                Log.d("test", success.getInt("code").toString())
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
                        UserInfo.PW=user.getString("user_password")
                        UserInfo.NICKNAME=user.getString("user_nickname")
                        UserInfo.BIRTHDAY=user.getString("user_birthday")
                        UserInfo.GENDER=user.getString("user_gender")
                        UserInfo.AUTHORITY=user.getString("user_authority")
                        UserInfo.PHONE=user.getString("user_phone")
                        UserInfo.BLOCKING=user.getInt("user_blocking")
                        UserInfo.ALARMLIKE=user.getInt("alarm_like")
                        UserInfo.ALARMMEET=user.getInt("alarm_meet")
                        UserInfo.ALARMCHECKPROFILE=user.getInt("alarm_checkprofile")
                        UserInfo.ALARMUPDATEPROFILE=user.getInt("alarm_updateprofile")
                        UserInfo.ALARMMESSAGE=user.getInt("alarm_message")
                        UserInfo.CANDYCOUNT=user.getInt("user_candycount")
                        UserInfo.LIKECOUNT=user.getInt("user_likecount")
                        UserInfo.MESSAGETICKET=user.getInt("user_messageticket")
                        UserInfo.VIP = user.getString("user_vip")


                        var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        var editor=pref.edit()
                        editor.putString("ID", UserInfo.ID)
                            .putString("PW", UserInfo.PW)
                            .putString("NICKNAME", UserInfo.NICKNAME)
                            .putString("BIRTHDAY", UserInfo.BIRTHDAY)
                            .putString("PHONE", UserInfo.PHONE)
                            .putString("GENDER", UserInfo.GENDER)
                            .putString("AUTHORITY", UserInfo.AUTHORITY)
                            .putInt("BLOCKING", UserInfo.BLOCKING)
                            .putInt("ALARMLIKE", UserInfo.ALARMLIKE)
                            .putInt("ALARMMEET", UserInfo.ALARMMEET)
                            .putInt("ALARMCHECKPROFILE", UserInfo.ALARMCHECKPROFILE)
                            .putInt("ALARMUPDATEPROFILE", UserInfo.ALARMUPDATEPROFILE)
                            .putInt("ALARMMESSAGE", UserInfo.ALARMMESSAGE)
                            .putInt("CANDYCOUNT", UserInfo.CANDYCOUNT)
                            .putInt("LIKECOUNT", UserInfo.LIKECOUNT)
                            .putInt("MESSAGETICKET", UserInfo.MESSAGETICKET)
                            .putString("VIP", UserInfo.VIP)
                            .apply()

                        Handler().postDelayed({
                            var intent: Intent = Intent(this, SplashActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },2000)
                    }
                }
            })
        }
    }
    override fun onBackPressed() {
        this.moveTaskToBack(true)
        this.finishAndRemoveTask()
        android.os.Process.killProcess(android.os.Process.myPid())
        }
    }
