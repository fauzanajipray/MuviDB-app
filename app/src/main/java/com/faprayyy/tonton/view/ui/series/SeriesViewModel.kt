package com.faprayyy.tonton.view.ui.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.local.repository.MuviDBRepository
import com.faprayyy.tonton.data.local.response.SeriesModel

class SeriesViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    private val isLoading = MutableLiveData<Boolean>()

    fun getSeriesList(): LiveData<ArrayList<SeriesModel>> {
        isLoading.postValue(true)
        val data = mMuviDBRepository.getSeriesFromApi()
        isLoading.postValue(false)
        return data
    }

    fun getLoadingState(): LiveData<Boolean> = isLoading

}