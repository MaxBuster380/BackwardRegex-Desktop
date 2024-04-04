/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "VersionWindow.kt" file from the BackwardRegex-Desktop project.
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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogWindow

@Composable
fun VersionWindow(
    creditsOpen: MutableState<Boolean>,
    colorTheme: ColorTheme
) {
    val versionsTexts = listOf(
        Pair("BackwardRegex-Desktop", "alpha-1.0.0"),
        Pair("BackwardRegex", "alpha-1.0.4"),
        Pair("", ""),
        Pair("Author", "MaxBuster380"),
    )

    DialogWindow(
        onCloseRequest = { creditsOpen.value = false },
        title = "Version",
        resizable = false
    ) {
        MaterialTheme {
            LazyColumn(
                Modifier
                    .background(colorTheme.background)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (text in versionsTexts) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.8F),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text.first, color = colorTheme.validMatchText)
                            Text(text.second, color = colorTheme.validMatchText)
                        }
                    }
                }
            }
        }

    }
}