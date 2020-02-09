package su.vasic2000.kotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.data.model.NoteResult
import su.vasic2000.kotlin.data.provider.RemoteDataProvider

object NoteRepository {
    private val remoteProvider : RemoteDataProvider = object : RemoteDataProvider {
        override fun subscribeToAllNotes(): LiveData<NoteResult> {
            return MutableLiveData<NoteResult>()
        }

        override fun getNoteById(id: String): LiveData<NoteResult> {
            return MutableLiveData<NoteResult>()
        }

        override fun saveNote(note: Note): LiveData<NoteResult> {
            return MutableLiveData<NoteResult>()
        }
    }

    fun getNotes() = remoteProvider.subscribeToAllNotes()
}