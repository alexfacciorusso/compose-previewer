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
import androidx.compose.ui.unit.dp

enum class PreviewTheme {
    Light,
    Dark,
    ;

    val isDark get() = this == Dark
}

interface PreviewerScope {
    fun preview(
        title: String = "",
        previewTheme: PreviewTheme = PreviewTheme.Light,
        height: Dp = Dp.Unspecified,
        width: Dp = Dp.Unspecified,
        maxWidth: Dp = Dp.Unspecified,
        maxHeight: Dp = Dp.Unspecified,
        contentPadding: PaddingValues = PaddingValues(16.dp),
        contentBackgroundOverride: Color? = null,
        content: @Composable PreviewScope.() -> Unit,
    )

    fun preview(
        title: AnnotatedString,
        previewTheme: PreviewTheme = PreviewTheme.Light,
        height: Dp = Dp.Unspecified,
        width: Dp = Dp.Unspecified,
        maxWidth: Dp = Dp.Unspecified,
        maxHeight: Dp = Dp.Unspecified,
        contentPadding: PaddingValues = PaddingValues(16.dp),
        contentBackgroundOverride: Color? = null,
        content: @Composable PreviewScope.() -> Unit,
    )

    fun previewLightAndDark(
        title: String = "",
        height: Dp = Dp.Unspecified,
        width: Dp = Dp.Unspecified,
        maxWidth: Dp = Dp.Unspecified,
        maxHeight: Dp = Dp.Unspecified,
        contentPadding: PaddingValues = PaddingValues(16.dp),
        contentBackgroundOverride: Color? = null,
        content: @Composable PreviewScope.() -> Unit,
    )

    fun previewLightAndDark(
        title: AnnotatedString,
        height: Dp = Dp.Unspecified,
        width: Dp = Dp.Unspecified,
        maxWidth: Dp = Dp.Unspecified,
        maxHeight: Dp = Dp.Unspecified,
        contentPadding: PaddingValues = PaddingValues(16.dp),
        contentBackgroundOverride: Color? = null,
        content: @Composable PreviewScope.() -> Unit,
    )
}

interface PreviewScope {
    val backgroundColor: Color
}

internal data class PreviewScopeImpl(override val backgroundColor: Color) : PreviewScope

internal data class PreviewInfo(
    val title: AnnotatedString,
    val theme: PreviewTheme,
    val height: Dp,
    val width: Dp,
    val maxWidth: Dp,
    val maxHeight: Dp,
    val contentPadding: PaddingValues,
    val contentBackgroundOverride: Color?,
    val content: @Composable (PreviewScope.() -> Unit),
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

    override fun preview(
        title: String,
        previewTheme: PreviewTheme,
        height: Dp,
        width: Dp,
        maxWidth: Dp,
        maxHeight: Dp,
        contentPadding: PaddingValues,
        contentBackgroundOverride: Color?,
        content: @Composable (PreviewScope.() -> Unit),
    ) {
        preview(
            title = AnnotatedString(title),
            previewTheme = previewTheme,
            height = height,
            width = width,
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            contentPadding = contentPadding,
            content = content
        )
    }

    override fun previewLightAndDark(
        title: AnnotatedString,
        height: Dp,
        width: Dp,
        maxWidth: Dp,
        maxHeight: Dp,
        contentPadding: PaddingValues,
        contentBackgroundOverride: Color?,
        content: @Composable (PreviewScope.() -> Unit),
    ) {
        listOf(PreviewTheme.Light, PreviewTheme.Dark).forEach {
            preview(
                title = title + buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Light)) {
                        append(" ")
                        append(if (it.isDark) "(dark)" else "(light)")
                    }
                },
                previewTheme = it,
                height = height,
                width = width,
                maxWidth = maxWidth,
                maxHeight = maxHeight,
                contentPadding = contentPadding,
                content = content
            )
        }
    }

    override fun previewLightAndDark(
        title: String,
        height: Dp,
        width: Dp,
        maxWidth: Dp,
        maxHeight: Dp,
        contentPadding: PaddingValues,
        contentBackgroundOverride: Color?,
        content: @Composable() (PreviewScope.() -> Unit),
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
}