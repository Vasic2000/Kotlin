package su.vasic2000.kotlin.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import su.vasic2000.kotlin.R
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.ui.note.NoteActivity

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        rv_notes.layoutManager = GridLayoutManager(this, 2)

        adapter = NotesRVAdapter(fun(note: Note) {
            NoteActivity.start(this, note)
        })

        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer {
            it?.let {
                adapter.notes = it.notes
            }
        })

        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

}
