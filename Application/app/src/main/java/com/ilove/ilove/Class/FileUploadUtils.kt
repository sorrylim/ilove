package com.ilove.ilove.Class

import android.util.Log
import okhttp3.*
import java.io.File
import java.io.IOException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class FileUploadUtils {
    companion object {
        fun uploadImage(imagePath: String) {
            val current = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
            val currentDate = current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

            var requestBody : RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("user_id", UserInfo.ID).addFormDataPart("img", "img.jpg", RequestBody.create(MultipartBody.FORM, File(imagePath))).addFormDataPart("image_usage", "story").addFormDataPart("image_content", "ddd").addFormDataPart("image_date", currentDate).build()

            var request : Request = Request.Builder().url("http://18.217.130.157:3000/image/upload").post(requestBody).build()

            var client  = OkHttpClient()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Failed to execute request")
                    Log.d("test", "$e")
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response?.body?.string()
                    Log.d("test", "$body")
                }
            })
        }
    }
}