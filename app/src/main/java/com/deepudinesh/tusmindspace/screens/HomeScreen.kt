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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // landscape scroll prep for tablet view
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
            // Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Home", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.weight(1f))
            }

            // Streak card
            Card(Modifier.fillMaxWidth()) {
                Row(
                    Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SolidCircleWithImageVectorIcon(Icons.Default.Person,28, circleSize = 54)
                    Spacer(Modifier.width(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("🔥 Streak: 4 days")
                    }
                }
            }

            // Meditate & Journaling quick nav buttons on home screen
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionCard("Meditate", Modifier.weight(1f), Icons.Default.Favorite)
                ActionCard("Log mood", Modifier.weight(1f), Icons.Default.Edit)
            }

            // Today's quote
            Text("Today's quote", style = MaterialTheme.typography.titleSmall)
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)

                ) {
                    Text("Placeholder quote")
                }
            }

            // Community affirmations
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Community affirmations", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.weight(1f))
                FilledTonalButton (onClick = { }) { Text("+ add") }
            }
            repeat(3) {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Placeholder Affirmation")
                    }
                }
            }
        }
    }

