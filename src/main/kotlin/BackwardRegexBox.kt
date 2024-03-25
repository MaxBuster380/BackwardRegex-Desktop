import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.compiler.BackwardRegexCompiler
import org.example.model.RegexSymbol

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
                        placeholder = { Text("Enter a regular expression.", fontSize = 20.sp) },
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
                for (text in exampleTexts)
                    item {
                        GeneratedTextItem(text, regex, colorTheme)
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
                .background(colorTheme.characterBackground),
        ) {

            Text(
                text,
                modifier = Modifier
                    .background(backgroundColor),
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
            }
        }
    }
}
