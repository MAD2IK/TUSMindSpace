package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.MoodFacePicker

@Composable
fun CheckInScreen(
    onBack: () -> Unit,
    onDone: (mood: Int) -> Unit
) {
    var selectedMood by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }

        Spacer(Modifier.weight(1f))

        Text(
            "How are you feeling today?",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(32.dp))
        MoodFacePicker(
            selected = selectedMood,
            onSelect = { selectedMood = it }
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { onDone(selectedMood) },
            enabled = selectedMood != 0,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
            Spacer(Modifier.width(4.dp))
            Text("Continue")
        }

        Spacer(Modifier.height(48.dp))
    }
}