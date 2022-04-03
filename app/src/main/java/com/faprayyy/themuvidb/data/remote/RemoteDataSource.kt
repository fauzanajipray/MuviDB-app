package com.faprayyy.themuvidb.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faprayyy.themuvidb.data.remote.response.*
import com.faprayyy.themuvidb.utils.EspressoIdlingResource
import com.faprayyy.themuvidb.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getDiscoverMovie() : LiveData<Resource<List<MovieModel>>> {
        EspressoIdlingResource.increment()
        val listMovie = MutableLiveData<Resource<List<MovieModel>>>()
        val client = apiService.getDiscoverMovies()
        client.enqueue(object : Callback<DiscoverMovieResponse> {
            override fun onResponse(call: Call<DiscoverMovieResponse>, response: Response<DiscoverMovieResponse>) {
                if (response.isSuccessful) {
                    listMovie.value = Resource.success( response.body()!!.results )
                }else{
                    listMovie.value = Resource.error( response.message(), null )
                }
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return listMovie
    }

    fun getDiscoverSeries(): LiveData<Resource<List<SeriesModel>>> {
        EspressoIdlingResource.increment()
        val listSeries = MutableLiveData<Resource<List<SeriesModel>>>()
        val client = apiService.getDiscoverSeries()
        client.enqueue(object : Callback<DiscoverSeriesResponse> {
            override fun onResponse(
                call: Call<DiscoverSeriesResponse>,
                response: Response<DiscoverSeriesResponse>
            ) {
                if (response.isSuccessful) {
                    listSeries.value = Resource.success( response.body()!!.results )
                }else{
                    listSeries.value = Resource.error( response.message(), null )
                }
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<DiscoverSeriesResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return listSeries
    }

    fun getDetailMovie(movieId: Int): LiveData<Resource<MovieDetail>> {
        EspressoIdlingResource.increment()
        val movieDetail = MutableLiveData<Resource<MovieDetail>>()
        val client = apiService.getMovie(movieId)
        client.enqueue(object : Callback<MovieDetail>{
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.isSuccessful) {
                    movieDetail.value = Resource.success(response.body())
                } else {
                    movieDetail.value = Resource.error(response.message(), null)
                    Log.e("DetailMovieViewModel", "onFailure: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.e("DetailMovieViewModel", "onFailure: ${t.message.toString()}")
                EspressoIdlingResource.decrement()
            }
        })
        return movieDetail
    }

    fun getDetailSeries(seriesId: Int): LiveData<Resource<SeriesDetail>> {
        EspressoIdlingResource.increment()
        val seriesDetail = MutableLiveData<Resource<SeriesDetail>>()
        val client = apiService.getSeries(seriesId)
        client.enqueue(object : Callback<SeriesDetail> {
            override fun onResponse(call: Call<SeriesDetail>, response: Response<SeriesDetail>) {
                if (response.isSuccessful) {
                    seriesDetail.value = Resource.success(response.body())
                } else {
                    seriesDetail.value = Resource.error(response.message(), null)
                    Log.e("DetailSeriesViewModel", "onFailure: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<SeriesDetail>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.e("DetailSeriesViewModel", "onFailure: ${t.message.toString()}")
            }
        })
        return seriesDetail
    }


}