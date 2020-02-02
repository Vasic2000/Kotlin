package su.vasic2000.kotlin.data

import su.vasic2000.kotlin.data.entity.Note

object NoteRepository {
    private val notes: List<Note>

    init {
        notes = ArrayList()
        notes.add(
            Note(
                "Заметка 1",
                "Текст заметки 1. Не очень длинный, но интересный",
                0xfff06292.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 2",
                "Текст заметки 2. Не очень длинный, но интересный",
                0xff9575cd.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 3",
                "Текст заметки 3. Длинный, но не интересный",
                0xff64b5f6.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 4",
                "Текст заметки 4. Бесполезный, но интересный",
                0xff4db6ac.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 5",
                "Текст заметки 5. Просто анекдот",
                0xffb2ff59.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 6",
                "Текст заметки 6. Не очень длинный, не очень интересный",
                0xfff06292.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 7",
                "Текст заметки 7. Жёлтая страница",
                0xffffee00.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 8",
                "Текст заметки 8. Не очень длинный, совсем не интересный",
                0xff5555ff.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 9",
                "Текст заметки 9. Бесполезный, но интересный, но смешная 9 заметка",
                0xff4db6ac.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 10",
                "Текст заметки 10. Бесполезный, но интересный, но смешная 10 заметка",
                0xffb2ff59.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 11",
                "Текст заметки 11. Бесполезный, но интересный, но смешная 11 заметка",
                0xfff06292.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 12",
                "Текст заметки 12. Ещё одна более жёлтая страница",
                0xffffff55.toInt()
            )
        )

        notes.add(
            Note(
                "Заметка 13",
                "Текст заметки 13. Не очень длинный, совсем не интересный",
                0xff5555ff.toInt()
            )
        )
    }


    fun getNotes(): List<Note> = notes
}