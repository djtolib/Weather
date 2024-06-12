package com.tolib.weather

import android.view.View
import android.widget.TextView
import androidx.fragment.app.testing.launchFragmentInContainer
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tolib.weather.ui.view.WeatherFragment
import org.hamcrest.Matcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.tolib.weather", appContext.packageName)
    }

    @Test
    fun testDialogIsOpened() {
        val scenario = launchFragmentInContainer<WeatherFragment>()
        onView(withId(R.id.settings)).perform(click())
        onView(withText("Settings")).check(matches(isDisplayed()))
        scenario.close()
    }

    fun testSwipe(){
        val scenario = launchFragmentInContainer<WeatherFragment>()
        onView(withId(R.id.recyclerview)).perform(swipeDown())
        onView(withId(R.id.todayTemp)).check(matches(hasText()))
        scenario.close()
    }
    private fun hasText(): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View): Boolean {
                return (view as? TextView)?.text?.isNotEmpty() ?: false
            }

            override fun describeTo(description: Description) {
                description.appendText("TextView should contain text")
            }
        }
    }
}
