package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import android.text.format.DateFormat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.HistoryRow
import com.deepudinesh.tusmindspace.components.SessionCard
import com.deepudinesh.tusmindspace.viewmodels.MeditateViewModel

@Composable
fun MeditateScreen(
    onStart: (title: String, mins: Int) -> Unit,
    viewModel: MeditateViewModel = viewModel()
) {
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

        val history by viewModel.history.collectAsStateWithLifecycle()
        Text("History", style = MaterialTheme.typography.titleMedium)
        Column {
            if (history.isEmpty()) {
                Text(
                    "No sessions yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            history.forEach {
                HistoryRow(DateFormat.format("EEE", it.completedAt).toString(), it.title, it.mins)
            }
        }
    }
}