package com.sanjay.moviebuff.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanjay.moviebuff.R
import com.sanjay.moviebuff.databinding.ItemLayoutBinding
import com.sanjay.moviebuff.model.Movie
import com.sanjay.moviebuff.utilities.Constants.Companion.POSTER_BASE_URL

class MovieAdapter(private val onClick: (Movie) -> Unit):
    ListAdapter<Movie,MovieAdapter.MovieVieHolder>(MovieDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVieHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),
                 parent,false)
        return MovieVieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieVieHolder, position: Int) {
      val currentMovie = getItem(position)
        holder.bindData(currentMovie)
    }

   inner class MovieVieHolder(private val binding: ItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {

       private var currentMovie: Movie? = null

       init {
           itemView.setOnClickListener {
               currentMovie?.let {
                   onClick(it)
               }
           }

       }

       fun bindData(movie: Movie) {
           currentMovie = movie
           binding.apply {
               Glide.with(binding.root)
                   .load("$POSTER_BASE_URL${movie.posterPath}")
                   .placeholder(R.drawable.placeholder)
                   .into(itemImage)
               title.text = movie.title
           }
       }

    }

}

class MovieDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}