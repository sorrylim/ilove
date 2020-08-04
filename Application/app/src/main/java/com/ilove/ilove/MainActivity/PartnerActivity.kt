package com.ilove.ilove.MainActivity

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ilove.ilove.Adapter.PartnerProfileAdapter
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_partner.*
import kotlinx.android.synthetic.main.fragment_channel.*
import org.json.JSONObject
import java.text.SimpleDateFormat

class PartnerActivity : PSAppCompatActivity() {

    var profileImageList = ArrayList<String>()
    var currentLayout: LinearLayout? = null
    var widthData : Int = 0
    var width : Int = 0
    var textViewList = ArrayList<TextView>()
    var linearList = ArrayList<LinearLayout>()
    var linearCount : Int = 0
    var filledWidth : Int = 0
    var dm : DisplayMetrics? = null
    var topPadding = 0
    var padding = 0
    var margin = 0
    var layoutMargin = 0
    var like = 0
    var meet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner)

        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())

        var psDialog = PSDialog(this)

        var displayMetrics: DisplayMetrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        dm = getResources().getDisplayMetrics()
        topPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, dm!!).toInt()
        padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15f, dm!!).toInt()
        margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, dm!!).toInt()
        layoutMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, dm!!).toInt()

        image_partnerbackpress.bringToFront()

        image_partnerbackpress.setOnClickListener {
            finish()
        }

        var intent = intent
        var userNickname = intent.getStringExtra("userNickname")
        var userId = intent.getStringExtra("userId")
        var userAge = intent.getStringExtra("userAge")
        var userCity = intent.getStringExtra("userCity")
        var userPhone = intent.getStringExtra("userPhone")

        text_partnernickname.text = userNickname
        text_partnerage.text = userCity + ", " + userAge

        scroll_partner.setOverScrollMode(View.OVER_SCROLL_NEVER)

        fab_like.setOnClickListener {
            if(like == 0) {
                VolleyService.insertExpressionReq(UserInfo.ID, userId!!, "like", curDate, this, {success->
                    VolleyService.sendFCMReq(userId,"like", this)
                    when(success) {
                        "success" -> {
                            fab_like.setImageResource(R.drawable.bigheart_on)
                            like = 1
                        }
                        "eachsuccess" -> {
                            fab_like.setImageResource(R.drawable.bigheart_on)
                            var dialog = PSDialog(this)
                            dialog.setEachExpressionLikeDialog(userId, userNickname!!, userAge + ", " + userCity, profileImageList.get(0))
                            like = 1
                            dialog.show()
                        }
                        else -> Toast.makeText(this , "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else if(like == 1) {
                VolleyService.deleteExpressionReq(UserInfo.ID, userId!!, "like", this, {success->
                    if(success=="success") {
                        fab_like.setImageResource(R.drawable.bigheart_off)
                        like = 0
                    }
                    else {
                        Toast.makeText(this, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        fab_call.setOnClickListener {
            if(meet == 0) {
                VolleyService.insertExpressionReq(UserInfo.ID, userId!!, "meet", curDate, this, {success->
                    VolleyService.sendFCMReq(userId,"meet", this)
                    when(success) {
                        "success" -> {
                            fab_call.setImageResource(R.drawable.call_icon)
                            meet = 1
                        }
                        "eachsuccess" -> {
                            fab_call.setImageResource(R.drawable.call_icon)
                            var dialog = PSDialog(this)
                            dialog.setEachExpressionMeetDialog(userNickname!!, userAge + ", " + userCity, userPhone!!, profileImageList.get(0))
                            meet = 1
                            dialog.show()
                        }
                        else -> Toast.makeText(this , "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            else if(meet == 1) {
                VolleyService.deleteExpressionReq(UserInfo.ID, userId!!, "meet", this, {success->
                    if(success=="success") {
                        fab_call.setImageResource(R.drawable.call_n_icon)
                        meet = 0
                    }
                    else {
                        Toast.makeText(this, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        VolleyService.getProfileImageReq(userId!!, this, {success->
            psDialog.setLoadingDialog()
            psDialog.show()
            profileImageList.clear()
            var array = success
            for(i in 0..array.length()-1) {
                var json = array[i] as JSONObject
                profileImageList.add(json.getString("image"))
            }

            viewpager_partnerprofile.adapter = PartnerProfileAdapter(this, profileImageList, profileImageList.size)


            indicator.setViewPager(viewpager_partnerprofile)
            indicator.bringToFront()
            indicator.createIndicators(profileImageList.size, 0)
            viewpager_partnerprofile.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (positionOffsetPixels == 0) {
                        viewpager_partnerprofile.setCurrentItem(position)
                    }
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    indicator.animatePageSelected(position % profileImageList.size)
                }
            })

            VolleyService.getUserOptionReq(userId, this, { success->
                var json = success

                if(json.getString("user_introduce") != "null")
                {
                    text_partnerintroduce.text = json.getString("user_introduce")
                    text_partnerintroduce.visibility = View.VISIBLE
                    view29.visibility = View.VISIBLE
                }

                if(json.getString("user_height") != "null")
                {
                    text_partnerheight.text = json.getString("user_height")
                    text_partnerheight.visibility = View.VISIBLE
                    text_partnerheight1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_bodytype") != "null")
                {
                    text_partnerbodytype.text = json.getString("user_bodytype")
                    text_partnerbodytype.visibility = View.VISIBLE
                    text_partnerbodytype1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_bloodtype") != "null")
                {
                    text_partnerbloodtype.text = json.getString("user_bloodtype")
                    text_partnerbloodtype.visibility = View.VISIBLE
                    text_partnerbloodtype1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_job") != "null")
                {
                    text_partnerjob.text = json.getString("user_job")
                    text_partnerjob.visibility = View.VISIBLE
                    text_partnerjob1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_education") != "null")
                {
                    text_partnereducation.text = json.getString("user_education")
                    text_partnereducation.visibility = View.VISIBLE
                    text_partnereducation1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_holiday") != "null")
                {
                    text_partnerholiday.text = json.getString("user_holiday")
                    text_partnerholiday.visibility = View.VISIBLE
                    text_partnerholiday1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_cigarette") != "null")
                {
                    text_partnercigarette.text = json.getString("user_cigarette")
                    text_partnercigarette.visibility = View.VISIBLE
                    text_partnercigarette1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_alcohol") != "null")
                {
                    text_partneralcohol.text = json.getString("user_alcohol")
                    text_partneralcohol.visibility = View.VISIBLE
                    text_partneralcohol1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_religion") != "null")
                {
                    text_partnerreligion.text = json.getString("user_religion")
                    text_partnerreligion.visibility = View.VISIBLE
                    text_partnerreligion1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_brother") != "null")
                {
                    text_partnerbrother.text = json.getString("user_brother")
                    text_partnerbrother.visibility = View.VISIBLE
                    text_partnerbrother1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_country") != "null")
                {
                    text_partnercountry.text = json.getString("user_country")
                    text_partnercountry.visibility = View.VISIBLE
                    text_partnercountry1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_salary") != "null")
                {
                    text_partnersalary.text = json.getString("user_salary")
                    text_partnersalary.visibility = View.VISIBLE
                    text_partnersalary1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_asset") != "null")
                {
                    text_partnerasset.text = json.getString("user_asset")
                    text_partnerasset.visibility = View.VISIBLE
                    text_partnerasset1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_marriagehistory") != "null")
                {
                    text_partnermarriagehistory.text = json.getString("user_marriagehistory")
                    text_partnermarriagehistory.visibility = View.VISIBLE
                    text_partnermarriagehistory1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_children") != "null")
                {
                    text_partnerchildren.text = json.getString("user_children")
                    text_partnerchildren.visibility = View.VISIBLE
                    text_partnerchildren1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_marriageplan") != "null")
                {
                    text_partnermarriageplan.text = json.getString("user_marriageplan")
                    text_partnermarriageplan.visibility = View.VISIBLE
                    text_partnermarriageplan1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_childrenplan") != "null")
                {
                    text_partnerchildrenplan.text = json.getString("user_childrenplan")
                    text_partnerchildrenplan.visibility = View.VISIBLE
                    text_partnerchildrenplan1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_parenting") != "null")
                {
                    text_partnerparenting.text = json.getString("user_parenting")
                    text_partnerparenting.visibility = View.VISIBLE
                    text_partnerparenting1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_wishdate") != "null")
                {
                    text_partnerwishdate.text = json.getString("user_wishdate")
                    text_partnerwishdate.visibility = View.VISIBLE
                    text_partnerwishdate1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_datecost") != "null")
                {
                    text_partnerdatecost.text = json.getString("user_datecost")
                    text_partnerdatecost.visibility = View.VISIBLE
                    text_partnerdatecost1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_roommate") != "null")
                {
                    text_partnerroommate.text = json.getString("user_roommate")
                    text_partnerroommate.visibility = View.VISIBLE
                    text_partnerroommate1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_language") != "null")
                {
                    text_partnerlanguage.text = json.getString("user_language")
                    text_partnerlanguage.visibility = View.VISIBLE
                    text_partnerlanguage1.visibility = View.VISIBLE
                    textView30.visibility = View.VISIBLE
                    view30.visibility = View.VISIBLE
                }

                if(json.getString("user_interest") != "null")
                {
                    text_partnerhobby.visibility = View.VISIBLE
                    layout_hobby.visibility = View.VISIBLE

                    var hobby : List<String> = json.getString("user_interest").split(",")
                    setLayout(layout_hobby, hobby)
                }

                if(json.getString("user_personality") != "null")
                {
                    text_partnerpersonality.visibility = View.VISIBLE
                    layout_personality.visibility = View.VISIBLE

                    var personality : List<String> = json.getString("user_personality").split(",")
                    setLayout(layout_personality, personality)
                }

                if(json.getString("user_favoriteperson") != "null")
                {
                    text_partnerfavoriteperson.visibility = View.VISIBLE
                    layout_favoriteperson.visibility = View.VISIBLE

                    var favoriteperson : List<String> = json.getString("user_favoriteperson").split(",")
                    setLayout(layout_favoriteperson, favoriteperson)
                }

                VolleyService.getPartnerExpressionReq(UserInfo.ID, userId, this, { success->
                    var json = success

                        if(json.getInt("like") == 1) {
                            fab_like.setImageResource(R.drawable.bigheart_on)
                            like = 1
                        }

                        if(json.getInt("meet") == 1) {
                            fab_call.setImageResource(R.drawable.call_icon)
                            meet = 1
                        }

                    psDialog.dismiss()
                })
            })
        })
    }

    fun setLayout(layout: LinearLayout, textList: List<String>) {
        widthData = 0
        filledWidth = 0
        linearCount = 0
        textViewList.clear()
        linearList.clear()
        for(i in 0..textList.size-2) {
            var view = TextView(this)

            var layoutParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(0, 0, margin, 0)
            view.setLayoutParams(layoutParams)

            view.setPadding(padding, topPadding, padding, topPadding)
            view.setBackgroundResource(R.drawable.more_rounded_corner_shape_button)
            view.setTextColor(Color.WHITE)
            view.setText(textList.get(i))
            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)

            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            widthData += view.measuredWidth + margin

            textViewList.add(view)
        }

        var test = width - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f, dm!!).toInt()

        if(test < widthData) {
            for(i in 0..widthData/test) {
                var linear : LinearLayout = LinearLayout(this)
                linear.setOrientation(LinearLayout.HORIZONTAL)
                var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(0, 0, 0, layoutMargin)
                linear.setLayoutParams(params)
                linearList.add(linear)
            }
        }
        else {
            var linear : LinearLayout = LinearLayout(this)
            linear.setOrientation(LinearLayout.HORIZONTAL)
            var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(0, 0, 0, layoutMargin)
            linear.setLayoutParams(params)
            linearList.add(linear)
        }

        for(i in 0..textViewList.size-1) {
            textViewList.get(i).measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            filledWidth += (textViewList.get(i).measuredWidth + margin)

            if(linearCount < linearList.size && filledWidth < test) {
                linearList.get(linearCount).addView(textViewList.get(i))
            }
            else {
                filledWidth = textViewList.get(i).measuredWidth + margin
                linearCount++
                linearList.get(linearCount).addView(textViewList.get(i))
            }
        }

        for(i in 0..linearList.size-1) {
            layout.addView(linearList.get(i))
        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)


    }
}