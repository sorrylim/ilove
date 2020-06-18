package com.ilove.ilove.Item

import android.graphics.Bitmap

data class Partner(
    val id: String,
    val nickname: String,
    val age: String,
    val city: String,
    val dateHistory: String,
    val like: Int,
    val meet: Int,
    val imageList: ArrayList<Bitmap>?
)

data class UserList(
    val id: String,
    val nickname: String,
    val age: String,
    val city: String,
    val recentGps: String,
    val introduce: String,
    val certification: String,
    val imageList: ArrayList<Bitmap>?
)