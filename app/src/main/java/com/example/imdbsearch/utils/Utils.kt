package com.example.imdbsearch.utils

import android.content.Context
import android.widget.Toast
import com.example.imdbsearch.R

object Utils {

    private val colorsList = mutableListOf<Int>(
        R.color.primary_red,
        R.color.light_purple,
        R.color.cyan,
        R.color.primary_blue,
        R.color.orange
    )

    fun getRandomColorPlaceHolder() : Int {
        return colorsList.random()
    }
}

fun Context.showToast(toastMsg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, toastMsg, duration).show()
}