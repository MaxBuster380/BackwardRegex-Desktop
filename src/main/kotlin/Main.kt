import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App(colorTheme: ColorTheme) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {}
                ) {
                    Text("Version")
                }
            }
            BackwardRegexBox(
                modifier = Modifier.fillMaxSize(),
                colorTheme = colorTheme
            )
        }
    }
}
fun main() = application {

    Window(
        title = "BackwardRegex-Desktop",
        onCloseRequest = ::exitApplication
    ) {
        App(ColorThemes.LIGHT_THEME_1.colorTheme)
    }
}
