package com.alexfacciorusso.previewer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp

fun PreviewerScope.preview(
    title: String = "",
    previewTheme: PreviewTheme = PreviewTheme.Light,
    height: Dp = Dp.Unspecified,
    width: Dp = Dp.Unspecified,
    maxWidth: Dp = Dp.Unspecified,
    maxHeight: Dp = Dp.Unspecified,
    contentPadding: PaddingValues = DefaultPreviewContentPadding,
    contentBackgroundOverride: Color? = null,
    content: @Composable PreviewScope.() -> Unit,
) {
    preview(
        title = AnnotatedString(title),
        previewTheme = previewTheme,
        height = height,
        width = width,
        maxWidth = maxWidth,
        maxHeight = maxHeight,
        contentPadding = contentPadding,
        contentBackgroundOverride = contentBackgroundOverride,
        content = content
    )
}

fun PreviewerScope.previewLightAndDark(
    title: AnnotatedString,
    height: Dp = Dp.Unspecified,
    width: Dp = Dp.Unspecified,
    maxWidth: Dp = Dp.Unspecified,
    maxHeight: Dp = Dp.Unspecified,
    contentPadding: PaddingValues = DefaultPreviewContentPadding,
    contentBackgroundOverride: Color? = null,
    content: @Composable (PreviewScope.() -> Unit),
) {
    listOf(PreviewTheme.Light, PreviewTheme.Dark).forEach {
        preview(
            title = title + buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.Light)) {
                    if (title.isNotBlank()) append(" ")

                    append(if (it.isDark) "(dark)" else "(light)")
                }
            },
            previewTheme = it,
            height = height,
            width = width,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            contentPadding = contentPadding,
            contentBackgroundOverride = contentBackgroundOverride,
            content = content
        )
    }
}

fun PreviewerScope.previewLightAndDark(
    title: String = "",
    height: Dp = Dp.Unspecified,
    width: Dp = Dp.Unspecified,
    maxWidth: Dp = Dp.Unspecified,
    maxHeight: Dp = Dp.Unspecified,
    contentPadding: PaddingValues = DefaultPreviewContentPadding,
    contentBackgroundOverride: Color? = null,
    content: @Composable (PreviewScope.() -> Unit),
) {
    previewLightAndDark(
        AnnotatedString(title),
        height,
        width,
        maxWidth,
        maxHeight,
        contentPadding,
        contentBackgroundOverride,
        content
    )
}
