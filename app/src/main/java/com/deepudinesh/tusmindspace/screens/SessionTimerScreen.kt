package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.BreathPattern
import com.deepudinesh.tusmindspace.components.BreathingCircle
import com.deepudinesh.tusmindspace.components.breathPatterns
import kotlinx.coroutines.delay

@Composable
fun SessionTimerScreen(title: String, mins: Int, onBack: () -> Unit) {
    val total = mins * 60
    var elapsed by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (elapsed < total) {
            delay(1000)
            elapsed++
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${mmss(elapsed)} / ${mmss(total)}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            IconButton(onClick = onBack) {
                Icon(Icons.Default.Close, "Close $title")
            }
        }
        Spacer(Modifier.weight(0.3f))
        BreathingCircle(
            running = elapsed < total,
            modifier = Modifier.fillMaxWidth(0.85f),
            pattern = breathPatterns[title] ?: BreathPattern()
        )
        Spacer(Modifier.weight(1f))
    }
}

private fun mmss(s: Int) = "%02d:%02d".format(s / 60, s % 60)