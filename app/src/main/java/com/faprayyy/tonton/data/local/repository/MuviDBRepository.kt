package com.faprayyy.tonton.data.local.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faprayyy.tonton.data.local.response.*
import com.faprayyy.tonton.data.remote.ApiService
import com.faprayyy.tonton.utils.EspressoIdlingResource
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
        EspressoIdlingResource.increment()
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
        EspressoIdlingResource.decrement()
        return movieDetail
    }

    override fun getDetailSeriesFromApi(seriesId: Int): LiveData<SeriesDetail> {
        EspressoIdlingResource.increment()
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
        EspressoIdlingResource.decrement()
        return seriesDetail
    }

    override fun getMovieFromApi(): LiveData<ArrayList<MovieModel>> {
        EspressoIdlingResource.increment()
        val listMovie = MutableLiveData<ArrayList<MovieModel>>()
        val client = apiService.getDiscoverMovies()
        client.enqueue(object : Callback<DiscoverMovieResponse> {
            override fun onResponse(call: Call<DiscoverMovieResponse>, response: Response<DiscoverMovieResponse>) {
                if (response.isSuccessful) {
                    listMovie.postValue(response.body()?.results)
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        EspressoIdlingResource.decrement()
        return listMovie
    }

    override fun getSeriesFromApi(): LiveData<ArrayList<SeriesModel>> {
        EspressoIdlingResource.increment()
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
        EspressoIdlingResource.decrement()
        return listSeries
    }


}