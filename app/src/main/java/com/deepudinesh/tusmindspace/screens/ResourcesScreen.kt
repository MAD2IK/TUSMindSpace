package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Accessible
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.deepudinesh.tusmindspace.components.EventRow
import com.deepudinesh.tusmindspace.components.ResourcesRow
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
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Map", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        ResourcesRow(
            icon = Icons.Filled.Psychology,
            title = "Student Counselling",
            subtitle = "Free & confidential · Mon–Fri 9–5"
        )
        ResourcesRow(
            icon = Icons.Filled.LocalHospital,
            title = "Student Health Centre",
            subtitle = "Nurse & GP appointments"
        )
        ResourcesRow(
            icon = Icons.Filled.Accessible,
            title = "Disability & Access Office",
            subtitle = "Learning supports & accommodations"
        )
        ResourcesRow(
            icon = Icons.Filled.SelfImprovement,
            title = "Chaplaincy",
            subtitle = "Pastoral care for all beliefs"
        )
        ResourcesRow(
            icon = Icons.Filled.Groups,
            title = "Students' Union Welfare",
            subtitle = "Peer support & advocacy"
        )
        ResourcesRow(
            icon = Icons.Filled.School,
            title = "Academic Support",
            subtitle = "Study skills & writing help"
        )
        ResourcesRow(
            icon = Icons.Filled.Phone,
            title = "Crisis Support",
            subtitle = "Samaritans 116 123 · Text 50808"
        )


    }
}

@Composable
fun CampusEventsScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        EventRow("12", "NOV", "Mindfulness Drop-in", "1:00 PM · Library Room 2")
        EventRow("14", "NOV", "Coffee & Chat", "11:00 AM · Student Centre")
        EventRow("19", "NOV", "Exam Stress Workshop", "4:00 PM · Lecture Hall B")
        EventRow("21", "NOV", "Movie Night", "7:00 PM · SU Common Room")
        EventRow("27", "NOV", "Walk & Talk", "12:30 PM · Main Entrance")
    }
}