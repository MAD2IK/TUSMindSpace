package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import com.deepudinesh.tusmindspace.components.SolidCircleWithImageVectorIcon
import com.deepudinesh.tusmindspace.data.ProfilePrefs

@Composable
fun LoginScreen(onContinue: () -> Unit) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var accepted by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(Modifier.height(32.dp))
        SolidCircleWithImageVectorIcon(icon = Icons.Filled.SelfImprovement, iconSize = 48, circleSize = 96)
        Text("TUS MindSpace", style = MaterialTheme.typography.headlineMedium)
        Text(
            "A quiet space to check in, breathe and reflect.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Display name (optional)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Before you start", style = MaterialTheme.typography.titleMedium)
                Text(
                    "TUS MindSpace is a self-help tool and is not a substitute for professional " +
                            "medical or mental health care. If you are in crisis, contact Samaritans " +
                            "on 116 123, text 50808, or call 112 / 999 in an emergency.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = accepted, onCheckedChange = { accepted = it })
                    Text("I understand", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Button(
            onClick = {
                ProfilePrefs.signIn(context, name.trim())
                onContinue()
            },
            enabled = accepted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue anonymously")
        }

        Text(
            "No email or password needed. Your data stays on this device.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}