package su.vasic2000.kotlin.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_note.*
import su.vasic2000.kotlin.R
import su.vasic2000.kotlin.data.entity.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity  : AppCompatActivity(){
    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 2500L

        fun start(context: Context, note: Note? = null) {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            context.startActivity(intent)
        }
    }

    private var note: Note? = null
    lateinit var viewModel: NoteViewModel

    val textChahgeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        setSupportActionBar(toolbar)

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        supportActionBar?.title = note?.let {node: Note ->
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(node.lastChanged)
        } ?: getString(R.string.new_note_title)

        initView()
    }

    fun initView() {
        note?.let {
            et_title.setText(it.title)
            et_body.setText(it.text)
            val color = when (it.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
                Note.Color.PINK -> R.color.pink
                Note.Color.ORANGE -> R.color.orange
                Note.Color.DARKBLUE -> R.color.darkblue
            }

            toolbar.setBackgroundColor(ContextCompat.getColor(this, color))
        }

        et_title.addTextChangedListener(textChahgeListener)
        et_body.addTextChangedListener(textChahgeListener)
    }

    fun saveNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return

        Handler().postDelayed({
            note = note?.copy(
                title = et_title.text.toString(),
                text = et_body.text.toString(),
                lastChanged = Date()
            ) ?: createNewNote()

            viewModel.save(note!!)
//            note?.let { viewModel.save(it) }
        }, SAVE_DELAY)
    }

    private fun createNewNote(): Note = Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString())

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}