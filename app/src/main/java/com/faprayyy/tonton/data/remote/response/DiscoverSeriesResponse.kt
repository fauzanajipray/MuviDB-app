package com.faprayyy.tonton.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DiscoverSeriesResponse (
    @SerializedName("results")
    @Expose
    var results: ArrayList<SeriesModel> = ArrayList()
)
