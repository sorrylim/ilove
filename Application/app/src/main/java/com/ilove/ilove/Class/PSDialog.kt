package com.ilove.ilove.Class

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.ilove.ilove.R

class PSDialog(activity: Activity) {
    var dialog : Dialog? = null
    var context : Activity? = null


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

    fun setEachExpressionLikeDialog(userNickname:String, userAgeCity:String) {
        dialog!!.setContentView(R.layout.dialog_eachexpressionlike)
        var chatBtn : Button = dialog!!.findViewById(R.id.btn_eachexpressionlike)
        var cancelBtn : TextView = dialog!!.findViewById(R.id.text_eachexpressionlikecancel)
        var nicknameText: TextView = dialog!!.findViewById(R.id.text_eachexpressionlikenickname)
        var agecityText: TextView = dialog!!.findViewById(R.id.text_eachexpressionlikeagecity)

        nicknameText.text = userNickname
        agecityText.text = userAgeCity

        chatBtn.setOnClickListener {

        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    fun setEachExpressionMeetDialog(userNickname:String, userAgeCity:String, userPhone:String) {
        dialog!!.setContentView(R.layout.dialog_eachexpressionmeet)
        var openBtn : Button = dialog!!.findViewById(R.id.btn_eachexpressionmeet)
        var cancelBtn : TextView = dialog!!.findViewById(R.id.text_eachexpressionmeetcancel)
        var phoneText : TextView = dialog!!.findViewById(R.id.text_eachexpressionmeetphone)
        var nicknameText: TextView = dialog!!.findViewById(R.id.text_eachexpressionmeetnickname)
        var agecityText : TextView = dialog!!.findViewById(R.id.text_eachexpressionmeetagecity)

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