package com.deepudinesh.tusmindspace.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

data class BreathPattern(
    val inhaleMs: Int = 4000,
    val holdInMs: Int = 0,
    val exhaleMs: Int = 4000,
    val holdOutMs: Int = 0
)

val breathPatterns = mapOf(
    "Energy" to BreathPattern(inhaleMs = 2000, exhaleMs = 2000),
    "Creativity" to BreathPattern(inhaleMs = 4000, holdInMs = 2000, exhaleMs = 6000),
    "Focus" to BreathPattern(4000, 4000, 4000, 4000)
)

@Composable
fun BreathingCircle(
    running: Boolean,
    modifier: Modifier = Modifier,
    pattern: BreathPattern = BreathPattern(),
    introMs: Int = 4000
) {
    val intro = remember { Animatable(0f) }
    val reveal = remember { Animatable(0f) }
    val pulse = remember { Animatable(0f) }
    var label by remember { mutableStateOf("Inhale") }

    val colors = MaterialTheme.colorScheme
    val gradient = listOf(colors.primaryContainer, colors.primary)
    val ease = FastOutSlowInEasing

    LaunchedEffect(running) {
        if (!running) return@LaunchedEffect
        intro.animateTo(1f, tween(introMs, easing = LinearEasing))
        reveal.animateTo(1f, tween(1000))
        while (true) {
            label = "Inhale"
            pulse.animateTo(1f, tween(pattern.inhaleMs, easing = ease))
            if (pattern.holdInMs > 0) { label = "Hold"; delay(pattern.holdInMs.toLong()) }
            label = "Exhale"
            pulse.animateTo(0f, tween(pattern.exhaleMs, easing = ease))
            if (pattern.holdOutMs > 0) { label = "Hold"; delay(pattern.holdOutMs.toLong()) }
        }
    }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Canvas(Modifier.fillMaxWidth().aspectRatio(1f)) {
            val stroke = 1.5.dp.toPx()
            val outerR = size.minDimension / 2 - stroke
            val innerR = outerR * 0.67f
            val halo = (outerR - innerR) * 0.25f
            val r = lerp(innerR, outerR - halo, pulse.value)

            drawCircle(colors.outlineVariant, outerR, style = Stroke(stroke))
            drawCircle(colors.primary.copy(alpha = 0.12f), r + halo, alpha = reveal.value)
            drawCircle(Brush.radialGradient(gradient, center, r), r, alpha = reveal.value)
            drawCircle(colors.outline, innerR, style = Stroke(stroke))
            drawArc(
                color = colors.onSurface,
                startAngle = -90f,
                sweepAngle = 360f * intro.value,
                useCenter = false,
                topLeft = Offset(center.x - innerR, center.y - innerR),
                size = Size(innerR * 2, innerR * 2),
                style = Stroke(stroke, cap = StrokeCap.Round),
                alpha = 1f - reveal.value
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                if (reveal.value < 1f) "Get comfortable" else label,
                style = MaterialTheme.typography.headlineSmall
            )
            if (reveal.value < 1f) {
                Text(
                    "Sit or lie down in a position to breathe comfortably",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}