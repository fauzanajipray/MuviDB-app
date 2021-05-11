package com.faprayyy.tonton.data.local.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DiscoverMovieResponse(
    @SerializedName("results")
    @Expose
    var results: ArrayList<MovieModel> = ArrayList()
)
