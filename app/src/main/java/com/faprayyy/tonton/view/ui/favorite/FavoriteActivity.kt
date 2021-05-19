package com.faprayyy.tonton.view.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.faprayyy.tonton.R
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.databinding.ActivityFavoriteBinding
import com.faprayyy.tonton.utils.SortUtils
import com.faprayyy.tonton.view.adapter.FavoriteAdapter
import com.faprayyy.tonton.view.viewmodel.ViewModelFactory

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
        viewModel.getFavoriteListFromDB(SortUtils.NEWEST).observe(this , favObserver)

        mFavoriteAdapter = FavoriteAdapter(this)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.setHasFixedSize(true)
        binding.rvFavorite.adapter = mFavoriteAdapter

        setupToolbar(binding.toolbar)
    }

    private fun setupToolbar(toolbar: Toolbar) {

        toolbar.setOnMenuItemClickListener{ item ->
            var sort = ""
            when (item.itemId) {
                R.id.action_newest -> sort = SortUtils.NEWEST
                R.id.action_oldest -> sort = SortUtils.OLDEST
                R.id.action_random -> sort = SortUtils.RANDOM
            }
            viewModel.getFavoriteListFromDB(sort).observe(this, favObserver)
            item.isChecked  = true
            true
        }
    }

    private val favObserver = Observer<PagedList<FavoriteEntity>> { noteList ->
        showWarning(false)
        if (noteList != null) {
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