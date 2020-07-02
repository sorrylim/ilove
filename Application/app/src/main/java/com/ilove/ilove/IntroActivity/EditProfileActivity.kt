package com.ilove.ilove.IntroActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : PSAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        toolbarBinding(toolbar_editprofile, "프로필 편집", true)

    }
}