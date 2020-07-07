package com.ilove.ilove.IntroActivity

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.ilove.ilove.Class.FileUploadUtils
import com.ilove.ilove.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_write_story.*
import java.io.FileNotFoundException
import java.io.IOException

class CropperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cropper)


        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }


        when (requestCode) {
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    val resultUri: Uri = result.uri
                    var imagePath = getPath(resultUri)
                    FileUploadUtils.uploadImage(imagePath)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                }
            }

        }
    }

    private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

}