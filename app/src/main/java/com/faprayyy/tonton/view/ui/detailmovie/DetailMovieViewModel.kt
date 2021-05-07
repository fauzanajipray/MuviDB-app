package com.faprayyy.tonton.view.ui.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.Response.MovieDetail
import com.faprayyy.tonton.data.local.repository.MuviDBRepository

class DetailMovieViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()

    fun getMovie(movieId : Int): LiveData<MovieDetail> {
        isLoading.postValue(true)
        val data = mMuviDBRepository.getDetailMovieFromApi(movieId)
        isLoading.postValue(false)
        return data
    }

    fun getLoadingState(): LiveData<Boolean> = isLoading

}