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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import com.deepudinesh.tusmindspace.components.CircleButtonsWithText
import com.deepudinesh.tusmindspace.components.HistoryRow
import com.deepudinesh.tusmindspace.components.MoodBarChart

@Composable
fun JournalScreen() {
    var selectedMood by rememberSaveable { mutableIntStateOf(5) }
    var message by rememberSaveable { mutableStateOf("")}

        Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Journal", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(4.dp))

        Text("How are you feeling today?", style = MaterialTheme.typography.titleMedium)
        CircleButtonsWithText(
            options = listOf(1,2,3,4,5),
            selected = selectedMood,
            onSelect = {selectedMood = it}
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
                "Mon" to 3, "Tue" to 4, "Wed" to 2, "Thu" to 5,
                "Fri" to 3, "Sat" to 4, "Sun" to 2
            )
        )
        Column {
            HistoryRow("Today", "2/5 · deadline on Friday", onClick = { })
            HistoryRow("Tue", "4/5 · good lab session", onClick = { })
        }

    }
}