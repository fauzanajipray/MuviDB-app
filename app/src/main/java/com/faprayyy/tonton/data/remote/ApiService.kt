package com.faprayyy.tonton.data.remote

import com.faprayyy.tonton.BuildConfig
import com.faprayyy.tonton.data.Response.DiscoverMovieResponse
import com.faprayyy.tonton.data.Response.DiscoverSeriesResponse
import com.faprayyy.tonton.data.Response.MovieDetail
import com.faprayyy.tonton.data.Response.SeriesDetail
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("3/movie/{movie_id}?")
    fun getMovie(
            @Path("movie_id") movie_id: Int,
            @Query("api_key") api_key: String = BuildConfig.THEMOVIEDB_TOKEN
    ): Call<MovieDetail>

    @GET("3/tv/{series_id}?")
    fun getSeries(
            @Path("series_id") series_id: Int,
            @Query("api_key") api_key: String = BuildConfig.THEMOVIEDB_TOKEN
    ): Call<SeriesDetail>

    @GET("3/discover/movie")
    fun getDiscoverMovies(
            @Query("api_key") api_key: String = BuildConfig.THEMOVIEDB_TOKEN
    ): Call<DiscoverMovieResponse>

    @GET("3/discover/tv")
    fun getDiscoverSeries(
        @Query("api_key") api_key: String = BuildConfig.THEMOVIEDB_TOKEN
    ): Call<DiscoverSeriesResponse>
}