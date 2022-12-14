package co.yml.tooltip.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import co.yml.tooltip.utils.CLOSE_STRING
import co.yml.tooltip.utils.DEFAULT_PADDING
import co.yml.tooltip.utils.EMPTY_STRING
import co.yml.tooltip.utils.ItemPosition
import co.yml.tooltip.utils.MAX_LINE
import co.yml.tooltip.utils.TOOLTIP_MAX_WIDTH_PERCENT


/**
 * Component that allow you to show default ToolTip view.
 * <p>
 * DefaultToolTipView Composable function will return @Composable (RowScope).
 * @Composable (RowScope) contains ToolTip's default content.
 *
 *
 * @param hintText Hint text.
 * @param hintTextColor Hint text color.
 * @param isHintVisible Hint visibility .
 * @param dismissHintText Dismiss text .
 * @param dismissHintTextColor Dismiss text color.
 * @param isDismissButtonHidden Dismiss button visibility.
 * @param animState Animation state.
 * */
@Composable
fun DefaultToolTipView(
    hintText: String = EMPTY_STRING,
    hintTextColor: Color = Color.White,
    isHintVisible: MutableState<Boolean>,
    dismissHintText: String = CLOSE_STRING,
    dismissHintTextColor: Color = Color.White,
    isDismissButtonHidden: Boolean = false,
    animState: MutableState<ItemPosition>? = null,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = hintText,
            maxLines = MAX_LINE,
            color = hintTextColor,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(DEFAULT_PADDING)
                .widthIn(max = (TOOLTIP_MAX_WIDTH_PERCENT * screenWidth).dp)
        )
        if (!isDismissButtonHidden)
            Text(
                text = dismissHintText,
                color = dismissHintTextColor,
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(DEFAULT_PADDING)
                    .clickable {
                        isHintVisible.value = !isHintVisible.value
                        if (animState != null) {
                            animState.value = when (animState.value) {
                                ItemPosition.Start -> ItemPosition.Finish
                                ItemPosition.Finish -> ItemPosition.Start
                            }
                        }
                    }
            )
    }
}
