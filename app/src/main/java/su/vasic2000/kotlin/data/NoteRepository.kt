package su.vasic2000.kotlin.data

import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.provider.RemoteDataProvider
class NoteRepository(val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    suspend fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    suspend fun deleteNote(id: String) = remoteProvider.deleteNote(id)
    suspend fun saveNote(note: Note) = remoteProvider.saveNote(note)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
}