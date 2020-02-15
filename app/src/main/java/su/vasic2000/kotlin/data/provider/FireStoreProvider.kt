package su.vasic2000.kotlin.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.model.NoteResult

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val noteReference = store.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
        noteReference.addSnapshotListener { snapshot, e ->
            e?.let {
                value = NoteResult.Error(e)
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
    }

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply {
        noteReference.document(id).get()
            .addOnSuccessListener { snapshot ->
                value = NoteResult.Success(snapshot.toObject(Note::class.java))
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        noteReference.document(note.id).set(note)
            .addOnSuccessListener {
                value = NoteResult.Success(note)
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
    }
}