import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.compiler.BackwardRegexCompiler
import org.example.model.RegexSymbol

@Composable
@Preview
fun App() {
    val compiler = BackwardRegexCompiler()
    val nbElements = 7

    var textualRegex by remember { mutableStateOf("") }
    var regex by remember { mutableStateOf( Regex("") ) }
    var compiledRegex by remember { mutableStateOf(compiler.generate("".toRegex())) }
    var errorText : String? by remember { mutableStateOf(null) }

    var exampleTexts = generateTexts(compiledRegex, nbElements)

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.75F)
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
                                        errorText = "This regular expression is valid but couldn't be analyzed. Please report it."
                                    }
                                }
                            },
                            placeholder = { Text("Enter a regular expression.", fontSize = 20.sp) },
                            isError = !isRegexValid(textualRegex),
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = RegexColoring()

                        )
                        if (errorText != null) {
                            Text(errorText!!, color = Color.Red)
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
                    for (text in exampleTexts)
                        item {
                            GeneratedTextItem(text, regex)
                        }
                }
            }
        }
    }
}

fun generateTexts(compiledRegex : RegexSymbol, size : Int, maxAttempts : Int = 10) : List<String> {
    val res = mutableListOf<String>()

    for(i in 1..size) {
        var newText: String
        var attempts = 0
        do {
            newText = compiledRegex.generateMatchingText()
            attempts++
        }while(newText in res && attempts < maxAttempts)

        if (attempts >= maxAttempts) {
            break
        } else {
            res += newText
        }
    }

    return res
}

fun isRegexValid(text : String) : Boolean = try {
    Regex(text); true
} catch (_: Exception) {
    false
}

@Composable
fun GeneratedTextItem(text : String, regex : Regex) {
    val matchError = !regex.matches(text)

    val backgroundColor = if ( !matchError ) {
        Color(0.75f, 0.75f, 0.75f)
    } else {
        Color.Red
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .background(Color.Gray),
        ) {

            Text(
                text,
                modifier = Modifier
                    .background(backgroundColor),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 30.sp
                )
            )

            if ( matchError ) {
                Text(
                    "This text doesn't match the regular expression. Please report it.",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 15.sp
                    )
                )
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
