package su.vasic2000.kotlin.main

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import su.vasic2000.kotlin.R
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.ui.main.MainActivity
import su.vasic2000.kotlin.ui.main.MainViewModel
import su.vasic2000.kotlin.ui.main.MainViewState
import su.vasic2000.kotlin.ui.main.NotesRVAdapter

class MainActivityTest {
    @get:Rule
    val activityTestRule = IntentsTestRule(MainActivity::class.java, true, false)

    private val model: MainViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<MainViewState>()

    private val testNotes = listOf(
        Note("1", "title1", "text1"),
        Note("2", "title2", "text2"),
        Note("3", "title3", "text3")
    )

    @Test
    fun check_data_is_displayed(){
        Espresso.onView(ViewMatchers.withId(R.id.rv_notes)).perform(scrollToPosition<NotesRVAdapter.ViewHolder>(1))
        Espresso.onView(ViewMatchers.withText(testNotes[1].text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Before
    fun setUp() {
        StandAloneContext.loadKoinModules(
            listOf(
                module {
                    viewModel(override = true) { model }
                }
            )
        )

        every { model } returns viewStateLiveData
        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(MainViewState(notes = testNotes))
    }

    @After
    fun tearDown(){
        StandAloneContext.stopKoin()
    }
}