package com.faprayyy.themuvidb.utils

import com.faprayyy.themuvidb.data.remote.response.Genre

object Converter{
    fun convertGenres(genres: ArrayList<Genre>) : String {
        var stringGenre = ""
        for (i in 0 until genres.size){
            stringGenre += "${genres[i].name}, "
        }
        return stringGenre.removeSuffix(", ")
    }
}
