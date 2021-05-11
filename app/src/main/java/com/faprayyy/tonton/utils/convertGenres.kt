package com.faprayyy.tonton.utils

import com.faprayyy.tonton.data.local.response.Genre

fun convertGenres(genres: ArrayList<Genre>) : String {
    var stringGenre = ""
    for (i in 0 until genres.size){
        stringGenre += "${genres[i].name}, "
    }
    return stringGenre.removeSuffix(", ")
}