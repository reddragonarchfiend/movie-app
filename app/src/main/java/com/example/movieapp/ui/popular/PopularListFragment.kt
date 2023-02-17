package com.example.movieapp.ui.popular

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
import com.example.movieapp.databinding.FragmentPopularListBinding
import com.example.movieapp.ui.list.ListLoadStateAdapter
import com.example.movieapp.ui.list.ListPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PopularListFragment : Fragment() {

    private val viewModel: PopularListViewModel by viewModels()
    lateinit var binding: FragmentPopularListBinding
    lateinit var recyclerViewAdapter: ListPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPopularListBinding.inflate(inflater, container, false)
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
            recyclerViewAdapter = ListPagingAdapter()
            recyclerViewAdapter.addLoadStateListener { loadState ->
                errorLayout.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvMovies.isVisible = loadState.source.refresh is LoadState.NotLoading
                errorLayout.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                errorLayout.tvError.isVisible = loadState.source.refresh is LoadState.Error
            }

            errorLayout.btnRetry.setOnClickListener {
                recyclerViewAdapter.retry()
            }

            rvMovies.apply {
                adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
                    //set click for header/footer
                    header = ListLoadStateAdapter { recyclerViewAdapter.retry() },
                    footer = ListLoadStateAdapter { recyclerViewAdapter.retry() },
                )
            }

            recyclerViewAdapter.onMovieClick {
                val action = PopularListFragmentDirections.actionMovieFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            }
        }


    }
}