package com.ilove.ilove.Class

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.messaging.FirebaseMessaging
import com.ilove.ilove.Adapter.MessageCandyAdapter
import com.ilove.ilove.Adapter.PersonalityAdapter
import com.ilove.ilove.Adapter.SignupUserOptionAdapter
import com.ilove.ilove.Adapter.UserOptionAdapter
import com.ilove.ilove.IntroActivity.ChargeCandyActivity
import com.ilove.ilove.IntroActivity.EditProfileActivity
import com.ilove.ilove.IntroActivity.SignupActivity
import com.ilove.ilove.IntroActivity.SplashActivity
import com.ilove.ilove.Item.ChatRoomItem
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.MainActivity.ChatActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.dialog_inquire.*
import org.json.JSONObject
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import kotlin.random.Random

class PSDialog(activity: Activity) {


    var context : Activity? = null
    var dialog : Dialog? = null

    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var curDate = simpleDateFormat.format(System.currentTimeMillis())

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
            if(previewIntroduce.text.length < 5) {
            Toast.makeText(context,"5자 이상 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else {
                VolleyService.updateIntroduce(UserInfo.ID, "user_previewintroduce", previewIntroduce.text.toString(), context!!, { success ->
                    if (success == "success") {
                        dismiss()
                        introduceText.text = previewIntroduce.text.toString()
                    } else {
                        Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
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
            if(introduce.text.length < 30) {
                lengthCheck.visibility = View.VISIBLE
                Toast.makeText(context,"30자 이상 입력해주세요",Toast.LENGTH_SHORT).show()
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
        var updateBtn: Button = dialogView.findViewById(R.id.image_updateoption)

        userOptionData = ""

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        updateBtn.setOnClickListener {
            if(userOptionData == "") {
                Toast.makeText(context, title+"을(를) 선택해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                if(userOption == "user_city" || userOption=="user_purpose") {
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
                    else if(userOptionData=="남자") {
                        userOptionData="M"
                    }

                    VolleyService.updateUserCityReq(UserInfo.ID, userOption, userOptionData!!, context!!, {success->
                        if(success == "success") {
                            var gender:String?=null
                            if(userOptionData=="F"){
                                gender="여자"
                            }
                            else{
                                gender="남자"
                            }

                            UserInfo.GENDER = userOptionData
                            userOptionText.text = gender
                            userOptionText.setTextColor(Color.parseColor("#FF8C00"))
                            dismiss()
                        }
                        else {
                            Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
                else {
                    if(userOption == "user_personality" || userOption == "user_favoriteperson") {
                        var count = 0
                        for(i in 0..userOptionList.size-1) {
                            if(userOptionList.get(i).isSelected == true) {
                                count ++
                            }
                        }

                        if(count < 2 ) {
                            Toast.makeText(context, "2개이상 선택해주세요.", Toast.LENGTH_SHORT).show()
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
        }


        if(userOption == "user_personality" || userOption == "user_favoriteperson" || userOption == "user_interest") {

            when(userOption) {
                "user_personality" -> {
                    var ssb : SpannableStringBuilder = SpannableStringBuilder(title+"을 알려주세요.(2개 이상 선택)")
                    ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    titleText.text = ssb
                }
                "user_favoriteperson" -> {
                    var ssb : SpannableStringBuilder = SpannableStringBuilder(title+"을 알려주세요.(2개 이상 선택)")
                    ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    titleText.text = ssb
                }
                "user_interest" -> {
                    var ssb : SpannableStringBuilder = SpannableStringBuilder(title+"을 알려주세요.(중복선택가능)")
                    ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    titleText.text = ssb
                }
            }

            userOptionRV.setHasFixedSize(true)
            userOptionRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            userOptionRV.adapter = PersonalityAdapter(context!!, userOptionList)
        }
        else {
            var ssb : SpannableStringBuilder = SpannableStringBuilder(title+"을 알려주세요.")
            ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            titleText.text = ssb

            userOptionRV.setHasFixedSize(true)
            userOptionRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            userOptionRV.adapter = UserOptionAdapter(context!!, userOptionList)
        }

    }

    fun setUserOption_signup(title : String, userOption:String, userOptionList: ArrayList<UserItem.UserOption>,UserId:String) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_useroption, null)
        var titleText : TextView = dialogView.findViewById(R.id.text_useroptiontitle)
        var userOptionRV: RecyclerView = dialogView.findViewById(R.id.rv_useroption)
        var updateBtn: Button = dialogView.findViewById(R.id.image_updateoption)

        userOptionData = ""


        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        updateBtn.setOnClickListener {
            if(userOptionData == "") {
                Toast.makeText(context, title+"을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
            else {
                if(userOption == "user_city" || userOption == "user_purpose") {
                    VolleyService.updateUserCityReq(UserId, userOption, userOptionData!!, context!!, {success->
                        if(success == "success") {
                            dismiss()
                        }
                        else {
                            Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                else if(userOption=="user_gender"){
                    if(userOptionData=="여성"){
                        userOptionData="F"
                    }
                    else if(userOptionData=="남성") {
                        userOptionData="M"
                    }
                    VolleyService.updateUserCityReq(UserId, userOption, userOptionData!!, context!!, {success->
                        if(success == "success") {
                            var gender:String?=null
                            if(userOptionData=="F"){
                                gender="여성"
                            }
                            else{
                                gender="남성"
                            }

                            dismiss()
                        }
                        else {
                            Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
                else {
                    if(userOption == "user_personality" || userOption == "user_favoriteperson") {
                        var count = 0
                        for(i in 0..userOptionList.size-1) {
                            if(userOptionList.get(i).isSelected == true) {
                                count ++
                            }
                        }

                        if(count < 2 ) {
                            Toast.makeText(context, "2개이상 선택해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            VolleyService.updateUserOptionReq(UserInfo.ID, userOption, userOptionData!!, context!!, {success->
                                if(success == "success") {
                                        dismiss()
                                }
                                else {
                                    Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                    else {
                        VolleyService.updateUserOptionReq(UserInfo.ID, userOption, userOptionData!!, context!!, {success->
                            if(success == "success") {
                                    dismiss()
                            }
                            else {
                                Toast.makeText(context, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
            }

        }



        if(userOption == "user_personality" || userOption == "user_favoriteperson" || userOption == "user_interest") {

            when(userOption) {
                "user_personality" -> {
                    var ssb : SpannableStringBuilder = SpannableStringBuilder(title+"을 알려주세요.(2개 이상 선택)")
                    ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    titleText.text = ssb
                }
                "user_favoriteperson" -> {
                    var ssb : SpannableStringBuilder = SpannableStringBuilder(title+"을 알려주세요.(2개 이상 선택)")
                    ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    titleText.text = ssb
                }
                "user_interest" -> {
                    var ssb : SpannableStringBuilder = SpannableStringBuilder(title+"을 알려주세요.(중복선택가능)")
                    ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    titleText.text = ssb
                }
            }

            userOptionRV.setHasFixedSize(true)
            userOptionRV.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            userOptionRV.adapter = PersonalityAdapter(context!!, userOptionList)
        }
        else {

            var ssb : SpannableStringBuilder = SpannableStringBuilder(title+"을 알려주세요.")
            ssb.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            titleText.text = ssb

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

    fun setUpProfile() {
        dialog!!.setContentView(R.layout.dialog_profileup)
        var titleText : TextView = dialog!!.findViewById(R.id.text_dialogtitle)
        var contentText : TextView = dialog!!.findViewById(R.id.text_dialogcontent)
        var subContentText : TextView = dialog!!.findViewById(R.id.text_dialogsubcontent)
        var acceptBtn : Button = dialog!!.findViewById(R.id.btn_dialogaccept)
        var cancelBtn : Button = dialog!!.findViewById(R.id.btn_dialogcancel)

        titleText.text = "프로필 올리기"
        contentText.text = "프로필 상단 올리기를 선택하셨습니다.\n(리스트에 1시간 노출)"

        subContentText.visibility = View.GONE

        acceptBtn.setOnClickListener {
            VolleyService.updateUpProfileReq(UserInfo.ID, curDate, context!!, {success->
                if(success=="success") {
                    dialog!!.dismiss()
                }
            })
        }

        cancelBtn.setOnClickListener {
            dialog!!.dismiss()
        }
    }

    fun setUpdateCandy(candyCount: Int, updateType: String) {
        dialog!!.setContentView(R.layout.dialog_profileup)
        var titleText : TextView = dialog!!.findViewById(R.id.text_dialogtitle)
        var contentText : TextView = dialog!!.findViewById(R.id.text_dialogcontent)
        var subContentText : TextView = dialog!!.findViewById(R.id.text_dialogsubcontent)
        var acceptBtn : Button = dialog!!.findViewById(R.id.btn_dialogaccept)
        var cancelBtn : Button = dialog!!.findViewById(R.id.btn_dialogcancel)
        val billingModue = BillingModule(context!!)

        titleText.text = "상품구매"
        contentText.text = "사탕 ${candyCount}개를 선택하셨습니다.\n구매하시겠습니까?"

        subContentText.visibility = View.GONE

        acceptBtn.setOnClickListener {
            Log.d("test", "결제")
            if(billingModue.mBillingClient.isReady) {
                billingModue.doBillingFlow(billingModue.skuDetails10, candyCount)
                dismiss()
            }
            else {
                Toast.makeText(context, "아직 준비되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        cancelBtn.setOnClickListener {
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
            view?.findViewById<TextView>(R.id.text_inquirepurchase)?.setOnClickListener {
                categoryText.text = text_inquirepurchase.text
                dismiss()
            }

            view?.findViewById<TextView>(R.id.text_inquireservice)?.setOnClickListener {
                categoryText.text = text_inquireservice.text
                dismiss()
            }
        }
    }

    fun setPermissionDialog() {
        dialog!!.setContentView(R.layout.dialog_permission)
        var displayMetrics: DisplayMetrics = DisplayMetrics()
        (context as Activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics) // 화면의 가로길이를 구함
        var width = displayMetrics.widthPixels * 0.8


        val checkBtn : Button = dialog!!.findViewById(R.id.btn_permissioncheck)
        val permissionLayout : ConstraintLayout = dialog!!.findViewById(R.id.layout_permission)

        permissionLayout.getLayoutParams().width = width.toInt()

        checkBtn.setOnClickListener {
            dismiss()
            val psDialog = PSDialog(context!!)
            psDialog.setPernsonalInfo()
            psDialog.show()
        }
    }

    fun setPernsonalInfo() {
        var displayMetrics: DisplayMetrics = DisplayMetrics()
        (context as Activity).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics) // 화면의 가로길이를 구함
        var width = displayMetrics.widthPixels * 0.8
        var height = displayMetrics.heightPixels * 0.8

        dialog!!.setContentView(R.layout.dialog_personalinfo)
        val agreeBtn : Button = dialog!!.findViewById(R.id.btn_personalinfo)
        val personalText : TextView = dialog!!.findViewById(R.id.textView80)
        var personalLayout : ConstraintLayout = dialog!!.findViewById(R.id.layout_personalinfo)

        personalLayout.getLayoutParams().width = width.toInt()
        personalLayout.getLayoutParams().height = height.toInt()
        personalLayout.requestLayout()

        personalText.movementMethod = ScrollingMovementMethod.getInstance()

        agreeBtn.setOnClickListener {
            dismiss()
            val psDialog = PSDialog(context!!)
            psDialog.setCertification1()
            psDialog.show()
        }
    }

    fun setCertification1() {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_certification1, null)
        val phoneEdit : EditText = dialogView.findViewById(R.id.edit_phone)
        val sendBtn : TextView = dialogView.findViewById(R.id.text_certificationphone)

        phoneEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(phoneEdit.length() == 11) {
                    sendBtn.setTextColor(Color.parseColor("#212121"))
                    sendBtn.isClickable = true
                }
                else {
                    sendBtn.setTextColor(Color.parseColor("#9E9E9E"))
                    sendBtn.isClickable = false
                }
            }
        })

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        sendBtn.setOnClickListener {
            var random = Random
            var certifyNum = random.nextInt(100000, 999999)
            var phone = phoneEdit.text.toString()

            VolleyService.sendSMSReq(phone, certifyNum, context!!, {success-> })

            dismiss()
            val psDialog = PSDialog(context!!)
            psDialog.setCertification2(phone, certifyNum)
            psDialog.show()
        }
    }

    fun setCertification2(phone:String, certifyNum: Int) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_certification2, null)
        val certifyEdit : EditText = dialogView.findViewById(R.id.edit_certifynum)
        val certifyBtn : TextView = dialogView.findViewById(R.id.text_certification)

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        certifyBtn.setOnClickListener {
            if(certifyEdit.text.toString() == certifyNum.toString()) {
                VolleyService.userCheckReq(phone, context!!, {success->
                    if(success == "1") {
                        var userPref = context!!.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        var editor = userPref.edit()
                        editor.putString("ID", phone).apply()

                        var intent = Intent(context!!, SplashActivity::class.java)
                        context!!.startActivity(intent)
                    }
                    else {
                        var intent = Intent(context!!, SignupActivity::class.java)
                        intent.putExtra("phone", phone)
                        context!!.startActivity(intent)
                        dismiss()
                    }
                })
            }
            else {
                Toast.makeText(context!!, "인증번호가 틀립니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setGuideLike() {
        dialog!!.setContentView(R.layout.dialog_guidelike)
        var acceptBtn : Button = dialog!!.findViewById(R.id.btn_guidelike)

        acceptBtn.setOnClickListener {
            dialog!!.setContentView(R.layout.dialog_guidemeet)
            var acceptBtn : Button = dialog!!.findViewById(R.id.btn_guidemeet)
            acceptBtn.setOnClickListener {
                dismiss()
                VolleyService.updateReq("user", "user_guide=0", "user_id='${UserInfo.ID}'", context!!, {success->
                })
            }
        }
    }

    fun setLoadingDialog() {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog!!.setContentView(R.layout.dialog_loading)
        var loadingImage : ImageView = dialog!!.findViewById(R.id.image_loading)

        Glide.with(context!!).asGif().load(R.raw.loading).into(loadingImage)
    }

    fun setInquireClickDialog(title: String, content: String, answer: String, date:String, answerDate: String) {
        dialog = Dialog(context!!, R.style.popCasterDlgTheme)
        val dialogView = context!!.layoutInflater.inflate(R.layout.dialog_inquireclick, null)
        val titleText : TextView = dialogView.findViewById(R.id.text_inquireclicktitle)
        val contentText : TextView = dialogView.findViewById(R.id.text_inquireclickcontent)
        val answerText : TextView = dialogView.findViewById(R.id.text_inquireclickanswer)
        val cancelBtn : ImageView = dialogView.findViewById(R.id.image_inquireclickcancel)
        val dateText : TextView = dialogView.findViewById(R.id.text_inquireclickdate)
        val answerDateText: TextView = dialogView.findViewById(R.id.text_inquireclickanswerdate)

        dateText.text = date
        answerDateText.text = answerDate
        titleText.text = title
        contentText.text = content
        answerText.text = answer

        dialog!!.getWindow()!!.getAttributes().windowAnimations = R.style.DialogSlideRight
        dialog!!.addContentView(dialogView, ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT))

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    fun setIncompleteProfile() {
        dialog!!.setContentView(R.layout.dialog_incompleteprofile)

        val editBtn : Button = dialog!!.findViewById(R.id.btn_incompleteprofileedit)
        val cancelBtn : TextView = dialog!!.findViewById(R.id.text_incompleteprofilecancel)
        val profileImage: ImageView = dialog!!.findViewById(R.id.image_incompleteprofile)

        VolleyService.getProfileImageReq(UserInfo.ID, context!!, {success->
            var array = success

            var json = array[0] as JSONObject

            Glide.with(context!!).load(json.getString("image")).apply(RequestOptions().override(100, 100)).apply(RequestOptions().circleCrop()).into(profileImage)
        })

        editBtn.setOnClickListener {
            dismiss()
            var intent = Intent(context!!, EditProfileActivity::class.java)
            context!!.startActivity(intent)
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    fun setIncompleteEditProfile() {
        dialog!!.setContentView(R.layout.dialog_incompleteeditprofile)

        val stayBtn : Button = dialog!!.findViewById(R.id.btn_incompleteeditprofileedit)
        val cancelBtn : TextView = dialog!!.findViewById(R.id.text_incompleteeditprofilecancel)

        stayBtn.setOnClickListener {
            dismiss()
        }

        cancelBtn.setOnClickListener {
            dismiss()
            VolleyService.updateReq("user", "user_enable=0", "user_id='${UserInfo.ID}'", context!!,
                { success ->
                    UserInfo.ENABLE = 0
                })
            context!!.finish()
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