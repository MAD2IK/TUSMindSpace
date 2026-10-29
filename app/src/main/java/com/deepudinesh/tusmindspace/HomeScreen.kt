package com.deepudinesh.tusmindspace

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Scaffold(
        bottomBar = {
            NavigationBar {
                repeat(5) { index ->
                    NavigationBarItem(
                        selected = index == 0,
                        onClick = { },
                        icon = { ButtonIcon(R.drawable.placeholder_img, 24) }
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
                    SolidCircleWithIcon(R.drawable.ic_launcher_foreground,56)
                    Spacer(Modifier.width(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("🔥 Streak: 4 days")
                    }
                }
            }

            // Meditate & Journaling quick nav buttons on home screen
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionCard("Meditate", Modifier.weight(1f))
                ActionCard("Log mood", Modifier.weight(1f))
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
fun ActionCard(label: String, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ButtonIcon(R.drawable.placeholder_img, 24)
            Text(label)
        }
    }
}

//Reusable button icon function
@Composable
fun ButtonIcon(@DrawableRes iconRes: Int, size: Int) {
    Icon(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = Modifier.size(size.dp)
    )
}

//Reusable button icon function
@Composable
fun SolidCircleWithIcon(@DrawableRes iconRes: Int, size: Int) {
    Box(modifier = Modifier.size(size.dp)
        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
        contentAlignment = Alignment.Center)
    {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(size.dp)
        )
    }

}