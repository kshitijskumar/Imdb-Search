package com.example.imdbsearch.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableInt
import com.example.imdbsearch.databinding.LayoutStarsRatingBinding
import kotlin.math.floor

class MovieRatingsStars @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleArr) {

    private val binding = LayoutStarsRatingBinding.inflate(LayoutInflater.from(context), this, true)

    private val ratingsInStar = ObservableInt(0)

    init {
        binding.ratings = ratingsInStar
    }


    fun updateStarsFromRatings(ratingOutOfTen: String) {
        val ratingInNumbers = try {
            ratingOutOfTen.toFloat()
        } catch (e: Exception) {
            e.printStackTrace()
            0f
        }

        val ratingsOutOfFive = floor(ratingInNumbers/2)

        ratingsInStar.set(ratingsOutOfFive.toInt())
    }

    companion object {
        @JvmStatic
        @BindingAdapter("starPosition", "ratings")
        fun setupMovieRatings(cb: CheckBox, starPosition: Int, ratings: Int?) {
            ratings ?: return
            cb.isChecked = starPosition <= ratings
        }

        @JvmStatic
        @BindingAdapter("ratingsOutOfTen")
        fun setRatingsOutOfTen(stars: MovieRatingsStars, ratings: String?) {
            ratings?.let {
                stars.updateStarsFromRatings(ratings)
            }
        }
    }
}