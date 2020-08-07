package com.ilove.ilove.IntroActivity

import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.MainActivity.MainActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class SplashActivity : AppCompatActivity() {
    private val GPS_ENABLE_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100

    private val requiredPermission = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)




        var userPref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        UserInfo.ID = "01041545154"//userPref.getString("ID", "")!!
        if(UserInfo.ID != "") {
            VolleyService.loginReq(UserInfo.ID, "", this, { success->
                when(success.getInt("code")) {
                    0 -> {
                        //통신 실패
                        Handler().postDelayed({
                            var intent: Intent = Intent(this, StartActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },2000)
                        Toast.makeText(this, "서버와의 통신에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    3 -> {
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


                        if(!checkLocationServicesStatus()) {
                            showDialogForLocationServiceSetting()
                        }
                        else {
                            checkRunTimePermission()
                        }

                        //로그인 성공
                        //프리퍼런스 저장
                        var user = success.getJSONObject("user")
                        UserInfo.ID=user.getString("user_id")
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
                        UserInfo.VIP = user.getInt("user_vip")
                        UserInfo.GUIDE = user.getInt("user_guide")
                        UserInfo.ENABLE = user.getInt("user_enable")

                        if(UserInfo.BLOCKING == 1) {
                            VolleyService.getMyBlockingListReq(UserInfo.ID, this, {success->
                                UserInfo.BLOCKINGNUMBER = success.getString("blocking_partner")
                            })
                        }


                        var pref=this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        var editor=pref.edit()
                        editor.putString("ID",UserInfo.ID)
                            .apply()

                        /*Handler().postDelayed({
                            var intent: Intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                            startActivity(intent)
                            finish()
                        },2000)*/
                    }
                }
            })
        }
        else {
            Handler().postDelayed({
                var intent= Intent(this, StartActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)  //액티비티 전환시 애니메이션을 무시
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == requiredPermission.size) {
            var checkResult = true

            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    showDialogForLocationServiceSetting()
                    break
                }
            }

            if(checkResult) {
                var gpsTracker = GpsTracker(this)

                UserInfo.LATITUDE = gpsTracker.getLatitude().toString()
                UserInfo.LONGITUDE = gpsTracker.getLongitude().toString()

                VolleyService.updateRecentGps(UserInfo.ID, UserInfo.LATITUDE.toString() + "," + UserInfo.LONGITUDE.toString() , currentDate,this, {success->
                    if(success != "success") {
                        Toast.makeText(this, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
                gpsTracker.stopUsingGPS()

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, requiredPermission.get(0)) || ActivityCompat.shouldShowRequestPermissionRationale(this, requiredPermission.get(1))) {
                Toast.makeText(this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                finish();
            }else {
                Toast.makeText(this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
            }
        }
    }

    fun checkRunTimePermission() {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(this@SplashActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this@SplashActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            var gpsTracker = GpsTracker(this)


            UserInfo.LATITUDE = gpsTracker.getLatitude().toString()
            UserInfo.LONGITUDE = gpsTracker.getLongitude().toString()

            VolleyService.updateRecentGps(UserInfo.ID, UserInfo.LATITUDE.toString() + "," + UserInfo.LONGITUDE.toString() , currentDate,this, {success->
                if(success != "success") {
                    Toast.makeText(this, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                }
            })
            gpsTracker.stopUsingGPS()

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@SplashActivity, requiredPermission.get(0))) {
                ActivityCompat.requestPermissions(this@SplashActivity, requiredPermission, PERMISSIONS_REQUEST_CODE)
            } else {
                ActivityCompat.requestPermissions(this@SplashActivity, requiredPermission, PERMISSIONS_REQUEST_CODE)
            }
        }
    }

    private fun showDialogForLocationServiceSetting() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@SplashActivity)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            """
                앱을 사용하기 위해서는 위치 서비스가 필요합니다.
                위치 설정을 수정하시겠습니까?
                """.trimIndent()
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, id ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_CODE)
        })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, id ->
                moveTaskToBack(true)
                finishAndRemoveTask()
                android.os.Process.killProcess(android.os.Process.myPid())})
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GPS_ENABLE_CODE ->
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음")
                        checkRunTimePermission()
                        return
                    }
                }
        }
    }

    fun checkLocationServicesStatus() : Boolean {
        var locationManager : LocationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER);
    }

}