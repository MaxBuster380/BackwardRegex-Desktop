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
            validMatchText = Color.Black,
            regexTextFieldSuggestion = Color.Gray
        )
    ),

    DARK_THEME_1(
        "Dark Theme 1",
        ColorTheme(
            background = Color(0.25f, 0.25f, 0.25f),
            errorText = Color.Red,
            matchBackground = Color.Gray,
            validMatchBackground = Color(0.35f, 0.35f, 0.35f),
            characterBackground = Color(0.4f, 0.4f, 0.4f),
            regexStructureColor = Color(1.0F, 0.5F, 0F),
            regexShortcutCharacterColor = Color(0F, 1F, 1F),
            regexSpecialCharacterColor = Color.Gray,
            regexGroupColor = Color(0F, 0.5F, 0.5F),
            regexOtherColor = Color.White,
            validMatchText = Color.White,
            regexTextFieldSuggestion = Color.Gray
        )
    );
}