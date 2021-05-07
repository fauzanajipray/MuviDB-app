package com.faprayyy.tonton.data.Response

import com.faprayyy.tonton.data.local.response.Genre
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SeriesDetail(
        @SerializedName("backdrop_path")
    @Expose
    var backdropPath : String? = "",
        @SerializedName("first_air_date")
    @Expose
    var firstAirDate : String? = "",
        @SerializedName("genres")
    @Expose
    var genres : ArrayList<Genre> = ArrayList(),
        @SerializedName("homepage")
    @Expose
    var homepage : String? = "",
        @SerializedName("id")
    @Expose
    var id : Int? = -2,
        @SerializedName("in_production")
    @Expose
    var inProduction : Boolean? = false,
        @SerializedName("last_air_date")
    @Expose
    var lastAirDate : String? = "",
        @SerializedName("name")
    @Expose
    var name : String? = "",
        @SerializedName("number_of_episodes")
    @Expose
    var numberOfEpisodes : Int? = 0,
        @SerializedName("number_of_seasons")
    @Expose
    var numberOfSeasons : Int? = 0,
        @SerializedName("original_language")
    @Expose
    var originalLanguage : String? = "",
        @SerializedName("original_name")
    @Expose
    var originalName : String? = "",
        @SerializedName("overview")
    @Expose
    var overview : String? = "",
        @SerializedName("popularity")
    @Expose
    var popularity : Double? = 0.0,
        @SerializedName("poster_path")
    @Expose
    var posterPath : String? = "",
        @SerializedName("status")
    @Expose
    var status : String? = "",
        @SerializedName("tagline")
    @Expose
    var tagline : String? = "",
        @SerializedName("type")
    @Expose
    var type : String? = "",
        @SerializedName("vote_average")
    @Expose
    var voteAverage : Double? = 0.0,
        @SerializedName("vote_count")
    @Expose
    var voteCount : Int? = 0
)

