package com.ilove.ilove.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.Class.UserInfo
import com.ilove.ilove.Object.VolleyService
import com.ilove.ilove.R
import java.text.SimpleDateFormat

class InquireFragment(fa: FragmentActivity) : Fragment() {
    var fa = fa
    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var curDate = simpleDateFormat.format(System.currentTimeMillis())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_inquire, container, false)
        var category : ConstraintLayout = rootView.findViewById(R.id.layout_inquirecategory)
        var categoryText : TextView = rootView.findViewById(R.id.text_selectcategory)
        var inquireContentText : EditText = rootView.findViewById(R.id.edit_inquirecontent)
        var sendBtn : Button = rootView.findViewById(R.id.btn_sendinquire)

        category.setOnClickListener {
            var psDialog = PSDialog.BottomSheetDialog(categoryText)
            psDialog.show(fa.supportFragmentManager, psDialog.tag)
        }

        sendBtn.setOnClickListener {
            if(categoryText.text == "설정해주세요." || inquireContentText.text.length == 0) {
                Toast.makeText(activity!!, "문의내용을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                VolleyService.sendInquireReq(UserInfo.ID, curDate, categoryText.text.toString(), inquireContentText.text.toString(), "qna", activity!!, {success->
                })
                categoryText.text = "설정해주세요."
                inquireContentText.setText(null)
                Toast.makeText(activity!!, "접수 되었습니다.", Toast.LENGTH_SHORT).show()

            }
        }

        return rootView
    }
}