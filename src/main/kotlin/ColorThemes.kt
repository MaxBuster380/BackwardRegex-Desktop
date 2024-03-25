import androidx.compose.ui.graphics.Color

enum class ColorThemes(
    val title: String,
    val colorTheme: ColorTheme
) {

    LIGHT_THEME_1(
        "Light Theme 1",
        ColorTheme(
            background = Color.White,
            errorText = Color.Red,
            matchBackground = Color.Gray,
            validMatchBackground = Color(0.75f, 0.75f, 0.75f),
            characterBackground = Color.Gray,
            regexStructureColor = Color(1.0F, 0.5F, 0F),
            regexShortcutCharacterColor = Color.Blue,
            regexSpecialCharacterColor = Color.Gray,
            regexGroupColor = Color(0F, 0.5F, 0.5F),
            regexOtherColor = Color.Black,
            validMatchText = Color.Black
        )
    );
}