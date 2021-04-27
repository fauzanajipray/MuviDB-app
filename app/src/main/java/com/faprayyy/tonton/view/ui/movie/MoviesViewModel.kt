package com.faprayyy.tonton.view.ui.movie

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.Response.DiscoverMovieResponse
import com.faprayyy.tonton.data.MovieModel
import com.google.gson.Gson

class MoviesViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val listMovie = MutableLiveData<ArrayList<MovieModel>>()

    @SuppressLint("LogNotTimber")
    fun getData(context: Context){
        isLoading.postValue(true)
        val fileName = "movieresponse.json"
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val obj = Gson().fromJson(jsonString, DiscoverMovieResponse::class.java)

            listMovie.postValue(obj.results)
        } catch (e : Exception){
            e.printStackTrace()
        }
        isLoading.postValue(false)
    }
}
