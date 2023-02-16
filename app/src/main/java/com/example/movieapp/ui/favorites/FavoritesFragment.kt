package com.example.movieapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.databinding.FragmentFavoritesBinding
import com.example.movieapp.ui.movies_list.MovieListFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels()
    lateinit var binding: FragmentFavoritesBinding
    lateinit var recyclerViewAdapter: FavoritesListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        viewModel.movies.onEach(::renderMovies).launchIn(lifecycleScope)
    }

    private fun setRecyclerView() {
        binding.rvFavorites.apply {
            recyclerViewAdapter = FavoritesListViewAdapter()
            adapter = recyclerViewAdapter

            recyclerViewAdapter.onMovieClick {
                val action = MovieListFragmentDirections.actionMovieFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            }
        }
    }


    private fun renderMovies(movies: List<MovieDetails>) {
        recyclerViewAdapter.submitList(movies)
        binding.tvEmptyList.isVisible = movies.isEmpty()
    }

}