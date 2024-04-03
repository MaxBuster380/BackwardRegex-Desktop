import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.MaxBuster380.compiler.BackwardRegexCompiler
import com.github.MaxBuster380.compiler.CompiledBackwardRegex

@Composable

fun BackwardRegexBox(
    modifier: Modifier = Modifier,
    colorTheme: ColorTheme
) {
    val compiler = BackwardRegexCompiler()
    val nbElements = 7

    var textualRegex by remember { mutableStateOf("") }
    var regex by remember { mutableStateOf( Regex("") ) }
    var compiledRegex by remember { mutableStateOf(compiler.generate("".toRegex())) }
    var errorText : String? by remember { mutableStateOf(null) }

    var exampleTexts = generateTexts(compiledRegex, nbElements)

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
                                try {
                                    compiledRegex = compiler.generate(regex)
                                    exampleTexts = generateTexts(compiledRegex, nbElements)
                                    errorText = null
                                } catch (_: Exception) {
                                    errorText =
                                        "This regular expression is valid but couldn't be analyzed. Please report it."
                                }
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

            Text(
                text,
                modifier = Modifier
                    .fillMaxWidth(0.9F),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = colorTheme.validMatchText,
                    fontSize = 30.sp
                )
            )

            if ( matchError ) {
                Text(
                    "This text doesn't match the regular expression. Please report it.",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = colorTheme.errorText,
                        fontSize = 15.sp
                    )
                )
            } else {
                Text(
                    "\uD83D\uDDF8",
                    modifier = Modifier
                        .fillMaxWidth(0.1F),
                    style = TextStyle(
                        fontSize = 30.sp
                    )
                )
            }
        }
    }
}
