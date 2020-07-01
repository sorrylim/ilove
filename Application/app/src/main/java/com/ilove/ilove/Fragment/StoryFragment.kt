package com.ilove.ilove.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ilove.ilove.Adapter.StoryAdapter
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Item.ImageItem
import com.ilove.ilove.MainActivity.WriteStoryActivity
import com.ilove.ilove.Object.ImageManager
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import org.json.JSONObject

class StoryFragment : Fragment() {
    var storyList = ArrayList<ImageItem.StoryImage>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_story, container, false)
        val writeStoryBtn : Button = rootView.findViewById(R.id.btn_writestory)
        val storyRV : RecyclerView = rootView.findViewById(R.id.rv_storyview)

        writeStoryBtn.setOnClickListener {
            var intent = Intent(activity!!, WriteStoryActivity::class.java)
            startActivity(intent)
        }

            VolleyService.getStoryImageReq(UserInfo.ID, "story", activity!!, { success->
            storyList.clear()

            var array = success

            for(i in 0..array.length()-1) {
                var json = array[i] as JSONObject

                var story = ImageItem.StoryImage(json.getInt("image_id"), json.getString("user_id"), json.getString("image"))

                storyList.add(story)
            }

            storyRV.setHasFixedSize(true)
            storyRV.layoutManager = GridLayoutManager(activity!!, 3)
            storyRV.adapter = StoryAdapter(activity!!, storyList)

        })

        return rootView
    }
    
}