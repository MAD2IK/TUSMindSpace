package com.deepudinesh.tusmindspace.screens

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deepudinesh.tusmindspace.components.QuoteCard
import com.deepudinesh.tusmindspace.data.Quote
import com.deepudinesh.tusmindspace.viewmodels.JournalViewModel
import java.time.LocalDate
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JournalScreen(viewModel: JournalViewModel = viewModel()) {
    val quotes by viewModel.quotes.collectAsStateWithLifecycle()
    var quoteIndex by rememberSaveable { mutableIntStateOf(LocalDate.now().dayOfYear) }
    val quote = quotes.getOrNull(quoteIndex % quotes.size.coerceAtLeast(1))

    var writing by rememberSaveable { mutableStateOf(false) }
    var message by rememberSaveable { mutableStateOf("") }

    BackHandler(enabled = writing) { writing = false }

    if (writing) {
        JournalWriting(
            quote = quote,
            message = message,
            onMessageChange = { message = it },
            onBack = { writing = false },
            onSave = { writing = false },
            onCancel = { message = ""; writing = false }
        )
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Journal", style = MaterialTheme.typography.headlineMedium)

        if (quotes.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val pagerState = rememberPagerState(initialPage = quoteIndex % quotes.size) { quotes.size }

            LaunchedEffect(pagerState.currentPage) {
                quoteIndex = pagerState.currentPage
            }

            VerticalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),
                pageSpacing = 16.dp
            ) { page ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    QuoteCard(
                        quote = quotes[page],
                        onBegin = {
                            quoteIndex = page
                            writing = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun JournalWriting(
    quote: Quote?,
    message: String,
    onMessageChange: (String) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp)
        ) {
            if (quote != null) {
                Row(Modifier.height(IntrinsicSize.Min)) {
                    Box(
                        Modifier
                            .width(2.dp)
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.outline)
                    )
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            "“${quote.text}”",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            quote.author,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            val textStyle = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
            BasicTextField(
                value = message,
                onValueChange = onMessageChange,
                textStyle = textStyle,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { inner ->
                    if (message.isEmpty()) {
                        Text(
                            "Start writing...",
                            style = textStyle,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    inner()
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
        ) {
            FilledTonalIconButton(
                onClick = onCancel,
                modifier = Modifier.size(56.dp)
            ) { Icon(Icons.Default.Close, contentDescription = "Discard") }
            FilledIconButton(
                onClick = onSave,
                enabled = message.isNotBlank(),
                modifier = Modifier.size(56.dp)
            ) { Icon(Icons.Default.Check, contentDescription = "Save entry") }
        }

        Spacer(Modifier.height(48.dp))
    }
}