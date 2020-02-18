package su.vasic2000.kotlin.ui.note

import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) :
    BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}
