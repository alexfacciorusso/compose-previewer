import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application


@Preview
@Composable
fun MyPreview() {
    AppThemePreviewer {
        previewLightAndDark("My button preview") {
            MyComposable()
        }

        preview {
            MyComposable()
        }

        previewLightAndDark("Radio button") {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onBackground) {
                RadioButtonWithLabel("This is a test radio", true, onClick = {})
            }
        }
    }
}

@Composable
fun MyComposable(modifier: Modifier = Modifier) {
    Button(onClick = {}, modifier = modifier) {
        Text("Text")
    }
}

@Composable
fun RadioButtonWithLabel(text: String, selected: Boolean, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(interactionSource, null, onClick = onClick),
    ) {
        RadioButton(
            selected,
            onClick,
            interactionSource = interactionSource,
        )
        Text(text)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(overrideTheme: ThemeInfo?, onOverrideThemeChanged: (ThemeInfo?) -> Unit) {
    Column(modifier = Modifier.padding(24.dp)) {
        FlowRow {
            RadioButtonWithLabel("Light theme", selected = overrideTheme == ThemeInfo.Light, onClick = {
                onOverrideThemeChanged(ThemeInfo.Light)
            })
            RadioButtonWithLabel("Dark theme", selected = overrideTheme == ThemeInfo.Dark, onClick = {
                onOverrideThemeChanged(ThemeInfo.Dark)
            })
            RadioButtonWithLabel("System theme", selected = overrideTheme == null, onClick = {
                onOverrideThemeChanged(null)
            })
        }

        MyComposable()
    }
}

fun main() = application {
    var overrideTheme by remember { mutableStateOf<ThemeInfo?>(null) }
    val themeInfo = overrideTheme ?: if (isSystemInDarkTheme()) ThemeInfo.Dark else ThemeInfo.Light

    CompositionLocalProvider(LocalThemeInfo provides themeInfo) {
        AppTheme {
            Window(onCloseRequest = ::exitApplication) {
                Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onBackground) {
                        MainScreen(overrideTheme, onOverrideThemeChanged = { overrideTheme = it })
                    }
                }
            }
        }
    }
}
