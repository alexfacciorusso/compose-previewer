package com.alexfacciorusso.previewer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class PreviewTheme {
    Light,
    Dark,
    ;

    val isDark get() = this == Dark
}

internal val DefaultPreviewContentPadding = PaddingValues(16.dp)

interface PreviewerScope {
    fun preview(
        title: AnnotatedString,
        previewTheme: PreviewTheme = PreviewTheme.Light,
        height: Dp = Dp.Unspecified,
        width: Dp = Dp.Unspecified,
        maxWidth: Dp = Dp.Unspecified,
        maxHeight: Dp = Dp.Unspecified,
        contentPadding: PaddingValues = DefaultPreviewContentPadding,
        contentBackgroundOverride: Color? = null,
        content: @Composable PreviewScope.() -> Unit,
    )
}

interface PreviewScope {
    val backgroundColor: Color
    val previewTheme: PreviewTheme
}

internal data class PreviewScopeImpl(
    override val backgroundColor: Color,
    override val previewTheme: PreviewTheme,
) : PreviewScope

internal data class PreviewInfo(
    val title: AnnotatedString,
    val theme: PreviewTheme,
    val height: Dp,
    val width: Dp,
    val maxWidth: Dp,
    val maxHeight: Dp,
    val contentPadding: PaddingValues,
    val contentBackgroundOverride: Color?,
    val content: @Composable PreviewScope.() -> Unit,
)

internal class PreviewerBuilderImpl : PreviewerScope {
    val previews = mutableListOf<PreviewInfo>()

    override fun preview(
        title: AnnotatedString,
        previewTheme: PreviewTheme,
        height: Dp,
        width: Dp,
        maxWidth: Dp,
        maxHeight: Dp,
        contentPadding: PaddingValues,
        contentBackgroundOverride: Color?,
        content: @Composable (PreviewScope.() -> Unit),
    ) {
        previews.add(
            PreviewInfo(
                title = title,
                theme = previewTheme,
                height = height,
                width = width,
                maxWidth = maxWidth,
                maxHeight = maxHeight,
                contentPadding = contentPadding,
                contentBackgroundOverride = contentBackgroundOverride,
                content = content,
            )
        )
    }
}