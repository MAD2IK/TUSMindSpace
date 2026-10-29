package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MeditateScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Meditate", style = MaterialTheme.typography.headlineMedium)
        Card(Modifier.fillMaxWidth()) { Text("Breathing reset · 3 min", Modifier.padding(12.dp)) }
        Card(Modifier.fillMaxWidth()) { Text("Exam calm · 5 min", Modifier.padding(12.dp)) }
        Card(Modifier.fillMaxWidth()) { Text("Body scan · 10 min", Modifier.padding(12.dp)) }
    }
}