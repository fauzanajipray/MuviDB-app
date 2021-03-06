package com.faprayyy.themuvidb.view.ui.main.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.faprayyy.themuvidb.R
import com.faprayyy.themuvidb.data.local.entity.MovieEntity
import com.faprayyy.themuvidb.databinding.FragmentMovieBinding
import com.faprayyy.themuvidb.view.adapter.MovieAdapter
import com.faprayyy.themuvidb.view.ui.detail.detailmovie.DetailMovieActivity
import com.faprayyy.themuvidb.view.ui.favorite.FavoriteActivity
import com.faprayyy.themuvidb.view.ui.main.MainViewModel
import com.faprayyy.themuvidb.vo.StatusMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding as FragmentMovieBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mAdapter: MovieAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerview()
        viewModel.getMovieList().observe(viewLifecycleOwner) { result ->
            if (result!=null){
                when(result.status){
                    StatusMessage.LOADING -> setupProgressBar(true)
                    StatusMessage.SUCCESS ->{
                        setupProgressBar(false)
                        mAdapter.submitList( result.data )
                    }
                    StatusMessage.ERROR -> {
                        setupProgressBar(false)
                    }
                    else -> { }
                }
            }
        }
    }

    private fun setupRecyclerview(){
        mAdapter = MovieAdapter(object : MovieAdapter.OnItemClickCallback{
            override fun onItemClicked(data: MovieEntity) {
                val intent = Intent(context, DetailMovieActivity::class.java)
                intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, data)
                startActivity(intent)
            }
        })
        with(binding.rvMovies){
            setHasFixedSize(true)
            adapter = mAdapter
        }
        binding.progressBar.visibility = View.GONE

    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setOnMenuItemClickListener {
                when(it?.itemId){
                    R.id.menu_favorite_item -> {
                        val intent = Intent(context, FavoriteActivity::class.java)
                        startActivity(intent)
                    }
                }
                true
            }
        }
    }

    private fun setupProgressBar(status: Boolean){
        with(binding.progressBar){
            visibility = if (status) View.VISIBLE
            else View.GONE
        }
    }
}