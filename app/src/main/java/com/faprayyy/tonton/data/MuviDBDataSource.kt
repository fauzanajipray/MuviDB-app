package com.faprayyy.tonton.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.local.entity.MovieEntity
import com.faprayyy.tonton.data.local.entity.SeriesEntity
import com.faprayyy.tonton.data.remote.response.MovieDetail
import com.faprayyy.tonton.data.remote.response.SeriesDetail
import com.faprayyy.tonton.vo.Resource

interface MuviDBDataSource  {

    fun getMovieFromApi() : LiveData<Resource<PagedList<MovieEntity>>>

    fun getSeriesFromApi() : LiveData<Resource<PagedList<SeriesEntity>>>

    fun getDetailMovieFromApi(movieId : Int) : LiveData<Resource<MovieDetail>>

    fun getDetailSeriesFromApi(seriesId : Int) : LiveData<Resource<SeriesDetail>>

    fun getFavorites(sort: String): LiveData<PagedList<FavoriteEntity>>

    fun getFavoriteById(id: Int, type : String) : LiveData<FavoriteEntity>

    fun setFavorite(fav: FavoriteEntity)

    fun deleteFavorite(id: Int, type: String)
}