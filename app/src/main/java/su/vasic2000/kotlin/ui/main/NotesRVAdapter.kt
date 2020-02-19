package su.vasic2000.kotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*
import su.vasic2000.kotlin.R
import su.vasic2000.kotlin.common.getColorInt
import su.vasic2000.kotlin.data.entity.Note

class NotesRVAdapter(val onItemViewClick : ((note : Note) -> Unit)? = null)  : RecyclerView.Adapter<NotesRVAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(vh: ViewHolder, pos: Int) = vh.bind(notes[pos])


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            tv_title.text = note.title
            tv_text.text = note.text

            val color = note.color.getColorInt(context)
            (this as CardView).setCardBackgroundColor(color)

            itemView.setOnClickListener {
                onItemViewClick?.invoke(note)
            }
        }
    }
}