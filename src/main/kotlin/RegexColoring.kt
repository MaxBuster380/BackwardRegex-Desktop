import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

class RegexColoring(
    private val colorTheme: ColorTheme
) : VisualTransformation {

    companion object {
        private val size = 20.sp
    }

    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            buildAnnotatedStringWithColors(text.toString()),
            OffsetMapping.Identity)
    }

    private fun buildAnnotatedStringWithColors(text:String): AnnotatedString {

        val builder = AnnotatedString.Builder()

        var i = 0
        var inBraces = false
        var inBrackets = false
        while(i < text.length) {
            val char = text[i]

            if (char in "^$[](){}|+*?") {
                addString(builder, char, colorTheme.regexStructureColor)

                if (char == '{') inBraces = true
                if (char == '}') inBraces = false
                if (char == '[') inBrackets = true
                if (char == ']') inBrackets = false

                i++
                continue
            }

            if ((char.isDigit() || char == ',') && inBraces) {

                addString(builder, char, colorTheme.regexStructureColor)

                i++
                continue
            }

            if (char == '-' && inBrackets) {

                addString(builder, char, colorTheme.regexStructureColor)

                i++
                continue
            }

            if (char == '\\' && i+1 < text.length) {
                val nextChar = text[i+1]
                val color = if (nextChar in "dDwWsS") {
                    colorTheme.regexShortcutCharacterColor
                } else if (nextChar in "nrt\\.") {
                    colorTheme.regexSpecialCharacterColor
                } else if (nextChar.isDigit()) {
                    colorTheme.regexGroupColor
                } else {
                    colorTheme.regexOtherColor
                }

                addString(builder, "\\$nextChar", color)
                i += 2
                continue
            }

            addString(builder, text[i], colorTheme.regexOtherColor)
            i++
        }

        return builder.toAnnotatedString()
    }

    private fun addString(builder: AnnotatedString.Builder, text: String, color: Color) {
        builder.withStyle(
            style = SpanStyle(
                color = color,
                fontSize = size
            )
        ) { append(text) }
    }

    private fun addString(builder: AnnotatedString.Builder, text: Char, color: Color) {
        return addString(builder, text.toString(), color)
    }
}