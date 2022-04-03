package com.faprayyy.themuvidb.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val NEWEST = "Newest"
    const val OLDEST = "Oldest"
    const val RANDOM = "Random"

    fun getSortedQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM favorite ")
        if (filter == NEWEST) {
            simpleQuery.append("ORDER BY idfav DESC")
        } else if (filter == OLDEST) {
            simpleQuery.append("ORDER BY idfav ASC")
        } else if (filter == RANDOM) {
            simpleQuery.append("ORDER BY RANDOM()")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}