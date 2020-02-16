package su.vasic2000.kotlin.data.provider

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.entity.User
import su.vasic2000.kotlin.data.errors.NoAuthException
import su.vasic2000.kotlin.data.model.NoteResult

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"

    }

    private val store by lazy { FirebaseFirestore.getInstance() }
    private val currentUser get() = FirebaseAuth.getInstance().currentUser
//    private val noteReference = store.collection(NOTES_COLLECTION)
//    private val noteReference by lazy {
//        store.collection(NOTES_COLLECTION)
//    }

    private val userNotesCollection: CollectionReference
        get() = currentUser?.let {                                                                                                                                                                                                                                                                                                                                      //Я копипастил код с урока и не заметил эту надпись
            store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
        } ?: throw NoAuthException()

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {

        try {
            userNotesCollection.addSnapshotListener { snapshot, e ->
                e?.let {
                    throw it
//                    value = NoteResult.Error(e)
                } ?: let {
                    snapshot?.let { snapshot ->
                        value = NoteResult.Success(snapshot.map {it.toObject(Note::class.java) })
//                    val notes = mutableListOf<Note>()
//                    for (doc: QueryDocumentSnapshot in snapshot) {
//                        notes.add(doc.toObject(Note::class.java))
//                    }
//                    value = NoteResult.Success(notes)
                    }
                }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }

    }

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
        try {
            userNotesCollection.document(id).get()
                .addOnSuccessListener { snapshot ->
                    value = NoteResult.Success(snapshot.toObject(Note::class.java))
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        try {
            userNotesCollection.document(note.id).set(note)
                .addOnSuccessListener {
                    value = NoteResult.Success(note)
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun getCurrentUser() = MutableLiveData<User?> ().apply {
        value = currentUser?.let {firebaseUser ->
            User(firebaseUser.displayName?: "", firebaseUser.email?: "")
        }
    }
}