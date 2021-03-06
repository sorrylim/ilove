package com.ilove.ilove.MainActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ilove.ilove.Class.GpsTracker
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.activity_story.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class StoryActivity : PSAppCompatActivity() {
    var userPhone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        toolbarBinding(toolbar_story, "", true)

        var image : ImageView = findViewById(R.id.image_storyimage)
        var psDialog = PSDialog(this)


        image.setClipToOutline(true)


        var intent = intent
        var imgUrl = intent.getStringExtra("image")
        var imageId = intent.getIntExtra("image_id", 0)
        var userId: String = ""
        var userNickname: String = ""
        var age: String = ""

        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var curDate = simpleDateFormat.format(System.currentTimeMillis())

        VolleyService.getStoryUserReq(UserInfo.ID, imageId, this, {success->
            psDialog.setLoadingDialog()
            psDialog.show()
            var gpsTracker = GpsTracker(this)
            var partnerDate : Date = simpleDateFormat.parse(success.getString("user_recenttime"))

            userPhone = success.getString("user_phone")
            age = (curDate.substring(0, 4).toInt() - success.getString("user_birthday").substring(0, 4).toInt() + 1).toString()
            userNickname = success.getString("user_nickname")
            text_storynickname.text = userNickname + ", " + age
            text_storyrecenttime.text = gpsTracker.timeDiff(partnerDate.getTime())

            if(success.getString("image_content") == "NULL") {
                text_storycontent.visibility = View.GONE
            }
            else {
                text_storycontent.text = success.getString("image_content")
            }

            text_storyviewcount.text = success.getInt("viewcount").toString()
            text_storylikecount.text = success.getInt("likecount").toString()
            userId = success.getString("user_id")
            Glide.with(this).load(imgUrl).transition(DrawableTransitionOptions().crossFade()).apply(RequestOptions().centerCrop()).apply(RequestOptions().override(300, 300)).into(image_storyimage)

            if(success.getInt("like") == 1) {
                btn_storylike.setLiked(true)
            }
            else if(success.getInt("like") == 0) {
                btn_storylike.setLiked(false)
            }

            VolleyService.getProfileImageReq(userId, this, {success->
                var json = success[0] as JSONObject
                Glide.with(this).load(json.getString("image")).transition(DrawableTransitionOptions().crossFade()).apply(RequestOptions().circleCrop()).into(image_storyuserprofile)

                layout_storyuser.setOnClickListener {
                    var intent = Intent(this, PartnerActivity::class.java)
                    intent.putExtra("userNickname", userNickname)
                    intent.putExtra("userId", userId)
                    intent.putExtra("userImage", json.getString("image"))
                    intent.putExtra("userAge", age)
                    intent.putExtra("userPhone", userPhone!!)

                    startActivity(intent)
                }
                psDialog.dismiss()
            })
        })

        btn_storylike.setOnLikeListener(object: OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertStoryExpressionReq(UserInfo.ID, imageId, curDate, this@StoryActivity, {success->
                    VolleyService.sendFCMReq(userId,"likestory",this@StoryActivity)
                    if(success=="success") {
                        text_storylikecount.text = (Integer.parseInt(text_storylikecount.text.toString()) + 1).toString()
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(this@StoryActivity.getResources(), R.drawable.bigheart_on, null))
                    }
                    else {
                        Toast.makeText(this@StoryActivity, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            override fun unLiked(likeButton: LikeButton?) {
                VolleyService.deleteStoryExpressionReq(UserInfo.ID, imageId, this@StoryActivity, {success->
                    if(success=="success") {
                        text_storylikecount.text = (Integer.parseInt(text_storylikecount.text.toString()) - 1).toString()
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(this@StoryActivity.getResources(), R.drawable.bigheart_off, null))
                    }
                    else {
                        Toast.makeText(this@StoryActivity, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })

    }
}