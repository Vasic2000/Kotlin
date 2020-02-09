package su.vasic2000.kotlin.data

import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.provider.FireStoreProvider
import su.vasic2000.kotlin.data.provider.RemoteDataProvider

object NoteRepository {
    private val remoteProvider : RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()

    fun getNoteById(id: String) = remoteProvider.getNoteById(id)

    fun saveNote(note: Note) {
        remoteProvider.saveNote(note)
    }
}