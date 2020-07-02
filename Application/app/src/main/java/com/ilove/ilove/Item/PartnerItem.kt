package com.ilove.ilove.Item

import android.graphics.Bitmap

data class Partner(
    val userId: String,
    val userNickname: String,
    val userAge: String,
    val userCity: String,
    val dateHistory: String,
    val userPhone: String,
    val userImage: String,
    val like: Int,
    val meet: Int
)

data class UserList(
    val userId: String,
    val userNickname: String,
    val userAge: String,
    val userCity: String,
    val recentGps: String,
    val userIntroduce: String,
    val userPhone: String,
    val userImage: String,
    val like: Int,
    val meet: Int
)