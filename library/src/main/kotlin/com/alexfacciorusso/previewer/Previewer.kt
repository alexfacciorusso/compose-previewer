package com.alexfacciorusso.previewer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

private typealias WrapperBlock = @Composable (isDarkTheme: Boolean, content: @Composable () -> Unit) -> Unit

private val PreviewContentTopAlignmentLine = HorizontalAlignmentLine(::max)

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowRowScope.PreviewItem(
    preview: PreviewInfo,
    wrapperBlock: WrapperBlock,
    previewContentBackgroundOverride: Color?,
) {
    val contentBackground =
        preview.contentBackgroundOverride ?: previewContentBackgroundOverride
        ?: if (preview.theme == PreviewTheme.Light) Color.White else Color(25, 25, 25)
    val textBackground = Color.White
    val roundedShape = RoundedCornerShape(6.dp)

    Column(modifier = Modifier.alignBy(PreviewContentTopAlignmentLine)) {
        if (preview.title.isNotBlank()) {
            Text(
                preview.title,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .clip(roundedShape)
                    .background(textBackground.copy(alpha = .4f))
                    .padding(vertical = 4.dp, horizontal = 8.dp),
            )
            Spacer(Modifier.height(8.dp))
        }

        CompositionLocalProvider(
            LocalInspectionMode provides true,
        ) {
            Box(
                Modifier
                    .clip(roundedShape)
                    .background(contentBackground)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(
                            placeable.width, placeable.height, alignmentLines = mapOf(
                                PreviewContentTopAlignmentLine to 0
                            )
                        ) {
                            placeable.place(0, 0)
                        }
                    },
            ) {
                Box(
                    Modifier
                        .padding(preview.contentPadding)
                        .then(if (preview.maxHeight != Dp.Unspecified) Modifier.heightIn(max = preview.maxHeight) else Modifier)
                        .then(if (preview.maxWidth != Dp.Unspecified) Modifier.widthIn(max = preview.maxWidth) else Modifier)
                        .then(if (preview.height != Dp.Unspecified) Modifier.height(preview.height) else Modifier)
                        .then(if (preview.width != Dp.Unspecified) Modifier.width(preview.width) else Modifier)
                ) {
                    wrapperBlock(preview.theme.isDark) {
                        preview.content(PreviewScopeImpl(backgroundColor = contentBackground))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Previewer(
    wrapperBlock: WrapperBlock,
    background: Color = Color.LightGray,
    contentBackgroundOverride: Color? = null,
    content: PreviewerScope.() -> Unit,
) {
    val builderInstance = PreviewerBuilderImpl()

    content(builderInstance)

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.background(background).padding(16.dp).fillMaxSize(),
    ) {
        builderInstance.previews.forEach {
            PreviewItem(it, wrapperBlock, contentBackgroundOverride)
        }
    }
}
