package com.example.imdbsearch.ui.features.searchmovies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.imdbsearch.databinding.FragmentMoviesSearchBinding
import com.example.imdbsearch.utils.DataResult
import com.example.imdbsearch.viewmodels.searchmovies.MoviesSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesSearchFragment : Fragment() {

    private var _binding: FragmentMoviesSearchBinding? = null
    private val binding: FragmentMoviesSearchBinding get() = _binding!!

    private val viewModel by viewModels<MoviesSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesSearchBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeValues()
    }

    private fun initViews() {

    }

    private fun observeValues() {
        viewModel.searchResultState.observe(viewLifecycleOwner) {
            Log.d("SearchResult", "$it and ${(it as DataResult.Success).data.searchQueryForResult}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}