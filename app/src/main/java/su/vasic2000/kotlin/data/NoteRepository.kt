package su.vasic2000.kotlin.data

import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.provider.RemoteDataProvider
class NoteRepository(val remoteProvider: RemoteDataProvider) {

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun deleteNote(id: String) = remoteProvider.deleteNote(id)
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
}