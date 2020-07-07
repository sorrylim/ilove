package com.ilove.ilove.IntroActivity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_write_story.*
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.IOException


class EditProfileActivity : PSAppCompatActivity() {

    var imagePath : String? = null
    var imageCaptureUri: Uri? = null
    val PICK_FROM_ALBUM = 1
    var userOptionList = ArrayList<UserItem.UserOption>()
    var mainProfileImagePath = ""
    var mainProfileImageId : Int? = null
    var editProfileImage1Path = ""
    var editProfileImage1Id : Int? = null
    var editProfileImage2Path = ""
    var editProfileImage2Id : Int? = null
    var editProfileImage3Path = ""
    var editProfileImage3Id : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        image_editmain.setClipToOutline(true)
        image_editsub1.setClipToOutline(true)
        image_editsub2.setClipToOutline(true)
        image_editsub3.setClipToOutline(true)

        toolbarBinding(toolbar_editprofile, "프로필 편집", true)

        image_editmain.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setType("image/*")
            startActivityForResult(intent, PICK_FROM_ALBUM)
        }

        VolleyService.getProfileImageReq(UserInfo.ID, this, {success->
            var array = success
            if(array.length() == 1) {
                var json = array[0] as JSONObject
                mainProfileImageId = json.getInt("image_id")
                mainProfileImagePath = json.getString("image")
                Glide.with(this)
                    .load(mainProfileImagePath)
                    .into(image_editmain)
            }
            else if(array.length() == 2) {
                var json = array[0] as JSONObject
                mainProfileImageId = json.getInt("image_id")
                mainProfileImagePath = json.getString("image")
                Glide.with(this)
                    .load(mainProfileImagePath)
                    .into(image_editmain)
                var json1 = array[1] as JSONObject
                editProfileImage1Id = json1.getInt("image_id")
                editProfileImage1Path = json1.getString("image")
                Glide.with(this)
                    .load(editProfileImage1Path)
                    .into(image_editsub1)
            }
            else if(array.length() == 3) {
                var json = array[0] as JSONObject
                mainProfileImageId = json.getInt("image_id")
                mainProfileImagePath = json.getString("image")
                Glide.with(this)
                    .load(mainProfileImagePath)
                    .into(image_editmain)
                var json1 = array[1] as JSONObject
                editProfileImage1Id = json1.getInt("image_id")
                editProfileImage1Path = json1.getString("image")
                Glide.with(this)
                    .load(editProfileImage1Path)
                    .into(image_editsub1)
                var json2 = array[2] as JSONObject
                editProfileImage2Id = json2.getInt("image_id")
                editProfileImage2Path = json2.getString("image")
                Glide.with(this)
                    .load(editProfileImage2Path)
                    .into(image_editsub2)
            }
            else if(array.length() == 4) {
                var json = array[0] as JSONObject
                mainProfileImageId = json.getInt("image_id")
                mainProfileImagePath = json.getString("image")
                Glide.with(this)
                    .load(mainProfileImagePath)
                    .into(image_editmain)
                var json1 = array[1] as JSONObject
                editProfileImage1Id = json1.getInt("image_id")
                editProfileImage1Path = json1.getString("image")
                Glide.with(this)
                    .load(editProfileImage1Path)
                    .into(image_editsub1)
                var json2 = array[2] as JSONObject
                editProfileImage2Id = json2.getInt("image_id")
                editProfileImage2Path = json2.getString("image")
                Glide.with(this)
                    .load(editProfileImage2Path)
                    .into(image_editsub2)
                var json3 = array[3] as JSONObject
                editProfileImage3Id = json3.getInt("image_id")
                editProfileImage3Path = json3.getString("image")
                Glide.with(this)
                    .load(editProfileImage3Path)
                    .into(image_editsub3)
            }
        })

        VolleyService.getUserOptionReq(UserInfo.ID, this, {success->
            var json = success

            if(json.getString("user_height") != "null")
            {
                text_editheight.text = json.getString("user_height")
                text_editheight.setTextColor(Color.parseColor("#FFA500"))
                text_editheight.setTypeface(text_editheight.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_bodytype") != "null")
            {
                text_editbody.text = json.getString("user_bodytype")
                text_editbody.setTextColor(Color.parseColor("#FFA500"))
                text_editbody.setTypeface(text_editbody.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_bloodtype") != "null")
            {
                text_editbloodtype.text = json.getString("user_bloodtype")
                text_editbloodtype.setTextColor(Color.parseColor("#FFA500"))
                text_editbloodtype.setTypeface(text_editbloodtype.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_city") != "null")
            {
                text_editcity.text = json.getString("user_city")
                text_editcity.setTextColor(Color.parseColor("#FFA500"))
                text_editcity.setTypeface(text_editcity.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_job") != "null")
            {
                text_editjob.text = json.getString("user_job")
                text_editjob.setTextColor(Color.parseColor("#FFA500"))
                text_editjob.setTypeface(text_editjob.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_education") != "null")
            {
                text_editeducation.text = json.getString("user_education")
                text_editeducation.setTextColor(Color.parseColor("#FFA500"))
                text_editeducation.setTypeface(text_editeducation.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_holiday") != "null")
            {
                text_editholiday.text = json.getString("user_holiday")
                text_editholiday.setTextColor(Color.parseColor("#FFA500"))
                text_editholiday.setTypeface(text_editholiday.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_cigarette") != "null")
            {
                text_editcigarette.text = json.getString("user_cigarette")
                text_editcigarette.setTextColor(Color.parseColor("#FFA500"))
                text_editcigarette.setTypeface(text_editcigarette.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_alcohol") != "null")
            {
                text_editalcohol.text = json.getString("user_alcohol")
                text_editalcohol.setTextColor(Color.parseColor("#FFA500"))
                text_editalcohol.setTypeface(text_editalcohol.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_religion") != "null")
            {
                text_editreligion.text = json.getString("user_religion")
                text_editreligion.setTextColor(Color.parseColor("#FFA500"))
                text_editreligion.setTypeface(text_editreligion.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_brother") != "null")
            {
                text_editbrother.text = json.getString("user_brother")
                text_editbrother.setTextColor(Color.parseColor("#FFA500"))
                text_editbrother.setTypeface(text_editbrother.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_country") != "null")
            {
                text_editcountry.text = json.getString("user_country")
                text_editcountry.setTextColor(Color.parseColor("#FFA500"))
                text_editcountry.setTypeface(text_editcountry.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_salary") != "null")
            {
                text_editsalary.text = json.getString("user_salary")
                text_editsalary.setTextColor(Color.parseColor("#FFA500"))
                text_editsalary.setTypeface(text_editsalary.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_asset") != "null")
            {
                text_editasset.text = json.getString("user_asset")
                text_editasset.setTextColor(Color.parseColor("#FFA500"))
                text_editasset.setTypeface(text_editasset.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_marriagehistory") != "null")
            {
                text_editmarriagehistory.text = json.getString("user_marriagehistory")
                text_editmarriagehistory.setTextColor(Color.parseColor("#FFA500"))
                text_editmarriagehistory.setTypeface(text_editmarriagehistory.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_children") != "null")
            {
                text_editchildren.text = json.getString("user_children")
                text_editchildren.setTextColor(Color.parseColor("#FFA500"))
                text_editchildren.setTypeface(text_editchildren.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_marriageplan") != "null")
            {
                text_editmarriageplan.text = json.getString("user_marriageplan")
                text_editmarriageplan.setTextColor(Color.parseColor("#FFA500"))
                text_editmarriageplan.setTypeface(text_editmarriageplan.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_childrenplan") != "null")
            {
                text_editchildrenplan.text = json.getString("user_childrenplan")
                text_editchildrenplan.setTextColor(Color.parseColor("#FFA500"))
                text_editchildrenplan.setTypeface(text_editchildrenplan.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_parenting") != "null")
            {
                text_editparenting.text = json.getString("user_parenting")
                text_editparenting.setTextColor(Color.parseColor("#FFA500"))
                text_editparenting.setTypeface(text_editparenting.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_wishdate") != "null")
            {
                text_editwishdate.text = json.getString("user_wishdate")
                text_editwishdate.setTextColor(Color.parseColor("#FFA500"))
                text_editwishdate.setTypeface(text_editwishdate.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_datecost") != "null")
            {
                text_editdatecost.text = json.getString("user_datecost")
                text_editdatecost.setTextColor(Color.parseColor("#FFA500"))
                text_editdatecost.setTypeface(text_editdatecost.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_roommate") != "null")
            {
                text_editroommate.text = json.getString("user_roommate")
                text_editroommate.setTextColor(Color.parseColor("#FFA500"))
                text_editroommate.setTypeface(text_editroommate.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_language") != "null")
            {
                text_editlanguage.text = json.getString("user_language")
                text_editlanguage.setTextColor(Color.parseColor("#FFA500"))
                text_editlanguage.setTypeface(text_editlanguage.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_interest") != "null")
            {
                text_editinterest.text = json.getString("user_interest")
                text_editinterest.setTextColor(Color.parseColor("#FFA500"))
                text_editinterest.setTypeface(text_editinterest.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_personality") != "null")
            {
                text_editpersonality.text = json.getString("user_personality")
                text_editpersonality.setTextColor(Color.parseColor("#FFA500"))
                text_editpersonality.setTypeface(text_editpersonality.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_favoriteperson") != "null")
            {
                text_editfavoriteperson.text = json.getString("user_favoriteperson")
                text_editfavoriteperson.setTextColor(Color.parseColor("#FFA500"))
                text_editfavoriteperson.setTypeface(text_editfavoriteperson.getTypeface(), Typeface.BOLD)
            }

        })

        var psDialog = PSDialog(this)


        layout_editheight.setOnClickListener {
            userOptionList.clear()
            height()
            psDialog.setUserOption("키", "user_height", userOptionList, text_editheight)
            psDialog.show()
        }

        layout_editbody.setOnClickListener {
            userOptionList.clear()
            bodyType()
            psDialog.setUserOption("체형", "user_bodytype",userOptionList, text_editbody)
            psDialog.show()
        }

        layout_editbloodtype.setOnClickListener {
            userOptionList.clear()
            bloodType()
            psDialog.setUserOption("혈액형", "user_bloodtype", userOptionList, text_editbloodtype)
            psDialog.show()
        }

        layout_editcity.setOnClickListener {
            userOptionList.clear()
            regidence()
            psDialog.setUserOption("거주지", "user_city", userOptionList, text_editcity)
            psDialog.show()
        }

        layout_editjob.setOnClickListener {
            userOptionList.clear()
            job()
            psDialog.setUserOption("직업", "user_job", userOptionList, text_editjob)
            psDialog.show()
        }

        layout_editeducation.setOnClickListener {
            userOptionList.clear()
            education()
            psDialog.setUserOption("학력", "user_education", userOptionList, text_editeducation)
            psDialog.show()
        }

        layout_editholiday.setOnClickListener {
            userOptionList.clear()
            holiday()
            psDialog.setUserOption("휴일", "user_holiday", userOptionList, text_editholiday)
            psDialog.show()
        }

        layout_editcigarette.setOnClickListener {
            userOptionList.clear()
            cigarette()
            psDialog.setUserOption("담배", "user_cigarette", userOptionList, text_editcigarette)
            psDialog.show()
        }

        layout_editalcohol.setOnClickListener {
            userOptionList.clear()
            alcohol()
            psDialog.setUserOption("술", "user_alcohol", userOptionList, text_editalcohol)
            psDialog.show()
        }

        layout_editreligion.setOnClickListener {
            userOptionList.clear()
            religion()
            psDialog.setUserOption("종교", "user_religion", userOptionList, text_editreligion)
            psDialog.show()
        }

        layout_editbrother.setOnClickListener {
            userOptionList.clear()
            brother()
            psDialog.setUserOption("형제자매", "user_brother", userOptionList, text_editbrother)
            psDialog.show()
        }

        layout_editcountry.setOnClickListener {
            userOptionList.clear()
            country()
            psDialog.setUserOption("국적", "user_country", userOptionList, text_editcountry)
            psDialog.show()
        }

        layout_editsalary.setOnClickListener {
            userOptionList.clear()
            salary()
            psDialog.setUserOption("연수입", "user_salary", userOptionList, text_editsalary)
            psDialog.show()
        }

        layout_editasset.setOnClickListener {
            userOptionList.clear()
            asset()
            psDialog.setUserOption("재산", "user_asset", userOptionList, text_editasset)
            psDialog.show()
        }

        layout_editmarriagehistory.setOnClickListener {
            userOptionList.clear()
            marriagehistory()
            psDialog.setUserOption("혼인이력", "user_marriagehistory", userOptionList, text_editmarriagehistory)
            psDialog.show()
        }

        layout_editchildren.setOnClickListener {
            userOptionList.clear()
            children()
            psDialog.setUserOption("자녀", "user_children", userOptionList, text_editchildren)
            psDialog.show()
        }

        layout_editmarriageplan.setOnClickListener {
            userOptionList.clear()
            marriageplan()
            psDialog.setUserOption("결혼계획", "user_marriageplan", userOptionList, text_editmarriageplan)
            psDialog.show()
        }

        layout_editchildrenplan.setOnClickListener {
            userOptionList.clear()
            childrenplan()
            psDialog.setUserOption("자녀계획", "user_childrenplan", userOptionList, text_editchildrenplan)
            psDialog.show()
        }

        layout_editparenting.setOnClickListener {
            userOptionList.clear()
            parenting()
            psDialog.setUserOption("가사, 육아", "user_parenting", userOptionList, text_editparenting)
            psDialog.show()
        }

        layout_editwishdate.setOnClickListener {
            userOptionList.clear()
            wishdate()
            psDialog.setUserOption("데이트 희망사항", "user_wishdate", userOptionList, text_editwishdate)
            psDialog.show()
        }

        layout_editdatecost.setOnClickListener {
            userOptionList.clear()
            costdate()
            psDialog.setUserOption("데이트 비용", "user_datecost", userOptionList, text_editdatecost)
            psDialog.show()
        }

        layout_editroommate.setOnClickListener {
            userOptionList.clear()
            roommate()
            psDialog.setUserOption("동거인", "user_roommate", userOptionList, text_editroommate)
            psDialog.show()
        }

        layout_editlanguage.setOnClickListener {
            userOptionList.clear()
            language()
            psDialog.setUserOption("사용가능언어", "user_language", userOptionList, text_editlanguage)
            psDialog.show()
        }

        layout_editinterest.setOnClickListener {
            userOptionList.clear()
            interest()
            psDialog.setUserOption("관심사", "user_interest", userOptionList, text_editinterest)
            psDialog.show()
        }

        layout_editpersonality.setOnClickListener {
            userOptionList.clear()
            personality()
            psDialog.setUserOption("내 성격", "user_personality", userOptionList, text_editpersonality)
            psDialog.show()
        }

        layout_editfavoriteperson.setOnClickListener {
            userOptionList.clear()
            favoriteperson()
            psDialog.setUserOption("내가 좋아하는 사람", "user_favoriteperson", userOptionList, text_editfavoriteperson)
            psDialog.show()
        }

    }

    fun height() {
        for(i in 130..200) {
            userOptionList.add(UserItem.UserOption(i.toString()+"cm"))
        }
    }

    fun bodyType() {
        userOptionList = arrayListOf(UserItem.UserOption("마른"), UserItem.UserOption("슬림탄탄"), UserItem.UserOption("보통"), UserItem.UserOption("글래머"), UserItem.UserOption("근육질"), UserItem.UserOption("통통한"), UserItem.UserOption("뚱뚱한"), UserItem.UserOption("기타"))
    }

    fun bloodType() {
        userOptionList = arrayListOf(UserItem.UserOption("A"), UserItem.UserOption("B"), UserItem.UserOption("O"), UserItem.UserOption("AB"), UserItem.UserOption("몰라요"))
    }

    fun regidence() {
        userOptionList = arrayListOf(UserItem.UserOption("서울"), UserItem.UserOption("경기"), UserItem.UserOption("인천"), UserItem.UserOption("대전"), UserItem.UserOption("대구"), UserItem.UserOption("부산"), UserItem.UserOption("울산"), UserItem.UserOption("광주"), UserItem.UserOption("강원"), UserItem.UserOption("세종"), UserItem.UserOption("충북"),
            UserItem.UserOption("충남"), UserItem.UserOption("경북"), UserItem.UserOption("전남"), UserItem.UserOption("제주"), UserItem.UserOption("해외"))
    }

    fun job() {
        userOptionList = arrayListOf(UserItem.UserOption("회사원"), UserItem.UserOption("사무직"), UserItem.UserOption("대기업"), UserItem.UserOption("외국계 컨설팅관련"), UserItem.UserOption("크리에이터"), UserItem.UserOption("IT관련업"), UserItem.UserOption("대기업 무역관련"), UserItem.UserOption("의사"),
            UserItem.UserOption("변호사"), UserItem.UserOption("공인회계사"), UserItem.UserOption("사장, 임원"), UserItem.UserOption("외국계 금융관련"), UserItem.UserOption("공무원"), UserItem.UserOption("학생"), UserItem.UserOption("승무원"), UserItem.UserOption("교수, 선생님"), UserItem.UserOption("패션업"),
            UserItem.UserOption("연예인, 모델"), UserItem.UserOption("광고"), UserItem.UserOption("방송, 신문사"), UserItem.UserOption("WEB관련"), UserItem.UserOption("아나운서"), UserItem.UserOption("나레이터 모델"), UserItem.UserOption("안내원"), UserItem.UserOption("비서"), UserItem.UserOption("간호사"), UserItem.UserOption("보육사"),
            UserItem.UserOption("자영업"), UserItem.UserOption("파일럿"), UserItem.UserOption("중소기업"), UserItem.UserOption("금융업"), UserItem.UserOption("컨설팅업"), UserItem.UserOption("조리사, 영양사"), UserItem.UserOption("교육관련"), UserItem.UserOption("식품관련"), UserItem.UserOption("제약관련"), UserItem.UserOption("보험"), UserItem.UserOption("부동산"),
            UserItem.UserOption("건설업"), UserItem.UserOption("통신"), UserItem.UserOption("유통"), UserItem.UserOption("서비스업"), UserItem.UserOption("미용관련"), UserItem.UserOption("연예게 관련"), UserItem.UserOption("여행관련"), UserItem.UserOption("결혼관련업"), UserItem.UserOption("복지관련"), UserItem.UserOption("약사"),
            UserItem.UserOption("스포츠선수"), UserItem.UserOption("기타"))
    }

    fun education() {
        userOptionList = arrayListOf(UserItem.UserOption("대학(2년제)/전문대 졸업"), UserItem.UserOption("고등학교 졸업"), UserItem.UserOption("대학교(4년제)졸업"), UserItem.UserOption("대학원 졸업"), UserItem.UserOption("기타"))
    }

    fun holiday() {
        userOptionList = arrayListOf(UserItem.UserOption("토일"), UserItem.UserOption("평일"), UserItem.UserOption("때에 따라 달라요"))
    }

    fun cigarette() {
        userOptionList = arrayListOf(UserItem.UserOption("비흡연자"), UserItem.UserOption("흡연자"), UserItem.UserOption("상대방이 싫다면 끊을 수 있어요"), UserItem.UserOption("가끔 피워요"))
    }

    fun alcohol() {
        userOptionList = arrayListOf(UserItem.UserOption("안마셔요"), UserItem.UserOption("마셔요"), UserItem.UserOption("가끔마셔요"))
    }

    fun religion() {
        userOptionList = arrayListOf(UserItem.UserOption("기독교"), UserItem.UserOption("불교"), UserItem.UserOption("천주교"), UserItem.UserOption("무교"), UserItem.UserOption("기타"))
    }

    fun brother() {
        userOptionList = arrayListOf(UserItem.UserOption("첫째"), UserItem.UserOption("둘째"), UserItem.UserOption("셋째"), UserItem.UserOption("기타"))
    }

    fun country() {
        userOptionList = arrayListOf(UserItem.UserOption("대한민국"), UserItem.UserOption("일본"), UserItem.UserOption("중국"), UserItem.UserOption("미국"), UserItem.UserOption("캐나다"), UserItem.UserOption("영국"), UserItem.UserOption("싱가포르"), UserItem.UserOption("대만"), UserItem.UserOption("기타"))
    }

    fun salary() {
        userOptionList = arrayListOf(UserItem.UserOption("2000만원 미만"), UserItem.UserOption("3000만원 이상"), UserItem.UserOption("4000만원 이상"), UserItem.UserOption("5000만원 이상"), UserItem.UserOption("6000만원 이상"), UserItem.UserOption("7000만원 이상"), UserItem.UserOption("8000만원 이상"))
    }

    fun asset() {
        userOptionList = arrayListOf(UserItem.UserOption("3천만원 미만"), UserItem.UserOption("7천만원 미만"), UserItem.UserOption("1억 미만"), UserItem.UserOption("3억 미만"), UserItem.UserOption("5억 미만"), UserItem.UserOption("10억 미만"), UserItem.UserOption("10억 이상"))
    }

    fun marriagehistory() {
        userOptionList = arrayListOf(UserItem.UserOption("없어요"), UserItem.UserOption("이혼 했어요"), UserItem.UserOption("사별 했어요"))
    }

    fun children() {
        userOptionList = arrayListOf(UserItem.UserOption("없음"), UserItem.UserOption("동거중"), UserItem.UserOption("별거중"))
    }

    fun marriageplan() {
        userOptionList = arrayListOf(UserItem.UserOption("바로 하고 싶어요"), UserItem.UserOption("좋은 사람 있으면 하고 싶어요"), UserItem.UserOption("상대방에게 맞춰주고 싶어요"), UserItem.UserOption("모르겠어요"))
    }

    fun childrenplan() {
        userOptionList = arrayListOf(UserItem.UserOption("비출산 주의에요"), UserItem.UserOption("저를 닮은 아이를 낳고싶어요"), UserItem.UserOption("상대방과 의논하고 싶어요"), UserItem.UserOption("상대방에게 맞춰주고 싶어요"), UserItem.UserOption("입양을 하고 싶어요"), UserItem.UserOption("모르겠어요"))
    }

    fun parenting() {
        userOptionList = arrayListOf(UserItem.UserOption("적극적으로 참여하고 싶어요"), UserItem.UserOption("가능하면 참여하고 싶어요"), UserItem.UserOption("상대방에게 맡기고 싶어요"), UserItem.UserOption("가능하면 상대방에게 맡기고 싶어요"), UserItem.UserOption("상대방과 의논하고 싶어요"), UserItem.UserOption("모르겠어요"))
    }

    fun wishdate() {
        userOptionList = arrayListOf(UserItem.UserOption("일단 만나보고 싶어요"), UserItem.UserOption("마음이 맞으면 만나보고 싶어요"), UserItem.UserOption("아이러브에서 충분히 얘기해보고 만나고 싶어요"), UserItem.UserOption("모르겠어요"))
    }

    fun costdate() {
        userOptionList = arrayListOf(UserItem.UserOption("남자가 전부 냈으면 좋겠어요"), UserItem.UserOption("남자가 많이 냈으면 좋겠어요"), UserItem.UserOption("더치페이 하고싶어요"), UserItem.UserOption("돈이 많은쪽이 냈으면 좋겠어요"), UserItem.UserOption("상대방과 의논하고 싶어요"), UserItem.UserOption("모르겠어요"))
    }

    fun roommate() {
        userOptionList = arrayListOf(UserItem.UserOption("자취해요"), UserItem.UserOption("친구랑 같이 살아요"), UserItem.UserOption("애완동물이랑 같이 살아요"), UserItem.UserOption("가족이랑 살아요"), UserItem.UserOption("기타"))
    }

    fun language() {
        userOptionList = arrayListOf(UserItem.UserOption("영어"), UserItem.UserOption("일본어"), UserItem.UserOption("중국어"), UserItem.UserOption("힌두어"), UserItem.UserOption("스페인어"), UserItem.UserOption("러시아어"), UserItem.UserOption("프랑스어"), UserItem.UserOption("아라비아어"), UserItem.UserOption("포르투갈어"), UserItem.UserOption("말레이시아어"), UserItem.UserOption("뱅골어"), UserItem.UserOption("독일어"))
    }

    fun interest() {
        userOptionList = arrayListOf(UserItem.UserOption("영화보기"), UserItem.UserOption("카페가기"), UserItem.UserOption("코인노래방"), UserItem.UserOption("편맥하기"), UserItem.UserOption("수다떨기"), UserItem.UserOption("맞집찾기"), UserItem.UserOption("야구보기"), UserItem.UserOption("축구보기"), UserItem.UserOption("여행가기"), UserItem.UserOption("등산하기"), UserItem.UserOption("춤추기"), UserItem.UserOption("독서하기"))
    }

    fun personality() {
        userOptionList = arrayListOf(UserItem.UserOption("엉뚱"), UserItem.UserOption("활발"), UserItem.UserOption("도도"), UserItem.UserOption("친절"), UserItem.UserOption("애교"), UserItem.UserOption("털털"), UserItem.UserOption("성실"), UserItem.UserOption("착한"), UserItem.UserOption("4차원"), UserItem.UserOption("순수"), UserItem.UserOption("귀여운"), UserItem.UserOption("다정다감"))
    }

    fun favoriteperson() {
        userOptionList = arrayListOf(UserItem.UserOption("귀여운"), UserItem.UserOption("털털한"), UserItem.UserOption("잘 웃는"), UserItem.UserOption("유머러스한"), UserItem.UserOption("다정다감한"), UserItem.UserOption("엄청 착한"), UserItem.UserOption("순수한"), UserItem.UserOption("박력있는"), UserItem.UserOption("듬직한"), UserItem.UserOption("마음이 예쁜"), UserItem.UserOption("욕 안하는"), UserItem.UserOption("열정적인"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }


        when (requestCode) {
            PICK_FROM_ALBUM -> {
                imageCaptureUri = data!!.data
                //imagePath = /*Environment.getExternalStorageDirectory().getAbsolutePath() +*/ getPath(imageCaptureUri!!)
                Log.d("test", "$imagePath")

                try {
                   /* val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageCaptureUri)
                    image_writestory.visibility = View.VISIBLE
                    image_writestory.setImageBitmap(imageBitmap)*/

                    CropImage.activity(imageCaptureUri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1920, 1080)
                        .setCropShape(CropImageView.CropShape.RECTANGLE).start(this)

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
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                }
            }

        }
    }





}