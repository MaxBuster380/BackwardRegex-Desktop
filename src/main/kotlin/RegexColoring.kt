import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

class RegexColoring : VisualTransformation {

    companion object {
        private val structureColor = Color(1.0F, 0.5F, 0F)
        private val shortcutCharacterColor = Color.Blue
        private val specialCharacterColor = Color.Gray
        private val otherColor = Color.Black
    }

    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedStringWithColors(text.toString()),
            OffsetMapping.Identity)
    }

    private fun buildAnnotatedStringWithColors(text:String): AnnotatedString {

        val builder = AnnotatedString.Builder()

        var i = 0
        while(i < text.length) {
            val char = text[i]

            if (char in "^$[](){}|+*?") {
                builder.withStyle(style = SpanStyle(color = structureColor)) { append(char) }
                i++
                continue
            }

            if (char == '\\' && i+1 < text.length) {
                val nextChar = text[i+1]
                val color = if (nextChar in ".dDwWsS") {
                    shortcutCharacterColor
                } else if (nextChar in "nrt\\") {
                    specialCharacterColor
                } else {
                    otherColor
                }

                builder.withStyle(style = SpanStyle(color = color)) { append("\\$nextChar") }
                i += 2
                continue
            }

            builder.withStyle(style = SpanStyle(color = otherColor)) { append(text[i]) }
            i++
        }

        return builder.toAnnotatedString()
    }
}