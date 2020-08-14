package com.ilove.ilove.IntroActivity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.*
import com.ilove.ilove.Fragment.ProfileFragment
import com.ilove.ilove.Item.UserItem
import com.ilove.ilove.MainActivity.MainActivity
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.image_editmain
import kotlinx.android.synthetic.main.activity_edit_profile.image_editsub1
import kotlinx.android.synthetic.main.activity_edit_profile.image_editsub2
import kotlinx.android.synthetic.main.activity_edit_profile.image_editsub3
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editalcohol
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editasset
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editbloodtype
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editbody
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editbrother
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editchildren
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editchildrenplan
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editcigarette
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editcity
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editcountry
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editdatecost
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editeducation
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editfavoriteperson
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editheight
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editholiday
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editinterest
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editintroduce
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editjob
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editmarriagehistory
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editmarriageplan
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editpersonality
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editpreviewintroduce
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editreligion
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editroommate
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editsalary
import kotlinx.android.synthetic.main.activity_edit_profile.layout_editwishdate
import kotlinx.android.synthetic.main.activity_edit_profile.text_editalcohol
import kotlinx.android.synthetic.main.activity_edit_profile.text_editasset
import kotlinx.android.synthetic.main.activity_edit_profile.text_editbloodtype
import kotlinx.android.synthetic.main.activity_edit_profile.text_editbody
import kotlinx.android.synthetic.main.activity_edit_profile.text_editbrother
import kotlinx.android.synthetic.main.activity_edit_profile.text_editchildren
import kotlinx.android.synthetic.main.activity_edit_profile.text_editchildrenplan
import kotlinx.android.synthetic.main.activity_edit_profile.text_editcigarette
import kotlinx.android.synthetic.main.activity_edit_profile.text_editcity
import kotlinx.android.synthetic.main.activity_edit_profile.text_editcountry
import kotlinx.android.synthetic.main.activity_edit_profile.text_editdatecost
import kotlinx.android.synthetic.main.activity_edit_profile.text_editeducation
import kotlinx.android.synthetic.main.activity_edit_profile.text_editfavoriteperson
import kotlinx.android.synthetic.main.activity_edit_profile.text_editheight
import kotlinx.android.synthetic.main.activity_edit_profile.text_editholiday
import kotlinx.android.synthetic.main.activity_edit_profile.text_editinterest
import kotlinx.android.synthetic.main.activity_edit_profile.text_editintroduce
import kotlinx.android.synthetic.main.activity_edit_profile.text_editjob
import kotlinx.android.synthetic.main.activity_edit_profile.text_editmarriagehistory
import kotlinx.android.synthetic.main.activity_edit_profile.text_editmarriageplan
import kotlinx.android.synthetic.main.activity_edit_profile.text_editpersonality
import kotlinx.android.synthetic.main.activity_edit_profile.text_editpreviewintroduce
import kotlinx.android.synthetic.main.activity_edit_profile.text_editreligion
import kotlinx.android.synthetic.main.activity_edit_profile.text_editroommate
import kotlinx.android.synthetic.main.activity_edit_profile.text_editsalary
import kotlinx.android.synthetic.main.activity_edit_profile.text_editwishdate
import kotlinx.android.synthetic.main.activity_edit_profile.toolbar_editprofile
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.IOException


class EditProfileActivity : PSAppCompatActivity() {

    companion object {
        var handler:Handler? = null
    }

    var imagePath : String? = null
    var imageCaptureUri: Uri? = null
    val PICK_FROM_ALBUM = 1
    var userOptionList = ArrayList<UserItem.UserOption>()
    var profileImageList = ArrayList<ImageView>()
    var profileImageIdList : ArrayList<Int?> = arrayListOf(null, null, null, null)
    var profileImagePath = ArrayList<String>()
    var editImageId : Int? = null
    var editImagePath : String? = null
    var layout:Int? = null

    private val PERMISSIONS_REQUEST_CODE = 100

    var mainprofile:Int?=null

