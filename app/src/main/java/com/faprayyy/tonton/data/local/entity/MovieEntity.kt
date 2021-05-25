package com.faprayyy.tonton.data.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
class MovieEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name ="id")
    var id: Int,

    @ColumnInfo(name ="vote_average")
    var voteAverage: Double,

    @ColumnInfo(name ="popularity")
    var popularity: Double,

    @ColumnInfo(name ="title")
    var title: String,

    @ColumnInfo(name ="poster_path")
    var posterPath: String,

    @ColumnInfo(name ="original_language")
    var originalLanguage: String,

    @ColumnInfo(name ="original_title")
    var originalTitle: String,

    @ColumnInfo(name ="backdrop_path")
    var backdropPath: String,

    @ColumnInfo(name ="release_date")
    var releaseDate: String,

    @ColumnInfo(name ="overview")
    var overview: String
) : Parcelable