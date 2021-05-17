package com.faprayyy.tonton.view.ui.detailseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.remote.response.SeriesDetail

class DetailSeriesViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()

    fun getSeries(seriesId: Int) : LiveData<SeriesDetail>{
        isLoading.postValue(true)
        val data = mMuviDBRepository.getDetailSeriesFromApi(seriesId)
        isLoading.postValue(false)
        return data
    }

    fun getLoadingState(): LiveData<Boolean> = isLoading
}