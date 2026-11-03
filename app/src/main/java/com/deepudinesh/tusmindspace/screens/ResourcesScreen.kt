package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.deepudinesh.tusmindspace.components.ToggleablePills

@Composable
fun ResourcesScreen() {
    val tabs = listOf("Services", "Events")
    var tab by rememberSaveable { mutableStateOf(tabs.first()) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Resources", style = MaterialTheme.typography.headlineMedium)
        ToggleablePills(
            options = listOf("Services","Events"),
            selected = tab,
            onSelect = {tab = it}
            )

        when (tab) {
            "Services" -> ServicesScreen()
            "Events" -> CampusEventsScreen()
        }
    }
}

@Composable
fun ServicesScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Services placeholder")
    }
}

@Composable
fun CampusEventsScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Campus events placeholder")
    }
}