package com.faprayyy.tonton.view.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.databinding.ActivityFavoriteBinding
import com.faprayyy.tonton.view.adapter.FavoriteAdapter
import com.faprayyy.tonton.view.ui.detailmovie.DetailMovieViewModel
import com.faprayyy.tonton.view.viewmodel.ViewModelFactory
import org.w3c.dom.Entity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var mFavoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel =  ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        viewModel.getfavoriteListFromDB().observe(this , favObserver)

        mFavoriteAdapter = FavoriteAdapter(this)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = mFavoriteAdapter
    }

    private val favObserver = Observer<PagedList<FavoriteEntity>> { noteList ->
        showWarning(false)
        if (noteList != null) {
            Log.d("CEKDATA", "DATA : $noteList")
            if (noteList.toString() == "[]"){
                showWarning(true)
            }
            mFavoriteAdapter.submitList(noteList)
        }
    }

    private fun showWarning(state: Boolean) {
        val stateView = if (state) {
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.warning.visibility = stateView
    }


}