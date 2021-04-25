package com.faprayyy.tonton.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesModel (
        @SerializedName("id")
        var id: Int = -1,
        @SerializedName("vote_count")
        var voteCount: Int? = 0,
        @SerializedName("vote_average")
        var voteAverage: Double? = 0.0,
        @SerializedName("popularity")
        var popularity: Double? = 0.0,
        @SerializedName("name")
        var name: String? = "",
        @SerializedName("poster_path")
        var posterPath: String? = "",
        @SerializedName("original_language")
        var originalLanguage: String? = "",
        @SerializedName("original_name")
        var originalName: String? = "",
        @SerializedName("backdrop_path")
        var backdropPath: String? = "",
        @SerializedName("first_air_date")
        var firstAirDate: String? = "",
        @SerializedName("overview")
        var overview: String? = ""
) : Parcelable