package com.tooltip.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntSize
import com.tooltip.utils.SCREEN_ALPHA
import com.tooltip.utils.TRANSPARENT_ALPHA

/**
 * @param anyHintVisible Hint visibility
 * @param viewOffset Offset from root.
 * @param viewOffsetSize Offset size to tip coordinate .
 * @param cornerRadius Corner radius for focus area.
 * @param padding Padding for offset.
 * @param backgroundTransparency Transparency for screen background.
 */
@Composable
fun DrawCanvas(
    anyHintVisible: Boolean,
    viewOffset: Offset,
    viewOffsetSize: IntSize,
    cornerRadius: Float,
    backgroundTransparency: Float = TRANSPARENT_ALPHA,
    padding: Float
) {
    val vOffsetSize = Size(
        viewOffsetSize.width.toFloat() + padding,
        viewOffsetSize.height.toFloat() + padding
    )
    val vOffset =
        Offset(viewOffset.x - padding / 2, viewOffset.y - padding / 2)


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                alpha = SCREEN_ALPHA
            }
    ) {
        drawRect(
            color = if (anyHintVisible) Color.Black.copy(backgroundTransparency) else Color.Transparent,
        )
        drawRoundRect(
            size = vOffsetSize,
            color = Color.Transparent,
            topLeft = vOffset,
            cornerRadius = CornerRadius(cornerRadius, cornerRadius),
            blendMode = BlendMode.Clear
        )
    }
}