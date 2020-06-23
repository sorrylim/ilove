package com.ilove.ilove.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ilove.ilove.MainActivity.WriteStoryActivity
import com.ilove.ilove.R

class StoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_story, container, false)
        val writeStoryBtn : Button = rootView.findViewById(R.id.btn_writestory)

        writeStoryBtn.setOnClickListener {
            var intent = Intent(activity!!, WriteStoryActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }
    
}