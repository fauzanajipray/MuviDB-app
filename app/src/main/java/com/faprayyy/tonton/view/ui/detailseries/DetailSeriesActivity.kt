package com.faprayyy.tonton.view.ui.detailseries

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faprayyy.tonton.R
import com.faprayyy.tonton.api.Config
import com.faprayyy.tonton.data.Response.SerieDetail
import com.faprayyy.tonton.data.SeriesModel
import com.faprayyy.tonton.databinding.ActivityDetailSeriesBinding

class DetailSeriesActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_SERIE = "extra_movie"
    }

    private lateinit var binding: ActivityDetailSeriesBinding
    private lateinit var serieData: SeriesModel
    private lateinit var serieDetail: SerieDetail
    private lateinit var viewModel: DetailSeriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serieDetail = SerieDetail()
        setupToolbar(serieDetail)
        showProgressBar(true)
        showData(false)
        viewModel = ViewModelProvider(this).get(DetailSeriesViewModel::class.java)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        serieData = intent.getParcelableExtra<SeriesModel>(EXTRA_SERIE) as SeriesModel
        viewModel.setData(serieData.id)
        viewModel.serieDetail.observe(this){
            setData(it)
            setupToolbar(it)
            serieDetail = it
        }
        viewModel.isLoading.observe(this){
            if (it){
                showData(false)
                showProgressBar(true)
            } else {
                showData(true)
                showProgressBar(false)
            }
        }
        setData(serieDetail)

    }

    @SuppressLint("StringFormatMatches")
    private fun setData(serieDetail: SerieDetail) {
        Log.d("TAG", "$serieData")
        binding.apply {
            collapsingToolbar.title = serieDetail.name
            seriesTitle.text = serieDetail.name
            seriesTagline.text = serieDetail.tagline
            seriesLang.text = serieDetail.originalLanguage
            seriesRating.rating = serieDetail.voteAverage?.div(2)?.toFloat() as Float
            seriesVoteCount.text = resources.getString(R.string.voters, serieDetail.voteCount)
            seriesRelease.text =  serieDetail.firstAirDate
            seriesOverview.text = serieDetail.overview
        }
        val posterImg = serieData.backdropPath?.let { Config.getBackdropPath(it) }
        Glide.with(this)
            .load(posterImg)
            .apply(RequestOptions().override(3000))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading_backdrop).error(R.drawable.ic_error_backdrop))
            .into(binding.backdropImg)
    }

    private fun setupToolbar(serieDetail: SerieDetail) {
        binding.toolbar.apply {
            setOnMenuItemClickListener {
                when(it?.itemId){
                    R.id.menu_share_item -> { onShareClick(serieDetail)}
                }
                true
            }
        }
    }

    private fun onShareClick(serieDetail: SerieDetail) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle("Share")
            .setText(resources.getString(R.string.share_text, serieDetail.name))
            .startChooser()
    }


    private fun showData(state: Boolean) {
        with(binding.dataDetail){
            if (state){
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }

    private fun showProgressBar(state : Boolean){
        with(binding.progressBar){
            if (state){
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
    }
}