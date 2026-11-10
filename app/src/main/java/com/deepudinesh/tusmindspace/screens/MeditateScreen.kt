package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.HistoryRow
import com.deepudinesh.tusmindspace.components.SessionCard
import com.deepudinesh.tusmindspace.components.ToggleablePills

@Composable
fun MeditateScreen(onStart: (title: String, mins: Int) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Meditations", style = MaterialTheme.typography.headlineMedium)

        SessionCard(
            title = "Breathing reset",
            subtitle = "3 min · Slow your breath",
            icon = Icons.Default.Refresh,
            onPlay = { onStart("Breathing reset", 3) }
        )
        SessionCard(
            title = "Exam calm",
            subtitle = "5 min · Settle pre-exam nerves",
            icon = Icons.Default.Star,
            onPlay = { onStart("Exam calm", 5) }
        )
        SessionCard(
            title = "Body scan",
            subtitle = "10 min · Release tension head to toe",
            icon = Icons.Default.Face,
            onPlay = { onStart("Body scan", 10) }
        )

        Text("History", style = MaterialTheme.typography.titleMedium)
        Column {
            HistoryRow("Mon", "Breathing reset", 3)
            HistoryRow("Sun", "Exam calm", 5)
            HistoryRow("Sat", "Body scan", 10)
        }
    }
}