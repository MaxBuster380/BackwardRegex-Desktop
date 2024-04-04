/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "ColorTheme.kt" file from the BackwardRegex-Desktop project.
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

data class ColorTheme(
    val background: Color,
    val errorText: Color,
    val validMatchBackground: Color,
    val characterBackground: Color,
    val matchBackground: Color,
    val validMatchText: Color,
    val regexStructureColor: Color,
    val regexShortcutCharacterColor: Color,
    val regexSpecialCharacterColor: Color,
    val regexGroupColor: Color,
    val regexOtherColor: Color,
    val regexTextFieldSuggestion: Color
)