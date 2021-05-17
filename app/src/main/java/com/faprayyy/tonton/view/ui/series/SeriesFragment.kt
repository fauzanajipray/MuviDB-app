package com.faprayyy.tonton.view.ui.series

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faprayyy.tonton.R
import com.faprayyy.tonton.data.remote.response.SeriesModel
import com.faprayyy.tonton.databinding.FragmentSeriesBinding
import com.faprayyy.tonton.view.adapter.SeriesAdapter
import com.faprayyy.tonton.view.ui.detailseries.DetailSeriesActivity
import com.faprayyy.tonton.view.ui.favorite.FavoriteActivity
import com.faprayyy.tonton.view.ui.search.SearchActivity
import com.faprayyy.tonton.view.viewmodel.ViewModelFactory

class SeriesFragment : Fragment() {

    private var _binding: FragmentSeriesBinding? = null
    private val binding get() = _binding as FragmentSeriesBinding
    private lateinit var seriesViewModel: SeriesViewModel
    private lateinit var mAdapter: SeriesAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val factory = ViewModelFactory.getInstance(requireActivity())
        seriesViewModel = ViewModelProvider(this, factory)[SeriesViewModel::class.java]
        _binding = FragmentSeriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter = SeriesAdapter()
        mAdapter.notifyDataSetChanged()
        binding.rvSeries.apply {
            adapter = mAdapter
            setHasFixedSize(true)
        }
        seriesViewModel.getSeriesListFromApi()
        showLoading(true)
        setupToolbar()

        seriesViewModel.getSeriesList().observe(viewLifecycleOwner, Observer {
            showLoading(false)
            if (it != null){
                mAdapter.setData(it)
            }
        })

        mAdapter.setOnItemClickCallback(object : SeriesAdapter.OnItemClickCallback{
            override fun onItemClicked(data: SeriesModel) {
                val intent = Intent(context, DetailSeriesActivity::class.java)
                intent.putExtra(DetailSeriesActivity.EXTRA_SERIES, data)
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