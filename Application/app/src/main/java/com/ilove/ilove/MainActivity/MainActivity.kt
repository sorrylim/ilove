package com.ilove.ilove.MainActivity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Fragment.*
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class MainActivity : PSAppCompatActivity() {

    var channelFragment : Fragment? = null
    var storyFragment : Fragment? = null
    var listFragment : Fragment? = null
    var messageFragment : Fragment? = null
    var profileFragment : Fragment? = null

    private val GPS_ENABLE_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 100

    val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    private val requiredPermission = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting()
        }
        else {
            checkRunTimePermission()
        }

        /*var gpsTracker = GpsTracker(this)

        var date = "2020-07-15 12:17:07"

        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var partnerDate : Date = simpleDateFormat.parse(date)

        var curTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
        val curDate = dateFormat.format(curTime)

        Toast.makeText(this, timeDiff(partnerDate.getTime()), Toast.LENGTH_LONG).show()

        Toast.makeText(this, gpsTracker.getDistance(35.8446984, 128.5479911, 35.8539492, 128.5790978), Toast.LENGTH_SHORT).show()*/

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("test", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                else {
                    // Get new Instance ID token
                    val token = task.result?.token

                    VolleyService.updateToken(UserInfo.ID, token, this, { success ->
                        UserInfo.TOKEN = token
                    })
                }
            })

        bnv_main.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            channelFragment = ChannelFragment()
            supportFragmentManager.beginTransaction().add(R.id.frame_main, channelFragment!!).commit()
        }
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.bnv_main_channel -> {
                if(channelFragment == null) {
                    channelFragment = ChannelFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, channelFragment!!).commit()
                }

                if(channelFragment != null) supportFragmentManager.beginTransaction().show(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().hide(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().hide(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().hide(profileFragment!!).commit()


                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_story -> {
                if(storyFragment == null) {
                    storyFragment = StoryFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, storyFragment!!).commit()
                }

                if(channelFragment != null) supportFragmentManager.beginTransaction().hide(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().show(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().hide(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().hide(profileFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_list -> {
                if(listFragment == null) {
                    listFragment = ListFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, listFragment!!).commit()
                }

                if(channelFragment != null) supportFragmentManager.beginTransaction().hide(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().hide(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().show(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().hide(profileFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_message -> {
                if(messageFragment == null) {
                    messageFragment = MessageFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, messageFragment!!).commit()
                }
                if(channelFragment != null) supportFragmentManager.beginTransaction().hide(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().hide(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().hide(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().show(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().hide(profileFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_profile -> {
                if(profileFragment == null) {
                    profileFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, profileFragment!!).commit()
                }
                if(channelFragment != null) supportFragmentManager.beginTransaction().hide(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().hide(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().hide(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().show(profileFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
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
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION)
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
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, requiredPermission.get(0))) {
                Toast.makeText(this@MainActivity, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show()
                ActivityCompat.requestPermissions(this@MainActivity, requiredPermission, PERMISSIONS_REQUEST_CODE)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity, requiredPermission, PERMISSIONS_REQUEST_CODE)
            }
        }
    }

    private fun showDialogForLocationServiceSetting() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
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

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



}