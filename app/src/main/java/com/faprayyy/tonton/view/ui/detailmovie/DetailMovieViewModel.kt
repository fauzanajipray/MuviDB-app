package com.faprayyy.tonton.view.ui.detailmovie

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.api.ApiConfig
import com.faprayyy.tonton.data.Genre
import com.faprayyy.tonton.data.Response.MovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val movieDetail = MutableLiveData<MovieDetail>()

    // TODO GANTI BUILD IMPORT
    private val apikey = com.faprayyy.tonton.utils.apikey.apiKey

    @SuppressLint("LogNotTimber")
    fun setData(movieId: Int){
        isLoading.postValue(true)
        val client = ApiConfig.getApiService().getMovie(movieId,apikey)
        client.enqueue(object : Callback<MovieDetail>{
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    Log.d("DetailMovieViewModel", "HIT API : ${response.body()}")
                    movieDetail.postValue(response.body())
                } else {
                    Log.e("DetailMovieViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                isLoading.postValue(false)
                Log.e("DetailMovieViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }
}