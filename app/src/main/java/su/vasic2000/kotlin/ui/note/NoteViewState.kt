package su.vasic2000.kotlin.ui.note

import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)
