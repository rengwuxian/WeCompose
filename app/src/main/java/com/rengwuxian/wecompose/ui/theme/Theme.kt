package com.rengwuxian.wecompose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorPalette = WeComposeColors(
  bottomBar = white1,
  background = white2,
  listItem = white,
  divider = white3,
  chatPage = white2,
  textPrimary = black3,
  textPrimaryMe = black3,
  textSecondary = grey1,
  onBackground = grey3,
  icon = black,
  iconCurrent = green3,
  badge = red1,
  onBadge = white,
  bubbleMe = green1,
  bubbleOthers = white,
  textFieldBackground = white,
  more = grey4,
  chatPageBgAlpha = 0f,
)

private val DarkColorPalette = WeComposeColors(
  bottomBar = black1,
  background = black2,
  listItem = black3,
  divider = black4,
  chatPage = black2,
  textPrimary = white4,
  textPrimaryMe = black6,
  textSecondary = grey1,
  onBackground = grey1,
  icon = white5,
  iconCurrent = green3,
  badge = red1,
  onBadge = white,
  bubbleMe = green2,
  bubbleOthers = black5,
  textFieldBackground = black7,
  more = grey5,
  chatPageBgAlpha = 0f,
)

private val NewYearColorPalette = WeComposeColors(
  bottomBar = red4,
  background = red5,
  listItem = red2,
  divider = red3,
  chatPage = red5,
  textPrimary = white,
  textPrimaryMe = black6,
  textSecondary = grey2,
  onBackground = grey2,
  icon = white5,
  iconCurrent = green3,
  badge = yellow1,
  onBadge = black3,
  bubbleMe = green2,
  bubbleOthers = red6,
  textFieldBackground = red7,
  more = red8,
  chatPageBgAlpha = 1f,
)

@Immutable
class WeComposeColors(
  val bottomBar: Color,
  val background: Color,
  val listItem: Color,
  val divider: Color,
  val chatPage: Color,
  val textPrimary: Color,
  val textPrimaryMe: Color,
  val textSecondary: Color,
  val onBackground: Color,
  val icon: Color,
  val iconCurrent: Color,
  val badge: Color,
  val onBadge: Color,
  val bubbleMe: Color,
  val bubbleOthers: Color,
  val textFieldBackground: Color,
  val more: Color,
  val chatPageBgAlpha: Float,
)

private val LocalWeComposeColors = staticCompositionLocalOf<WeComposeColors> {
  LightColorPalette
}

object WeComposeTheme {
  val colors: WeComposeColors
    @Composable
    get() = LocalWeComposeColors.current

  enum class Theme {
    Light, Dark, NewYear
  }
}

@Composable
fun ProvideWeComposeColors(
  colors: WeComposeColors,
  content: @Composable () -> Unit,
) {
  CompositionLocalProvider(LocalWeComposeColors provides colors, content = content)
}

@Composable
fun WeComposeTheme(
  theme: WeComposeTheme.Theme = if (isSystemInDarkTheme()) WeComposeTheme.Theme.Dark else WeComposeTheme.Theme.Light,
  content: @Composable () -> Unit,
) {
  val targetColors = when (theme) {
    WeComposeTheme.Theme.Light -> LightColorPalette
    WeComposeTheme.Theme.Dark -> DarkColorPalette
    WeComposeTheme.Theme.NewYear -> NewYearColorPalette
  }

  val colors = WeComposeColors(
    bottomBar = animateColorAsState(targetColors.bottomBar, tween(600)).value,
    background = animateColorAsState(targetColors.background, tween(600)).value,
    listItem = animateColorAsState(targetColors.listItem, tween(600)).value,
    divider = animateColorAsState(targetColors.divider, tween(600)).value,
    chatPage = animateColorAsState(targetColors.chatPage, tween(600)).value,
    textPrimary = animateColorAsState(targetColors.textPrimary, tween(600)).value,
    textPrimaryMe = animateColorAsState(targetColors.textPrimaryMe, tween(600)).value,
    textSecondary = animateColorAsState(targetColors.textSecondary, tween(600)).value,
    onBackground = animateColorAsState(targetColors.onBackground, tween(600)).value,
    icon = animateColorAsState(targetColors.icon, tween(600)).value,
    iconCurrent = animateColorAsState(targetColors.iconCurrent, tween(600)).value,
    badge = animateColorAsState(targetColors.badge, tween(600)).value,
    onBadge = animateColorAsState(targetColors.onBadge, tween(600)).value,
    bubbleMe = animateColorAsState(targetColors.bubbleMe, tween(600)).value,
    bubbleOthers = animateColorAsState(targetColors.bubbleOthers, tween(600)).value,
    textFieldBackground = animateColorAsState(targetColors.textFieldBackground, tween(600)).value,
    more = animateColorAsState(targetColors.more, tween(600)).value,
    chatPageBgAlpha = animateFloatAsState(targetColors.chatPageBgAlpha, tween(600)).value,
  )

  ProvideWeComposeColors(colors) {
    MaterialTheme(
      typography = Typography,
      content = content
    )
  }
}