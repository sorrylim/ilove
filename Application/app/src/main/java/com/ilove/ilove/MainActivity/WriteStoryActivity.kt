package com.ilove.ilove.MainActivity

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gun0912.tedpermission.PermissionListener
import com.ilove.ilove.Class.FileUploadUtils
import com.ilove.ilove.Class.PSAppCompatActivity
import com.ilove.ilove.R
import kotlinx.android.synthetic.main.activity_write_story.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class WriteStoryActivity : PSAppCompatActivity() {
    val PICK_FROM_ALBUM = 1
    var imagePath : String? = null
    var imageCaptureUri: Uri? = null

    private val requiredPermission = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_story)

        toolbarBinding(toolbar_writestory, "스토리작성", true)


        checkPermissions()


        text_addphoto.setOnClickListener {
            var albumIntent = Intent(Intent.ACTION_PICK)
            albumIntent.setType("image/*")
            startActivityForResult(albumIntent, PICK_FROM_ALBUM)
        }

        text_insertstory.setOnClickListener {
            FileUploadUtils.uploadStoryImage(imagePath!!, edit_storycontent.text.toString())
            finish()
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