package com.faprayyy.tonton.view.ui.detailseries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.faprayyy.tonton.data.MuviDBRepository
import com.faprayyy.tonton.data.local.entity.FavoriteEntity
import com.faprayyy.tonton.data.remote.response.SeriesDetail
import com.faprayyy.tonton.utils.DataDummy
import com.faprayyy.tonton.utils.DataDummy.generateDetailSeries
import com.faprayyy.tonton.view.ui.detailmovie.DetailMovieActivity
import com.faprayyy.tonton.view.ui.detailseries.DetailSeriesActivity.Companion.SERIES_TYPE
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
class DetailSeriesViewModelTest {

    private lateinit var viewModel: DetailSeriesViewModel
    private val dummySeries = generateDetailSeries()
    private val seriesId = dummySeries.id
    @get:Rule

    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var muviDBRepository: MuviDBRepository

    @Mock
    private lateinit var observer: Observer<SeriesDetail>

    @Mock
    private lateinit var observer2: Observer<FavoriteEntity>

    @Before
    fun setUp() {
        viewModel = DetailSeriesViewModel(muviDBRepository)
    }

    @Test
    fun `Get Detail Series From Api should be success `() {
        val series = MutableLiveData<SeriesDetail>()
        series.value = dummySeries

        `when`(muviDBRepository.getDetailSeriesFromApi(seriesId)).thenReturn(series)
        viewModel.getSeriesFromApi(seriesId)
        val seriesDetail = viewModel.getSeries().value as SeriesDetail
        assertNotNull(seriesDetail)
        assertEquals(dummySeries.id, seriesDetail.id)
        assertEquals(dummySeries.name, seriesDetail.name)
        assertEquals(dummySeries.overview, seriesDetail.overview)

        viewModel.getSeries().observeForever(observer)
        verify(observer).onChanged(dummySeries)
    }

    @Test
    fun `Get Data Favorite From DB should be success`(){
        val dummyFavorite = DataDummy.generateFavEntity(SERIES_TYPE)
        val favorite = MutableLiveData<FavoriteEntity>()
        favorite.value = dummyFavorite

        `when`(muviDBRepository.getFavoriteById(seriesId, SERIES_TYPE)).thenReturn(favorite)
        val seriesFavorite = viewModel.getSeriesFromDB(seriesId, SERIES_TYPE).value as FavoriteEntity

        assertNotNull(seriesFavorite)
        assertEquals(dummyFavorite.id, seriesFavorite.id)
        assertEquals(dummyFavorite.name, seriesFavorite.name)
        assertEquals(dummyFavorite.posterPath, seriesFavorite.posterPath)
        assertEquals(dummyFavorite.backdropPath, seriesFavorite.backdropPath)
        assertEquals(dummyFavorite.type, seriesFavorite.type)

        viewModel.getSeriesFromDB(seriesId, DetailMovieActivity.MOVIE_TYPE).observeForever(observer2)
        verify(observer2).onChanged(dummyFavorite)
    }

}