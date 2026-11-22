package com.deepudinesh.tusmindspace.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.ReminderDialog
import com.deepudinesh.tusmindspace.components.ResourcesRow
import com.deepudinesh.tusmindspace.components.SolidCircleWithImageVectorIcon
import com.deepudinesh.tusmindspace.data.ProfilePrefs
import com.deepudinesh.tusmindspace.notifications.ReminderScheduler

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    var name by rememberSaveable { mutableStateOf(ProfilePrefs.name(context)) }
    var course by rememberSaveable { mutableStateOf(ProfilePrefs.course(context)) }
    var year by rememberSaveable { mutableStateOf(ProfilePrefs.year(context)) }
    var saved by rememberSaveable { mutableStateOf(false) }

    var reminderOn by rememberSaveable { mutableStateOf(ReminderScheduler.isEnabled(context)) }
    var reminderHour by rememberSaveable { mutableStateOf(ReminderScheduler.hour(context)) }
    var reminderMinute by rememberSaveable { mutableStateOf(ReminderScheduler.minute(context)) }
    var showReminder by rememberSaveable { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    if (showReminder) {
        ReminderDialog(
            enabled = reminderOn,
            hour = reminderHour,
            minute = reminderMinute,
            onSave = { on, hour, minute ->
                ReminderScheduler.save(context, on, hour, minute)
                if (on && Build.VERSION.SDK_INT >= 33) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                reminderOn = on
                reminderHour = hour
                reminderMinute = minute
                showReminder = false
            },
            onDismiss = { showReminder = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text("Profile", style = MaterialTheme.typography.headlineMedium)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SolidCircleWithImageVectorIcon(icon = Icons.Filled.Person, iconSize = 48, circleSize = 96)
            Text(name.ifBlank { "Anonymous student" }, style = MaterialTheme.typography.titleLarge)
            Text(
                "Signed in anonymously",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text("Your details", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = name,
            onValueChange = { name = it; saved = false },
            label = { Text("Display name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = course,
            onValueChange = { course = it; saved = false },
            label = { Text("Course") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = year,
            onValueChange = { year = it; saved = false },
            label = { Text("Year of study") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                ProfilePrefs.save(context, name.trim(), course.trim(), year.trim())
                saved = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (saved) "Saved" else "Save details")
        }

        HorizontalDivider()

        Text("Settings", style = MaterialTheme.typography.titleMedium)
        ResourcesRow(
            icon = Icons.Filled.Notifications,
            title = "Daily reminder",
            subtitle = if (reminderOn) "On · %02d:%02d".format(reminderHour, reminderMinute) else "Off",
            onClick = { showReminder = true }
        )
    }
}