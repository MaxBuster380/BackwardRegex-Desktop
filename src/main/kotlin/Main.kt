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
