package com.sanjay.moviebuff.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sanjay.moviebuff.R
import com.sanjay.moviebuff.databinding.FragmentMovieDetailsBinding
import com.sanjay.moviebuff.databinding.FragmentMovieListBinding
import com.sanjay.moviebuff.model.Movie
import com.sanjay.moviebuff.utilities.Constants
import com.sanjay.moviebuff.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    val  args:MovieDetailsFragmentArgs by navArgs()

    private val movieViewModel: MovieViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = args.id
        movieViewModel.getMovie(movieId).observe(viewLifecycleOwner,{
            setData(it)
        })



    }

    private fun setData(movie: Movie?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = movie?.title
        binding.apply {
            Glide.with(binding.root)
                .load("${Constants.BACKDROP_BASE_URL}${movie?.backdropPath}")
                .placeholder(R.drawable.placeholder)
                .into(movieBackdrop)
            Glide.with(binding.root)
                .load("${Constants.POSTER_BASE_URL}${movie?.posterPath}")
                .placeholder(R.drawable.placeholder)
                .into(moviePoster)

            movieTitle.text = movie?.title
            movieSynopsis.text = movie?.overview
            releaseDate.text = movie?.releaseDate
            rating.text = movie?.voteAverage.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}