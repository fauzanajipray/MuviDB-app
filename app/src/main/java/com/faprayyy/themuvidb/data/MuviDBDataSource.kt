package com.faprayyy.themuvidb.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.local.entity.MovieEntity
import com.faprayyy.themuvidb.data.local.entity.SeriesEntity
import com.faprayyy.themuvidb.data.remote.response.MovieDetail
import com.faprayyy.themuvidb.data.remote.response.SeriesDetail
import com.faprayyy.themuvidb.vo.Resource

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