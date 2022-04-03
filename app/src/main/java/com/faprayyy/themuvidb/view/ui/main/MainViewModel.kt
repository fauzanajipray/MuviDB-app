package com.faprayyy.themuvidb.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.faprayyy.themuvidb.data.MuviDBRepository
import com.faprayyy.themuvidb.data.local.entity.MovieEntity
import com.faprayyy.themuvidb.data.local.entity.SeriesEntity
import com.faprayyy.themuvidb.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MuviDBRepository
    ) : ViewModel() {

    fun getMovieList(): LiveData<Resource<PagedList<MovieEntity>>> = repository.getMovieFromApi()

    fun getSeriesList() : LiveData<Resource<PagedList<SeriesEntity>>> = repository.getSeriesFromApi()

}