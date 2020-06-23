package com.ilove.ilove.Item

import android.graphics.Bitmap

data class Partner(
    val userId: String,
    val userNickname: String,
    val userAge: String,
    val userCity: String,
    val dateHistory: String,
    val userPhone: String,
    val like: Int,
    val meet: Int,
    val userImage: String?
)

data class UserList(
    val userId: String,
    val userNickname: String,
    val userAge: String,
    val userCity: String,
    val recentGps: String,
    val userIntroduce: String,
    val userCertification: String,
    val userPhone: String,
    val like: Int,
    val meet: Int,
    val userImage: String?
)