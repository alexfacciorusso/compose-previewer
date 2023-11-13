import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.alexfacciorusso.previewer.PreviewTheme
import com.alexfacciorusso.previewer.Previewer
import com.alexfacciorusso.previewer.PreviewerScope

enum class ThemeInfo {
    Dark,
    Light,
}

val LocalThemeInfo = staticCompositionLocalOf<ThemeInfo> {
    error("No ThemeInfo provided")
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (LocalThemeInfo.current == ThemeInfo.Dark) darkColorScheme() else lightColorScheme(),
    ) {
        content()
    }
}

@Composable
fun AppThemePreviewer(content: PreviewerScope.() -> Unit) {
    Previewer(wrapperBlock = { wrappedContent ->
        val themeInfo = when (previewTheme) {
            PreviewTheme.Light -> ThemeInfo.Light
            PreviewTheme.Dark -> ThemeInfo.Dark
        }

        CompositionLocalProvider(LocalThemeInfo provides themeInfo) {
            AppTheme {
                wrappedContent()
            }
        }
    }, content = content)
}
