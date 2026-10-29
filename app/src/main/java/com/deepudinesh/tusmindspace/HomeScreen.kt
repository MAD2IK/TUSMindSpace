package com.deepudinesh.tusmindspace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun HomeScreen() {

    val navTabs = mapOf(
        "Home" to Icons.Default.Home,
        "Meditate" to Icons.Default.Favorite, // Meditate placeholder
        "Journal" to Icons.Default.Edit, // Journal placeholder
        "Resources" to Icons.Default.LocationOn, // Resources
        "Contact" to Icons.Default.Call // Call
    )
    var selectedTab by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                navTabs.forEach { (label, icon) ->
                    NavigationBarItem(
                        selected = label == selectedTab,
                        onClick = { selectedTab = label },
                        icon = { ButtonIcon(icon, 24) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
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
}

@Composable
fun ActionCard(label: String, modifier: Modifier = Modifier,icon: ImageVector) {
    Card(modifier) {
        Column(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ButtonIcon(icon, 24)
            Text(label)
        }
    }
}

@Composable
fun ButtonIcon(icon: ImageVector, iconSize: Int)
{
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier.size(iconSize.dp)
    )
}

//Reusable button icon function
@Composable
fun SolidCircleWithImageVectorIcon(icon: ImageVector, iconSize: Int, circleSize: Int) {
    Box(
        modifier = Modifier
            .size(circleSize.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(iconSize.dp)
        )
    }
}
