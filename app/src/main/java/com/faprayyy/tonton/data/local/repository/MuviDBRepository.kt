package com.faprayyy.tonton.data.local.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faprayyy.tonton.data.Response.DiscoverMovieResponse
import com.faprayyy.tonton.data.Response.DiscoverSeriesResponse
import com.faprayyy.tonton.data.Response.MovieDetail
import com.faprayyy.tonton.data.Response.SeriesDetail
import com.faprayyy.tonton.data.local.response.MovieModel
import com.faprayyy.tonton.data.local.response.SeriesModel
import com.faprayyy.tonton.data.remote.ApiConfig
import com.faprayyy.tonton.data.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MuviDBRepository private constructor(
        private val apiService: ApiService
): MuviDBDataSource {

    companion object {
        @Volatile
        private var instance: MuviDBRepository? = null
        fun getInstance(apiService: ApiService): MuviDBRepository =
                instance ?: synchronized(this) {
                    MuviDBRepository(apiService).apply { instance = this }
                }
    }

    override fun getDetailMovieFromApi(movieId: Int): LiveData<MovieDetail> {
        val movieDetail = MutableLiveData<MovieDetail>()
        val client = apiService.getMovie(movieId)
        client.enqueue(object : Callback<MovieDetail>{
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.isSuccessful) {
                    movieDetail.postValue(response.body())
                } else {
                    Log.e("DetailMovieViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.e("DetailMovieViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return movieDetail
    }

    override fun getDetailSeriesFromApi(seriesId: Int): LiveData<SeriesDetail> {
        val seriesDetail = MutableLiveData<SeriesDetail>()
        val client = apiService.getSeries(seriesId)
        client.enqueue(object : Callback<SeriesDetail> {
            override fun onResponse(call: Call<SeriesDetail>, response: Response<SeriesDetail>) {
                if (response.isSuccessful) {
                    seriesDetail.postValue(response.body())
                } else {
                    Log.e("DetailSerieViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SeriesDetail>, t: Throwable) {
                Log.e("DetailSerieViewModel", "onFailure: ${t.message.toString()}")
            }
        })

        return seriesDetail
    }

    override fun getMovieFromApi(): LiveData<ArrayList<MovieModel>> {
        val listMovie = MutableLiveData<ArrayList<MovieModel>>()

        val client = apiService.getDiscoverMovies()
        client.enqueue(object : Callback<DiscoverMovieResponse> {
            override fun onResponse(call: Call<DiscoverMovieResponse>, response: Response<DiscoverMovieResponse>) {
                if (response.isSuccessful) {
                    Log.d("TryInjection", "${response.body()}")
                    listMovie.postValue(response.body()?.results)
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return listMovie
    }

    override fun getSeriesFromApi(): LiveData<ArrayList<SeriesModel>> {
        val listSeries = MutableLiveData<ArrayList<SeriesModel>>()
        val client = apiService.getDiscoverSeries()
        client.enqueue(object : Callback<DiscoverSeriesResponse> {
            override fun onResponse(
                    call: Call<DiscoverSeriesResponse>,
                    response: Response<DiscoverSeriesResponse>
            ) {
                if (response.isSuccessful) {
                    listSeries.postValue(response.body()?.results)
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DiscoverSeriesResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return listSeries
    }


}