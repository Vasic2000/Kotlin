package su.vasic2000.kotlin.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.*

import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.errors.NoAuthException
import su.vasic2000.kotlin.data.model.NoteResult

class FireStoreProviderTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDB = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val testNotes = listOf(Note("1"), Note("2"), Note("3"))

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val provider = FireStoreProvider(mockAuth, mockDB)

    @Test
    fun `should throw NoAuthException if no auth`() {
        var result: Any? = null
        every { mockAuth.currentUser } returns null
        provider.subscribeToAllNotes().observeForever {
            result = (it as? NoteResult.Error)?.error
        }
        Assert.assertTrue(result is NoAuthException)
    }

    @Test
    fun `saveNote calls set`() {
        val mockDocumentReference = mockk<DocumentReference>()

        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        provider.saveNote(testNotes[0])
        verify(exactly = 1) { mockDocumentReference.set(testNotes[0])
        }

        every { mockResultCollection.document(testNotes[2].id) } returns mockDocumentReference
        provider.saveNote(testNotes[2])
        verify(exactly = 1) { mockDocumentReference.set(testNotes[2])
        }

        every { mockResultCollection.document(testNotes[1].id) } returns mockDocumentReference
        provider.saveNote(testNotes[1])
        verify() { mockDocumentReference.set(testNotes[1])
        }
    }


    companion object {
        @BeforeClass
        fun setupClass(){}

        @AfterClass
        fun tearDownClass(){}
    }

    @Before
    fun setup(){
        clearAllMocks()
        every{mockAuth.currentUser} returns mockUser
        every { mockUser.uid } returns ""
        every { mockDB.collection(any()).document(any()).collection(any()) } returns mockResultCollection

        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]
    }

    @After
    fun tearDown(){}
}