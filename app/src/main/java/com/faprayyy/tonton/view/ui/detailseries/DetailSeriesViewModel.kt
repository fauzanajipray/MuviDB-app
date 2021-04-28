package com.faprayyy.tonton.view.ui.detailseries

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.Response.SerieDetail
import com.faprayyy.tonton.data.Response.SerieDetailResponse
import com.faprayyy.tonton.utils.getListSeriesDetail
import com.google.gson.Gson

class DetailSeriesViewModel : ViewModel() {

    val seriesDetail = MutableLiveData<SerieDetail>()

    fun setDataJson(idSerie: Int) {
        val dataJsonString = getListSeriesDetail()
        try {
            val obj = Gson().fromJson(dataJsonString, SerieDetailResponse::class.java)
            val serieList = obj.results
            for (i in 0 until serieList.size){
                if (serieList[i].id == idSerie){
                    seriesDetail.postValue(serieList[i])
                    break
                }
            }
        } catch (e: Exception){
            Log.e("Failed","${e.printStackTrace()}")
        }
    }
}