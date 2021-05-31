package com.faprayyy.tonton

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.faprayyy.tonton.utils.EspressoIdlingResource
import com.faprayyy.tonton.view.ui.main.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest{

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
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
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(20))
    }

    @Test
    fun loadDetailMovie(){

        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.data_detail)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_genres)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_release)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_overview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_tagline)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_lang)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_rating)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.movie_vote_count)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun loadDetailSeries(){
        onView(withId(R.id.seriesFragment)).perform(click())
        onView(withId(R.id.coordinator)).perform(swipeUp())
        onView(withId(R.id.rv_series)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(13, click()))
        onView(withId(R.id.series_title)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_genres)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_release)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_overview)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_tagline)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_lang)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_rating)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.series_vote_count)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.data_detail)).perform(swipeUp())
        onView(withId(R.id.data_detail)).perform(swipeDown())

    }

    @Test
    fun loadSeries(){
        onView(withId(R.id.seriesFragment)).perform(click())
        onView(withId(R.id.rv_series)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun addAndDeleteMovieFromFavorite(){
        onView(withId(R.id.rv_movies)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.data_detail)).perform(swipeUp())
        onView(withId(R.id.data_detail)).perform(swipeDown())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.rv_movies)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.menu_favorite_item)).perform(click())
        onView(withId(R.id.rv_favorite)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.data_detail)).perform(swipeUp())
        onView(withId(R.id.data_detail)).perform(swipeDown())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.warning)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun addAndDeleteSeriesFromFavorite(){
        onView(withId(R.id.seriesFragment)).perform(click())
        onView(withId(R.id.coordinator)).perform(swipeUp())
        onView(withId(R.id.rv_series)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_series)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.data_detail)).perform(swipeUp())
        onView(withId(R.id.data_detail)).perform(swipeDown())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.coordinator)).perform(swipeDown())
        onView(withId(R.id.rv_series)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.menu_favorite_item)).perform(click())
        onView(withId(R.id.rv_favorite)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.rv_favorite)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.data_detail)).perform(swipeUp())
        onView(withId(R.id.data_detail)).perform(swipeDown())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.warning)).check(ViewAssertions.matches(isDisplayed()))
    }
}