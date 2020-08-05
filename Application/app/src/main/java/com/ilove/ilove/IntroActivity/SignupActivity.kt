package com.ilove.ilove.IntroActivity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.FileUploadUtils
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.MainActivity.MainActivity
import com.ilove.ilove.MainActivity.PartnerActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_signup.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.IOException
import java.time.LocalDate
import java.util.regex.Pattern

class SignupActivity: PSAppCompatActivity() {

    companion object {
        var handler: Handler? = null
    }

    var userOptionList = ArrayList<UserItem.UserOption>()
    var phone: String? = null
    var imagePath : String? = null
    var imageCaptureUri: Uri? = null
    val PICK_FROM_ALBUM = 1
    var profileImageList = ArrayList<ImageView>()
    var profileImageIdList : ArrayList<Int?> = arrayListOf(null, null, null, null)
    var profileImagePath = ArrayList<String>()
    var editImageId : Int? = null
    var editImagePath : String? = null
    var mainprofile : Int? = null


    private val requiredPermission = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )



    //사용자가 홈키 눌렀을때
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
    }

    //사용자가 뒤로가기 키 눌렀을때
    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        checkPermissions()

        handler =object : Handler() {
            override fun handleMessage(msg: Message) {
                when(msg.what){
                    0 -> {
                        refreshProfileImage()
                    }
                }
            }
        }

        var psDialog = PSDialog(this)
        toolbarBinding(toolbar_signup, "회원가입", true)

        phone = intent.getStringExtra("phone")

        var displayMetrics: DisplayMetrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics) // 화면의 가로길이를 구함
        var width = displayMetrics.widthPixels / 3

        var imageMain : ImageView = findViewById(R.id.image_signupmain)

        var layoutMain : ConstraintLayout = findViewById(R.id.layout_signupmain)

        layoutMain.getLayoutParams().width = width
        layoutMain.getLayoutParams().height = width

        layoutMain.requestLayout()

        imageMain.setClipToOutline(true)


        layout_signuppreviewintroduce.setOnClickListener {
            psDialog.setPreviewIntroduce(text_signuppreviewintroduce)
            psDialog.show()
        }

        layout_signupintroduce.setOnClickListener {
            psDialog.setIntroduce(text_signupintroduce)
            psDialog.show()
        }

        refreshProfileImage()

        imageMain.setOnClickListener{
            VolleyService.getProfileImageReq(phone!!, this, {success->
                var array = success
                if(array.length() == 0) {
                    photoFromGallery()
                }
                else {
                    var json = array[0] as JSONObject
                    var list : List<String> = json.getString("image").split("/")
                    editImageId = json.getInt("image_id")
                    editImagePath = list.get(3)
                    photoFromGallery()
                }
            })
        }


        btn_save.setOnClickListener {
            if(imageMain.background.equals(R.drawable.default_image)) {
                Toast.makeText(this, "프로필 사진은 필수요소 입니다.", Toast.LENGTH_SHORT).show()
            }
            else if(edit_nickname.text.toString()==""){
                Toast.makeText(this,"닉네임을 입력해주세요.",Toast.LENGTH_SHORT).show()
            }
            else if(edit_birth.text.toString()==""){
                Toast.makeText(this,"생년월일을 입력해주세요.",Toast.LENGTH_SHORT).show()
            }
            else if(edit_birth.text.toString().length!=8){
                Toast.makeText(this,"생년월일 형식을 확인해주세요.",Toast.LENGTH_SHORT).show()
            }
            else if(text_signuppreviewintroduce.text == "한줄 소개를 입력해주세요.") {
                Toast.makeText(this, "한줄 소개를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if(text_signupintroduce.text == "자기 소개를 입력해주세요.") {
                Toast.makeText(this, "자기 소개를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                var nickname=edit_nickname.text.toString()
                var birth = edit_birth.text.toString()
                var user_signdate =LocalDate.now()

                var table = "user"
                var col = "user_id, user_nickname, user_birthday, user_authority, user_signdate, user_phone, user_previewintroduce, user_introduce"
                var values = "'${phone}', '${nickname}', '${birth}', 'normal', '${user_signdate}', '${phone}', '${text_signuppreviewintroduce.text}', '${text_signupintroduce.text}'"

                VolleyService.insertReq(table,col, values, this,{success->
                    var userPref = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                    var editor = userPref.edit()
                    editor.putString("ID", phone).apply()
                    UserInfo.ID = phone!!

                    VolleyService.insertReq("useroption", "user_id", "'${phone!!}'", this, {success->
                        var psDialog=PSDialog(this)
                        purpose(psDialog)
                    })
                })
            }
        }
    }

    fun gender(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("여성"), UserItem.UserOption("남성"))
        psDialog.setUserOption_signup("성별", "user_gender", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            height(psDialog)
        }

    }

    fun purpose(psDialog: PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("결혼"), UserItem.UserOption("재혼"), UserItem.UserOption("연애"))
        psDialog.setUserOption_signup("이용목적", "user_purpose", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            gender(psDialog)
        }
    }

    fun height(psDialog:PSDialog) {
        userOptionList.clear()
        for(i in 130..200) {
            userOptionList.add(UserItem.UserOption(i.toString()+"cm"))
        }
        psDialog.setUserOption_signup("키", "user_height", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            bodyType(psDialog)
        }


    }

    fun bodyType(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("마른"), UserItem.UserOption("슬림탄탄"), UserItem.UserOption("보통"), UserItem.UserOption("글래머"), UserItem.UserOption("근육질"), UserItem.UserOption("통통한"), UserItem.UserOption("뚱뚱한"), UserItem.UserOption("기타"))
        psDialog.setUserOption_signup("체형", "user_bodytype", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            bloodType(psDialog)
        }
    }

    fun bloodType(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("A"), UserItem.UserOption("B"), UserItem.UserOption("O"), UserItem.UserOption("AB"), UserItem.UserOption("몰라요"))
        psDialog.setUserOption_signup("혈액형", "user_bloodType", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            regidence(psDialog)
        }
    }

    fun regidence(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("서울"), UserItem.UserOption("경기"), UserItem.UserOption("인천"), UserItem.UserOption("대전"), UserItem.UserOption("대구"), UserItem.UserOption("부산"), UserItem.UserOption("울산"), UserItem.UserOption("광주"), UserItem.UserOption("강원"), UserItem.UserOption("세종"), UserItem.UserOption("충북"),
                UserItem.UserOption("충남"), UserItem.UserOption("경북"), UserItem.UserOption("전남"), UserItem.UserOption("제주"), UserItem.UserOption("해외"))
        psDialog.setUserOption_signup("거주지", "user_city", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            job(psDialog)
        }
    }

    fun job(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("회사원"), UserItem.UserOption("사무직"), UserItem.UserOption("대기업"), UserItem.UserOption("외국계 컨설팅관련"), UserItem.UserOption("크리에이터"), UserItem.UserOption("IT관련업"), UserItem.UserOption("대기업 무역관련"), UserItem.UserOption("의사"),
                UserItem.UserOption("변호사"), UserItem.UserOption("공인회계사"), UserItem.UserOption("사장, 임원"), UserItem.UserOption("외국계 금융관련"), UserItem.UserOption("공무원"), UserItem.UserOption("학생"), UserItem.UserOption("승무원"), UserItem.UserOption("교수, 선생님"), UserItem.UserOption("패션업"),
                UserItem.UserOption("연예인, 모델"), UserItem.UserOption("광고"), UserItem.UserOption("방송, 신문사"), UserItem.UserOption("WEB관련"), UserItem.UserOption("아나운서"), UserItem.UserOption("나레이터 모델"), UserItem.UserOption("안내원"), UserItem.UserOption("비서"), UserItem.UserOption("간호사"), UserItem.UserOption("보육사"),
                UserItem.UserOption("자영업"), UserItem.UserOption("파일럿"), UserItem.UserOption("중소기업"), UserItem.UserOption("금융업"), UserItem.UserOption("컨설팅업"), UserItem.UserOption("조리사, 영양사"), UserItem.UserOption("교육관련"), UserItem.UserOption("식품관련"), UserItem.UserOption("제약관련"), UserItem.UserOption("보험"), UserItem.UserOption("부동산"),
                UserItem.UserOption("건설업"), UserItem.UserOption("통신"), UserItem.UserOption("유통"), UserItem.UserOption("서비스업"), UserItem.UserOption("미용관련"), UserItem.UserOption("연예게 관련"), UserItem.UserOption("여행관련"), UserItem.UserOption("결혼관련업"), UserItem.UserOption("복지관련"), UserItem.UserOption("약사"),
                UserItem.UserOption("스포츠선수"), UserItem.UserOption("기타"))
        psDialog.setUserOption_signup("직업", "user_job", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            education(psDialog)
        }
    }

    fun education(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("대학(2년제)/전문대 졸업"), UserItem.UserOption("고등학교 졸업"), UserItem.UserOption("대학교(4년제)졸업"), UserItem.UserOption("대학원 졸업"), UserItem.UserOption("기타"))
        psDialog.setUserOption_signup("학력", "user_education", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            holiday(psDialog)
        }
    }

    fun holiday(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("토일"), UserItem.UserOption("평일"), UserItem.UserOption("때에 따라 달라요"))
        psDialog.setUserOption_signup("휴일", "user_holiday", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            cigarette(psDialog)
        }
    }

    fun cigarette(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("비흡연자"), UserItem.UserOption("흡연자"), UserItem.UserOption("상대방이 싫다면 끊을 수 있어요"), UserItem.UserOption("가끔 피워요"))
        psDialog.setUserOption_signup("담배", "user_cigarette", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            alcohol(psDialog)
        }
    }

    fun alcohol(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("안마셔요"), UserItem.UserOption("마셔요"), UserItem.UserOption("가끔마셔요"))
        psDialog.setUserOption_signup("술", "user_alcohol", userOptionList, phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            religion(psDialog)
        }
    }

    fun religion(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("기독교"), UserItem.UserOption("불교"), UserItem.UserOption("천주교"), UserItem.UserOption("무교"), UserItem.UserOption("기타"))
        psDialog.setUserOption_signup("종교", "user_religion", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            brother(psDialog)
        }
    }

    fun brother(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("첫째"), UserItem.UserOption("둘째"), UserItem.UserOption("셋째"), UserItem.UserOption("기타"))
        psDialog.setUserOption_signup("형제자매", "user_brother", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            country(psDialog)
        }
    }

    fun country(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("대한민국"), UserItem.UserOption("일본"), UserItem.UserOption("중국"), UserItem.UserOption("미국"), UserItem.UserOption("캐나다"), UserItem.UserOption("영국"), UserItem.UserOption("싱가포르"), UserItem.UserOption("대만"), UserItem.UserOption("기타"))
        psDialog.setUserOption_signup("국적", "user_country", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            salary(psDialog)
        }
    }

    fun salary(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("2000만원 미만"), UserItem.UserOption("3000만원 이상"), UserItem.UserOption("4000만원 이상"), UserItem.UserOption("5000만원 이상"), UserItem.UserOption("6000만원 이상"), UserItem.UserOption("7000만원 이상"), UserItem.UserOption("8000만원 이상"))
        psDialog.setUserOption_signup("연수입", "user_salary", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            asset(psDialog)
        }
    }

    fun asset(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("3천만원 미만"), UserItem.UserOption("7천만원 미만"), UserItem.UserOption("1억 미만"), UserItem.UserOption("3억 미만"), UserItem.UserOption("5억 미만"), UserItem.UserOption("10억 미만"), UserItem.UserOption("10억 이상"))
        psDialog.setUserOption_signup("재산", "user_asset", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            marriagehistory(psDialog)
        }
    }

    fun marriagehistory(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("미혼"), UserItem.UserOption("이혼 했어요"), UserItem.UserOption("사별 했어요"))
        psDialog.setUserOption_signup("혼인이력", "user_marriagehistory", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            children(psDialog)
        }
    }

    fun children(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("없음"), UserItem.UserOption("동거중"), UserItem.UserOption("별거중"))
        psDialog.setUserOption_signup("자녀", "user_children", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            marriageplan(psDialog)
        }
    }

    fun marriageplan(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("바로 하고 싶어요"), UserItem.UserOption("좋은 사람 있으면 하고 싶어요"), UserItem.UserOption("상대방에게 맞춰주고 싶어요"), UserItem.UserOption("모르겠어요"),UserItem.UserOption("자유롭게 살고싶어요."),UserItem.UserOption("연애만 하고싶어요."))
        psDialog.setUserOption_signup("결혼계획", "user_marriageplan", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            childrenplan(psDialog)
        }
    }

    fun childrenplan(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("비출산 주의에요"), UserItem.UserOption("저를 닮은 아이를 낳고싶어요"), UserItem.UserOption("상대방과 의논하고 싶어요"), UserItem.UserOption("상대방에게 맞춰주고 싶어요"), UserItem.UserOption("입양을 하고 싶어요"), UserItem.UserOption("모르겠어요"))
        psDialog.setUserOption_signup("자녀계획", "user_childrenplan", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            wishdate(psDialog)
        }
    }

    fun parenting(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("적극적으로 참여하고 싶어요"), UserItem.UserOption("가능하면 참여하고 싶어요"), UserItem.UserOption("상대방에게 맡기고 싶어요"), UserItem.UserOption("가능하면 상대방에게 맡기고 싶어요"), UserItem.UserOption("상대방과 의논하고 싶어요"), UserItem.UserOption("모르겠어요"))
        psDialog.setUserOption_signup("가사,육아", "user_parenting", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            wishdate(psDialog)
        }
    }

    fun wishdate(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("일단 만나보고 싶어요"), UserItem.UserOption("마음이 맞으면 만나보고 싶어요"), UserItem.UserOption("아이러브팅에서 충분히 얘기해보고 만나고 싶어요"), UserItem.UserOption("모르겠어요"))
        psDialog.setUserOption_signup("데이트 희망사항", "user_wishdate", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            costdate(psDialog)
        }
    }

    fun costdate(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("상대가 전부 냈으면 좋겠어요"), UserItem.UserOption("제가 많이 냈으면 좋겠어요"), UserItem.UserOption("더치페이 하고싶어요"), UserItem.UserOption("돈이 많은쪽이 냈으면 좋겠어요"), UserItem.UserOption("상대방과 의논하고 싶어요"), UserItem.UserOption("모르겠어요"))
        psDialog.setUserOption_signup("데이트비용", "user_datecost", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            roommate(psDialog)
        }
    }

    fun roommate(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("혼자 살아요"), UserItem.UserOption("친구랑 같이 살아요"), UserItem.UserOption("애완동물이랑 같이 살아요"), UserItem.UserOption("가족이랑 살아요"), UserItem.UserOption("기타"))
        psDialog.setUserOption_signup("동거인", "user_roommate", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            language(psDialog)
        }
    }

    fun language(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("영어"), UserItem.UserOption("일본어"), UserItem.UserOption("중국어"), UserItem.UserOption("힌두어"), UserItem.UserOption("스페인어"), UserItem.UserOption("러시아어"), UserItem.UserOption("프랑스어"), UserItem.UserOption("아라비아어"), UserItem.UserOption("포르투갈어"), UserItem.UserOption("말레이시아어"), UserItem.UserOption("뱅골어"), UserItem.UserOption("독일어"))
        psDialog.setUserOption_signup("사용가능언어", "user_language", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            interest(psDialog)
        }
    }

    fun interest(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("영화보기"), UserItem.UserOption("카페가기"), UserItem.UserOption("코인노래방"), UserItem.UserOption("편맥하기"), UserItem.UserOption("수다떨기"), UserItem.UserOption("맞집찾기"), UserItem.UserOption("야구보기"), UserItem.UserOption("축구보기"), UserItem.UserOption("여행가기"), UserItem.UserOption("등산하기"), UserItem.UserOption("춤추기"), UserItem.UserOption("독서하기"))
        psDialog.setUserOption_signup("관심사", "user_interest", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            personality(psDialog)
        }
    }

    fun personality(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("엉뚱"), UserItem.UserOption("활발"), UserItem.UserOption("도도"), UserItem.UserOption("친절"), UserItem.UserOption("애교"), UserItem.UserOption("털털"), UserItem.UserOption("성실"), UserItem.UserOption("착한"), UserItem.UserOption("4차원"), UserItem.UserOption("순수"), UserItem.UserOption("귀여운"), UserItem.UserOption("다정다감"))
        psDialog.setUserOption_signup("내 성격", "user_personality", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            favoriteperson(psDialog)
        }
    }

    fun favoriteperson(psDialog:PSDialog) {
        userOptionList.clear()
        userOptionList = arrayListOf(UserItem.UserOption("귀여운"), UserItem.UserOption("털털한"), UserItem.UserOption("잘 웃는"), UserItem.UserOption("유머러스한"), UserItem.UserOption("다정다감한"), UserItem.UserOption("엄청 착한"), UserItem.UserOption("순수한"), UserItem.UserOption("박력있는"), UserItem.UserOption("듬직한"), UserItem.UserOption("마음이 예쁜"), UserItem.UserOption("욕 안하는"), UserItem.UserOption("열정적인"))
        psDialog.setUserOption_signup("내가 좋아하는 사람", "user_favoriteperson", userOptionList,phone!!)
        psDialog.show()
        psDialog.dialog!!.setOnDismissListener {
            var intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }


        when (requestCode) {
            PICK_FROM_ALBUM -> {
                imageCaptureUri = data!!.data
                Log.d("test", "$imagePath")

                try {
                    CropImage.activity(imageCaptureUri).setGuidelines(CropImageView.Guidelines.ON).start(this)

                } catch (e: FileNotFoundException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                } catch (e: IOException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

            }
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri: Uri = result.uri
                    var imagePath = resultUri.getPath()

                    if(editImageId != null) {
                        FileUploadUtils.uploadSignupProfileImage(imagePath!!, "", "update", editImageId!!, editImagePath!!, phone!!)
                    } else {
                        FileUploadUtils.uploadSignupProfileImage(imagePath!!, "mainprofile", "insert", null, "", phone!!)
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                    editImageId = null
                    editImagePath = null
                }
                editImageId = null
                editImagePath = null
            }

        }
    }

    fun refreshProfileImage() {
        VolleyService.getProfileImageReq(phone!!, this, {success->
            var array = success
            if(array.length() != 0) {
                var json = array[0] as JSONObject
                Glide.with(this).load(json.getString("image")).apply(RequestOptions().override(640, 640))
                    .into(image_signupmain)
            }
        })
    }

    private fun checkPermissions(){
        val rejectedPermissionList = java.util.ArrayList<String>()

        for(permission in requiredPermission){
            if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED) {
                rejectedPermissionList.add(permission)
            }
        }

        if(rejectedPermissionList.isNotEmpty()){
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this,rejectedPermissionList.toArray(array), 100)
        }
    }

    fun photoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }
}