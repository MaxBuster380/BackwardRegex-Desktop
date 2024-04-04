/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "ColorThemes.kt" file from the BackwardRegex-Desktop project.
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

enum class ColorThemes(
    val title: String,
    val colorTheme: ColorTheme
) {

    LIGHT_THEME_1(
        "Light Theme 1",
        ColorTheme(
            background = Color.White,
            errorText = Color.Red,
            matchBackground = Color.Gray,
            validMatchBackground = Color(0.75f, 0.75f, 0.75f),
            characterBackground = Color.Gray,
            regexStructureColor = Color(1.0F, 0.5F, 0F),
            regexShortcutCharacterColor = Color.Blue,
            regexSpecialCharacterColor = Color.Gray,
            regexGroupColor = Color(0F, 0.5F, 0.5F),
            regexOtherColor = Color.Black,
            validMatchText = Color.Black,
            regexTextFieldSuggestion = Color.Gray
        )
    ),

    DARK_THEME_1(
        "Dark Theme 1",
        ColorTheme(
            background = Color(0.25f, 0.25f, 0.25f),
            errorText = Color.Red,
            matchBackground = Color.Gray,
            validMatchBackground = Color(0.35f, 0.35f, 0.35f),
            characterBackground = Color(0.4f, 0.4f, 0.4f),
            regexStructureColor = Color(1.0F, 0.5F, 0F),
            regexShortcutCharacterColor = Color(0F, 1F, 1F),
            regexSpecialCharacterColor = Color.Gray,
            regexGroupColor = Color(0F, 0.5F, 0.5F),
            regexOtherColor = Color.White,
            validMatchText = Color.White,
            regexTextFieldSuggestion = Color.Gray
        )
    );
}