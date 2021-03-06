package com.ilove.ilove.IntroActivity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import android.widget.Switch
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.MainActivity.MainActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_alarm_setting.*

class AlarmSettingActivity : PSAppCompatActivity() {

    private val requiredPermission = arrayOf(
        android.Manifest.permission.READ_CONTACTS
    )

    private val PERMISSIONS_REQUEST_CODE = 100
    private val GPS_ENABLE_CODE = 2001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)


        toolbarBinding(toolbar_alarmsetting, "알림설정", true)

        settingSwitch(switch_alarmupprofile,UserInfo.ALARMUPDATEPROFILE)
        settingSwitch(switch_alarmcheckprofile,UserInfo.ALARMCHECKPROFILE)
        settingSwitch(switch_alarmlike,UserInfo.ALARMLIKE)
        settingSwitch(switch_alarmmessage,UserInfo.ALARMMESSAGE)
        settingSwitch(switch_alarmmeet,UserInfo.ALARMMEET)
        settingSwitch(switch_blockfriend, UserInfo.BLOCKING)

        switch_blockfriend.setOnClickListener {
            if(switch_blockfriend.isChecked == false) {
                updateAlarm("blocking", false)
                switch_blockfriend.isChecked = false
                VolleyService.deleteReq("blocking", "blocking_user='${UserInfo.ID}'", this, {success->
                    VolleyService.updateReq("user", "user_blockingnumber=''", "user_id='${UserInfo.ID}'", this, {success->
                        var userPref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        var editor = userPref.edit()
                        editor.putString("BLOCKINGNUMBER", "")
                            .putInt("BLOCKING", 0)
                            .apply()

                        UserInfo.BLOCKING = 0
                        UserInfo.BLOCKINGNUMBER = ""
                    })
                })
            }
            else if(switch_blockfriend.isChecked == true) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    checkContactsPermission()
                }
                else
                {
                    switch_blockfriend.isChecked = true
                    val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    val projection = arrayOf(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )

                    val cursor = contentResolver.query(
                        uri, projection, null,
                        null , null
                    )


                    var blockingNumber = ""

                    if (cursor!!.moveToFirst()) {
                        do {
                            if (cursor.getString(0).startsWith("01")) {
                                blockingNumber += (cursor.getString(0).replace("-","").replace(" ", "") + ",")
                            }
                        } while (cursor.moveToNext())
                    }

                    Log.d(
                        "test",
                        "phone=" + blockingNumber
                    )

                    VolleyService.updateReq("user", "user_blockingnumber='${blockingNumber}'", "user_id='${UserInfo.ID}'", this, {success->
                        VolleyService.insertReq("blocking", "blocking_user, blocking_partner", "'${UserInfo.ID}','${blockingNumber}'", this, {success->
                            var userPref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                            var editor = userPref.edit()
                            editor.putString("BLOCKINGNUMBER", blockingNumber)
                                .putInt("BLOCKING", 1)
                                .apply()

                            UserInfo.BLOCKING = 1
                            UserInfo.BLOCKINGNUMBER = blockingNumber
                        })
                    })
                    cursor?.close()
                }
            }
        }

        /*switch_blockfriend.setOnCheckedChangeListener { compoundButton, b ->
            when(b) {
                true -> {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        checkContactsPermission()
                    }
                    else
                    {
                        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                        val projection = arrayOf(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )

                        val cursor = contentResolver.query(
                            uri, projection, null,
                            null , null
                        )


                        var blockingNumber = ""

                        if (cursor!!.moveToFirst()) {
                            do {
                                if (cursor.getString(0).startsWith("01")) {
                                    blockingNumber += (cursor.getString(0).replace("-","").replace(" ", "") + ",")
                                }
                            } while (cursor.moveToNext())
                        }

                        Log.d(
                            "test",
                            "phone=" + blockingNumber
                        )

                        VolleyService.updateReq("user", "user_blockingnumber='${blockingNumber}'", "user_id='${UserInfo.ID}'", this, {success->
                            VolleyService.insertReq("blocking", "blocking_user, blocking_partner", "'${UserInfo.ID}','${blockingNumber}'", this, {success->
                                var userPref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                                var editor = userPref.edit()
                                editor.putString("BLOCKINGNUMBER", blockingNumber)
                                    .putInt("BLOCKING", 1)
                                    .apply()

                                UserInfo.BLOCKING = 1
                                UserInfo.BLOCKINGNUMBER = blockingNumber
                            })
                        })
                        cursor?.close()
                    }
                }
                false -> {
                    updateAlarm("blocking", b)
                    VolleyService.deleteReq("blocking", "blocking_user='${UserInfo.ID}'", this, {success->
                        VolleyService.updateReq("user", "user_blockingnumber=''", "user_id='${UserInfo.ID}'", this, {success->
                            var userPref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                            var editor = userPref.edit()
                            editor.putString("BLOCKINGNUMBER", "")
                                .putInt("BLOCKING", 0)
                                .apply()

                            UserInfo.BLOCKING = 0
                            UserInfo.BLOCKINGNUMBER = ""
                        })
                    })
                }
            }
        }*/

        switch_alarmupprofile.setOnCheckedChangeListener { compoundButton, b ->
            updateAlarm("upprofile",b)
        }

        switch_alarmcheckprofile.setOnCheckedChangeListener { compoundButton, b ->
            updateAlarm("visit",b)
        }

        switch_alarmlike.setOnCheckedChangeListener { compoundButton, b ->
            updateAlarm("like",b)
        }

        switch_alarmmessage.setOnCheckedChangeListener { compoundButton, b ->
            updateAlarm("chat",b)
        }

        switch_alarmmeet.setOnCheckedChangeListener { compoundButton, b ->
            updateAlarm("meet",b)
        }
    }

    fun settingSwitch(switch: Switch, state: Int){
        if(state==1) switch.isChecked=true
        else switch.isChecked=false
    }


    fun updateAlarm(alarmType:String,alarmState:Boolean){
        VolleyService.updateAlarm(UserInfo.ID,alarmType,alarmState,this)
    }

    private fun showDialogForLocationServiceSetting() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@AlarmSettingActivity)
        builder.setTitle("주소록 비활성화")
        builder.setMessage(
            """
                지인차단 기능을 사용하기 위해서는 주소록 권한이 필요합니다.
                주소록 권한 설정을 수정하시겠습니까?
                """.trimIndent()
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, id ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()})
        builder.create().show()
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
                switch_blockfriend.isChecked = true
                updateAlarm("blocking", true)
                val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                val projection = arrayOf(
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                )

                val cursor = contentResolver.query(
                    uri, projection, null,
                    null , null
                )


                var blockingNumber = ""

                if (cursor!!.moveToFirst()) {
                    do {
                        if (cursor.getString(0).startsWith("01")) {
                            blockingNumber += (cursor.getString(0).replace("-","").replace(" ", "") + ",")
                        }
                    } while (cursor.moveToNext())
                }

                Log.d(
                    "test",
                    "phone=" + blockingNumber
                )

                VolleyService.updateReq("user", "user_blockingnumber='${blockingNumber}'", "user_id='${UserInfo.ID}'", this, {success->
                    VolleyService.insertReq("blocking", "blocking_user, blocking_partner", "'${UserInfo.ID}','${blockingNumber}'", this, {success->

                        UserInfo.BLOCKING = 1
                        UserInfo.BLOCKINGNUMBER = blockingNumber
                    })
                })
                cursor?.close()
            }
        }
        else {
            switch_blockfriend.isChecked = false
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, requiredPermission.get(0))) {
                Toast.makeText(this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                finish()
            }else {
                Toast.makeText(this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
            }
        }
    }

    fun checkContactsPermission() {
        val readContactPermission = ContextCompat.checkSelfPermission(this@AlarmSettingActivity, android.Manifest.permission.READ_CONTACTS)
        if(readContactPermission == PackageManager.PERMISSION_GRANTED) {
            switch_blockfriend.isChecked = true
            updateAlarm("blocking", true)
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER
            )

            val cursor = contentResolver.query(
                uri, projection, null,
                null , null
            )


            var blockingNumber = ""

            if (cursor!!.moveToFirst()) {
                do {
                    if (cursor.getString(0).startsWith("01")) {
                        blockingNumber += (cursor.getString(0).replace("-","").replace(" ", "") + ",")
                    }
                } while (cursor.moveToNext())
            }

            Log.d(
                "test",
                "phone=" + blockingNumber
            )

            VolleyService.updateReq("user", "user_blockingnumber='${blockingNumber}'", "user_id='${UserInfo.ID}'", this, {success->
                VolleyService.insertReq("blocking", "blocking_user, blocking_partner", "'${UserInfo.ID}','${blockingNumber}'", this, {success->

                    UserInfo.BLOCKING = 1
                    UserInfo.BLOCKINGNUMBER = blockingNumber
                })
            })
            cursor?.close()
        }
        else {
            switch_blockfriend.isChecked = false
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@AlarmSettingActivity, requiredPermission.get(0))) {
                ActivityCompat.requestPermissions(this@AlarmSettingActivity, requiredPermission, PERMISSIONS_REQUEST_CODE)
            } else {
                ActivityCompat.requestPermissions(this@AlarmSettingActivity, requiredPermission, PERMISSIONS_REQUEST_CODE)
            }
        }
    }


}