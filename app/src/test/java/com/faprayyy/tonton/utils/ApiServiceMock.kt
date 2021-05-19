package com.faprayyy.tonton.utils

import com.faprayyy.tonton.data.remote.ApiService
import com.faprayyy.tonton.data.remote.response.DiscoverMovieResponse
import com.faprayyy.tonton.data.remote.response.DiscoverSeriesResponse
import com.faprayyy.tonton.data.remote.response.MovieDetail
import com.faprayyy.tonton.data.remote.response.SeriesDetail
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class ApiServiceMock : ApiService{
    override fun getMovie(movie_id: Int, api_key: String): Call<MovieDetail> {
        TODO("Not yet implemented")
    }

    override fun getSeries(series_id: Int, api_key: String): Call<SeriesDetail> {
        TODO("Not yet implemented")
    }

    override fun getDiscoverMovies(api_key: String): Call<DiscoverMovieResponse> {
        return object: Call<DiscoverMovieResponse> {
            override fun enqueue(callback: Callback<DiscoverMovieResponse>) { }

            override fun isExecuted(): Boolean = false

            override fun clone(): Call<DiscoverMovieResponse> = this

            override fun isCanceled(): Boolean = false

            override fun cancel() { }

            override fun execute(): Response<DiscoverMovieResponse> {
                val response = DataDummy.generateMoviesList()
                return Response.success(DiscoverMovieResponse(response))
            }

            override fun request(): Request {
                return Request.Builder().build()
            }

            override fun timeout(): Timeout {
                val mTimeout = Timeout()
                mTimeout.timeout(1, TimeUnit.SECONDS)
                return mTimeout
            }

        }
    }

    override fun getDiscoverSeries(api_key: String): Call<DiscoverSeriesResponse> {
        return object: Call<DiscoverSeriesResponse> {
            override fun enqueue(callback: Callback<DiscoverSeriesResponse>) { }

            override fun isExecuted(): Boolean = false

            override fun clone(): Call<DiscoverSeriesResponse> = this

            override fun isCanceled(): Boolean  = false

            override fun cancel() { }

            override fun execute(): Response<DiscoverSeriesResponse> {
                val response = DataDummy.generateSeriesList()
                return Response.success(DiscoverSeriesResponse(response))
            }

            override fun request(): Request {
                return Request.Builder().build()
            }

            override fun timeout(): Timeout {
                return Timeout()
            }

        }
    }

}