package com.ilove.ilove.MainActivity

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.activity_story.*
import kotlinx.android.synthetic.main.item_storylist.view.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class StoryActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        toolbarBinding(toolbar_story, "", true)

        var image : ImageView = findViewById(R.id.image_storyimage)

        image.setClipToOutline(true)

        var intent = intent
        var imgUrl = intent.getStringExtra("image")
        var imageId = intent.getIntExtra("image_id", 0)
        var userId: String = ""

        VolleyService.getStoryUserReq(UserInfo.ID, imageId, this, {success->
            text_storynickname.text = success.getString("user_nickname") + success.getString("user_birthday")
            text_storygps.text = success.getString("user_recentgps")
            text_storycontent.text = success.getString("image_content")
            text_storyviewcount.text = success.getInt("viewcount").toString()
            text_storylikecount.text = success.getInt("likecount").toString()
            userId = success.getString("user_id")
            Glide.with(this).load(imgUrl).into(image_storyimage)

            if(success.getInt("like") == 1) {
                btn_storylike.setLiked(true)
            }
            else if(success.getInt("like") == 0) {
                btn_storylike.setLiked(false)
            }
        })

        btn_storylike.setOnLikeListener(object: OnLikeListener {
            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            override fun liked(likeButton: LikeButton?) {
                VolleyService.insertStoryExpressionReq(UserInfo.ID, imageId, currentDate, this@StoryActivity, {success->
                    if(success=="success") {
                        text_storylikecount.text = (Integer.parseInt(text_storylikecount.text.toString()) + 1).toString()
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(this@StoryActivity.getResources(), R.drawable.heart_on, null))
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
                        likeButton!!.setLikeDrawable(ResourcesCompat.getDrawable(this@StoryActivity.getResources(), R.drawable.heart_off, null))
                    }
                    else {
                        Toast.makeText(this@StoryActivity, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        })

    }
}