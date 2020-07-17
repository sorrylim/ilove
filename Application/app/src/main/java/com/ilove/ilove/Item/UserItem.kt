package com.ilove.ilove.Item

class UserItem {
    data class UserOption(
        val title:String,
        var isSelected: Boolean = false
    )

    data class MessageTicket(
        val day: String,
        val candyCount : String,
        var isSelected: Boolean = false
    )

    data class Candy(
        val candyCount: String,
        val candyAmount : String,
        var isSelected: Boolean = false
    )
}