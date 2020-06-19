package com.ilove.ilove.MainActivity

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
import com.google.android.material.tabs.TabLayoutMediator
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.Fragment.*
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : PSAppCompatActivity() {

    var channelFragment : Fragment? = null
    var storyFragment : Fragment? = null
    var listFragment : Fragment? = null
    var messageFragment : Fragment? = null
    var profileFragment : Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bnv_main.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            channelFragment = ChannelFragment()
            supportFragmentManager.beginTransaction().add(R.id.frame_main, channelFragment!!).commit()
            toolbarCenterBinding(toolbar_main, "아이러브", false)
        }
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.bnv_main_channel -> {
                if(channelFragment == null) {
                    channelFragment = ChannelFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, channelFragment!!).commit()
                }

                if(channelFragment != null) supportFragmentManager.beginTransaction().show(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().hide(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().hide(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().hide(profileFragment!!).commit()


                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_story -> {
                if(storyFragment == null) {
                    storyFragment = StoryFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, storyFragment!!).commit()
                }

                if(channelFragment != null) supportFragmentManager.beginTransaction().hide(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().show(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().hide(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().hide(profileFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_list -> {
                if(listFragment == null) {
                    listFragment = ListFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, listFragment!!).commit()
                }

                if(channelFragment != null) supportFragmentManager.beginTransaction().hide(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().hide(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().show(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().hide(profileFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_message -> {
                if(messageFragment == null) {
                    messageFragment = MessageFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, messageFragment!!).commit()
                }
                if(channelFragment != null) supportFragmentManager.beginTransaction().hide(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().hide(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().hide(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().show(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().hide(profileFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.bnv_main_profile -> {
                if(profileFragment == null) {
                    profileFragment = ProfileFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_main, profileFragment!!).commit()
                }
                if(channelFragment != null) supportFragmentManager.beginTransaction().hide(channelFragment!!).commit()
                if(storyFragment != null) supportFragmentManager.beginTransaction().hide(storyFragment!!).commit()
                if(listFragment != null) supportFragmentManager.beginTransaction().hide(listFragment!!).commit()
                if(messageFragment != null) supportFragmentManager.beginTransaction().hide(messageFragment!!).commit()
                if(profileFragment != null) supportFragmentManager.beginTransaction().show(profileFragment!!).commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}