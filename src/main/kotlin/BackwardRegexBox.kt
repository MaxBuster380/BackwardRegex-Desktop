/*
 * MIT License
 *
 * Copyright (c) 2024 MaxBuster380
 *
 * This is the "BackwardRegexBox.kt" file from the BackwardRegex-Desktop project.
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogWindow
import com.github.MaxBuster380.compiler.BackwardRegexCompiler
import com.github.MaxBuster380.compiler.CompiledBackwardRegex

@Composable

fun BackwardRegexBox(
    modifier: Modifier = Modifier,
    colorTheme: ColorTheme
) {
    val compiler = BackwardRegexCompiler()
    val nbElements = 10

    var textualRegex by remember { mutableStateOf("") }
    var regex by remember { mutableStateOf( Regex("") ) }
    var compiledRegex by remember { mutableStateOf(compiler.generate("".toRegex())) }
    var errorText : String? by remember { mutableStateOf(null) }

    var exampleTexts = generateTexts(compiledRegex, nbElements)

    fun updateTexts() {
        try {
            compiledRegex = compiler.generate(regex)
            exampleTexts = generateTexts(compiledRegex, nbElements)
            errorText = null
        } catch (_: Exception) {
            errorText =
                "This regular expression is valid but couldn't be analyzed. Please report it."
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Box {
                Column {
                    TextField(
                        value = textualRegex,
                        onValueChange = {
                            textualRegex = it
                            if (isRegexValid(textualRegex)) {
                                regex = Regex(textualRegex)
                                updateTexts()
                            }
                        },
                        placeholder = {
                            Text(
                                "Enter a regular expression.",
                                fontSize = 20.sp,
                                color = colorTheme.regexTextFieldSuggestion
                            )
                        },
                        isError = !isRegexValid(textualRegex),
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = RegexColoring(colorTheme)
                    )
                    if (errorText != null) {
                        Text(errorText!!, color = colorTheme.errorText)
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxHeight(0.025F)
            )

            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight(0.775F)
                    .fillMaxWidth()

            ) {
                items(
                    items = exampleTexts
                ) {
                    GeneratedTextItem(it, regex, colorTheme)
                }
            }
        }
    }
}


fun generateTexts(compiledRegex: CompiledBackwardRegex, size: Int, maxAttempts: Int = 10): List<String> {
    val res = mutableListOf<String>()

    for(i in 1..size) {
        var newText: String?
        var attempts = 0
        do {
            newText = compiledRegex.generateMatchingText()
            attempts++
        } while (newText != null && newText in res && attempts < maxAttempts)

        if (newText == null)
            break

        if (attempts >= maxAttempts)
            break

        res += newText
    }

    return res
}

fun isRegexValid(text : String) : Boolean = try {
    Regex(text); true
} catch (_: Exception) {
    false
}

@Composable
fun GeneratedTextItem(text: String, regex: Regex, colorTheme: ColorTheme) {
    val matchError = !regex.matches(text)

    val backgroundColor = if ( !matchError ) {
        colorTheme.validMatchBackground
    } else {
        colorTheme.errorText
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(backgroundColor),
        ) {

            /*
            Text(
                text,
                modifier = Modifier
                    .fillMaxWidth(0.9F),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = colorTheme.validMatchText,
                    fontSize = 30.sp
                )
            )*/

            TextField(
                value = text,
                onValueChange = {},
                enabled = false,
                modifier = Modifier
                    .fillMaxSize(),
                textStyle = TextStyle(
                    color = colorTheme.validMatchText,
                    fontSize = 30.sp
                ),
                trailingIcon = { if (matchError) Cross(colorTheme) else Checkmark(colorTheme) }
            )

            /*
            if ( matchError && false ) {
                Text(
                    "This text doesn't match the regular expression. Please report it.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(Color.White),
                    style = TextStyle(
                        color = colorTheme.errorText,
                        fontSize = 15.sp
                    )
                )
            }
            */
        }
    }
}

@Composable
fun Checkmark(colorTheme: ColorTheme) {
    var dialogOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(0.05F)
            .clickable {
                dialogOpen = true
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            "\uD83D\uDDF8",
            color = Color(0F, 1F, 0.4F),
            style = TextStyle(
                fontSize = 30.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
        )

    }

    if (dialogOpen) {

        DialogWindow(
            onCloseRequest = { dialogOpen = false },
            title = "",
            resizable = false
        ) {
            MaterialTheme {
                Box(
                    modifier = Modifier
                        .background(colorTheme.background)
                        .fillMaxSize()
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "\uD83D\uDDF8",
                            color = Color(0F, 1F, 0.4F),
                            style = TextStyle(
                                fontSize = 30.sp
                            ),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "This text was checked if it does indeed match the given regular expression. This test passed.",
                            color = colorTheme.validMatchText
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun Cross(colorTheme: ColorTheme) {
    var dialogOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize(0.05F)
            .clickable {
                dialogOpen = true
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            "⤫",
            color = Color.White,
            style = TextStyle(
                fontSize = 30.sp
            )
        )
    }

    if (dialogOpen) {

        DialogWindow(
            onCloseRequest = { dialogOpen = false },
            title = "",
            resizable = false
        ) {
            MaterialTheme {
                Box(
                    modifier = Modifier
                        .background(colorTheme.background)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "⤫",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 30.sp
                            )
                        )

                        Text(
                            text = "This text doesn't match the regular expression. Please report it.",
                            color = colorTheme.validMatchText
                        )
                    }
                }
            }

        }
    }
}