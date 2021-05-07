package com.faprayyy.tonton.view.ui.detailseries

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.Response.SeriesDetail
import com.faprayyy.tonton.data.Response.SeriesDetailResponse
import com.faprayyy.tonton.utils.getListSeriesDetail
import com.google.gson.Gson

class DetailSeriesViewModel : ViewModel() {

    val seriesDetail = MutableLiveData<SeriesDetail>()

    fun setDataJson(idSeries: Int) {
        val dataJsonString = getListSeriesDetail()
        try {
            val obj = Gson().fromJson(dataJsonString, SeriesDetailResponse::class.java)
            val seriesList = obj.results
            for (i in 0 until seriesList.size){
                if (seriesList[i].id == idSeries){
                    seriesDetail.postValue(seriesList[i])
                    break
                }
            }
        } catch (e: Exception){
            Log.e("Failed","${e.printStackTrace()}")
        }
    }
}