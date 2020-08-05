package com.ilove.ilove.Item

import android.graphics.Bitmap

class ImageItem {
    data class StoryImage(
        val imageId: Int,
        val userId: String,
        val image: String?,
        val userPhone: String
    )
}