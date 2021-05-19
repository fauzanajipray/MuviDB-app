package com.faprayyy.tonton.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faprayyy.tonton.data.remote.response.DiscoverMovieResponse
import com.faprayyy.tonton.data.remote.response.MovieModel
import com.faprayyy.tonton.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource private constructor(private val apiService: ApiService) {

    companion object {
        private var INSTANCE: RemoteDataSource? = null

        fun getInstance(apiService: ApiService): RemoteDataSource =
                INSTANCE ?: RemoteDataSource(apiService).apply {
                    INSTANCE = this
                }

    }

    fun getDiscoverMovie() : LiveData<ArrayList<MovieModel>> {
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

}