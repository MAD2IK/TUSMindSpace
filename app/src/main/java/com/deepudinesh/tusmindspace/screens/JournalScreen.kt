package com.deepudinesh.tusmindspace.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deepudinesh.tusmindspace.components.QuoteCard
import com.deepudinesh.tusmindspace.viewmodels.JournalViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JournalScreen(viewModel: JournalViewModel = viewModel()) {
    val quotes by viewModel.quotes.collectAsStateWithLifecycle()
    var quoteIndex by rememberSaveable { mutableIntStateOf(LocalDate.now().dayOfYear) }
    val quote = quotes.getOrNull(quoteIndex % quotes.size.coerceAtLeast(1))

    var writing by rememberSaveable { mutableStateOf(false) }
    var message by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Journal", style = MaterialTheme.typography.headlineMedium)

        if (!writing) {
            QuoteCard(
                quote = quote,
                onNext = { quoteIndex++ },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { writing = true },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) { Text("Begin journalling") }
        } else {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("What's on your mind?") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            Button(
                onClick = { writing = false },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) { Text("Save Entry") }
            TextButton(
                onClick = { writing = false },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Cancel") }
        }
    }
}