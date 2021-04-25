package com.faprayyy.tonton.view.ui.detailseries

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.api.ApiConfig
import com.faprayyy.tonton.data.Response.SerieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSeriesViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val serieDetail = MutableLiveData<SerieDetail>()

    // TODO GANTI BUILD IMPORT
    private val apikey = com.faprayyy.tonton.utils.apikey.apiKey

    @SuppressLint("LogNotTimber")
    fun setData(movieId: Int){
        isLoading.postValue(true)
        val client = ApiConfig.getApiService().getSerie(movieId,apikey)
        client.enqueue(object : Callback<SerieDetail> {
            override fun onResponse(call: Call<SerieDetail>, response: Response<SerieDetail>) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    Log.d("DetailSerieViewModel", "HIT API : ${response.body()}")
                    serieDetail.postValue(response.body())
                } else {
                    Log.e("DetailSerieViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SerieDetail>, t: Throwable) {
                isLoading.postValue(false)
                Log.e("DetailSerieViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }
}