package com.deepudinesh.tusmindspace

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Home", style = MaterialTheme.typography.headlineMedium)
        Text("Streak: 0 days")
        Button(onClick = { }) { Text("Meditate") }
        Button(onClick = { }) { Text("Log mood") }
        Text("Today's quote goes here")
    }
}