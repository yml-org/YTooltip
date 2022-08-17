package com.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.components.core.ToolTipAnchorEdgeView
import com.components.core.ToolTipEdgePosition
import com.components.core.ToolTipHorizontalEdge
import com.components.core.ToolTipVerticalEdge

@Composable
fun PredictAnchorEdge(
    anchorEdgeState: MutableState<ToolTipAnchorEdgeView>,
    yPosition: Int,
    gravity: ToolTipGravity = ToolTipGravity.NONE,
    maxHeightOfTooltip: Dp = TOOLTIP_MAX_HEIGHT
) {
    val screenHeightDp = LocalConfiguration.current.screenWidthDp.dp
    val verticalCenterDp = screenHeightDp / 2

    with(LocalDensity.current) {

        if (gravity == ToolTipGravity.NONE) {
            val anchorYPosDp = yPosition.toDp()

            if (anchorYPosDp >= verticalCenterDp) {
                val spaceBelowAnchor = screenHeightDp - anchorYPosDp - TOOLTIP_ADDITIONAL_PADDING
                if (spaceBelowAnchor > maxHeightOfTooltip) {
                    anchorEdgeState.value = ToolTipHorizontalEdge.Bottom
                } else {
                    anchorEdgeState.value = ToolTipHorizontalEdge.Top
                }
            } else {
                anchorEdgeState.value = ToolTipHorizontalEdge.Bottom
            }
        } else {
            when (gravity) {
                ToolTipGravity.TOP -> anchorEdgeState.value = ToolTipHorizontalEdge.Top
                ToolTipGravity.BOTTOM -> anchorEdgeState.value = ToolTipHorizontalEdge.Bottom
                ToolTipGravity.START -> anchorEdgeState.value = ToolTipVerticalEdge.Start
                ToolTipGravity.END -> anchorEdgeState.value = ToolTipVerticalEdge.End
                else -> {}
            }
        }
    }
}

@Composable
fun PredictTipPosition(
    tipPosition: ToolTipEdgePosition,
    xPosition: Int,
    maxWidthPercent: Float = TOOLTIP_MAX_WIDTH_PERCENT
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val horizontalCenterDp = screenWidthDp / 2

    val maxWidthOfTooltip = screenWidthDp * maxWidthPercent

    with(LocalDensity.current) {
        // Decide Tip position
        val anchorPosInDp = xPosition.toDp()

        if (anchorPosInDp >= horizontalCenterDp) {
            val spaceToRightOfAnchor = screenWidthDp - anchorPosInDp - TOOLTIP_ADDITIONAL_PADDING
            if (spaceToRightOfAnchor > maxWidthOfTooltip / 2) {
                tipPosition.percent = TIP_POS_PERCENT_CENTER
            } else {
                tipPosition.percent = anchorPosInDp / screenWidthDp
            }

        } else {
            val spaceToLeftOfAnchor = anchorPosInDp - TOOLTIP_ADDITIONAL_PADDING

            if (spaceToLeftOfAnchor > maxWidthOfTooltip / 2) {
                tipPosition.percent = TIP_POS_PERCENT_CENTER
            } else {
                tipPosition.percent = anchorPosInDp / screenWidthDp
            }
        }
    }
}
