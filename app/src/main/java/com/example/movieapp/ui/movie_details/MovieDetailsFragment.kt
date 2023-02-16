package com.example.movieapp.ui.movie_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.data.model.movie_details.MovieDetails
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.util.Resource
import com.google.android.material.snackbar.Snackbar
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
        binding.movieDetails = MovieDetails()
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
            val snackbar = Snackbar.make(requireView(), getString(R.string.movie_removed_from_favorites), Snackbar.LENGTH_SHORT)
            snackbar.setAnchorView(binding.fabFavourites)
            snackbar.show()

            setFabClickAddMovie()
        }
    }
    private fun setFabClickAddMovie(){
        binding.fabFavourites.setOnClickListener {
            viewModel.saveFavoriteMovie(movieDetails = args.movieDetails)
            binding.fabFavourites.setImageResource(R.drawable.ic_favorite_24)
            Snackbar.make(requireView(), getString(R.string.movie_added_to_favorites), Snackbar.LENGTH_SHORT)
                .show()

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
                    Snackbar.make(requireView(), getString(R.string.results_error), Snackbar.LENGTH_SHORT)
                        .show()
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