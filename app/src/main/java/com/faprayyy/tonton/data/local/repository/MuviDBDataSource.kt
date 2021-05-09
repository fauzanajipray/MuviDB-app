package com.faprayyy.tonton.data.local.repository

import androidx.lifecycle.LiveData
import com.faprayyy.tonton.data.local.response.MovieDetail
import com.faprayyy.tonton.data.local.response.MovieModel
import com.faprayyy.tonton.data.local.response.SeriesDetail
import com.faprayyy.tonton.data.local.response.SeriesModel

interface MuviDBDataSource  {

    fun getDetailMovieFromApi(movieId : Int) : LiveData<MovieDetail>

    fun getDetailSeriesFromApi(seriesId : Int) : LiveData<SeriesDetail>

    fun getMovieFromApi() : LiveData<ArrayList<MovieModel>>

    fun getSeriesFromApi() : LiveData<ArrayList<SeriesModel>>
}