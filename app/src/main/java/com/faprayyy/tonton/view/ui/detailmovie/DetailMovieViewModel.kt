package com.faprayyy.tonton.view.ui.detailmovie

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.Response.MovieDetail
import com.faprayyy.tonton.data.Response.MovieDetailResponse
import com.faprayyy.tonton.utils.getListMovieDetail
import com.google.gson.Gson

class DetailMovieViewModel : ViewModel() {

    val movieDetail = MutableLiveData<MovieDetail>()

    fun setDataJson(idMovie: Int) {
        val dataJsonString = getListMovieDetail()
        try {
            val obj = Gson().fromJson(dataJsonString, MovieDetailResponse::class.java)
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