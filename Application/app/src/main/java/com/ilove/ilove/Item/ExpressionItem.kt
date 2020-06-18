package com.ilove.ilove.Item

data class ExpressionItem(
    val userId: String,
    val partnerId: String,
    var userLike: Int?,
    var partnerLike: Int?,
    var userMeet: Int?,
    var partnerMeet: Int?
)