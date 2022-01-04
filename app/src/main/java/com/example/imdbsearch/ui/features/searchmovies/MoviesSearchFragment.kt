package com.example.imdbsearch.ui.features.searchmovies

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imdbsearch.databinding.FragmentMoviesSearchBinding
import com.example.imdbsearch.utils.DataResult
import com.example.imdbsearch.viewmodels.searchmovies.MoviesSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesSearchFragment : Fragment() {

    private var _binding: FragmentMoviesSearchBinding? = null
    private val binding: FragmentMoviesSearchBinding get() = _binding!!

    private lateinit var moviesAdapter: MoviesSearchAdapter

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
        initRecyclerView()
    }

    private fun initRecyclerView() {
        moviesAdapter = MoviesSearchAdapter(
            fetchNextResult = {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.updateCurrentPageNumber()
            }
        )
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = moviesAdapter
        }
    }

    private fun observeValues() {
        viewModel.searchResultState.observe(viewLifecycleOwner) {
            resetViewStates()
            when(it) {
                is DataResult.Success -> {
                    moviesAdapter.submitData(it.data)
                }
            }

        }
    }

    private fun resetViewStates() {
        binding.apply {
            progressBar.visibility = View.GONE
            etSearch.clearAnimation()
        }
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}