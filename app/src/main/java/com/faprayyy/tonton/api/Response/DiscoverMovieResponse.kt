package com.faprayyy.tonton.api.Response

import com.faprayyy.tonton.data.MovieModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DiscoverMovieResponse(
    @SerializedName("page")
    @Expose
    var page: Int = 0,
        @SerializedName("results")
    @Expose
    var results: ArrayList<MovieModel> = ArrayList(),
        @SerializedName("total_pages")
    @Expose
    var totalPages: Int = 0,
        @SerializedName("total_results")
    @Expose
    var totalResults : Int = 0
)
