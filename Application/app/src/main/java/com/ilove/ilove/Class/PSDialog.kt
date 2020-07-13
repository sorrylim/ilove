package com.ilove.ilove.Class

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Adapter.PersonalityAdapter
import com.ilove.ilove.Adapter.UserOptionAdapter
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R

class PSDialog(activity: Activity) {

    var dialog : Dialog? = null
    var context : Activity? = null

    companion object {
        var userOptionData : String = ""
    }


    init {
        dialog = Dialog(activity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        context = activity
    }

    fun show() {
        if(dialog != null) {
            dialog!!.show()
        }
    }

    fun dismiss() {
        if(dialog != null) {
            dialog!!.dismiss()
        }
    }


    fun setUserOption(title : String, userOption:String, userOptionList: ArrayList<UserItem.UserOption>, userOptionText: TextView) {
        dialog = Dialog(context!!, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_useroption, null)
        var titleText : TextView = dialogView.findViewById(R.id.text_useroptiontitle)
        var userOptionRV: RecyclerView = dialogView.findViewById(R.id.rv_useroption)
        var updateBtn: ImageView = dialogView.findViewById(R.id.image_updateoption)

        userOptionData = ""

        titleText.text = title

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        updateBtn.setOnClickListener {
            if(userOptionData == "") {
                Toast.makeText(context, title+"을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                if(userOption == "user_city") {
                    VolleyService.updateUserCityReq(UserInfo.ID, userOption, userOptionData!!, context!!, {success->
                        if(success == "success") {
                            userOptionText.text = userOptionData
                            dismiss()
                        }
                        else {
                            Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                else {
                    VolleyService.updateUserOptionReq(UserInfo.ID, userOption, userOptionData!!, context!!, {success->
                        if(success == "success") {
                            userOptionText.text = userOptionData
                            dismiss()
                        }
                        else {
                            Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }


        if(userOption == "user_personality" || userOption == "user_favoriteperson") {
            userOptionRV.setHasFixedSize(true)
            userOptionRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            userOptionRV.adapter = PersonalityAdapter(context!!, userOptionList)
        }
        else {
            userOptionRV.setHasFixedSize(true)
            userOptionRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            userOptionRV.adapter = UserOptionAdapter(context!!, userOptionList)
        }

    }

    fun setBuyVipDialog() {
        dialog!!.setContentView(R.layout.dialog_buyvip)
        var buyBtn : Button = dialog!!.findViewById(R.id.btn_buyvippurchase)
        var cancelBtn : Button = dialog!!.findViewById(R.id.btn_buyvipcancel)

        buyBtn.setOnClickListener{

        }
        cancelBtn.setOnClickListener {
            dismiss()
        }

        /*if (dialog!!.getWindow() != null) {
            dialog!!.getWindow()!!.attributes = getLayoutParams(dialog!!)
            dialog!!.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        }*/
    }

    fun setEachExpressionLikeDialog(userNickname:String, userAgeCity:String, userImage:String) {
        dialog!!.setContentView(R.layout.dialog_eachexpressionlike)
        var chatBtn : Button = dialog!!.findViewById(R.id.btn_eachexpressionlike)
        var cancelBtn : TextView = dialog!!.findViewById(R.id.text_eachexpressionlikecancel)
        var nicknameText: TextView = dialog!!.findViewById(R.id.text_eachexpressionlikenickname)
        var agecityText: TextView = dialog!!.findViewById(R.id.text_eachexpressionlikeagecity)
        var expressionLikeProfile: ImageView = dialog!!.findViewById(R.id.image_eachexpressionlikeprofile)

        Glide.with(context!!).load(userImage).apply(RequestOptions().circleCrop()).into(expressionLikeProfile)

        nicknameText.text = userNickname
        agecityText.text = userAgeCity

        chatBtn.setOnClickListener {

        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    fun setEachExpressionMeetDialog(userNickname:String, userAgeCity:String, userPhone:String, userImage: String) {
        dialog!!.setContentView(R.layout.dialog_eachexpressionmeet)
        var openBtn : Button = dialog!!.findViewById(R.id.btn_eachexpressionmeet)
        var cancelBtn : TextView = dialog!!.findViewById(R.id.text_eachexpressionmeetcancel)
        var phoneText : TextView = dialog!!.findViewById(R.id.text_eachexpressionmeetphone)
        var nicknameText: TextView = dialog!!.findViewById(R.id.text_eachexpressionmeetnickname)
        var agecityText : TextView = dialog!!.findViewById(R.id.text_eachexpressionmeetagecity)
        var expressionMeetProfile: ImageView = dialog!!.findViewById(R.id.image_eachexpressionmeetprofile)

        Glide.with(context!!).load(userImage).apply(RequestOptions().circleCrop()).into(expressionMeetProfile)

        phoneText.text = userPhone
        nicknameText.text = userNickname
        agecityText.text = userAgeCity


        openBtn.setOnClickListener{
            phoneText.visibility = View.VISIBLE
            cancelBtn.setText("닫기")
            openBtn.visibility = View.GONE
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }



    private fun getLayoutParams(dialog: Dialog): WindowManager.LayoutParams {
        val layoutParams = WindowManager.LayoutParams()
        if (dialog.window != null) {
            layoutParams.copyFrom(dialog.window!!.attributes)
        }
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        return layoutParams
    }


}