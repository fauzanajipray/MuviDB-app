package com.faprayyy.tonton.view.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.local.repository.MuviDBRepository
import com.faprayyy.tonton.data.local.response.MovieModel

class MoviesViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()

    fun getMoviesList(): LiveData<ArrayList<MovieModel>> {
        isLoading.postValue(true)
        val data = mMuviDBRepository.getMovieFromApi()
        isLoading.postValue(false)
        return data
    }

    fun getLoadingState(): LiveData<Boolean> = isLoading
}