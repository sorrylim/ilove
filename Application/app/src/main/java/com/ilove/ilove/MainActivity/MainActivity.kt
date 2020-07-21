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
import com.google.firebase.messaging.FirebaseMessaging
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Fragment.*
import com.ilove.ilove.IntroActivity.ChargeCandyActivity
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



    val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
    val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic(UserInfo.ID)
            .addOnCompleteListener {
                Log.d("test","success subscribe to topic")
            }


        btn_candy.setOnClickListener {
            var intent = Intent(this, ChargeCandyActivity::class.java)
            startActivity(intent)
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
            channelFragment = ChannelFragment(text_maintoolbar)
            supportFragmentManager.beginTransaction().add(R.id.frame_main, channelFragment!!).commit()
        }
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.bnv_main_channel -> {
                if(channelFragment == null) {
                    channelFragment = ChannelFragment(text_maintoolbar)
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
                    storyFragment = StoryFragment(text_maintoolbar)
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
                    listFragment = ListFragment(text_maintoolbar)
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
                    messageFragment = MessageFragment(text_maintoolbar)
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
                    profileFragment = ProfileFragment(text_maintoolbar)
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





}