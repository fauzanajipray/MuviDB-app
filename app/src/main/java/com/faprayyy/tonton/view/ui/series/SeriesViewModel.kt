package com.faprayyy.tonton.view.ui.series

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.remote.ApiConfig
import com.faprayyy.tonton.data.Response.DiscoverSeriesResponse
import com.faprayyy.tonton.data.local.repository.MuviDBRepository
import com.faprayyy.tonton.data.local.response.MovieModel
import com.faprayyy.tonton.data.local.response.SeriesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeriesViewModel(private val mMuviDBRepository: MuviDBRepository) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()

    fun getSeriesList(): LiveData<ArrayList<SeriesModel>> {
        isLoading.postValue(true)
        val data = mMuviDBRepository.getSeriesFromApi()
        isLoading.postValue(false)
        return data
    }

    fun getLoadingState(): LiveData<Boolean> = isLoading

}