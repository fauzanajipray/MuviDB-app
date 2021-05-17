package com.faprayyy.tonton.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
        @SerializedName("results")
    @Expose
    var results: ArrayList<MovieDetail> = ArrayList(),
)