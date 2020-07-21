package com.ilove.ilove.Class

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.messaging.FirebaseMessaging
import com.ilove.ilove.Adapter.MessageCandyAdapter
import com.ilove.ilove.Adapter.PersonalityAdapter
import com.ilove.ilove.Adapter.UserOptionAdapter
import com.ilove.ilove.IntroActivity.ChargeCandyActivity
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.MainActivity.ChatActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.dialog_inquire.*

class PSDialog(activity: Activity) {


    var context : Activity? = null
    var dialog : Dialog? = null

    companion object {
        var userOptionData : String = ""
    }


    init {
        dialog = Dialog(activity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

    fun setPreviewIntroduce(introduceText: TextView) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        var dialogView = context!!.layoutInflater.inflate(R.layout.dialog_editpreviewintroduce, null)

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        var previewIntroduce : EditText = dialogView.findViewById(R.id.edit_editpreviewintroduce)
        var updatePreviewIntroduce : ImageView = dialogView.findViewById(R.id.image_updatepreviewintroduce)

        updatePreviewIntroduce.setOnClickListener {
            VolleyService.updateIntroduce(UserInfo.ID, "user_previewintroduce", previewIntroduce.text.toString(), context!!, {success->
                if(success == "success") {
                    dismiss()
                    introduceText.text = previewIntroduce.text.toString()
                }
                else {
                    Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                }
            } )
        }

    }

    fun setIntroduce(introduceText: TextView) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        var dialogView = context!!.layoutInflater.inflate(R.layout.dialog_editintroduce, null)

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        var lengthCheck: TextView = dialogView.findViewById(R.id.text_editintroducecheck)
        var introduce : EditText = dialogView.findViewById(R.id.edit_editintroduce)
        var updateIntroduce : ImageView = dialogView.findViewById(R.id.image_updateintroduce)

        updateIntroduce.setOnClickListener {
            if(introduce.text.length < 50) {
                lengthCheck.visibility = View.VISIBLE
            }
            else {
                VolleyService.updateIntroduce(UserInfo.ID, "user_introduce", introduce.text.toString(), context!!, {success->
                    if(success == "success") {
                        dismiss()
                        introduceText.text = introduce.text.toString()
                    }
                    else {
                        Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                } )
            }
        }
    }


    fun setUserOption(title : String, userOption:String, userOptionList: ArrayList<UserItem.UserOption>, userOptionText: TextView) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
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
                            userOptionText.setTextColor(Color.parseColor("#FF8C00"))
                            dismiss()
                        }
                        else {
                            Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                else if(userOption=="user_gender"){
                    if(userOptionData=="여자"){
                        userOptionData="F"
                    }
                    else userOptionData="M"
                    //VolleyService만 추가해주면 됌

                }
                else {
                    VolleyService.updateUserOptionReq(UserInfo.ID, userOption, userOptionData!!, context!!, {success->
                        if(success == "success") {
                            if(userOptionData.length > 8) {
                                userOptionText.text = userOptionData.substring(0, 9) + "..."
                                userOptionText.setTextColor(Color.parseColor("#FF8C00"))
                                dismiss()
                            }
                            else {
                                userOptionText.text = userOptionData
                                userOptionText.setTextColor(Color.parseColor("#FF8C00"))
                                dismiss()
                            }
                        }
                        else {
                            Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }


        if(userOption == "user_personality" || userOption == "user_favoriteperson" || userOption == "user_interest") {
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

    fun setEachExpressionLikeDialog(userId:String,userNickname:String, userAgeCity:String, userImage:String) {
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
            VolleyService.createRoomReq(userId,userNickname,context!!,{success ->
                var json=success
                var room=ChatRoomItem(
                    json.getString("room_id"),
                    json.getString("room_maker"),
                    json.getString("room_partner"),
                    json.getString("room_title"),
                    "",
                    "",
                    ""
                )

                FirebaseMessaging.getInstance().subscribeToTopic(room.roomId)
                    .addOnCompleteListener {
                        Log.d("test","success subscribe to topic")
                    }

                var intent = Intent(context, ChatActivity::class.java)

                intent.putExtra("room",room)
                ContextCompat.startActivity(context!!, intent, null)
                dismiss()
            })
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

    fun setMessageTicketDialog() {
        dialog!!.setContentView(R.layout.dialog_messageticket)
        var messageCandyCount : RecyclerView = dialog!!.findViewById(R.id.rv_messagecandycount)
        var chargeBtn : Button = dialog!!.findViewById(R.id.btn_messagechargecandy)
        var cancelText : TextView = dialog!!.findViewById(R.id.text_cancelmessage)
        var negativeText : TextView = dialog!!.findViewById(R.id.text_negativecharge)
        
        var candyCount : ArrayList<UserItem.MessageTicket> = arrayListOf(UserItem.MessageTicket("1", "7"),
            UserItem.MessageTicket("5", "21"), UserItem.MessageTicket("10", "40"),
            UserItem.MessageTicket("15", "60"), UserItem.MessageTicket("30", "110"))

        messageCandyCount.setHasFixedSize(true)
        messageCandyCount.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        messageCandyCount.adapter = MessageCandyAdapter(context!!, candyCount, negativeText)


        chargeBtn.setOnClickListener {
            var intent = Intent(context, ChargeCandyActivity::class.java)
            context!!.startActivity(intent)
        }

        cancelText.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    class BottomSheetDialog(categoryText : TextView) : BottomSheetDialogFragment() {
        var categoryText = categoryText

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            super.onCreateView(inflater, container, savedInstanceState)
            return inflater.inflate(R.layout.dialog_inquire, container, false)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            view?.findViewById<TextView>(R.id.text_inquireservice)?.setOnClickListener {
                categoryText.text = text_inquireservice.text
                dismiss()
            }
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