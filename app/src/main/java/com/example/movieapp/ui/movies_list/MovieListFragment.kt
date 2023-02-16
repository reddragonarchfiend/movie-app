package com.example.movieapp.ui.movies_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.movieapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    lateinit var binding: FragmentMovieListBinding
    lateinit var recyclerViewAdapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()


        lifecycleScope.launchWhenCreated {
            viewModel.getMovieList().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun setRecyclerView() {
        binding.apply {
            recyclerViewAdapter = MoviePagingAdapter()
            recyclerViewAdapter.addLoadStateListener { loadState ->
                errorLayout.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
                errorLayout.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                errorLayout.textViewError.isVisible = loadState.source.refresh is LoadState.Error
            }

            errorLayout.buttonRetry.setOnClickListener {
                recyclerViewAdapter.retry()
            }

            rvMovies.apply {
                adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
                    header = MovieListLoadStateAdapter { recyclerViewAdapter.retry() },
                    footer = MovieListLoadStateAdapter { recyclerViewAdapter.retry() },
                )
            }

            recyclerViewAdapter.onMovieClick {
                val action = MovieListFragmentDirections.actionMovieFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            }
        }


    }
}