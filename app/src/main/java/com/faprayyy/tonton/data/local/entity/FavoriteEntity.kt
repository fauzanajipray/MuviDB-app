package com.faprayyy.tonton.data.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class FavoriteEntity(
    var id : Int,
    var name : String?,
    var posterPath : String?,
    var backdropPath : String?,
    var type : String = "",
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var idfav : Int = 0,
) : Parcelable