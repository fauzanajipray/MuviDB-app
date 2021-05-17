package com.faprayyy.tonton.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.di.Injection
import com.faprayyy.tonton.view.ui.detailmovie.DetailMovieViewModel
import com.faprayyy.tonton.view.ui.detailseries.DetailSeriesViewModel
import com.faprayyy.tonton.view.ui.favorite.FavoriteViewModel
import com.faprayyy.tonton.view.ui.movie.MoviesViewModel
import com.faprayyy.tonton.view.ui.series.SeriesViewModel

class ViewModelFactory private constructor(private val mMuviDBRepository: MuviDBRepository): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    ViewModelFactory(Injection.provideRepository(context)).apply { instance = this }
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SeriesViewModel::class.java) -> {
                SeriesViewModel(mMuviDBRepository) as T
            }
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                MoviesViewModel(mMuviDBRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(mMuviDBRepository) as T
            }
            modelClass.isAssignableFrom(DetailSeriesViewModel::class.java) -> {
                DetailSeriesViewModel(mMuviDBRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(mMuviDBRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}