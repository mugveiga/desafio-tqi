package com.example.desafiotqi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.desafiotqi.activity.MainActivity
import com.example.desafiotqi.utils.TestUtils.atPosition
import com.example.desafiotqi.utils.TestUtils.emptyRecyclerView
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

  @Rule
  @JvmField
  val activityRule = ActivityTestRule(MainActivity::class.java)

  @Before
  fun setup() {
    onView(withId(R.id.action_search)).perform(click())
  }

  @After
  fun tearDown() {
    onView(withId(androidx.appcompat.R.id.search_close_btn)).perform(click())
    onView(withId(androidx.appcompat.R.id.search_close_btn)).perform(click())
  }

  @Test
  fun search_valid_single() {
    onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("itau"))
    onView(withId(R.id.recyclerView)).check(matches(atPosition(0,
      hasDescendant(withText("341 - ITAÚ UNIBANCO BM S.A.")))))
  }

  @Test
  fun search_valid_multiple() {
    onView(withId(R.id.action_search)).perform(click())
    onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("banco"))
    onView(withId(R.id.recyclerView)).check(matches(atPosition(2,
      hasDescendant(withText("077 - BANCO INTER")))))
  }

  @Test
  fun search_invalid() {
    onView(withId(R.id.action_search)).perform(click())
    onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("not a bank"))
    onView(withId(R.id.recyclerView)).check(matches(emptyRecyclerView()))
  }
}
