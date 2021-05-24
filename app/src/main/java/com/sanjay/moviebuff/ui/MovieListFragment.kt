package com.sanjay.moviebuff.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.sanjay.moviebuff.R
import com.sanjay.moviebuff.adapter.MovieAdapter
import com.sanjay.moviebuff.databinding.FragmentMovieListBinding
import com.sanjay.moviebuff.utilities.NetworkStatus
import com.sanjay.moviebuff.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private var _binding:FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by activityViewModels()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
            _binding = FragmentMovieListBinding.inflate(inflater,container,false)
       setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        movieViewModel.moviesList.observe(viewLifecycleOwner,{
            if(it.isEmpty()) {
                movieViewModel.fetchFromNetwork()
            }
            movieAdapter.submitList(it)
        })

        movieViewModel.status.observe(viewLifecycleOwner,{
            when(it) {
                is NetworkStatus.Success -> {
                  binding.apply {
                      shimmerFrameLayout.stopShimmer()
                      shimmerFrameLayout.visibility = View.GONE
                      recyclerview.visibility = View.VISIBLE
                      textError.isVisible = false
                      errorImage.isVisible = false
                      retryButton.isVisible = false
                  }
                }
                is NetworkStatus.Loading -> {
                    binding.apply {
                        shimmerFrameLayout.startShimmer()
                        shimmerFrameLayout.visibility = View.VISIBLE
                        recyclerview.visibility = View.GONE
                        textError.isVisible = false
                        errorImage.isVisible = false
                        retryButton.isVisible = false
                    }
                }
                is NetworkStatus.Error -> {
                    binding.apply {
                        shimmerFrameLayout.stopShimmer()
                        shimmerFrameLayout.visibility = View.GONE
                        recyclerview.visibility = View.GONE
                        textError.isVisible = true
                        errorImage.isVisible = true
                        retryButton.isVisible = true
                    }
                }
            }
        })

        binding.retryButton.setOnClickListener {
            movieViewModel.deleteAllMovies()
          movieViewModel.fetchFromNetwork()
        }



    }

    private fun setUpRecyclerView() {
        movieAdapter = MovieAdapter {

            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(it.id)
            findNavController().navigate(action)
        }
        binding.recyclerview.apply {
            adapter = movieAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_memu,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                movieViewModel.deleteAllMovies()
                movieViewModel.fetchFromNetwork()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmer()
    }

    override fun onStop() {
        binding.shimmerFrameLayout.stopShimmer()
        super.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}