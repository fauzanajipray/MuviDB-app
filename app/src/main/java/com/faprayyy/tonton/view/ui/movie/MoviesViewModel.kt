package com.faprayyy.tonton.view.ui.movie

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.api.ApiConfig
import com.faprayyy.tonton.api.Response.DiscoverMovieResponse
import com.faprayyy.tonton.data.MovieModel
import com.faprayyy.tonton.view.adapter.MovieAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val listMovie = MutableLiveData<ArrayList<MovieModel>>()

    // TODO GANTI BUILD IMPORT
    val apikey = com.faprayyy.tonton.utils.apikey.apiKey

    @SuppressLint("LogNotTimber")
    fun setData(){
        isLoading.postValue(true)
        val client = ApiConfig.getApiService().getDiscoverMovies(apikey)
        client.enqueue(object : Callback<DiscoverMovieResponse> {

            override fun onResponse(call: Call<DiscoverMovieResponse>, response: Response<DiscoverMovieResponse>) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    Log.d("MainViewModel", "HIT API")
                    listMovie.postValue(response.body()?.results)
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                isLoading.postValue(false)
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }
}