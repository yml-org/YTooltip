package co.yml.tooltip.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.constraintlayout.compose.ConstraintLayout
import kotlin.math.roundToInt

/**
 * @class ToolTipHorizontalEdge is child class of @class ToolTipHorizontalAnchorEdge.
 * Setup ToolTipHorizontalEdge to Top or Bottom of Popup.
 * */
sealed class ToolTipHorizontalEdge : ToolTipHorizontalAnchorEdge() {

    /**
     * Setup ToolTipHorizontalEdge Top of Popup
     * */
    object Top : ToolTipHorizontalEdge() {
        /**
         * @function drawTip that allow you to create a Tip.
         * This component is used to draw shape for ToolTip.
         * We can set tooltipStyle for Tip.
         *
         *
         * @param size size of view.
         * @param layoutDirection Layout direction.
         */
        override fun Path.drawTip(size: Size, layoutDirection: LayoutDirection) {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width / 2f, size.height)
            lineTo(0f, 0f)
        }

        /**
         * @function anchorEdgeViewTooltipContainer is used to ToolTip content container.
         *
         * @param modifier : Custom modifier.
         * @param cornerRadius : Setup corner radius.
         * @param tipPosition : Set toolTip Position.
         * @param tip : Tip Composable View.
         * @param content : Content Composable View.
         * */
        @Composable
        override fun anchorEdgeViewTooltipContainer(
            modifier: Modifier,
            cornerRadius: Dp,
            tipPosition: ToolTipEdgePosition,
            tip: @Composable () -> Unit,
            content: @Composable () -> Unit
        ) {
            val tipPositionOffset = tipPosition.toolTipOffset
            ConstraintLayout(modifier = modifier) {
                val (contentContainer, tipContainer) = createRefs()
                Box(
                    modifier = Modifier
                        .constrainAs(contentContainer) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .padding(
                            start = if (tipPositionOffset < 0.dp) tipPositionOffset * -2 else 0.dp,
                            end = if (tipPositionOffset > 0.dp) tipPositionOffset * 2 else 0.dp
                        )
                ) {
                    content()
                }
                val tipPadding = cornerRadius + tipPositionOffset.absoluteValue
                Box(
                    modifier = Modifier
                        .constrainAs(tipContainer) {
                            linkTo(
                                contentContainer.start,
                                contentContainer.end,
                                bias = tipPosition.toolTipPercent
                            )
                            top.linkTo(contentContainer.bottom)
                        }
                        .padding(horizontal = tipPadding)
                ) {
                    tip()
                }
            }
        }

        /**
         * @function popupPositionCalculation is used to calculate Tooltip Popup Position at screen.
         *
         *
         * @param density : Custom local density..
         * @param tooltipStyle : Style for ToolTipStyle.
         * @param tipPosition : ToolTip Position.
         * @param anchorPosition : AnchorPosition.
         * @param margin : Custom margin.
         * @param anchorBounds : IntRect - Left, Right, Top, Bottom.
         * @param layoutDirection : LayoutDirection ltr or rtl.
         * @param popupContentSize : PopupContent size.
         * @param statusBarHeight : StatusBarHeight.
         * */
        override fun popupPositionCalculation(
            density: Density,
            tooltipStyle: TooltipStyle,
            tipPosition: ToolTipEdgePosition,
            anchorPosition: ToolTipEdgePosition,
            margin: Dp,
            anchorBounds: IntRect,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
            statusBarHeight: Int
        ): IntOffset = with(density) {
            val x = calculatePopupPositionX(
                density,
                layoutDirection,
                anchorBounds,
                anchorPosition,
                tooltipStyle,
                tipPosition,
                popupContentSize
            )
            val y = anchorBounds.top - margin.toPx() - popupContentSize.height
            return IntOffset(x.roundToInt(), y.roundToInt())
        }
    }


    /**
     * Setup ToolTipHorizontalEdge Bottom of Popup
     * */
    object Bottom : ToolTipHorizontalEdge() {

        /**
         * @function drawTip that allow you to create a Tip.
         * This component is used to draw shape for ToolTip.
         * We can set tooltipStyle for Tip.
         *
         *
         * @param size size of view.
         * @param layoutDirection Layout direction.
         */
        override fun Path.drawTip(size: Size, layoutDirection: LayoutDirection) {
            moveTo(0f, size.height)
            lineTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
        }

        /**
         * @function anchorEdgeViewTooltipContainer is used to ToolTip content container.
         *
         * @param modifier : Custom modifier.
         * @param cornerRadius : Setup corner radius.
         * @param tipPosition : Set toolTip Position.
         * @param tip : Tip Composable View.
         * @param content : Content Composable View.
         * */
        @Composable
        override fun anchorEdgeViewTooltipContainer(
            modifier: Modifier,
            cornerRadius: Dp,
            tipPosition: ToolTipEdgePosition,
            tip: @Composable () -> Unit,
            content: @Composable () -> Unit
        ) {
            val tipPositionOffset = tipPosition.toolTipOffset
            ConstraintLayout(modifier = modifier) {
                val (contentContainer, tipContainer) = createRefs()
                Box(
                    modifier = Modifier
                        .constrainAs(contentContainer) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(
                            start = if (tipPositionOffset < 0.dp) tipPositionOffset * -2 else 0.dp,
                            end = if (tipPositionOffset > 0.dp) tipPositionOffset * 2 else 0.dp
                        )
                ) {
                    content()
                }
                val tipPadding = cornerRadius + tipPositionOffset.absoluteValue
                Box(
                    modifier = Modifier
                        .constrainAs(tipContainer) {
                            linkTo(
                                contentContainer.start,
                                contentContainer.end,
                                bias = tipPosition.toolTipPercent
                            )
                            bottom.linkTo(contentContainer.top)
                        }
                        .padding(start = tipPadding, end = tipPadding)
                ) {
                    tip()
                }
            }
        }

        /**
         * @function popupPositionCalculation is used to calculate Tooltip Popup Position at screen.
         *
         *
         * @param density : Custom local density..
         * @param tooltipStyle : Style for ToolTipStyle.
         * @param tipPosition : ToolTip Position.
         * @param anchorPosition : AnchorPosition.
         * @param margin : Custom margin.
         * @param anchorBounds : IntRect - Left, Right, Top, Bottom.
         * @param layoutDirection : LayoutDirection ltr or rtl.
         * @param popupContentSize : PopupContent size.
         * @param statusBarHeight : StatusBarHeight.
         * */
        override fun popupPositionCalculation(
            density: Density,
            tooltipStyle: TooltipStyle,
            tipPosition: ToolTipEdgePosition,
            anchorPosition: ToolTipEdgePosition,
            margin: Dp,
            anchorBounds: IntRect,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize,
            statusBarHeight: Int
        ): IntOffset = with(density) {
            val x = calculatePopupPositionX(
                density,
                layoutDirection,
                anchorBounds,
                anchorPosition,
                tooltipStyle,
                tipPosition,
                popupContentSize
            )
            val y = anchorBounds.bottom + margin.toPx()
            return IntOffset(x.roundToInt(), y.roundToInt())
        }
    }
}