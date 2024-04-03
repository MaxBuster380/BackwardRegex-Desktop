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