package com.faprayyy.tonton.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
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

    fun getFavorites(sort: String): LiveData<PagedList<FavoriteEntity>>

    fun getFavoriteById(id: Int, type : String) : LiveData<FavoriteEntity>

    fun setFavorite(fav: FavoriteEntity)

    fun deleteFavorite(id: Int, type: String)
}