package com.faprayyy.themuvidb.view.ui.detail.detailmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faprayyy.themuvidb.R
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.local.entity.MovieEntity
import com.faprayyy.themuvidb.data.remote.Config
import com.faprayyy.themuvidb.data.remote.response.MovieDetail
import com.faprayyy.themuvidb.databinding.ActivityDetailMovieBinding
import com.faprayyy.themuvidb.utils.Converter.convertGenres
import com.faprayyy.themuvidb.view.ui.detail.DetailViewModel
import com.faprayyy.themuvidb.view.ui.favorite.FavoriteActivity
import com.faprayyy.themuvidb.vo.StatusMessage
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_FAVORITE = "extra_fav"
        const val MOVIE_TYPE = "MOVIE"
    }

    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var movieData: MovieEntity
    private lateinit var movieDetail: MovieDetail
    private lateinit var movieFavorite: FavoriteEntity
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var backdropImg: String
    private lateinit var imgNotFavorite : Drawable
    private lateinit var imgFavorite : Drawable
    private var movieId: Int = 0
    private var stateFavorite : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgNotFavorite =  ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_border_24, null) as Drawable
        imgFavorite =  ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_24, null) as Drawable

        movieDetail = MovieDetail()
        setupToolbar(movieDetail)
        showProgressBar(true)
        showData(false)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        stateFavorite = false
        val favData = intent.getParcelableExtra<FavoriteEntity>(EXTRA_FAVORITE)
        movieId = if (favData == null){
            movieData = intent.getParcelableExtra<MovieEntity>(EXTRA_MOVIE) as MovieEntity
            backdropImg = movieData.backdropPath
            movieData.id
        }
        else {
            backdropImg = favData.backdropPath as String
            favData.id
        }

        getFavFromDB(movieId)
        showProgressBar(true)
        showData(false)
        viewModel.getDetailMovie(movieId).observe(this, { result ->
            if (result!=null){
                when(result.status){
                    StatusMessage.LOADING -> {
                        showProgressBar(true)
                    }
                    StatusMessage.SUCCESS ->{
                        showProgressBar(false)
                        result.data?.let {
                            setData(it)
                            setupToolbar(it)
                            movieDetail = it
                            showData(true)
                            setDataFav()
                        }
                    }
                    StatusMessage.ERROR -> {
                        showProgressBar(false)
                    }
                    else -> { }
                }
            }
        })

        binding.fab.setOnClickListener{
            if(stateFavorite){
                deleteFavFromDB(movieFavorite.id, movieFavorite.type)
            } else {
                addFavToDB(movieFavorite)
            }
        }
        setData(movieDetail)

    }

    private fun setDataFav() {
        movieFavorite = movieDetail.let {
            FavoriteEntity(
                it.id,
                it.originalTitle,
                it.posterPath,
                it.backdropPath,
                MOVIE_TYPE
            )
        }
    }

    private fun addFavToDB(movieFavorite: FavoriteEntity) {
        viewModel.setFavorite(movieFavorite)
        val string = getString(R.string.x_add_favorite, movieFavorite.name)
        showToast(string)
        changeImgFAB(true)
    }

    private fun deleteFavFromDB(id : Int, type : String) {
        viewModel.deleteFavorite(id,type)
        val string = getString(R.string.x_remove_favorite, movieFavorite.name)
        showToast(string)
        changeImgFAB(false)
    }

    private fun changeImgFAB(state : Boolean) {
        stateFavorite = state
        if (state) {
            binding.fab.setImageDrawable(imgFavorite)
        } else {
            binding.fab.setImageDrawable(imgNotFavorite)
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun getFavFromDB(movieId: Int) {
        viewModel.getMovieFromDB(movieId, MOVIE_TYPE).observe(this, {
            stateFavorite = false
            if (it != null) {
                stateFavorite = true
                changeImgFAB(stateFavorite)
            } else {
                stateFavorite = false
                changeImgFAB(stateFavorite)
            }
        })

    }

    private fun showData(state: Boolean) {
        val data = if (state){
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.apply {
            fab.visibility = data
            dataDetail.visibility = data
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun setData(movieDetail: MovieDetail) {
        binding.apply {
            collapsingToolbar.title = movieDetail.title
            movieTitle.text = movieDetail.title
            movieTagline.text = movieDetail.tagline
            movieLang.text = movieDetail.originalLanguage?.uppercase(Locale.ROOT)
            movieRating.rating = movieDetail.voteAverage?.div(2)?.toFloat() as Float
            movieVoteCount.text = resources.getString(R.string.voters, movieDetail.voteCount)
            movieRelease.text = movieDetail.releaseDate
            movieOverview.text = movieDetail.overview
            movieGenres.text = convertGenres(movieDetail.genres)
        }

        val posterImg = Config.getBackdropPath(backdropImg)
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
                    R.id.menu_favorite_item -> {
                        val intent = Intent(context, FavoriteActivity::class.java)
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
            visibility = if (state) View.VISIBLE
             else View.GONE
        }
    }

}