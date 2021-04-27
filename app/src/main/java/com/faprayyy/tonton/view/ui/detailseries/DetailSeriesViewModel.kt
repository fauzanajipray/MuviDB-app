package com.faprayyy.tonton.view.ui.detailseries

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.Response.SerieDetail
import com.faprayyy.tonton.data.Response.SerieDetailResponse
import com.google.gson.Gson

class DetailSeriesViewModel : ViewModel() {

    val serieDetail = MutableLiveData<SerieDetail>()

    @SuppressLint("LogNotTimber")
    fun setDataJson(idSerie: Int, context: Context) {
        val fileName = "listdetailseries.json"
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val obj = Gson().fromJson(jsonString, SerieDetailResponse::class.java)
            val serieList = obj.results
            for (i in 0 until serieList.size){
                if (serieList[i].id == idSerie){
                    Log.i("SERIEDETAIL","${serieList[i]}")
                    serieDetail.postValue(serieList[i])
                    break
                }
            }
        } catch (e: Exception){
            Log.e("Failed","${e.printStackTrace()}")
        }
    }
}