package com.ilove.ilove.MainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_write_story.*

class WriteStoryActivity : PSAppCompatActivity() {
    val PICK_FROM_ALBUM = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_story)

        toolbarBinding(toolbar_writestory, "스토리작성", true)


        text_addphoto.setOnClickListener {
            var albumIntent = Intent(Intent.ACTION_PICK)
            albumIntent.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(albumIntent, PICK_FROM_ALBUM)
        }
    }
}