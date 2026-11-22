package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.deepudinesh.tusmindspace.components.EventRow
import com.deepudinesh.tusmindspace.components.MapPin
import com.deepudinesh.tusmindspace.components.OsmMap
import com.deepudinesh.tusmindspace.components.ResourcesRow
import com.deepudinesh.tusmindspace.components.ToggleablePills
import org.osmdroid.util.GeoPoint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deepudinesh.tusmindspace.viewmodels.ResourcesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            OsmMap(
                center = GeoPoint(52.6745, -8.6490), // TUS Moylish (approx.) — swap for your campus
                modifier = Modifier.fillMaxSize(),
                pins = listOf(MapPin(GeoPoint(52.6745, -8.6490), "Student Counselling"))
            )
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
fun CampusEventsScreen(viewModel: ResourcesViewModel = viewModel()) {
    val events by viewModel.events.collectAsStateWithLifecycle()
    val dayFmt = remember { SimpleDateFormat("dd", Locale.getDefault()) }
    val monthFmt = remember { SimpleDateFormat("MMM", Locale.getDefault()) }
    val timeFmt = remember { SimpleDateFormat("h:mm a", Locale.getDefault()) }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(events, key = { it.id }) { event ->
            val date = Date(event.startMillis)
            EventRow(
                day = dayFmt.format(date),
                month = monthFmt.format(date).uppercase(),
                title = event.title,
                details = "${timeFmt.format(date)} · ${event.location}"
            )
        }
    }
}