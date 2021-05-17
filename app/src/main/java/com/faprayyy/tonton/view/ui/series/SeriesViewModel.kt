package com.faprayyy.tonton.view.ui.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.remote.response.SeriesModel

class SeriesViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    private var seriesList = MutableLiveData<ArrayList<SeriesModel>>()

    fun getSeriesListFromApi() {
        val data = mMuviDBRepository.getSeriesFromApi()
        seriesList = data as MutableLiveData<ArrayList<SeriesModel>>

    }

    fun getSeriesList() : LiveData<ArrayList<SeriesModel>>{
        return seriesList
    }

}