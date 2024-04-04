/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "Main.kt" file from the BackwardRegex-Desktop project.
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

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App(colorTheme: ColorTheme) {

    val creditsOpen: MutableState<Boolean> = mutableStateOf(false)

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorTheme.background)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                Button(
                    onClick = { creditsOpen.value = true }
                ) { Text("Version") }
            }
            BackwardRegexBox(
                modifier = Modifier.fillMaxSize(),
                colorTheme = colorTheme
            )
        }

        if (creditsOpen.value) {
            VersionWindow(
                creditsOpen,
                colorTheme
            )
        }
    }
}
fun main() = application {

    Window(
        title = "BackwardRegex-Desktop",
        onCloseRequest = ::exitApplication,
        resizable = false
    ) {
        App(ColorThemes.DARK_THEME_1.colorTheme)
    }
}
