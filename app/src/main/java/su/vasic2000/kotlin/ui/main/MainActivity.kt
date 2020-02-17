package su.vasic2000.kotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import su.vasic2000.kotlin.R
import su.vasic2000.kotlin.data.entity.Note
import su.vasic2000.kotlin.ui.base.BaseActivity
import su.vasic2000.kotlin.ui.note.NoteActivity
import su.vasic2000.kotlin.ui.splash.SplashActivity

class MainActivity : BaseActivity<List<Note>?, MainViewState>(), OutDialog.LogoutListener {

    companion object {
        fun start(context: Context) = Intent(
            context,
            MainActivity::class.java
        ).apply {
            context.startActivity(this)
        }
    }

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override val layoutRes = R.layout.activity_main
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    fun showOutDialog() {
        supportFragmentManager.findFragmentByTag(OutDialog.TAG) ?:
        OutDialog.createInstance().show(
            supportFragmentManager,
            OutDialog.TAG
        )
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

    override fun onLogOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {startSplashActivity()}
    }

    private fun startSplashActivity() {
        SplashActivity.start(this)
        finish()
    }
}
