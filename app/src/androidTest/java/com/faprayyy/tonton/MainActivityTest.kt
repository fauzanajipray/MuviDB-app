package com.faprayyy.tonton

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.faprayyy.tonton.data.local.response.MovieDetail
import com.faprayyy.tonton.data.local.response.MovieModel
import com.faprayyy.tonton.data.local.response.SeriesDetail
import com.faprayyy.tonton.data.local.response.SeriesModel
import com.faprayyy.tonton.utils.DataDummy.generateMoviesDetailList
import com.faprayyy.tonton.utils.DataDummy.generateMoviesList
import com.faprayyy.tonton.utils.DataDummy.generateSeriesDetailList
import com.faprayyy.tonton.utils.DataDummy.generateSeriesList
import com.faprayyy.tonton.utils.EspressoIdlingResource
import com.faprayyy.tonton.utils.convertGenres
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest{

    private lateinit var listMovie: ArrayList<MovieModel>
    private lateinit var listSeries: ArrayList<SeriesModel>
    private lateinit var movieDetail: MovieDetail
    private lateinit var seriesDetail: SeriesDetail

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        listMovie = generateMoviesList()
        listSeries = generateSeriesList()
        movieDetail = generateMoviesDetailList()[0]
        seriesDetail = generateSeriesDetailList()[0]

        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovie(){
        onView(withId(R.id.rv_movies)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listMovie.size))
    }

    @Test
    fun loadDetailMovie(){
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.movie_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_title)).check(ViewAssertions.matches(withText(movieDetail.title)))
        onView(withId(R.id.movie_genres)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_genres)).check(ViewAssertions.matches(withText(convertGenres(movieDetail.genres))))
        onView(withId(R.id.movie_release)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_release)).check(ViewAssertions.matches(withText(movieDetail.releaseDate)))
        onView(withId(R.id.movie_overview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_overview)).check(ViewAssertions.matches(withText(movieDetail.overview)))
        onView(withId(R.id.movie_tagline)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_tagline)).check(ViewAssertions.matches(withText(movieDetail.tagline)))
        onView(withId(R.id.movie_lang)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_lang)).check(ViewAssertions.matches(withText(movieDetail.originalLanguage?.toUpperCase())))
        onView(withId(R.id.movie_rating)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_vote_count)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_vote_count)).check(ViewAssertions.matches(withText("(${ movieDetail.voteCount } voters)")))
    }

    @Test
    fun loadDetailSeries(){
        onView(withId(R.id.seriesFragment)).perform(click())
        onView(withId(R.id.rv_series)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.series_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_title)).check(ViewAssertions.matches(withText(seriesDetail.name)))
        onView(withId(R.id.series_genres)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_genres)).check(ViewAssertions.matches(withText(convertGenres(seriesDetail.genres))))
        onView(withId(R.id.series_release)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_release)).check(ViewAssertions.matches(withText(seriesDetail.firstAirDate)))
        onView(withId(R.id.series_overview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_overview)).check(ViewAssertions.matches(withText(seriesDetail.overview)))
        onView(withId(R.id.series_tagline)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_tagline)).check(ViewAssertions.matches(withText(seriesDetail.tagline)))
        onView(withId(R.id.series_lang)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_lang)).check(ViewAssertions.matches(withText(seriesDetail.originalLanguage?.toUpperCase())))
        onView(withId(R.id.series_rating)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_vote_count)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_vote_count)).check(ViewAssertions.matches(withText("(${ seriesDetail.voteCount } voters)")))
    }

    @Test
    fun loadSeries(){
        onView(withId(R.id.seriesFragment)).perform(click())
        onView(withId(R.id.rv_series)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_series)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSeries.size))
    }
}