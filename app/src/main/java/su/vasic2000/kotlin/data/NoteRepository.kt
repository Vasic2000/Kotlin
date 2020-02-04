package su.vasic2000.kotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import su.vasic2000.kotlin.data.entity.Note
import java.util.*
import kotlin.collections.ArrayList

object NoteRepository {
    private val notesLiveData = MutableLiveData<List<Note>>()

    private val notes: MutableList<Note>

    init {
        notes = ArrayList()
        notes.add(
            Note("1",
                "Заметка 1",
                "Текст заметки 1. Не очень длинный, но интересный",
                Note.Color.WHITE
            )
        )

        notes.add(
            Note(
                "2",
                "Заметка 2",
                "Текст заметки 2. Не очень длинный, но интересный",
                Note.Color.YELLOW
            )
        )

        notes.add(
            Note(
                "3",
                "Заметка 3",
                "Текст заметки 3. Длинный, но не интересный",
                Note.Color.GREEN
            )
        )

        notes.add(
            Note(
                "4",
                "Заметка 4",
                "Текст заметки 4. Бесполезный, но интересный",
                Note.Color.BLUE
            )
        )

        notes.add(
            Note(
                "5",
                "Заметка 5",
                "Текст заметки 5. Просто анекдот",
                Note.Color.RED
            )
        )

        notes.add(
            Note(
                "6",
                "Заметка 6",
                "Текст заметки 6. Не очень длинный, не очень интересный",
                Note.Color.VIOLET
            )
        )

        notes.add(
            Note(
                "7",
                "Заметка 7",
                "Текст заметки 7. Жёлтая страница",
                Note.Color.PINK
            )
        )

        notes.add(
            Note(
                "8",
                "Заметка 8",
                "Текст заметки 8. Не очень длинный, совсем не интересный",
                Note.Color.ORANGE
            )
        )

        notes.add(
            Note(
                UUID.randomUUID().toString(),
                "Заметка 9",
                "Текст заметки 9. Бесполезный, но интересный, но смешная 9 заметка",
                Note.Color.PINK
            )
        )

        notes.add(
            Note(
                UUID.randomUUID().toString(),
                "Заметка 10",
                "Текст заметки 10. Бесполезный, но интересный, но смешная 10 заметка",
                Note.Color.DARKBLUE
            )
        )

        notes.add(
            Note(
                UUID.randomUUID().toString(),
                "Заметка 11",
                "Текст заметки 11. Бесполезный, но интересный, но смешная 11 заметка",
                Note.Color.PINK
            )
        )

        notes.add(
            Note(
                UUID.randomUUID().toString(),
                "Заметка 12",
                "Текст заметки 12. Ещё одна более жёлтая страница",
                Note.Color.YELLOW
            )
        )

        notes.add(
            Note(
                UUID.randomUUID().toString(),
                "Заметка 13",
                "Текст заметки 13. Не очень длинный, совсем не интересный",
                Note.Color.PINK
            )
        )

        notesLiveData.value = notes
    }
    fun saveNote(note: Note){
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note){
        for(i in notes.indices){
            if(notes[i] == note){
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }

    fun getNotes(): LiveData<List<Note>> = notesLiveData
}