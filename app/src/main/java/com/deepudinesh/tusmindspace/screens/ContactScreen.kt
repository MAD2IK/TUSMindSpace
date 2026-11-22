package com.deepudinesh.tusmindspace.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.ContactInfoCard
import com.deepudinesh.tusmindspace.components.EmergencyCard
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

private const val SUPPORT_PHONE = "+35312345678"
private const val SUPPORT_EMAIL = "wellbeing@tus.ie"

private fun Context.launch(intent: Intent) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "No app found to handle this", Toast.LENGTH_SHORT).show()
    }
}

private fun Context.dial(number: String) =
    launch(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")))

private fun Context.sms(number: String, body: String = "") =
    launch(Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$number")).apply {
        putExtra("sms_body", body)
    })

private fun Context.email(to: String, subject: String = "", body: String = "") =
    launch(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")).apply {
        putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    })

@Composable
fun ContactScreen() {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().imePadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    )
    {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {
            Text("Contact Us", style = MaterialTheme.typography.headlineMedium)

            EmergencyCard(
                onCall = { context.dial("112") },
                onText = { context.sms("50808", "HELLO") }
            )

            ContactInfoCard(Icons.Default.Email, "Email", SUPPORT_EMAIL) { context.email(SUPPORT_EMAIL) }
            ContactInfoCard(Icons.Default.Phone, "Phone", "+353 1 234 5678") { context.dial(SUPPORT_PHONE) }
            ContactInfoCard(Icons.Default.LocationOn, "Visit Us", "Student Services, TUS Campus") {
                context.launch(Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=TUS Moylish Campus Limerick")))
            }

            Spacer(Modifier.height(8.dp))
            Text("Send us a message", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    context.email(
                        to = SUPPORT_EMAIL,
                        subject = "TUSMindSpace message from $name",
                        body = "$message\n\nFrom: $name ($email)"
                    )
                },
                enabled = name.isNotBlank() && message.isNotBlank(),
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.Default.Send, null)
                Spacer(Modifier.width(8.dp))
                Text("Send Message")
            }
        }
    }

}