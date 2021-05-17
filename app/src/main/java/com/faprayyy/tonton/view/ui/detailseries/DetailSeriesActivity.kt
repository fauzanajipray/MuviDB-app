package com.faprayyy.tonton.view.ui.detailseries

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faprayyy.tonton.R
import com.faprayyy.tonton.data.remote.Config
import com.faprayyy.tonton.data.remote.response.SeriesDetail
import com.faprayyy.tonton.data.remote.response.SeriesModel
import com.faprayyy.tonton.databinding.ActivityDetailSeriesBinding
import com.faprayyy.tonton.utils.convertGenres
import com.faprayyy.tonton.view.ui.search.SearchActivity
import com.faprayyy.tonton.view.viewmodel.ViewModelFactory
import java.util.*

class DetailSeriesActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_SERIES = "extra_movie"
    }

    private lateinit var binding: ActivityDetailSeriesBinding
    private lateinit var seriesData: SeriesModel
    private lateinit var seriesDetail: SeriesDetail
    private lateinit var viewModel: DetailSeriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seriesDetail = SeriesDetail()
        setupToolbar(seriesDetail)
        showProgressBar(true)
        showData(false)
        val factory = ViewModelFactory.getInstance(this)
        viewModel =  ViewModelProvider(this, factory)[DetailSeriesViewModel::class.java]
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        seriesData = intent.getParcelableExtra<SeriesModel>(EXTRA_SERIES) as SeriesModel

        viewModel.getSeries(seriesData.id).observe(this, Observer{
            setData(it)
            setupToolbar(it)
            seriesDetail = it
        })
        viewModel.getLoadingState().observe(this, Observer {
            if (it){
                showData(false)
                showProgressBar(true)
            } else {
                showData(true)
                showProgressBar(false)
            }
        })
        setData(seriesDetail)

    }

    @SuppressLint("StringFormatMatches")
    private fun setData(seriesDetail: SeriesDetail) {
        Log.d("TAG", "$seriesData")
        binding.apply {
            collapsingToolbar.title = seriesDetail.name
            seriesTitle.text = seriesDetail.name
            seriesTagline.text = seriesDetail.tagline
            seriesLang.text = seriesDetail.originalLanguage?.toUpperCase(Locale.ROOT)
            seriesRating.rating = seriesDetail.voteAverage?.div(2)?.toFloat() as Float
            seriesVoteCount.text = resources.getString(R.string.voters, seriesDetail.voteCount)
            seriesRelease.text =  seriesDetail.firstAirDate
            seriesOverview.text = seriesDetail.overview
            seriesGenres.text = convertGenres(seriesDetail.genres)
        }
        val posterImg = seriesData.backdropPath?.let { Config.getBackdropPath(it) }
        Glide.with(this)
            .load(posterImg)
            .apply(RequestOptions().override(3000))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading_backdrop).error(R.drawable.ic_error_backdrop))
            .into(binding.backdropImg)
    }

    private fun setupToolbar(seriesDetail: SeriesDetail) {
        binding.toolbar.apply {
            setOnMenuItemClickListener {
                when(it?.itemId){
                    R.id.menu_share_item -> { onShareClick(seriesDetail) }
                    R.id.menu_search_item -> {
                        val intent = Intent(context, SearchActivity::class.java)
                        startActivity(intent)
                    }
                }
                true
            }
        }
    }

    private fun onShareClick(seriesDetail: SeriesDetail) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle("Share")
            .setText(resources.getString(R.string.share_text, seriesDetail.name))
            .startChooser()
    }


    private fun showData(state: Boolean) {
        with(binding.dataDetail){
            visibility = if (state){
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun showProgressBar(state : Boolean){
        with(binding.progressBar){
            visibility = if (state){
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}