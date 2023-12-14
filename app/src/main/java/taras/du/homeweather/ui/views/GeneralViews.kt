package taras.du.homeweather.ui.views

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val TitleTextStyle = TextStyle(color = Color.Black, fontSize = 18.sp)
val NormalTextStyle = TextStyle(color = Color.DarkGray, fontSize = 14.sp)

@Composable
fun TitleText(modifier: Modifier = Modifier, text: String, onClick: () -> Unit = {}) {
    ClickableText(
        modifier = modifier,
        text = AnnotatedString(text = text),
        style = TitleTextStyle,
        onClick = { onClick() })
}

@Composable
fun NormalText(modifier: Modifier = Modifier, text: String, onClick: () -> Unit = {}) {
    ClickableText(
        modifier = modifier,
        text = AnnotatedString(text = text),
        style = NormalTextStyle,
        onClick = { onClick() })
}