package su.vasic2000.kotlin.data.entity

class Note(
    val id: String,
    val title: String,
    val text: String,
    val color: Color
) {
    override fun equals(other: Any?): Boolean {
        if(this === other) return true

        if(javaClass != other?.javaClass) return false
        other as Note
        return (id == other.id)
    }

    enum class Color {
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET,
        PINK,
        ORANGE,
        DARKBLUE
    }
}