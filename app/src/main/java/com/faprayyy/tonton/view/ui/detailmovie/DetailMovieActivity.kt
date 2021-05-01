package com.faprayyy.tonton.view.ui.detailmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faprayyy.tonton.R
import com.faprayyy.tonton.api.Config
import com.faprayyy.tonton.data.Genre
import com.faprayyy.tonton.data.MovieModel
import com.faprayyy.tonton.data.Response.MovieDetail
import com.faprayyy.tonton.databinding.ActivityDetailMovieBinding
import com.faprayyy.tonton.helper.convertGenres
import com.faprayyy.tonton.view.ui.search.SearchActivity
import java.util.*


class DetailMovieActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var movieData: MovieModel
    private lateinit var movieDetail: MovieDetail
    private lateinit var viewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieDetail = MovieDetail()
        setupToolbar(movieDetail)
        showProgressBar(true)
        showData(false)
        viewModel = ViewModelProvider(this).get(DetailMovieViewModel::class.java)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        movieData = intent.getParcelableExtra<MovieModel>(EXTRA_MOVIE) as MovieModel

        viewModel.setData(movieData.id)
        viewModel.movieDetail.observe(this){
            setData(it)
            setupToolbar(it)
            movieDetail = it
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
        setData(movieDetail)

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

    @SuppressLint("StringFormatMatches")
    private fun setData(movieDetail: MovieDetail) {
        binding.apply {
            collapsingToolbar.title = movieDetail.title
            movieTitle.text = movieDetail.title
            movieTagline.text = movieDetail.tagline
            movieLang.text = movieDetail.originalLanguage?.toUpperCase(Locale.ROOT)
            movieRating.rating = movieDetail.voteAverage?.div(2)?.toFloat() as Float
            movieVoteCount.text = resources.getString(R.string.voters, movieDetail.voteCount)
            movieRelease.text = movieDetail.releaseDate
            movieOverview.text = movieDetail.overview
            movieGenres.text = convertGenres(movieDetail.genres)
        }

        val posterImg = movieData.backdropPath?.let { Config.getBackdropPath(it) }
        Glide.with(this)
            .load(posterImg)
            .apply(RequestOptions().override(3000))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading_backdrop).error(R.drawable.ic_error_backdrop))
            .into(binding.backdropImg)
    }

    private fun setupToolbar(movieDetail: MovieDetail) {
        binding.toolbar.apply {
            setOnMenuItemClickListener {
                when(it?.itemId){
                    R.id.menu_share_item -> { onShareClick(movieDetail) }
                    R.id.menu_search_item -> {
                        val intent = Intent(context, SearchActivity::class.java)
                        startActivity(intent)
                    }
                }
                true
            }
        }
    }

    private fun onShareClick(movieDetail: MovieDetail) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setChooserTitle("Share")
            .setText(resources.getString(R.string.share_text, movieDetail.title))
            .startChooser()
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