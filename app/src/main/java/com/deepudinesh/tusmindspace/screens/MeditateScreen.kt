package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Palette
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
            title = "Energy",
            subtitle = "3 min · Boost your energy and increase alertness through breath",
            icon = Icons.Default.LocalFireDepartment,
            onPlay = { onStart("Energy", 3) }
        )
        SessionCard(
            title = "Creativity",
            subtitle = "5 min · Clear your mind, improve focus and creativity",
            icon = Icons.Default.Palette,
            onPlay = { onStart("Creativity", 5) }
        )
        SessionCard(
            title = "Focus",
            subtitle = "10 min · Clear your mind, relax your body, and improve your focus",
            icon = Icons.Default.CenterFocusStrong,
            onPlay = { onStart("Focus", 10) }
        )

        Text("History", style = MaterialTheme.typography.titleMedium)
        Column {
            HistoryRow("Mon", "Energy", 3)
            HistoryRow("Sun", "Creativity", 5)
            HistoryRow("Sat", "Focus", 10)
        }

    }
}