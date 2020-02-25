package su.vasic2000.kotlin.common

import android.content.Context
import androidx.core.content.ContextCompat
import su.vasic2000.kotlin.R
import su.vasic2000.kotlin.data.entity.Note

fun Note.Color.getColorInt(context: Context): Int =
    ContextCompat.getColor(
        context, when (this) {
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
    )


fun Note.Color.getColorRes(): Int = when (this) {
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