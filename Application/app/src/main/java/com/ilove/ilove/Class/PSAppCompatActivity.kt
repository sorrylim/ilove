package com.ilove.ilove.Class

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.view.*

public abstract class PSAppCompatActivity : AppCompatActivity() {
    fun toolbarBinding(toolbar : Toolbar, titleText: String, backPress: Boolean) : Toolbar {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(backPress)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.text_maintoolbar.text = titleText
        return toolbar
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
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
}