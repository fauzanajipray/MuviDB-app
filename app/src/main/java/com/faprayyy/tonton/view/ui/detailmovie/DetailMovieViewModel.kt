package com.faprayyy.tonton.view.ui.detailmovie

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.Response.MovieDetail
import com.faprayyy.tonton.data.Response.MovieDetailResponse
import com.google.gson.Gson

class DetailMovieViewModel : ViewModel() {

    val movieDetail = MutableLiveData<MovieDetail>()

    @SuppressLint("LogNotTimber")
    fun setDataJson(idMovie: Int, context: Context) {
        val fileName = "listdetailmovie.json"
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }

            val obj = Gson().fromJson(jsonString, MovieDetailResponse::class.java)
            val movieList = obj.results
            for (i in 0 until movieList.size){
                if (movieList[i].id == idMovie){
                    movieDetail.postValue(movieList[i])
                    break
                }
            }

        } catch (e: Exception){
            Log.e("Failed","${e.printStackTrace()}")
        }
    }
}