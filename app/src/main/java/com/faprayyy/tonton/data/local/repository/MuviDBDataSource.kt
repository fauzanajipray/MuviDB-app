package com.faprayyy.tonton.data.local.repository

import androidx.lifecycle.LiveData
import com.faprayyy.tonton.data.Response.DiscoverMovieResponse
import com.faprayyy.tonton.data.Response.DiscoverSeriesResponse
import com.faprayyy.tonton.data.Response.MovieDetail
import com.faprayyy.tonton.data.Response.SeriesDetail
import com.faprayyy.tonton.data.local.response.MovieModel
import com.faprayyy.tonton.data.local.response.SeriesModel
import retrofit2.Response

interface MuviDBDataSource  {
    /**
     * Remote
     */
    fun getDetailMovieFromApi(movieId : Int) : LiveData<MovieDetail>

    fun getDetailSeriesFromApi(seriesId : Int) : LiveData<SeriesDetail>

    fun getMovieFromApi() : LiveData<ArrayList<MovieModel>>

    fun getSeriesFromApi() : LiveData<ArrayList<SeriesModel>>
}