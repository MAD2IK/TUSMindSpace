package com.deepudinesh.tusmindspace.screens

import com.deepudinesh.tusmindspace.components.ActionCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.CheckInCard
import com.deepudinesh.tusmindspace.components.HistoryRow
import com.deepudinesh.tusmindspace.components.HomeTopBar
import com.deepudinesh.tusmindspace.components.MoodBarChart
import android.text.format.DateFormat
import android.text.format.DateUtils
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deepudinesh.tusmindspace.components.StreakDialog
import com.deepudinesh.tusmindspace.components.moodOptions
import com.deepudinesh.tusmindspace.viewmodels.HomeViewModel
import androidx.compose.runtime.setValue

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onCheckIn: () -> Unit,
    onProfile: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val streak by viewModel.streak.collectAsStateWithLifecycle()
    val week by viewModel.week.collectAsStateWithLifecycle()
    val checkIns by viewModel.checkIns.collectAsStateWithLifecycle()
    var showStreak by rememberSaveable { mutableStateOf(false) }

    if (showStreak) {
        StreakDialog(streak = streak, onDismiss = { showStreak = false })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HomeTopBar(
            streakDays = streak,
            onProfileClick = onProfile,
            onStreakClick = { showStreak = true }
        )

        CheckInCard(onBegin = onCheckIn)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ActionCard("Meditate", Modifier.weight(1f), Icons.Default.Favorite) { onNavigate("Meditate") }
            ActionCard("Journal", Modifier.weight(1f), Icons.Default.Edit) { onNavigate("Journal") }
        }

        Text("History", style = MaterialTheme.typography.titleMedium)
        if (checkIns.isEmpty()) {
            Text(
                "No check-ins yet",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            MoodBarChart(values = week)
            Column {
                checkIns.take(5).forEach { checkIn ->
                    val day = if (DateUtils.isToday(checkIn.createdAt)) "Today"
                    else DateFormat.format("EEE", checkIn.createdAt).toString()
                    HistoryRow(day, moodOptions.find { it.value == checkIn.mood }?.label ?: "")
                }
            }
        }
    }
}