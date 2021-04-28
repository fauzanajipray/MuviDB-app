package com.faprayyy.tonton

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Before
import androidx.test.espresso.contrib.RecyclerViewActions
import com.faprayyy.tonton.data.MovieModel
import com.faprayyy.tonton.data.Response.*
import com.faprayyy.tonton.data.SeriesModel
import com.faprayyy.tonton.utils.getListMovieDetail
import com.faprayyy.tonton.utils.getListSeriesDetail
import com.faprayyy.tonton.utils.getMovieResponse
import com.faprayyy.tonton.utils.getSeriesResponse
import com.google.gson.Gson
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    private lateinit var listMovie: ArrayList<MovieModel>
    private lateinit var listSeries: ArrayList<SeriesModel>
    private lateinit var movieDetail: MovieDetail
    private lateinit var seriesDetail: SerieDetail

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        val obj = Gson().fromJson(getMovieResponse(), DiscoverMovieResponse::class.java)
        listMovie = obj.results

        val obj2 = Gson().fromJson(getSeriesResponse(), DiscoverSeriesResponse::class.java)
        listSeries = obj2.results

        val obj3 = Gson().fromJson(getListMovieDetail(), MovieDetailResponse::class.java)
        movieDetail = obj3.results[0]

        val obj4 = Gson().fromJson(getListSeriesDetail(), SerieDetailResponse::class.java)
        seriesDetail = obj4.results[0]
    }

    @Test
    fun loadMovie(){
        onView(withId(R.id.recyclerView)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listMovie.size))
    }

    @Test
    fun loadDetailMovie(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.movie_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_title)).check(ViewAssertions.matches(withText(movieDetail.title)))
        onView(withId(R.id.movie_release)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_release)).check(ViewAssertions.matches(withText("Release date ${movieDetail.releaseDate}")))
        onView(withId(R.id.movie_overview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_overview)).check(ViewAssertions.matches(withText(movieDetail.overview)))
        onView(withId(R.id.movie_tagline)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_tagline)).check(ViewAssertions.matches(withText(movieDetail.tagline)))
        onView(withId(R.id.movie_lang)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_lang)).check(ViewAssertions.matches(withText(movieDetail.originalLanguage)))
        onView(withId(R.id.movie_rating)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_rating)).check(ViewAssertions.matches(withText(movieDetail.voteAverage.toString())))
    }

    @Test
    fun loadDetailSeries(){
        onView(withId(R.id.seriesFragment)).perform(click())
        onView(withId(R.id.recyclerView)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.series_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_title)).check(ViewAssertions.matches(withText(seriesDetail.name)))
        onView(withId(R.id.series_release)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_release)).check(ViewAssertions.matches(withText("First air date ${seriesDetail.firstAirDate}")))
        onView(withId(R.id.series_overview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_overview)).check(ViewAssertions.matches(withText(seriesDetail.overview)))
        onView(withId(R.id.series_tagline)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_tagline)).check(ViewAssertions.matches(withText(seriesDetail.tagline)))
        onView(withId(R.id.series_lang)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_lang)).check(ViewAssertions.matches(withText(seriesDetail.originalLanguage)))
        onView(withId(R.id.series_rating)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_rating)).check(ViewAssertions.matches(withText(seriesDetail.voteAverage.toString())))

    }

    @Test
    fun loadSeries(){
        onView(withId(R.id.seriesFragment)).perform(click())
        onView(withId(R.id.recyclerView)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(listSeries.size))
    }
}