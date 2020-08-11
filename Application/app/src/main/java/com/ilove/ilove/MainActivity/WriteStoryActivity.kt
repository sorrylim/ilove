package com.ilove.ilove.MainActivity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gun0912.tedpermission.PermissionListener
import com.ilove.ilove.Class.FileUploadUtils
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_write_story.*
import kotlinx.android.synthetic.main.item_storylist.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class WriteStoryActivity : PSAppCompatActivity() {
    val PICK_FROM_ALBUM = 1
    var imagePath : String? = null
    var imageCaptureUri: Uri? = null
    var imageCheck = false

    private val requiredPermission = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_story)

        toolbarBinding(toolbar_writestory, "스토리작성", true)

        var displayMetrics: DisplayMetrics = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics) // 화면의 가로길이를 구함
        var width = displayMetrics.widthPixels
        image_writestory.getLayoutParams().width = width
        image_writestory.getLayoutParams().height = width
        image_writestory.requestLayout()


        checkPermissions()


        text_addphoto.setOnClickListener {
            var albumIntent = Intent(Intent.ACTION_PICK)
            albumIntent.setType("image/*")
            startActivityForResult(albumIntent, PICK_FROM_ALBUM)
        }

        text_insertstory.setOnClickListener {
            if(imageCheck == true) {
                FileUploadUtils.uploadStoryImage(imagePath!!, edit_storycontent.text.toString())
                finish()
            }
            else {
                Toast.makeText(this, "이미지는 필수 항목입니다.", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun getPath(uri: Uri) : String {
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (data == null) {
                return
            }


        when (requestCode) {
            PICK_FROM_ALBUM -> {
                imageCaptureUri = data!!.data
                imagePath = getPath(imageCaptureUri!!)
                Log.d("test", "$imagePath")

                try {
                    val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageCaptureUri)
                    image_writestory.visibility = View.VISIBLE
                    image_writestory.setImageBitmap(imageBitmap)
                    imageCheck = true

                } catch (e: FileNotFoundException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                } catch (e: IOException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

            }

        }
    }

    private fun checkPermissions(){
        val rejectedPermissionList = ArrayList<String>()

        for(permission in requiredPermission){
            if(ContextCompat.checkSelfPermission(this,permission)!=PackageManager.PERMISSION_GRANTED) {
                rejectedPermissionList.add(permission)
            }
        }

        if(rejectedPermissionList.isNotEmpty()){
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this,rejectedPermissionList.toArray(array), 100)
        }
    }

}