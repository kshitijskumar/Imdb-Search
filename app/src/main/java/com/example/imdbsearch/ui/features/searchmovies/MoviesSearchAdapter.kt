package com.example.imdbsearch.ui.features.searchmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbsearch.databinding.LayoutSingleMovieItemBinding
import com.example.imdbsearch.domain.models.MoviesSearchResultResponse
import com.example.imdbsearch.domain.models.SingleMovieItem

class MoviesSearchAdapter(
    private val fetchNextResult: () -> Unit,
    private val onMovieItemClick: (imdbId: String) -> Unit = {}
) : ListAdapter<SingleMovieItem, MoviesSearchAdapter.SingleMovieViewHolder>(
    diffUtil
) {

    private var searchQueryForResult: String = ""
    private var lastPositionWherePaginationChecked: Int = 0

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SingleMovieItem>() {
            override fun areItemsTheSame(
                oldItem: SingleMovieItem,
                newItem: SingleMovieItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SingleMovieItem,
                newItem: SingleMovieItem,
            ): Boolean {
                return oldItem.imdbId == newItem.imdbId
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutSingleMovieItemBinding.inflate(inflater, parent, false)
        return SingleMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SingleMovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        if (currentList.size == holder.adapterPosition+1 && holder.adapterPosition != lastPositionWherePaginationChecked) {
            lastPositionWherePaginationChecked = holder.adapterPosition
            fetchNextResult.invoke()
        }
    }

    fun submitData(data: MoviesSearchResultResponse) {
        if (data.searchQueryForResult != searchQueryForResult) {
            // new data arrived, reset values
            searchQueryForResult = data.searchQueryForResult
            lastPositionWherePaginationChecked = 0
        }
        submitList(data.searchResultList)
    }

    inner class SingleMovieViewHolder(val binding: LayoutSingleMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val itemVm = SingleMovieItemVm(onMovieItemClick)

        init {
            binding.vm = itemVm
        }

        fun bind(movieItem: SingleMovieItem) {
            itemVm.bindData(movieItem)
        }


    }
}