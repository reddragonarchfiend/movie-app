package com.example.movieapp.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSearchMoviesBinding
import com.example.movieapp.ui.list.ListLoadStateAdapter
import com.example.movieapp.ui.list.ListPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SearchMoviesFragment : Fragment(){

    private val viewModel: SearchMoviesViewModel by viewModels()
    lateinit var binding: FragmentSearchMoviesBinding
    lateinit var recyclerViewAdapter: ListPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setSearch()

        lifecycleScope.launchWhenCreated {
            viewModel.movies.collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }

    private fun setSearch(){
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_search, menu)

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView
                //don't collapse searchview on backpress
                searchView.setIconifiedByDefault(false)

                //set current query in case of orientation change or when going back to this fragment
                val currentQuery = viewModel.query.value
                if(currentQuery.isNotEmpty()){
                    searchView.setQuery(currentQuery,false)
                    searchView.clearFocus()
                }

                setSearchListener(searchView)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setSearchListener(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    //in case of a new search scroll recyclerview on top to see the first results
                    binding.rvSearch.scrollToPosition(0)
                    viewModel.doSearching(it)
                }
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    private fun setRecyclerView() {
        binding.apply {
            recyclerViewAdapter = ListPagingAdapter()
            recyclerViewAdapter.addLoadStateListener { loadState ->
                errorLayout.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvSearch.isVisible = loadState.source.refresh is LoadState.NotLoading
                errorLayout.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                errorLayout.tvError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    recyclerViewAdapter.itemCount < 1
                ) {
                    rvSearch.isVisible = false
                    errorLayout.textViewEmpty.isVisible = true
                } else {
                    errorLayout.textViewEmpty.isVisible = false
                }
            }

            errorLayout.btnRetry.setOnClickListener {
                recyclerViewAdapter.retry()
            }

            rvSearch.apply {
                adapter = recyclerViewAdapter.withLoadStateHeaderAndFooter(
                    //set click for header/footer
                    header = ListLoadStateAdapter { recyclerViewAdapter.retry() },
                    footer = ListLoadStateAdapter { recyclerViewAdapter.retry() },
                )
            }

            recyclerViewAdapter.onMovieClick {
                val action = SearchMoviesFragmentDirections.actionSearchFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            }
        }
    }

}