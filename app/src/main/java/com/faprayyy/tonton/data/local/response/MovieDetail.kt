package com.faprayyy.tonton.data.Response

import com.faprayyy.tonton.data.local.response.Genre
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDetail(
        @SerializedName("adult")
    @Expose
    var adult: Boolean? = false,
        @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = "",
        @SerializedName("budget")
    @Expose
    var budget: Int? = 0,
        @SerializedName("genres")
    @Expose
    var  genres : ArrayList<Genre> = ArrayList(),
        @SerializedName("homepage")
    @Expose
    var homepage: String? = "",
        @SerializedName("id")
    @Expose
    var id: Int? = -2,
        @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = "",
        @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = "",
        @SerializedName("original_title")
    @Expose
    var originalTitle: String? = "",
        @SerializedName("overview")
    @Expose
    var overview: String? = "",
        @SerializedName("popularity")
    @Expose
    var popularity: String? = "",
        @SerializedName("poster_path")
    @Expose
    var posterPath: String? = "",
        @SerializedName("release_date")
    @Expose
    var releaseDate: String? = "",
        @SerializedName("revenue")
    @Expose
    var revenue: Int? = 0,
        @SerializedName("runtime")
    @Expose
    var runtime: Int? = 0,
        @SerializedName("status")
    @Expose
    var status: String? = "",
        @SerializedName("tagline")
    @Expose
    var tagline: String? = "",
        @SerializedName("title")
    @Expose
    var title: String? = "",
        @SerializedName("video")
    @Expose
    var video: Boolean? = false,
        @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = 0.0,
        @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = 0,
)