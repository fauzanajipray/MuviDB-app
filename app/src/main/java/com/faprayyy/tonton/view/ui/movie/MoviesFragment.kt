package com.faprayyy.tonton.view.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faprayyy.tonton.R
import com.faprayyy.tonton.data.remote.response.MovieModel
import com.faprayyy.tonton.databinding.FragmentMovieBinding
import com.faprayyy.tonton.view.adapter.MovieAdapter
import com.faprayyy.tonton.view.ui.detailmovie.DetailMovieActivity
import com.faprayyy.tonton.view.ui.favorite.FavoriteActivity
import com.faprayyy.tonton.view.ui.search.SearchActivity
import com.faprayyy.tonton.view.viewmodel.ViewModelFactory
import com.loopj.android.http.AsyncHttpClient.log

class MoviesFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding as FragmentMovieBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var mAdapter: MovieAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireActivity())
        moviesViewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]

        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter = MovieAdapter()
        mAdapter.notifyDataSetChanged()
        binding.rvMovies.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }
        binding.progressBar.visibility = View.VISIBLE
        moviesViewModel.getMoviesListFromApi()
        showLoading(true)
        setupToolbar()
        moviesViewModel.getMoviesList().observe(viewLifecycleOwner, Observer {
            showLoading(false)
            if (it != null){
                mAdapter.setData(it)
            }
        })

        mAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback{
            override fun onItemClicked(data: MovieModel) {
                val intent = Intent(context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, data)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        val mProgressBar = binding.progressBar
        if (state){
            mProgressBar.visibility = View.VISIBLE
        } else {
            mProgressBar.visibility = View.INVISIBLE
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setOnMenuItemClickListener {
                when(it?.itemId){
                    R.id.menu_search_item -> {
                        val intent = Intent(context, SearchActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.menu_favorite_item -> {
                        val intent = Intent(context, FavoriteActivity::class.java)
                        startActivity(intent)
                    }
                }
                true
            }
        }
    }
}