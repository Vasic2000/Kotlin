package su.vasic2000.kotlin.data.provider

import androidx.lifecycle.LiveData
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.entity.User
import su.vasic2000.kotlin.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>
}