package su.vasic2000.kotlin.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
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

    @Test
    fun `deleteNote calls document delete`() {
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockResultCollection.document(testNotes[0].id) } returns mockDocumentReference
        provider.deleteNote(testNotes[0].id)
        verify(exactly = 1) { mockDocumentReference.delete() }
    }

    @Test
    fun `subscribe to all notes returns notes`() {
        var result: List<Note>? = null
        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        every { mockSnapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()
        provider.subscribeToAllNotes().observeForever{
            result = (it as? NoteResult.Success<List<Note>>)?.data
        }
        slot.captured.onEvent(mockSnapshot, null)
        Assert.assertEquals(testNotes, result)
    }

    @Test
    fun `getNoteByID return note`() {
        var noteResult: Note? = null
        val slot = slot<OnSuccessListener<DocumentSnapshot>>()
        every {
            mockResultCollection.document(testNotes[0].id).get().addOnSuccessListener(capture(slot))
        } returns mockk()

        provider.getNoteById("1").observeForever {
            noteResult = (it as? NoteResult.Success<Note>)?.data
        }
        slot.captured.onSuccess(mockDocument1)
        Assert.assertEquals(testNotes[0], noteResult)
        Assert.assertNotEquals(testNotes[1], noteResult)
        Assert.assertNotEquals(testNotes[2], noteResult)

        slot.captured.onSuccess(mockDocument3)
        provider.getNoteById("3").observeForever {
            noteResult = (it as? NoteResult.Success<Note>)?.data
        }
        Assert.assertNotEquals(testNotes[2], noteResult)
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
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every { mockDB.collection(any()).document(any()).collection(any()) } returns mockResultCollection

        every { mockDocument1.toObject(Note::class.java) } returns testNotes[0]
        every { mockDocument2.toObject(Note::class.java) } returns testNotes[1]
        every { mockDocument3.toObject(Note::class.java) } returns testNotes[2]

    }

    @After
    fun tearDown(){}
}