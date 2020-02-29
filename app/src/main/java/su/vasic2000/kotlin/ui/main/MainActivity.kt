package su.vasic2000.kotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import su.vasic2000.kotlin.R
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.ui.base.BaseActivity
import su.vasic2000.kotlin.ui.note.NoteActivity
import su.vasic2000.kotlin.ui.splash.SplashActivity

class MainActivity : BaseActivity<List<Note>?>() {

    companion object {
        fun start(context: Context) = Intent(
            context,
            MainActivity::class.java
        ).apply {
            context.startActivity(this)
        }
    }

    override val model: MainViewModel by viewModel()
    override val layoutRes = R.layout.activity_main
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter(fun(note: Note) {
            NoteActivity.start(this, note.id)
        })

        rv_notes.adapter = adapter

        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = MenuInflater(this)
        .inflate(R.menu.menu, menu).let { true }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.logOut -> showOutDialog()?.let { true }
        else -> false
    }

    private fun showOutDialog() {
        alert {
            titleResource = R.string.exit_title
            messageResource = R.string.logout_message
            positiveButton(R.string.positive_button) {onLogOut()}
            negativeButton(R.string.negative_button) {dialog ->  dialog.dismiss()}
        }.show()
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

    private fun onLogOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {startSplashActivity()}
    }

    private fun startSplashActivity() {
        SplashActivity.start(this)
        finish()
    }
}
