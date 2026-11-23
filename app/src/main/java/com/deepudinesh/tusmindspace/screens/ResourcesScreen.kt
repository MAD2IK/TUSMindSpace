package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.deepudinesh.tusmindspace.components.ResourcesRow
import com.deepudinesh.tusmindspace.components.ToggleablePills
import org.osmdroid.util.GeoPoint
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deepudinesh.tusmindspace.components.CampusMapCard
import com.deepudinesh.tusmindspace.components.openInMaps
import com.deepudinesh.tusmindspace.components.serviceIcon
import com.deepudinesh.tusmindspace.viewmodels.ResourcesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import com.deepudinesh.tusmindspace.components.isWideScreen

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
fun ServicesScreen(viewModel: ResourcesViewModel = viewModel()) {
    val services by viewModel.services.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val wide = isWideScreen()
    val pins = remember(services) {
        services.mapNotNull { s ->
            if (s.lat != null && s.lon != null) MapPin(GeoPoint(s.lat, s.lon), s.title, s.subtitle) else null
        }
    }

    val list: @Composable () -> Unit = {
        services.forEach { s ->
            ResourcesRow(
                icon = serviceIcon(s.icon),
                title = s.title,
                subtitle = s.subtitle,
                onClick = {
                    if (s.lat != null && s.lon != null) openInMaps(context, s.lat, s.lon, s.title)
                }
            )
        }
    }

    if (wide) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            CampusMapCard(pins, Modifier.weight(1f).fillMaxHeight())
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) { list() }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CampusMapCard(pins)
            list()
        }
    }
}

@Composable
fun CampusEventsScreen(viewModel: ResourcesViewModel = viewModel()) {
    val events by viewModel.events.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val wide = isWideScreen()
    val dayFmt = remember { SimpleDateFormat("dd", Locale.getDefault()) }
    val monthFmt = remember { SimpleDateFormat("MMM", Locale.getDefault()) }
    val timeFmt = remember { SimpleDateFormat("h:mm a", Locale.getDefault()) }
    val pins = remember(events) {
        events.map { MapPin(GeoPoint(it.lat, it.lon), it.title, it.location) }
    }

    val list: @Composable (Modifier) -> Unit = { modifier ->
        LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(events, key = { it.id }) { event ->
                val date = Date(event.startMillis)
                EventRow(
                    day = dayFmt.format(date),
                    month = monthFmt.format(date).uppercase(),
                    title = event.title,
                    details = "${timeFmt.format(date)} · ${event.location}",
                    onClick = { openInMaps(context, event.lat, event.lon, event.title) }
                )
            }
        }
    }

    if (wide) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            CampusMapCard(pins, Modifier.weight(1f).fillMaxHeight())
            list(Modifier.weight(1f))
        }
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            CampusMapCard(pins)
            list(Modifier)
        }
    }
}