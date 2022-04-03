package com.faprayyy.themuvidb.view.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.faprayyy.themuvidb.data.MuviDBRepository
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.data.remote.response.MovieDetail
import com.faprayyy.themuvidb.data.remote.response.SeriesDetail
import com.faprayyy.themuvidb.utils.DataDummy
import com.faprayyy.themuvidb.utils.LiveDataTestUtils
import com.faprayyy.themuvidb.view.ui.detail.detailmovie.DetailMovieActivity.Companion.MOVIE_TYPE
import com.faprayyy.themuvidb.view.ui.detail.detailseries.DetailSeriesActivity.Companion.SERIES_TYPE
import com.faprayyy.themuvidb.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class DetailViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyMovieDetail = DataDummy.generateDetailMovie()
    private val movieId = dummyMovieDetail.id
    private val dummySeriesDetail = DataDummy.generateDetailSeries()
    private val seriesId = dummySeriesDetail.id
    private val repository = Mockito.mock(MuviDBRepository::class.java)
    private val viewModel =  DetailViewModel(repository)
    private val dummyFavEntity = DataDummy.generateFavEntity(MOVIE_TYPE)
    private val dummyFavEntity2 = DataDummy.generateFavEntity(SERIES_TYPE)

    @Test
    fun `Get Detail Movie From Api should be success `() {
        val movieDetailFactory = MutableLiveData<Resource<MovieDetail>>()
        movieDetailFactory.value = Resource.success(dummyMovieDetail)

        Mockito.`when`(repository.getDetailMovieFromApi(movieId)).thenReturn(movieDetailFactory)
        val data = viewModel.getDetailMovie(movieId)
        val movieDetail = LiveDataTestUtils.getValue(data)

        Mockito.verify(repository).getDetailMovieFromApi(movieId)
        assertNotNull(movieDetail)
        assertEquals(dummyMovieDetail, movieDetail.data)
    }

    @Test
    fun `Get Detail Series From Api should be success `() {
        val seriesDetailFactory = MutableLiveData<Resource<SeriesDetail>>()
        seriesDetailFactory.value = Resource.success(dummySeriesDetail)

        Mockito.`when`(repository.getDetailSeriesFromApi(seriesId)).thenReturn(seriesDetailFactory)
        val data = viewModel.getDetailSeries(seriesId)
        val seriesDetail = LiveDataTestUtils.getValue(data)

        Mockito.verify(repository).getDetailSeriesFromApi(seriesId)
        assertNotNull(seriesDetail)
        assertEquals(dummySeriesDetail, seriesDetail.data)
    }

    @Test
    fun `set favorite should return success`(){
        viewModel.setFavorite(dummyFavEntity)
        Mockito.verify(repository).setFavorite(dummyFavEntity)
    }

    @Test
    fun `delete favorite should return success`(){
        val id = dummyFavEntity.id
        val type = dummyFavEntity.type
        viewModel.deleteFavorite(id, type)
        Mockito.verify(repository).deleteFavorite(id, type)
    }

    @Test
    fun `Get Movie From DB Favorite should be success`(){

        val movieFavoriteFactory = MutableLiveData<FavoriteEntity>()
        movieFavoriteFactory.value = dummyFavEntity

        val id = dummyFavEntity.id
        val type = dummyFavEntity.type

        Mockito.`when`(repository.getFavoriteById(id, type)).thenReturn(movieFavoriteFactory)

        val movieFavorite = LiveDataTestUtils.getValue(viewModel.getMovieFromDB(id, type))

        Mockito.verify(repository).getFavoriteById(id, type)
        assertNotNull(movieFavorite)
        assertEquals(dummyFavEntity, movieFavorite)
    }

    @Test
    fun `Get Series From DB Favorite should be success`(){

        val movieFavoriteFactory = MutableLiveData<FavoriteEntity>()
        movieFavoriteFactory.value = dummyFavEntity2

        val id = dummyFavEntity2.id
        val type = dummyFavEntity2.type

        Mockito.`when`(repository.getFavoriteById(id, type)).thenReturn(movieFavoriteFactory)

        val movieFavorite = LiveDataTestUtils.getValue(viewModel.getMovieFromDB(id, type))

        Mockito.verify(repository).getFavoriteById(id, type)
        assertNotNull(movieFavorite)
        assertEquals(dummyFavEntity2, movieFavorite)
    }

}