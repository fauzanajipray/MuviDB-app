package com.faprayyy.tonton.view.ui.detailseries

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faprayyy.tonton.R
import com.faprayyy.tonton.api.Config
import com.faprayyy.tonton.data.Response.SeriesDetail
import com.faprayyy.tonton.data.SeriesModel
import com.faprayyy.tonton.databinding.ActivityDetailSeriesBinding

class DetailSeriesActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_SERIES = "extra_movie"
    }

    private lateinit var binding: ActivityDetailSeriesBinding
    private lateinit var serieData: SeriesModel
    private lateinit var seriesDetail: SeriesDetail
    private lateinit var viewModel: DetailSeriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seriesDetail = SeriesDetail()
        setupToolbar(seriesDetail)
        viewModel = ViewModelProvider(this).get(DetailSeriesViewModel::class.java)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        serieData = intent.getParcelableExtra<SeriesModel>(EXTRA_SERIES) as SeriesModel
        viewModel.setDataJson(serieData.id)
        viewModel.seriesDetail.observe(this){
            setData(it)
            setupToolbar(it)
            seriesDetail = it
        }
        setData(seriesDetail)
    }

    @SuppressLint("LogNotTimber")
    private fun setData(seriesDetail: SeriesDetail) {
        Log.d("TAG", "$serieData")
        binding.apply {
            collapsingToolbar.title = seriesDetail.name
            seriesTitle.text = seriesDetail.name
            seriesTagline.text = seriesDetail.tagline
            seriesLang.text = seriesDetail.originalLanguage
            seriesRating.text = seriesDetail.voteAverage.toString()
            seriesRelease.text = resources.getString(R.string.first_air_date, seriesDetail.firstAirDate)
            seriesOverview.text = seriesDetail.overview
        }
        val posterImg = serieData.backdropPath?.let { Config.getBackdropPath(it) }
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
                    R.id.menu_share_item -> { onShareClick(seriesDetail)}
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
}