    private val requiredPermission = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private fun checkPermissions(){
        val readStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if(readStoragePermission == PackageManager.PERMISSION_GRANTED) {
            photoFromGallery()
        }
        else {
            ActivityCompat.requestPermissions(this, requiredPermission, PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == requiredPermission.size) {
            var checkResult = true

            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }

            if(checkResult) {
                photoFromGallery()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        
        setContentView(R.layout.activity_edit_profile)
        scroll_editprofile.setOverScrollMode(View.OVER_SCROLL_NEVER)


        var displayMetrics: DisplayMetrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics) // 화면의 가로길이를 구함
        var width = displayMetrics.widthPixels / 3

        var imageMain : ImageView = findViewById(R.id.image_editmain)
        var imageSub1 : ImageView = findViewById(R.id.image_editsub1)
        var imageSub2 : ImageView = findViewById(R.id.image_editsub2)
        var imageSub3 : ImageView = findViewById(R.id.image_editsub3)

        var layoutSubImage : LinearLayout = findViewById(R.id.layout_subimage)
        var layoutMain : ConstraintLayout = findViewById(R.id.layout_editmain)
        var layoutSub1 : ConstraintLayout = findViewById(R.id.layout_editsub1)
        var layoutSub2 : ConstraintLayout = findViewById(R.id.layout_editsub2)
        var layoutSub3 : ConstraintLayout = findViewById(R.id.layout_editsub3)

        layoutSubImage.getLayoutParams().height = width
        layoutMain.getLayoutParams().width = width
        layoutMain.getLayoutParams().height = width
        layoutSub1.getLayoutParams().width = width
        layoutSub1.getLayoutParams().height = width
        layoutSub2.getLayoutParams().width = width
        layoutSub2.getLayoutParams().height = width
        layoutSub3.getLayoutParams().width = width
        layoutSub3.getLayoutParams().height = width

        layoutMain.requestLayout()
        layoutSub1.requestLayout()
        layoutSub2.requestLayout()
        layoutSub3.requestLayout()

        imageMain.setClipToOutline(true)
        imageSub1.setClipToOutline(true)
        imageSub2.setClipToOutline(true)
        imageSub3.setClipToOutline(true)

        profileImageList.add(imageMain)
        profileImageList.add(imageSub1)
        profileImageList.add(imageSub2)
        profileImageList.add(imageSub3)


        toolbarCenterBinding(toolbar_editprofile, "프로필편집", true)

        refreshProfileImage()

        image_editmain.setOnClickListener{
            editImageId= null
            editImagePath = null
            mainprofile=1
            if(profileImageIdList.size!=0) {
                editImageId = profileImageIdList.get(0)
                var list : List<String> = profileImagePath.get(0).split("/")
                editImagePath = list.get(3)
            }
            checkPermissions()
        }

        image_editsub1.setOnClickListener {
            editImagePath = null
            editImageId = null
            mainprofile=0
            when(profileImageIdList.size) {
                1 -> checkPermissions()
                else -> {
                    dialogPhotoType()
                    editImageId = profileImageIdList.get(1)
                    var list : List<String> = profileImagePath.get(1).split("/")
                    editImagePath = list.get(3)
                }
            }
        }

        image_editsub2.setOnClickListener {
            editImagePath = null
            editImageId = null
            mainprofile=0
            when(profileImageIdList.size) {
                1 -> checkPermissions()
                2 -> checkPermissions()
                else -> {
                    dialogPhotoType()
                    editImageId = profileImageIdList.get(2)
                    var list : List<String> = profileImagePath.get(2).split("/")
                    editImagePath = list.get(3)
                }
            }
        }

        image_editsub3.setOnClickListener {
            editImagePath = null
            editImageId = null
            mainprofile=0
            when(profileImageIdList.size) {
                1 -> checkPermissions()
                2 -> checkPermissions()
                3 -> checkPermissions()
                else -> {
                    dialogPhotoType()
                    editImageId = profileImageIdList.get(3)
                    var list : List<String> = profileImagePath.get(3).split("/")
                    editImagePath = list.get(3)
                }
            }
        }

        VolleyService.getUserOptionReq(UserInfo.ID, this, {success->
            var json = success

            if(json.getString("user_introduce") != "null")
            {
                text_editintroduce.text = json.getString("user_introduce")
                text_editintroduce.setTextColor(Color.parseColor("#FFA500"))
                text_editintroduce.setTypeface(text_editintroduce.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_previewintroduce") != "null")
            {
                text_editpreviewintroduce.text = json.getString("user_previewintroduce")
                text_editpreviewintroduce.setTextColor(Color.parseColor("#FFA500"))
                text_editpreviewintroduce.setTypeface(text_editpreviewintroduce.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_purpose") != "null") {
                text_editpurpose.text = json.getString("user_purpose")
                text_editpurpose.setTextColor(Color.parseColor("#FFA500"))
                text_editpurpose.setTypeface(text_editpurpose.getTypeface(), Typeface.BOLD)
            }

            if(json.getString("user_gender") != "null") {
                when(json.getString("user_gender")) {
                    "M" -> text_editgender.text = "남자"
                    "F" -> text_editgender.text = "여자"
                }
                text_editgender.setTextColor(Color.parseColor("#FFA500"))
                text_editgender.setTypeface(text_editgender.getTypeface(), Typeface.BOLD)
            }

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

            if(json.getString("user_interest") != "null")
            {
                if(json.getString("user_interest").length > 8) {
                    text_editinterest.text = json.getString("user_interest").substring(0, 9) + "..."
                    text_editinterest.setTextColor(Color.parseColor("#FFA500"))
                    text_editinterest.setTypeface(text_editinterest.getTypeface(), Typeface.BOLD)
                }
                else {
                    text_editinterest.text = json.getString("user_interest")
                    text_editinterest.setTextColor(Color.parseColor("#FFA500"))
                    text_editinterest.setTypeface(text_editinterest.getTypeface(), Typeface.BOLD)
                }

            }

            if(json.getString("user_personality") != "null")
            {
                if(json.getString("user_personality").length > 8) {
                    text_editpersonality.text = json.getString("user_personality").substring(0, 9) + "..."
                    text_editpersonality.setTextColor(Color.parseColor("#FFA500"))
                    text_editpersonality.setTypeface(text_editpersonality.getTypeface(), Typeface.BOLD)
                }
                else {
                    text_editpersonality.text = json.getString("user_personality")
                    text_editpersonality.setTextColor(Color.parseColor("#FFA500"))
                    text_editpersonality.setTypeface(text_editpersonality.getTypeface(), Typeface.BOLD)
                }

            }

            if(json.getString("user_favoriteperson") != "null")
            {
                if(json.getString("user_favoriteperson").length > 8)
                {
                    text_editfavoriteperson.text = json.getString("user_favoriteperson").substring(0, 9) + "..."
                    text_editfavoriteperson.setTextColor(Color.parseColor("#FFA500"))
                    text_editfavoriteperson.setTypeface(text_editfavoriteperson.getTypeface(), Typeface.BOLD)
                }
                else {
                    text_editfavoriteperson.text = json.getString("user_favoriteperson")
                    text_editfavoriteperson.setTextColor(Color.parseColor("#FFA500"))
                    text_editfavoriteperson.setTypeface(text_editfavoriteperson.getTypeface(), Typeface.BOLD)
                }

            }

        })

        var psDialog = PSDialog(this)

        layout_editpreviewintroduce.setOnClickListener {
            psDialog.setPreviewIntroduce(text_editpreviewintroduce)
            psDialog.show()
        }

        layout_editintroduce.setOnClickListener {
            psDialog.setIntroduce(text_editintroduce)
            psDialog.show()
        }

        layout_editpurpose.setOnClickListener {
            userOptionList.clear()
            purpose()
            psDialog.setUserOption("이용목적", "user_purpose", userOptionList,text_editpurpose)
            psDialog.show()
        }

        layout_editgender.setOnClickListener {
            userOptionList.clear()
            gender()
            psDialog.setUserOption("성별", "user_gender", userOptionList, text_editgender)
            psDialog.show()
        }

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

//        layout_editparenting.setOnClickListener {
//            userOptionList.clear()
//            parenting()
//            psDialog.setUserOption("가사, 육아", "user_parenting", userOptionList, text_editparenting)
//            psDialog.show()
//        }

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

        handler=object : Handler() {
            override fun handleMessage(msg: Message) {
                when(msg.what){
                    0 -> {
                        refreshProfileImage()
                    }
                }
            }
        }

    }

    override fun onBackPressed() {
        checkIncomplete()
    }

    fun profileImageInit() {
        for(i in 0..profileImageList.size-1) {
            profileImageList.get(i).setImageResource(R.drawable.default_image)
        }
    }

    fun setProfileImage(array: JSONArray) {
        profileImageIdList.clear()
        profileImagePath.clear()
        for(i in 0..array.length()-1) {
            var json = array[i] as JSONObject
            Glide.with(this)
                .load(json.getString("image")).apply(RequestOptions().override(100, 100)).transition(
                    DrawableTransitionOptions().crossFade())
                .into(profileImageList.get(i))
            profileImagePath.add(json.getString("image"))
            profileImageIdList.add(json.getInt("image_id"))
        }
    }

    fun refreshProfileImage() {
        VolleyService.getProfileImageReq(UserInfo.ID, this, {success->
            var array = success
            profileImageInit()
            setProfileImage(array)
        })
    }

    fun purpose() {
        userOptionList = arrayListOf(UserItem.UserOption("결혼"), UserItem.UserOption("재혼"), UserItem.UserOption("연애"))
    }

    fun gender() {
        userOptionList = arrayListOf(UserItem.UserOption("남자"), UserItem.UserOption("여자"))
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
            UserItem.UserOption("건설업"), UserItem.UserOption("통신"), UserItem.UserOption("유통"), UserItem.UserOption("서비스업"), UserItem.UserOption("미용관련"), UserItem.UserOption("연예계 관련"), UserItem.UserOption("여행관련"), UserItem.UserOption("결혼관련업"), UserItem.UserOption("복지관련"), UserItem.UserOption("약사"),
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
        userOptionList = arrayListOf(UserItem.UserOption("미혼"), UserItem.UserOption("이혼 했어요"), UserItem.UserOption("사별 했어요"))
    }

    fun children() {
        userOptionList = arrayListOf(UserItem.UserOption("없음"), UserItem.UserOption("동거중"), UserItem.UserOption("별거중"))
    }

    fun marriageplan() {
        userOptionList = arrayListOf(UserItem.UserOption("바로 하고 싶어요"), UserItem.UserOption("좋은 사람 있으면 하고 싶어요"), UserItem.UserOption("상대방에게 맞춰주고 싶어요"), UserItem.UserOption("모르겠어요"),UserItem.UserOption("자유롭게 살고싶어요."),UserItem.UserOption("연애만 하고싶어요."))
    }

    fun childrenplan() {
        userOptionList = arrayListOf(UserItem.UserOption("비출산 주의에요"), UserItem.UserOption("저를 닮은 아이를 낳고싶어요"), UserItem.UserOption("상대방과 의논하고 싶어요"), UserItem.UserOption("상대방에게 맞춰주고 싶어요"), UserItem.UserOption("입양을 하고 싶어요"), UserItem.UserOption("모르겠어요"))
    }

    fun parenting() {
        userOptionList = arrayListOf(UserItem.UserOption("적극적으로 참여하고 싶어요"), UserItem.UserOption("가능하면 참여하고 싶어요"), UserItem.UserOption("상대방에게 맡기고 싶어요"), UserItem.UserOption("가능하면 상대방에게 맡기고 싶어요"), UserItem.UserOption("상대방과 의논하고 싶어요"), UserItem.UserOption("모르겠어요"))
    }

    fun wishdate() {
        userOptionList = arrayListOf(UserItem.UserOption("일단 만나보고 싶어요"), UserItem.UserOption("마음이 맞으면 만나보고 싶어요"), UserItem.UserOption("아이러브팅에서 충분히 얘기해보고 만나고 싶어요"), UserItem.UserOption("모르겠어요"))
    }

    fun costdate() {
        userOptionList = arrayListOf(UserItem.UserOption("상대가 전부 냈으면 좋겠어요"), UserItem.UserOption("제가 많이 냈으면 좋겠어요"), UserItem.UserOption("더치페이 하고싶어요"), UserItem.UserOption("돈이 많은쪽이 냈으면 좋겠어요"), UserItem.UserOption("상대방과 의논하고 싶어요"), UserItem.UserOption("모르겠어요"))
    }

    fun roommate() {
        userOptionList = arrayListOf(UserItem.UserOption("혼자 살아요"), UserItem.UserOption("친구랑 같이 살아요"), UserItem.UserOption("애완동물이랑 같이 살아요"), UserItem.UserOption("가족이랑 살아요"), UserItem.UserOption("기타"))
    }

    fun interest() {
        userOptionList = arrayListOf(UserItem.UserOption("영화보기"), UserItem.UserOption("카페가기"), UserItem.UserOption("코인노래방"), UserItem.UserOption("편맥하기"), UserItem.UserOption("수다떨기"), UserItem.UserOption("맛집찾기"), UserItem.UserOption("야구보기"), UserItem.UserOption("축구보기"), UserItem.UserOption("여행가기"), UserItem.UserOption("등산하기"), UserItem.UserOption("춤추기"), UserItem.UserOption("독서하기"))
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
                Log.d("test", "$imagePath")

                try {
                    CropImage.activity(imageCaptureUri).setAspectRatio(1, 1).setGuidelines(CropImageView.Guidelines.ON).start(this)

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
                        FileUploadUtils.uploadProfileImage(imagePath!!, "", "update", editImageId!!, editImagePath!!)
                    } else {
                        if(mainprofile==1){
                            FileUploadUtils.uploadProfileImage(imagePath!!, "mainprofile", "insert", null, "")
                        }
                        FileUploadUtils.uploadProfileImage(imagePath!!, "profile", "insert", null, "")
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

    fun dialogPhotoType() {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_phototype)

        var editPhotoBtn : TextView = dialog.findViewById(R.id.text_editphoto)
        var deletePhotoBtn : TextView = dialog.findViewById(R.id.text_deletephoto)

        editPhotoBtn.setOnClickListener{
            checkPermissions()
            dialog.dismiss()
        }

        deletePhotoBtn.setOnClickListener {
            VolleyService.deleteImageReq(editImageId!!, editImagePath!!, this, {success->
                if(success == "success") {
                    editImageId = null
                    editImagePath = null
                    refreshProfileImage()
                }
            })

            dialog.dismiss()
        }

        dialog.show()
    }

    fun photoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    fun checkIncomplete() {
        if(text_editpurpose.text == "선택" || text_editgender.text =="선택" ||text_editheight.text == "선택" || text_editbody.text == "선택" || text_editbloodtype.text == "선택" || text_editcity.text == "선택"
            || text_editjob.text == "선택" || text_editeducation.text == "선택" || text_editholiday.text == "선택" || text_editcigarette.text == "선택"
            || text_editalcohol.text == "선택" || text_editreligion.text == "선택" || text_editbrother.text == "선택" || text_editcountry.text == "선택"
            || text_editsalary.text == "선택" || text_editasset.text == "선택" || text_editmarriagehistory.text == "선택" || text_editchildren.text == "선택"
            || text_editmarriageplan.text == "선택" || text_editchildrenplan.text == "선택" || text_editwishdate.text == "선택" || text_editdatecost.text == "선택"
            || text_editroommate.text == "선택" ||  text_editinterest.text == "선택" || text_editpersonality.text == "선택"
            || text_editfavoriteperson.text == "선택") {
            val psDialog = PSDialog(this)
            psDialog.setIncompleteEditProfile()
            psDialog.show()
        }
        else {
            finish()
            VolleyService.updateReq("user", "user_enable=1", "user_id='${UserInfo.ID}'", this, {success->
                UserInfo.ENABLE = 1
            })
        }
    }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item != null) {
//            when (item.itemId) {
//                android.R.id.home -> {
//                    var intent = Intent(this, ProfileFragment::class.java)
//                    startActivity(intent)
//                }
//
//            }
//        }
//        return false
//    }
      if (item != null) {
            when (item.itemId) {
                android.R.id.home -> {
                   onBackPressed()
                }

            }
        }
        return false
    }


}