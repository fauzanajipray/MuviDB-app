package com.faprayyy.tonton.view.ui.movie

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.MovieModel
import com.faprayyy.tonton.data.Response.DiscoverMovieResponse
import com.faprayyy.tonton.utils.getMovieResponse
import com.google.gson.Gson

class MoviesViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val listMovie = MutableLiveData<ArrayList<MovieModel>>()

    fun getData(){
        isLoading.postValue(true)
        val dataJsonString = getMovieResponse()
        try {
            val obj = Gson().fromJson(dataJsonString, DiscoverMovieResponse::class.java)
            listMovie.postValue(obj.results)

        } catch (e: Exception){
            e.printStackTrace()
        }
        isLoading.postValue(false)
    }
}
