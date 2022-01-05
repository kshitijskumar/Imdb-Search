package com.example.imdbsearch.ui.features.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.imdbsearch.databinding.FragmentMovieDetailsBinding
import com.example.imdbsearch.utils.DataResult
import com.example.imdbsearch.viewmodels.moviedetails.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    companion object {
        const val MOVIE_ITEM_ID = "MOVIE_ITEM_ID"
    }

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding: FragmentMovieDetailsBinding get() = _binding!!

    private lateinit var movieImdbId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContent()
    }

    private val viewModel by viewModels<MovieDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeValues()
    }

    private fun initContent() {
        movieImdbId = arguments?.getString(MOVIE_ITEM_ID, "") ?: ""
        viewModel.fetchMovieDetailsById(movieImdbId)
    }

    private fun observeValues() {
        viewModel.movieDetailsState.observe(viewLifecycleOwner) {
            when (it) {
                is DataResult.Success -> {
                    binding.movie = it.data
                }
                else -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}