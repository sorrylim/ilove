package com.ilove.ilove.Class

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_main.view.*

public abstract class PSAppCompatActivity : AppCompatActivity() {
    fun toolbarCenterBinding(toolbar : Toolbar, titleText: String, backPress: Boolean) : Toolbar {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(backPress)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        toolbar.text_maintoolbar.setText(titleText)
        return toolbar
    }

    fun toolbarBinding(toolbar: Toolbar, titleText : String, backPress: Boolean) : Toolbar {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(backPress)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))
        supportActionBar?.setTitle(titleText)
        return toolbar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item != null) {
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    return true
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }
/*
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        
    }*/
}