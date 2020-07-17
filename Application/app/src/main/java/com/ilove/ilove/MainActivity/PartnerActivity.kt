package com.ilove.ilove.MainActivity

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ilove.ilove.Adapter.PartnerProfileAdapter
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_partner.*
import org.json.JSONObject

class PartnerActivity : PSAppCompatActivity() {

    var profileImageList = ArrayList<String>()
    var currentLayout: LinearLayout? = null
    var widthData : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner)







        var hobbyLayout : LinearLayout = findViewById(R.id.layout_hobby)

        /*var linear : LinearLayout = LinearLayout(this)
        linear.setOrientation(LinearLayout.HORIZONTAL)
        var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        linear.setLayoutParams(params)

        hobbyLayout.addView(linear)*/


        var displayMetrics: DisplayMetrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        var width = displayMetrics.widthPixels

        var textList = ArrayList<TextView>()
        var linearList = ArrayList<LinearLayout>()
        var linearCount : Int = 0
        var filledWidth : Int = 0

        var dm = getResources().getDisplayMetrics()
        var topPadding = Math.round(5 * dm.density)
        var padding = Math.round(15 * dm.density)
        var margin = Math.round(20 * dm.density)
        var layoutMargin = Math.round(5 * dm.density)

        for(i in 0..10) {
            var view = TextView(this)

            var layoutParams : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(margin, 0, 0, 0)
            view.setLayoutParams(layoutParams)

            view.setPadding(padding, topPadding, padding, topPadding)
            view.setBackgroundResource(R.drawable.more_rounded_corner_shape_button)
            view.setTextColor(Color.BLACK)
            view.setText("도도")
            view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)

            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            widthData += view.measuredWidth + margin

            textList.add(view)
        }

        if(width < widthData) {
            for(i in 0..widthData/width) {
                var linear : LinearLayout = LinearLayout(this)
                linear.setOrientation(LinearLayout.HORIZONTAL)
                var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                params.setMargins(0, 0, 0, layoutMargin)
                linear.setLayoutParams(params)
                linearList.add(linear)
            }
        }

        for(i in 0..textList.size-1) {
            textList.get(i).measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            filledWidth += (textList.get(i).measuredWidth + margin)

            if(linearCount < linearList.size && filledWidth < width) {
                linearList.get(linearCount).addView(textList.get(i))
            }
            else {
                filledWidth = textList.get(i).measuredWidth + margin
                linearCount++
                linearList.get(linearCount).addView(textList.get(i))
            }
        }

        for(i in 0..linearList.size-1) {
            hobbyLayout.addView(linearList.get(i))
        }

        Log.d("test", "widthData : ${widthData} deviceWidth :${width} linearLayoutCount: ${linearList.size} textViewCount: ${textList.size}")


        image_partnerbackpress.bringToFront()

        image_partnerbackpress.setOnClickListener {
            finish()
        }


        var intent = intent
        var userNickname = intent.getStringExtra("userNickname")
        var userId = intent.getStringExtra("userId")
        var userAge = intent.getStringExtra("userAge")
        var userCity = intent.getStringExtra("userCity")

        text_partnernickname.text = userNickname
        text_partnerage.text = userCity + ", " + userAge


        //toolbarCenterBinding(toolbar_partner, userNickname!!, true)

        VolleyService.getProfileImageReq(userId!!, this, {success->
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

            })
        })
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)


    }
}