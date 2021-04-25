package com.faprayyy.tonton.view.ui.series

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faprayyy.tonton.api.ApiConfig
import com.faprayyy.tonton.data.Response.DiscoverSeriesResponse
import com.faprayyy.tonton.data.SeriesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeriesViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val listSeries = MutableLiveData<ArrayList<SeriesModel>>()

    // TODO GANTI BUILD IMPORT
    val apikey = com.faprayyy.tonton.utils.apikey.apiKey

    @SuppressLint("LogNotTimber")
    fun setData(){
        isLoading.postValue(true)
        val client = ApiConfig.getApiService().getDiscoverSeries(apikey)
        client.enqueue(object : Callback<DiscoverSeriesResponse> {
            override fun onResponse(
                call: Call<DiscoverSeriesResponse>,
                response: Response<DiscoverSeriesResponse>
            ) {
                if (response.isSuccessful) {
                    isLoading.postValue(false)
                    Log.d("MainViewModel", "HIT API")
                    listSeries.postValue(response.body()?.results)
                } else {
                    Log.e("MainViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DiscoverSeriesResponse>, t: Throwable) {
                isLoading.postValue(false)
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }


        })
    }

}