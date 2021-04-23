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
        @SerializedName("adult")
        var adult: Boolean? = false,
        @SerializedName("popularity")
        var popularity: Double? = 0.0,
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("poster_path")
        var posterPath: String? = "",
        @SerializedName("original_language")
        var originalLanguage: String? = "",
        @SerializedName("original_title")
        var originalTitle: String? = "",
        @SerializedName("backdrop_path")
        var backdropPath: String? = "",
        @SerializedName("release_date")
        var releaseDate: String? = "",
        @SerializedName("overview")
        var overview: String? = ""
) : Parcelable