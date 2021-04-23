package com.faprayyy.tonton.api

import com.faprayyy.tonton.api.Response.DiscoverMovieResponse
import com.faprayyy.tonton.api.Response.DiscoverSeriesResponse
import com.faprayyy.tonton.data.MovieModel
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("3/movie/{movie_id}?")
    fun getMovie(
            @Path("movie_id") movie_id: Int,
            @Query("api_key") api_key: String?
    ): Call<MovieModel?>?

    @GET("3/tv/{series_id}?")
    fun getSerie(
            @Path("series_id") series_id: Int,
            @Query("api_key") api_key: String?
    ): Call<MovieModel?>?

    @GET("3/discover/movie")
    fun getDiscoverMovies(
            @Query("api_key") api_key: String?
    ): Call<DiscoverMovieResponse>

    @GET("3/discover/tv")
    fun getDiscoverSeries(
        @Query("api_key") api_key: String?
    ): Call<DiscoverSeriesResponse>
}