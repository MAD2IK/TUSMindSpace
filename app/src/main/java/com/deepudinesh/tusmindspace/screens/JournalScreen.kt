package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.HistoryRow
import com.deepudinesh.tusmindspace.components.MoodBarChart
import com.deepudinesh.tusmindspace.components.MoodFacePicker

@Composable
fun JournalScreen() {
    var selectedMood by rememberSaveable { mutableIntStateOf(3) }
    var message by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Journal", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(4.dp))

        Text("How are you feeling today?", style = MaterialTheme.typography.titleMedium)
        MoodFacePicker(
            selected = selectedMood,
            onSelect = { selectedMood = it }
        )

        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message") },
            minLines = 4,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Spacer(Modifier.width(8.dp))
            Text("Save Entry")
        }
        Spacer(Modifier.width(4.dp))

        Text("History", style = MaterialTheme.typography.titleMedium)
        MoodBarChart(
            values = listOf(
                "Mon" to 2, "Tue" to 3, "Wed" to 1, "Thu" to 3,
                "Fri" to 2, "Sat" to 3, "Sun" to 1
            )
        )
        Column {
            HistoryRow("Today", "Poor · deadline on Friday", onClick = { })
            HistoryRow("Tue", "Good · good lab session", onClick = { })
        }
    }
}