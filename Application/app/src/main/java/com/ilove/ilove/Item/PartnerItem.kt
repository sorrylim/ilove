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
    val recentTime: String,
    val like: Int,
    val meet: Int
)

data class NewUserList(
    val userId: String,
    val userNickname: String,
    val userAge: String,
    val userCity: String,
    val recentGps: String,
    val recentTime: String,
    val userPhone: String,
    val userImage: String
)

data class UserList(
    val userId: String,
    val userNickname: String,
    val userAge: String,
    val userCity: String,
    val recentGps: Float,
    val recentTime: String,
    val userIntroduce: String,
    val userPhone: String,
    val userImage: String,
    val userPurpose: String,
    val like: Int,
    val meet: Int
)