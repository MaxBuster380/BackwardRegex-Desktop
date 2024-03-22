import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.compiler.BackwardRegexCompiler

@Composable
@Preview
fun App() {
    val compiler = BackwardRegexCompiler()
    val nbElements = 15

    var textualRegex by remember { mutableStateOf("") }
    var compiledRegex by remember { mutableStateOf(compiler.generate("".toRegex())) }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.75F)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxHeight(0.3F)
                ) {
                    Box{
                        TextField(
                            value = textualRegex,
                            onValueChange = { textualRegex = it },
                            placeholder = { Text("Enter a regular expression.") },
                            isError = try {
                                Regex(textualRegex); false
                            } catch (_: Exception) {
                                true
                            },
                            modifier = Modifier.fillMaxWidth(0.8F)
                        )
                    }
                    Button(
                        onClick = {
                            compiledRegex = compiler.generate(textualRegex.toRegex())
                        },
                        enabled = try {
                            Regex(textualRegex); true
                        } catch (_: Exception) {
                            false
                        }
                    ) {
                        Text("Generate")
                    }
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxHeight(0.05F)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight(0.65F)
                        .fillMaxWidth()

                ) {
                    for (i in 1..nbElements) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color.Red),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(compiledRegex.generateMatchingText())
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
