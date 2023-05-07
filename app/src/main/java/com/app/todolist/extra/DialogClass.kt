package com.app.todolist.extra

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.cardview.widget.CardView
import com.app.todolist.R
import com.google.android.material.button.MaterialButton

class DialogClass(activity: Activity) {

    private val loading = Dialog(activity)
    var category : String = ""

    fun customDialog() {
        loading.setContentView(R.layout.loading_box)
        loading.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // this line used to remove dialog class white background...
        loading.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loading.setCancelable(false)
        loading.show()
    }

    fun dismissLoading() {
        loading.dismiss()
    }

    fun showDialog(activity: Activity) {

        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loading.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.choose_category)

        val groceryCard = dialog.findViewById(R.id.groceryCard) as CardView
        val workCard = dialog.findViewById(R.id.workCard) as CardView
        val sportCard = dialog.findViewById(R.id.sportCard) as CardView
        val designCard = dialog.findViewById(R.id.designCard) as CardView
        val studyCard = dialog.findViewById(R.id.uniCard) as CardView
        val socialCard = dialog.findViewById(R.id.socialCard) as CardView
        val musicCard = dialog.findViewById(R.id.musicCard) as CardView
        val healthCard = dialog.findViewById(R.id.HealthCard) as CardView
        val homeCard = dialog.findViewById(R.id.homeCard) as CardView


        val groceryPic = dialog.findViewById(R.id.groceryPic) as ImageView
        val workImg = dialog.findViewById(R.id.workImg) as ImageView
        val sportImg = dialog.findViewById(R.id.gym) as ImageView
        val designImg = dialog.findViewById(R.id.designImg) as ImageView
        val studyImg = dialog.findViewById(R.id.studyImg) as ImageView
        val socialImg = dialog.findViewById(R.id.socialImg) as ImageView
        val musicImg = dialog.findViewById(R.id.musicImg) as ImageView
        val healthImg = dialog.findViewById(R.id.healthImg) as ImageView
        val homeImg = dialog.findViewById(R.id.home) as ImageView


        groceryCard.setOnClickListener {
            groceryPic.setImageResource(R.drawable.ic_checked)

        }

        workCard.setOnClickListener {

        }
        sportCard.setOnClickListener {

        }
        designCard.setOnClickListener {

        }
        studyCard.setOnClickListener {

        }
        socialCard.setOnClickListener {

        }
        musicCard.setOnClickListener {

        }

        healthCard.setOnClickListener {

        }

        homeCard.setOnClickListener {

        }


        dialog.show()
    }


    fun showCategoryDialog(activity: Activity){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loading.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.category_spinner)


        val spinner = dialog.findViewById<Spinner>(R.id.spinner)
        val spinnerItems = arrayOf("Grocery", "Work", "Sport", "Study", "Social","Music","Health", "Home")
        val adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, spinnerItems)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = spinnerItems[position]
                category = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        val save = dialog.findViewById<MaterialButton>(R.id.saveButton) as MaterialButton

        save.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


}