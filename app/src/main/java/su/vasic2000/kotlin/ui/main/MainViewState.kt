package su.vasic2000.kotlin.ui.main

import androidx.lifecycle.LiveData
import su.vasic2000.kotlin.data.entity.Note

class MainViewState(val notes: LiveData<List<Note>>)
