package com.faprayyy.tonton.data.Response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SeriesDetailResponse(
        @SerializedName("results")
        @Expose
        var results: ArrayList<SeriesDetail> = ArrayList(),
)
