package com.ilove.ilove.Item

class InquireItem {
    data class Inquire(
        val date : String,
        val inquireTitle : String,
        val inquireContent : String,
        val inquireAnswer : String,
        val inquireType: String,
        val check: Int,
        val answerDate : String
    )
}