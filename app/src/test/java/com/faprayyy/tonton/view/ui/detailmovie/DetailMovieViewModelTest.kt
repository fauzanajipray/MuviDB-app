package com.faprayyy.tonton.view.ui.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.remote.response.MovieDetail
import com.faprayyy.tonton.utils.DataDummy.generateDetailMovie
import com.faprayyy.tonton.utils.DataDummy.generateFavEntity
import com.faprayyy.tonton.view.ui.detailmovie.DetailMovieActivity.Companion.MOVIE_TYPE
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = generateDetailMovie()
    private val movieId = dummyMovie.id
    @get:Rule

    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var muviDBRepository: MuviDBRepository

    @Mock
    private lateinit var observer: Observer<MovieDetail>

    @Mock
    private lateinit var observer2: Observer<FavoriteEntity>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(muviDBRepository)
    }

    @Test
    fun `Get Detail Movie From Api should be success `() {
        val movie = MutableLiveData<MovieDetail>()
        movie.value = dummyMovie

        `when`(muviDBRepository.getDetailMovieFromApi(movieId)).thenReturn(movie)
        viewModel.getMovieFromApi(movieId)
        val movieDetail = viewModel.getMovie().value as MovieDetail
        assertNotNull(movieDetail)
        assertEquals(dummyMovie.id, movieDetail.id)
        assertEquals(dummyMovie.title, movieDetail.title)
        assertEquals(dummyMovie.overview, movieDetail.overview)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun `Get Data Favorite From DB should be success`(){
        val dummyFavorite = generateFavEntity(MOVIE_TYPE)
        val favorite = MutableLiveData<FavoriteEntity>()
        favorite.value = dummyFavorite

        `when`(muviDBRepository.getFavoriteById(movieId, MOVIE_TYPE)).thenReturn(favorite)
        val movieFavorite = viewModel.getMovieFromDB(movieId, MOVIE_TYPE).value as FavoriteEntity

        assertNotNull(movieFavorite)
        assertEquals(dummyFavorite.id, movieFavorite.id)
        assertEquals(dummyFavorite.name, movieFavorite.name)
        assertEquals(dummyFavorite.posterPath, movieFavorite.posterPath)
        assertEquals(dummyFavorite.backdropPath, movieFavorite.backdropPath)
        assertEquals(dummyFavorite.type, movieFavorite.type)

        viewModel.getMovieFromDB(movieId, MOVIE_TYPE).observeForever(observer2)
        verify(observer2).onChanged(dummyFavorite)
    }

}