package com.ilove.ilove.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.ilove.ilove.Class.PSDialog
import com.ilove.ilove.R

class InquireFragment(fa: FragmentActivity) : Fragment() {
    var fa = fa

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_inquire, container, false)
        var category : ConstraintLayout = rootView.findViewById(R.id.layout_inquirecategory)
        var categoryText : TextView = rootView.findViewById(R.id.text_selectcategory)

        category.setOnClickListener {
            var psDialog = PSDialog.BottomSheetDialog(categoryText)
            psDialog.show(fa.supportFragmentManager, psDialog.tag)
        }

        return rootView
    }
}