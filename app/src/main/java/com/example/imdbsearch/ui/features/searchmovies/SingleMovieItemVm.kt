package com.example.imdbsearch.ui.features.searchmovies

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.bumptech.glide.Glide
import com.example.imdbsearch.domain.models.SingleMovieItem
import com.example.imdbsearch.utils.Utils

/**
 * Simple data holder to pass in databinding
 */
class SingleMovieItemVm(private val onMovieClick: (String) -> Unit) {

    val movie = ObservableField<SingleMovieItem>()
    val randomColorPlaceholder = ObservableInt(Utils.getRandomColorPlaceHolder())

    fun bindData(movieItem: SingleMovieItem) {
        movie.set(movieItem)
    }

    fun onMovieClicked() {
        movie.get()?.let {
            onMovieClick.invoke(it.imdbId ?: "")
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter("app:loadImageUrl", "app:placeholder", requireAll = false)
        fun loadImageUrl(iv: ImageView, imageUrl: String?, @DrawableRes placeholder: Int?) {
            val glideLoader = Glide.with(iv.context)
                .load(imageUrl)

            placeholder?.let { glideLoader.placeholder(placeholder) }

            glideLoader.into(iv)

        }
    }
}