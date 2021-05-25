package com.faprayyy.tonton.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.entity.MovieEntity
import com.faprayyy.tonton.data.local.entity.SeriesEntity
import com.faprayyy.tonton.data.remote.response.MovieModel
import com.faprayyy.tonton.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MuviDBRepository
    ) : ViewModel() {

//    private var movieList = MutableLiveData<ArrayList<MovieModel>>()
//
//    fun getMoviesListFromApi() {
//        val data = mMuviDBRepository.getMovieFromApi()
//        movieList = data as MutableLiveData<ArrayList<MovieModel>>
//    }
//
//    fun getMoviesList() : LiveData<ArrayList<MovieModel>>{
//        return movieList
//    }

    fun getMovieList(): LiveData<Resource<PagedList<MovieEntity>>> = repository.getMovieFromApi()

    fun getSeriesList() : LiveData<Resource<PagedList<SeriesEntity>>> = repository.getSeriesFromApi()

}