package com.faprayyy.tonton.view.ui.detailseries

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        viewModel = ViewModelProvider(this).get(DetailSeriesViewModel::class.java)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        serieData = intent.getParcelableExtra<SeriesModel>(EXTRA_SERIE) as SeriesModel
        viewModel.setDataJson(serieData.id, this)
        viewModel.serieDetail.observe(this){
            setData(it)
        }
        setData(serieDetail)

    }

    @SuppressLint("LogNotTimber")
    private fun setData(serieDetail: SerieDetail) {
        Log.d("TAG", "$serieData")
        binding.apply {
            collapsingToolbar.title = serieDetail.name
            seriesTitle.text = serieDetail.name
            seriesRelease.text = serieDetail.firstAirDate
            seriesOverview.text = serieDetail.overview
        }
        val posterImg = serieData.backdropPath?.let { Config.getBackdropPath(it) }
        Glide.with(this)
            .load(posterImg)
            .apply(RequestOptions().override(3000))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading_backdrop).error(R.drawable.ic_error_backdrop))
            .into(binding.backdropImg)
    }
}