package com.deepudinesh.tusmindspace.screens

import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deepudinesh.tusmindspace.components.JournalHistoryCard
import com.deepudinesh.tusmindspace.data.JournalEntry
import com.deepudinesh.tusmindspace.viewmodels.JournalViewModel
import java.text.SimpleDateFormat
import java.util.*

private fun dayHeader(millis: Long): String {
    val date = SimpleDateFormat("d MMM", Locale.getDefault()).format(Date(millis))
    return when {
        DateUtils.isToday(millis) -> "TODAY, $date"
        DateUtils.isToday(millis + DateUtils.DAY_IN_MILLIS) -> "YESTERDAY, $date"
        else -> SimpleDateFormat("EEEE, d MMM", Locale.getDefault()).format(Date(millis)).uppercase()
    }
}

@Composable
fun JournalHistoryScreen(
    onBack: () -> Unit,
    onOpen: (Int) -> Unit,
    viewModel: JournalViewModel = viewModel()
) {
    val entries by viewModel.entries.collectAsStateWithLifecycle()
    val grouped = entries.groupBy { dayHeader(it.createdAt) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("History", style = MaterialTheme.typography.headlineMedium)
        }

        if (entries.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    "No journal entries yet",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                grouped.forEach { (header, dayEntries) ->
                    item(key = header) {
                        Text(
                            header,
                            style = MaterialTheme.typography.labelLarge,
                            letterSpacing = 3.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    items(dayEntries, key = { it.id }) { entry ->
                        JournalHistoryCard(entry, onClick = { onOpen(entry.id) })
                    }
                }
            }
        }
    }
}