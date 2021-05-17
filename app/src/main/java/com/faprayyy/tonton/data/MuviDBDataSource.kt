package com.faprayyy.tonton.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.remote.response.MovieDetail
import com.faprayyy.tonton.data.remote.response.MovieModel
import com.faprayyy.tonton.data.remote.response.SeriesDetail
import com.faprayyy.tonton.data.remote.response.SeriesModel

interface MuviDBDataSource  {

    fun getDetailMovieFromApi(movieId : Int) : LiveData<MovieDetail>

    fun getDetailSeriesFromApi(seriesId : Int) : LiveData<SeriesDetail>

    fun getMovieFromApi() : LiveData<ArrayList<MovieModel>>

    fun getSeriesFromApi() : LiveData<ArrayList<SeriesModel>>

    fun getFavorite(): DataSource.Factory<Int, FavoriteEntity>

    fun setFavorite(fav: FavoriteEntity)

    fun getFavoriteById(id: Int, type : String) : LiveData<FavoriteEntity>

    fun deleteFavorite(id: Int, type: String)
}