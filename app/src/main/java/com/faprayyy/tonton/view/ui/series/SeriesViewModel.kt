package com.faprayyy.tonton.view.ui.series

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.data.Response.DiscoverSeriesResponse
import com.faprayyy.tonton.data.SeriesModel
import com.google.gson.Gson
import java.lang.Exception

class SeriesViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val listSeries = MutableLiveData<ArrayList<SeriesModel>>()

    fun getData(context: Context){
        isLoading.postValue(true)
        val fileName = "seriesresponse.json"
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val obj = Gson().fromJson(jsonString, DiscoverSeriesResponse::class.java)
            listSeries.postValue(obj.results)
        } catch (e : Exception){
            e.printStackTrace()
        }

        isLoading.postValue(false)
    }

}
