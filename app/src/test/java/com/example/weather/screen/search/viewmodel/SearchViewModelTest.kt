package com.example.weather.screen.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weather.repository.interfaces.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.rules.TestRule
import org.mockito.Mockito.*


import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @Mock
    lateinit var respository : WeatherRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var uut: SearchViewModel
    @Before
    fun setUp(){
        `when`(respository.getPreviousCity()).thenReturn("samplePlace")
        uut = SearchViewModel(respository)
    }

    @Test
    fun `when setIsAccessLocationEnabled() called verify value posted to live data`() {
        uut.setIsAccessLocationEnabled(true)
        assertEquals(uut.isAccessLocationEnabled.value, true)
    }


    @Test
    fun `when setProgress() called verify value posted to live data`() {
        uut.setProgress(true)
        assertEquals(uut.dataLoading.value, true)
    }

    @Test
    fun `when setSnackBar() called verify value posted to live data`() {
        uut.setSnackBarText("data not found")
        assertEquals(uut.snackbarText.value?.getContentIfNotHandled(), "data not found")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when onSearchClicked() called to verify showDetailsByCityName is called`() = runTest{
        uut.searchTerm.value = "Allen"
        Dispatchers.setMain(Dispatchers.Unconfined)
        uut.onSearchClicked()
        verify(respository, times(1)).fetchWeatherDetailsByCity("Allen")
    }



}