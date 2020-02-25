package su.vasic2000.kotlin.data.provider

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.entity.User
import su.vasic2000.kotlin.data.errors.NoAuthException
import su.vasic2000.kotlin.data.model.NoteResult

class FireStoreProvider(
    private val fairbaseAuth: FirebaseAuth,
    private val store: FirebaseFirestore) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"

    }
    private val currentUser get() = fairbaseAuth.currentUser
    private val userNotesCollection: CollectionReference
        get() = currentUser?.let {   //Я НЕ копипастил код с урока и заметил эту надпись
            store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
        } ?: throw NoAuthException()

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {

        try {
            userNotesCollection.addSnapshotListener { snapshot, e ->
                e?.let {
                    throw it
                } ?: let {
                    snapshot?.let { snapshot ->
                        value = NoteResult.Success(snapshot.documents.map {it.toObject(Note::class.java) })
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


    override fun deleteNote(noteId: String) = MutableLiveData<NoteResult>().apply {
        try {
            userNotesCollection.document(noteId).delete()
                .addOnSuccessListener {
                    value = NoteResult.Success(null)
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