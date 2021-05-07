package com.faprayyy.tonton.view.ui.detailseries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.BuildConfig
import com.faprayyy.tonton.data.remote.ApiConfig
import com.faprayyy.tonton.data.Response.SeriesDetail
import com.faprayyy.tonton.data.local.repository.MuviDBRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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