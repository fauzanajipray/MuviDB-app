package com.faprayyy.tonton.view.ui.detailmovie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.BuildConfig
import com.faprayyy.tonton.api.ApiConfig
import com.faprayyy.tonton.data.Response.MovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val movieDetail = MutableLiveData<MovieDetail>()
    var isLoading_= false

    private val apikey = BuildConfig.THEMOVIEDB_TOKEN

    fun setData(movieId: Int){
        isLoading.postValue(true)
        val client = ApiConfig.getApiService().getMovie(movieId,apikey)
        client.enqueue(object : Callback<MovieDetail>{
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.isSuccessful) {
                    Log.d("DetailMovieViewModel", "HIT API : ${response.body()}")
                    movieDetail.postValue(response.body())
                    isLoading.postValue(false)
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