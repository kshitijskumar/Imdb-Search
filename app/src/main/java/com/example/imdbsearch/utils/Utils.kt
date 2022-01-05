package com.example.imdbsearch.utils

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