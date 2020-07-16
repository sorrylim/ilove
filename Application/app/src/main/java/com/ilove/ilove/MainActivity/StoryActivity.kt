package com.ilove.ilove.MainActivity

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
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
import kotlinx.android.synthetic.main.item_storylist.view.*
import org.json.JSONObject
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class StoryActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        toolbarBinding(toolbar_story, "", true)

        var image : ImageView = findViewById(R.id.image_storyimage)
        var gpsTracker = GpsTracker(this)


        image.setClipToOutline(true)

        var intent = intent
        var imgUrl = intent.getStringExtra("image")
        var imageId = intent.getIntExtra("image_id", 0)
        var userId: String = ""
        var userNickname: String = ""
        var age: String = ""
        var userCity : String = ""
        val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        VolleyService.getStoryUserReq(UserInfo.ID, imageId, this, {success->
            var location : List<String> = success.getString("user_recentgps").split(",")
            var distance = gpsTracker.getDistance(UserInfo.LATITUDE!!, UserInfo.LONGITUDE!!, location.get(0), location.get(1))
            age = (currentDate.substring(0, 4).toInt() - success.getString("user_birthday").substring(0, 4).toInt() + 1).toString()
            userNickname = success.getString("user_nickname")
            text_storynickname.text = userNickname + ", " + age
            text_storygps.text = distance
            userCity = success.getString("user_city")

            if(success.getString("image_content") == "NULL") {
                text_storycontent.visibility = View.GONE
            }
            else {
                text_storycontent.text = success.getString("image_content")
            }

            text_storyviewcount.text = success.getInt("viewcount").toString()
            text_storylikecount.text = success.getInt("likecount").toString()
            userId = success.getString("user_id")
            Glide.with(this).load(imgUrl).apply(RequestOptions().centerCrop()).into(image_storyimage)

            if(success.getInt("like") == 1) {
                btn_storylike.setLiked(true)
            }
            else if(success.getInt("like") == 0) {
                btn_storylike.setLiked(false)
            }

            VolleyService.getProfileImageReq(userId, this, {success->
                var json = success[0] as JSONObject
                Glide.with(this).load(json.getString("image")).apply(RequestOptions().circleCrop()).into(image_storyuserprofile)

                layout_storyuser.setOnClickListener {
                    var intent = Intent(this, PartnerActivity::class.java)
                    intent.putExtra("userNickname", userNickname)
                    intent.putExtra("userId", userId)
                    intent.putExtra("userImage", json.getString("image"))
                    intent.putExtra("userAge", age)
                    intent.putExtra("userCity", userCity)

                    startActivity(intent)
                }
            })
        })

        btn_storylike.setOnLikeListener(object: OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertStoryExpressionReq(UserInfo.ID, imageId, currentDate, this@StoryActivity, {success->
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