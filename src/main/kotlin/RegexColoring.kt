/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "RegexColoring.kt" file from the BackwardRegex-Desktop project.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
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

                if (nextChar.isDigit()) {
                    addBoldString(builder, "\\$nextChar", colorTheme.regexOtherColor)
                    i += 2
                    continue
                }

                val color = if (nextChar in "dDwWsS") {
                    colorTheme.regexShortcutCharacterColor
                } else if (nextChar in "nrt\\.") {
                    colorTheme.regexSpecialCharacterColor
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

    private fun addBoldString(builder: AnnotatedString.Builder, text: String, color: Color) {
        builder.withStyle(
            style = SpanStyle(
                color = color,
                fontSize = size,
                fontWeight = FontWeight.Bold
            )
        ) { append(text) }
    }

    private fun addString(builder: AnnotatedString.Builder, text: Char, color: Color) {
        return addString(builder, text.toString(), color)
    }
}