package com.faprayyy.tonton.utils

import com.faprayyy.tonton.data.remote.response.Genre

object Converter{
    fun convertGenres(genres: ArrayList<Genre>) : String {
        var stringGenre = ""
        for (i in 0 until genres.size){
            stringGenre += "${genres[i].name}, "
        }
        return stringGenre.removeSuffix(", ")
    }
}
