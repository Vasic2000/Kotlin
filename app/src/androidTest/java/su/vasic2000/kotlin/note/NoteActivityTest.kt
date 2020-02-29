package su.vasic2000.kotlin.note

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.intent.rule.IntentsTestRule
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import su.vasic2000.kotlin.ui.note.NoteActivity
import su.vasic2000.kotlin.ui.note.NoteViewModel
import su.vasic2000.kotlin.ui.note.NoteViewState

class NoteActivityTest {
    @get:Rule
    val activityTestRule = IntentsTestRule(NoteActivity::class.java, true, false)
    private val model: NoteViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<NoteViewState>()

    @Before
    fun setUp() {
        StandAloneContext.loadKoinModules(
            listOf(
                module {
                    viewModel(override = true) { model }
                }
            )
        )

        every { model.getViewState() } returns viewStateLiveData
        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(NoteViewState())
    }

    @After
    fun tearDown(){
        StandAloneContext.stopKoin()
    }
}