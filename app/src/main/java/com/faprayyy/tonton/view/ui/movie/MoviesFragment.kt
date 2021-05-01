package com.faprayyy.tonton.view.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.faprayyy.tonton.R
import com.faprayyy.tonton.data.MovieModel
import com.faprayyy.tonton.databinding.FragmentMovieBinding
import com.faprayyy.tonton.view.adapter.MovieAdapter
import com.faprayyy.tonton.view.ui.detailmovie.DetailMovieActivity
import com.faprayyy.tonton.view.ui.search.SearchActivity

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
        moviesViewModel =
                ViewModelProvider(this).get(MoviesViewModel::class.java)
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter = MovieAdapter()
        mAdapter.notifyDataSetChanged()
        binding.recyclerView.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }
        setupToolbar()
        moviesViewModel.setData()
        moviesViewModel.listMovie.observe(viewLifecycleOwner){
            if (it != null){
                mAdapter.setData(it)
            }
        }

        moviesViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        mAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback{
            override fun onItemClicked(data: MovieModel) {
                val intent = Intent(context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, data)
                startActivity(intent)
            }
        })
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setOnMenuItemClickListener {
                when(it?.itemId){
                    R.id.menu_search_item -> {
                        val intent = Intent(context, SearchActivity::class.java)
                        startActivity(intent)
                    }
                }
                true
            }
        }
    }

    private fun showLoading(state: Boolean) {
        val mProgressBar = binding.progressBar
        if (state){
            mProgressBar.visibility = View.VISIBLE
        } else {
            mProgressBar.visibility = View.INVISIBLE
        }
    }
}