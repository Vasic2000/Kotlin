package su.vasic2000.kotlin.ui.main

import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.ui.base.BaseViewState

class MainViewState(val notes: List<Note>? = null, error: Throwable? = null): BaseViewState<List<Note>?> (notes, error)

