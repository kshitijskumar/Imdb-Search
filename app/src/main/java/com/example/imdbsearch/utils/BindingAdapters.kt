package com.example.imdbsearch.utils

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("dataResultLoading")
fun setLoadingScreen(v: View, state: DataResult<Any>?) {
    v.isVisible = state is DataResult.Loading
}