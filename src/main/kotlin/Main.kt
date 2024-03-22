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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.compiler.BackwardRegexCompiler
import org.example.model.RegexSymbol

@Composable
@Preview
fun App() {
    val compiler = BackwardRegexCompiler()
    val nbElements = 8

    var exampleTexts = listOf<String>()

    var textualRegex by remember { mutableStateOf("") }
    var regex by remember { mutableStateOf( Regex("") ) }
    var compiledRegex by remember { mutableStateOf(compiler.generate("".toRegex())) }

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

                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.2F)
                ) {
                    Box{
                        TextField(
                            value = textualRegex,
                            onValueChange = {
                                textualRegex = it
                                if (isRegexValid(textualRegex)) {
                                    regex = Regex(textualRegex)
                                    compiledRegex = compiler.generate(regex)
                                    exampleTexts = generateTexts(compiledRegex, nbElements)
                                }
                            },
                            placeholder = { Text("Enter a regular expression.") },
                            isError = !isRegexValid( textualRegex ),
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = RegexColoring()
                        )
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
                    for (text in exampleTexts) {
                        item {
                            GeneratedTextItem(
                                text,
                                regex
                            )
                        }
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
    val color = if ( regex.matches(text) ) {
        Color.DarkGray
    } else {
        Color.Red
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text,
                modifier = Modifier
                    .fillMaxWidth(0.8F),
                textAlign = TextAlign.Center,
                style = TextStyle(color = color, fontSize = 30.sp)
            )
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
