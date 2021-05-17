package com.faprayyy.tonton.view.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.remote.response.MovieModel

class MoviesViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    private var movieList = MutableLiveData<ArrayList<MovieModel>>()

    fun getMoviesListFromApi() {
        val data = mMuviDBRepository.getMovieFromApi()
        movieList = data as MutableLiveData<ArrayList<MovieModel>>
    }

    fun getMoviesList() : LiveData<ArrayList<MovieModel>>{
        return movieList
    }

}