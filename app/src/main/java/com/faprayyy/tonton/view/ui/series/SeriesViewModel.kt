package com.faprayyy.tonton.view.ui.series

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.Response.DiscoverSeriesResponse
import com.faprayyy.tonton.data.SeriesModel
import com.faprayyy.tonton.utils.getSeriesResponse
import com.google.gson.Gson
import java.lang.Exception

class SeriesViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val listSeries = MutableLiveData<ArrayList<SeriesModel>>()

    fun getData(){
        isLoading.postValue(true)
        val dataJsonString = getSeriesResponse()
        try {
            val obj = Gson().fromJson(dataJsonString, DiscoverSeriesResponse::class.java)
            listSeries.postValue(obj.results)
        } catch (e : Exception){
            e.printStackTrace()
        }
        isLoading.postValue(false)
    }

}
