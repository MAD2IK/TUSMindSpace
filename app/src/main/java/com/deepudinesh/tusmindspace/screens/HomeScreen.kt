package com.deepudinesh.tusmindspace.screens

import com.deepudinesh.tusmindspace.components.ActionCard
import com.deepudinesh.tusmindspace.components.SolidCircleWithImageVectorIcon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.CheckInCard
import com.deepudinesh.tusmindspace.components.HistoryRow
import com.deepudinesh.tusmindspace.components.HomeTopBar
import com.deepudinesh.tusmindspace.components.MoodBarChart
import com.deepudinesh.tusmindspace.components.MoodFacePicker

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onCheckIn: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HomeTopBar(
            streakDays = 4,
            onProfileClick = { },
            onStreakClick = { }
        )

        CheckInCard(onBegin = onCheckIn)

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ActionCard("Meditate", Modifier.weight(1f), Icons.Default.Favorite) { onNavigate("Meditate") }
            ActionCard("Journal", Modifier.weight(1f), Icons.Default.Edit) { onNavigate("Journal") }
        }

        Text("History", style = MaterialTheme.typography.titleMedium)
        MoodBarChart(
            values = listOf(
                "Mon" to 2, "Tue" to 4, "Wed" to 1, "Thu" to 5,
                "Fri" to 3, "Sat" to 4, "Sun" to 2
            )
        )
        Column {
            HistoryRow("Today", "Poor · deadline on Friday", onClick = { })
            HistoryRow("Tue", "Good · good lab session", onClick = { })
        }
    }
}