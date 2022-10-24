package com.lalosapps.composeanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lalosapps.composeanimations.ui.theme.ComposeAnimationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationsTheme {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var isVisible by remember { mutableStateOf(false) }
                    var isRound by remember { mutableStateOf(false) }
                    Button(onClick = {
                        isVisible = !isVisible
                        isRound = !isRound
                    }) {
                        Text(text = "Toggle")
                    }
//                    MyAnimatedVisibility(
//                        isVisible = isVisible,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f)
//                    )
//                    MyAnimationAsState(isRound = isRound)
                    MyUpdateTransition(isRound = isRound)
                }
            }
        }
    }
}

@Composable
fun MyAnimatedVisibility(
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally() + fadeIn(),
        modifier = modifier
    ) {
        Box(modifier = Modifier.background(Color.Red))
    }
}

@Composable
fun MyAnimationAsState(
    isRound: Boolean
) {
    // animationSpec can be tween or spring (default built-in animations) (there is also keyframes {})
    // For spring we need to set the target value between 0..100 according to how bouncy it is.
    // Setting it too high or too low might crash the app.
    val borderRadius by animateIntAsState(
        targetValue = if (isRound) 100 else 0,
        animationSpec = tween(durationMillis = 2000)
    )
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(borderRadius))
            .background(Color.Red)
    )
}

@Composable
fun MyUpdateTransition(
    isRound: Boolean
) {
    val transition = updateTransition(targetState = isRound, label = null)
    val borderRadius by transition.animateInt(
        transitionSpec = { tween((2000)) },
        label = "borderRadius",
        targetValueByState = { round ->
            if (round) 100 else 0
        }
    )
    val color by transition.animateColor(
        transitionSpec = { tween((1000)) },
        label = "color",
        targetValueByState = { round ->
            if (round) Color.Green else Color.Red
        }
    )
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(borderRadius))
            .background(color)
    )
}