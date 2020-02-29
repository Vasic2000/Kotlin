package su.vasic2000.kotlin.data.provider

import kotlinx.coroutines.channels.ReceiveChannel
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.entity.User
import su.vasic2000.kotlin.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<NoteResult>
    suspend fun getNoteById(id: String): Note?
    suspend fun saveNote(note: Note): Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(noteId: String)

//    fun subscribeToAllNotes(): LiveData<NoteResult>
//    fun getNoteById(id: String): LiveData<NoteResult>
//    fun saveNote(note: Note) : LiveData<NoteResult>
//    fun getCurrentUser(): LiveData<User?>
//    fun deleteNote(noteId: String): MutableLiveData<NoteResult>
}