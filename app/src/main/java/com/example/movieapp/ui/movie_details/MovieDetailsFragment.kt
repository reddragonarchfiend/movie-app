package com.example.movieapp.ui.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.util.Resource
import com.example.movieapp.util.SnackbarHelper

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       setClickListeners()

        viewModel.getMovieDetails(args.movieDetails.id!!)
        viewModel.checkIfMovieSaved(args.movieDetails)

        setObservers()
    }

    private fun setClickListeners(){
        binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setFabClickRemoveMovie(){
        binding.fabFavourites.setOnClickListener {
            viewModel.deleteFavoriteMovie(movieDetails = args.movieDetails)
            binding.fabFavourites.setImageResource(R.drawable.ic_not_favorite_24)
            SnackbarHelper.createShortSnackbar(requireView(),getString(R.string.movie_removed_from_favorites),binding.fabFavourites)

            setFabClickAddMovie()
        }
    }
    private fun setFabClickAddMovie(){
        binding.fabFavourites.setOnClickListener {
            viewModel.saveFavoriteMovie(movieDetails = args.movieDetails)
            binding.fabFavourites.setImageResource(R.drawable.ic_favorite_24)
            SnackbarHelper.createShortSnackbar(requireView(),getString(R.string.movie_added_to_favorites),binding.fabFavourites)

            setFabClickRemoveMovie()
        }
    }

    private fun setObservers(){
        viewModel.isMovieSaved.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.fabFavourites.setImageResource(R.drawable.ic_favorite_24)
                    setFabClickRemoveMovie()
                }
                false -> {
                    binding.fabFavourites.setImageResource(R.drawable.ic_not_favorite_24)
                    setFabClickAddMovie()
                }
            }
        }


        viewModel.movieDetails.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    binding.detailsProgress.visibility = View.GONE
                    binding.movieDetails = response.data
                }

                Resource.Status.ERROR -> {
                    binding.detailsProgress.visibility = View.GONE
                    binding.movieDetails = args.movieDetails
                    SnackbarHelper.createShortSnackbar(requireView(),getString(R.string.error_fetching_data),binding.fabFavourites)

                }
                Resource.Status.LOADING -> {
                    binding.detailsProgress.visibility = View.VISIBLE
                }

                Resource.Status.NONE -> {

                }

                Resource.Status.UNAUTHORIZED -> {

                }
            }
        }
    }
}