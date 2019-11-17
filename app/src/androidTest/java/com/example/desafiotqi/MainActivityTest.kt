package com.example.desafiotqi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.desafiotqi.activity.MainActivity
import com.example.desafiotqi.model.Bank
import com.example.desafiotqi.network.RequestCallback
import com.example.desafiotqi.repository.BankRepository
import com.example.desafiotqi.utils.TestUtils.atPosition
import com.example.desafiotqi.utils.TestUtils.emptyRecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Type

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

  @Rule
  @JvmField
  val activityRule = ActivityTestRule(MainActivity::class.java)

  @Before
  fun setup() {
    val mockRepo: BankRepository = mockk()
    val slot = slot<RequestCallback<List<Bank>>>()
    every { mockRepo.getBanks(capture(slot)) } answers {
      slot.captured.onSuccess(testData())
    }
    activityRule.activity.viewModel.repository = mockRepo
    activityRule.activity.viewModel.refresh()
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
    onView(withId(R.id.recyclerView)).check(
      matches(
        atPosition(0, hasDescendant(withText("341 - ITAÚ UNIBANCO BM S.A.")))
      )
    )
  }

  @Test
  fun search_valid_multiple() {
    onView(withId(R.id.action_search)).perform(click())
    onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("banco"))
    onView(withId(R.id.recyclerView)).check(
      matches(
        atPosition(2, hasDescendant(withText("078 - BANCO INTER")))
      )
    )
  }

  @Test
  fun search_invalid() {
    onView(withId(R.id.action_search)).perform(click())
    onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText("not a bank"))
    onView(withId(R.id.recyclerView)).check(matches(emptyRecyclerView()))
  }

  // TODO extract this to a test resource file
  private fun testData(): List<Bank> {
    val listType: Type = object : TypeToken<ArrayList<Bank?>?>() {}.type
    return Gson().fromJson(
      "[\n" +
          "    {\n" +
          "      \"name\": \"CAIXA ECONOMICA FEDERAL\",\n" +
          "      \"code\": \"104\",\n" +
          "      \"favorite\": true,\n" +
          "      \"image\": \"https://is2-ssl.mzstatic.com/image/thumb/Purple124/v4/a2/ba/0a/a2ba0a46-1927-dc46-c3f6-32944dcb05c5/mzl.wegkcouz.png/246x0w.jpg\"\n" +
          "    },\n" +
          "    {\n" +
          "      \"name\": \"ITAÚ UNIBANCO BM S.A.\",\n" +
          "      \"code\": \"341\",\n" +
          "      \"favorite\": true,\n" +
          "      \"imageName\": \"https://www.itau.com.br/_arquivosestaticos/Itau/defaultTheme/img/logo-itau-fb.png\"\n" +
          "    },\n" +
          "    {\n" +
          "      \"name\": \"BRADESCO S.A.\",\n" +
          "      \"code\": \"237\",\n" +
          "      \"favorite\": true,\n" +
          "      \"image\": \"https://matheustesouro.rifa4.me/assets/images/bank/logo-bradesco.png\"\n" +
          "    },\n" +
          "    {\n" +
          "      \"name\": \"NU PAGAMENTOS S.A.\",\n" +
          "      \"code\": \"260\",\n" +
          "      \"favorite\": false,\n" +
          "      \"image\": \"\"\n" +
          "    },\n" +
          "    {\n" +
          "      \"name\": \"BANCO INTER\",\n" +
          "      \"code\": \"078\",\n" +
          "      \"favorite\": false,\n" +
          "      \"image\": \"\"\n" +
          "    },\n" +
          "    {\n" +
          "      \"name\": \"BANCO 1\",\n" +
          "      \"code\": \"890\",\n" +
          "      \"favorite\": false,\n" +
          "      \"image\": \"\"\n" +
          "    },\n" +
          "    {\n" +
          "      \"name\": \"CREFISA\",\n" +
          "      \"code\": \"069\",\n" +
          "      \"favorite\": false,\n" +
          "      \"image\": \"\"\n" +
          "    }\n" +
          "]\n", listType
    )
  }
}
