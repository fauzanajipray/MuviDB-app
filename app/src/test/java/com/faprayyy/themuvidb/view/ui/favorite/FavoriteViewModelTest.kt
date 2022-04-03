package com.faprayyy.themuvidb.view.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.faprayyy.themuvidb.data.MuviDBRepository
import com.faprayyy.themuvidb.data.local.entity.FavoriteEntity
import com.faprayyy.themuvidb.utils.DataDummy
import com.faprayyy.themuvidb.utils.PagedTestDataSources
import com.faprayyy.themuvidb.utils.SortUtils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest{
    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var muviDBRepository: MuviDBRepository

    @Mock
    private lateinit var observer: Observer<PagedList<FavoriteEntity>>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(muviDBRepository)
    }

    @Test
    fun getFavoriteList(){
        val expected = MutableLiveData<PagedList<FavoriteEntity>>()
        expected.value = PagedTestDataSources.snapshotFavoriteEntity(DataDummy.generateFavEntityList())

        Mockito.`when`(muviDBRepository.getFavorites(SortUtils.NEWEST)).thenReturn(expected)

        viewModel.getFavoriteListFromDB().observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteListFromDB().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)
    }
}