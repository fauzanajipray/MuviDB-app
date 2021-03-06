package com.faprayyy.themuvidb.view.ui.detail.detailseries

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
import com.faprayyy.themuvidb.data.local.entity.SeriesEntity
import com.faprayyy.themuvidb.data.remote.Config
import com.faprayyy.themuvidb.data.remote.response.SeriesDetail
import com.faprayyy.themuvidb.databinding.ActivityDetailSeriesBinding
import com.faprayyy.themuvidb.utils.Converter.convertGenres
import com.faprayyy.themuvidb.view.ui.detail.DetailViewModel
import com.faprayyy.themuvidb.view.ui.favorite.FavoriteActivity
import com.faprayyy.themuvidb.vo.StatusMessage
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailSeriesActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_SERIES = "extra_movie"
        const val EXTRA_FAVORITE = "extra_fav"
        const val SERIES_TYPE = "SERIES"
    }

    private lateinit var binding: ActivityDetailSeriesBinding
    private lateinit var seriesData: SeriesEntity
    private lateinit var seriesDetail: SeriesDetail
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var mFavoriteEntity: FavoriteEntity
    private lateinit var backdropImg: String
    private lateinit var imgNotFavorite : Drawable
    private lateinit var imgFavorite : Drawable
    private var seriesId: Int = 0
    private var stateFavorite : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSeriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgNotFavorite =  ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_border_24, null) as Drawable
        imgFavorite =  ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_24, null) as Drawable

        seriesDetail = SeriesDetail()
        setupToolbar(seriesDetail)
        showProgressBar(true)
        showData(false)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        stateFavorite = false
        val favData = intent.getParcelableExtra<FavoriteEntity>(EXTRA_FAVORITE)
        seriesId = if (favData == null){
            seriesData = intent.getParcelableExtra<SeriesEntity>(EXTRA_SERIES) as SeriesEntity
            backdropImg = seriesData.backdropPath
            seriesData.id
        }
        else {
            backdropImg = favData.backdropPath as String
            favData.id
        }

        getFavFromDB(seriesId)
        showProgressBar(true)
        showData(false)

        viewModel.getDetailSeries(seriesId).observe(this, { result ->
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
                            seriesDetail = it
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
                deleteFavFromDB(mFavoriteEntity.id, mFavoriteEntity.type)
            } else {
                addFavToDB(mFavoriteEntity)
            }
        }
        setData(seriesDetail)

    }

    private fun addFavToDB(mFavoriteEntity: FavoriteEntity) {
        viewModel.setFavorite(mFavoriteEntity)
        val string = getString(R.string.x_add_favorite, mFavoriteEntity.name)
        showToast(string)
        changeImgFAB(true)
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavFromDB(id: Int, type: String) {
        viewModel.deleteFavorite(id,type)
        val string = getString(R.string.x_remove_favorite, mFavoriteEntity.name)
        showToast(string)
        changeImgFAB(false)
    }

    private fun setDataFav() {
        mFavoriteEntity = seriesDetail.let {
            FavoriteEntity(
                    it.id,
                    it.name,
                    it.posterPath,
                    it.backdropPath,
                    SERIES_TYPE
            )
        }
    }

    private fun getFavFromDB(seriesId: Int) {
        viewModel.getMovieFromDB(seriesId, SERIES_TYPE).observe(this, {

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

    private fun changeImgFAB(state: Boolean) {
        stateFavorite = state
        if (state) {
            binding.fab.setImageDrawable(imgFavorite)
        } else {
            binding.fab.setImageDrawable(imgNotFavorite)
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun setData(seriesDetail: SeriesDetail) {
        binding.apply {
            collapsingToolbar.title = seriesDetail.name
            seriesTitle.text = seriesDetail.name
            seriesTagline.text = seriesDetail.tagline
            seriesLang.text = seriesDetail.originalLanguage?.uppercase(Locale.ROOT)
            seriesRating.rating = seriesDetail.voteAverage?.div(2)?.toFloat() as Float
            seriesVoteCount.text = resources.getString(R.string.voters, seriesDetail.voteCount)
            seriesRelease.text =  seriesDetail.firstAirDate
            seriesOverview.text = seriesDetail.overview
            seriesGenres.text = convertGenres(seriesDetail.genres)
        }
        val posterImg = Config.getBackdropPath(backdropImg)
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
                    R.id.menu_favorite_item -> {
                        val intent = Intent(context, FavoriteActivity::class.java)
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