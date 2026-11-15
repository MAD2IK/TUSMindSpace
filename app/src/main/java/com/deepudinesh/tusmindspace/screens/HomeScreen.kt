package com.deepudinesh.tusmindspace.screens

import android.os.Build
import androidx.annotation.RequiresApi
import com.deepudinesh.tusmindspace.components.ActionCard
import com.deepudinesh.tusmindspace.components.SolidCircleWithImageVectorIcon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deepudinesh.tusmindspace.components.QuoteCard

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val quotes by viewModel.quotes.collectAsStateWithLifecycle()
    var quoteIndex by rememberSaveable { mutableIntStateOf(LocalDate.now().dayOfYear) }
    val quote = quotes.getOrNull(quoteIndex % quotes.size.coerceAtLeast(1))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Title
        Text("TUSMindSpace", style = MaterialTheme.typography.headlineMedium)

        // Streak card
        Card(Modifier.fillMaxWidth()) {
            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                SolidCircleWithImageVectorIcon(Icons.Default.Person, 28, circleSize = 54)
                Spacer(Modifier.width(12.dp))
                Text("🔥 Streak: 4 days")
            }
        }

        // Quick nav
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ActionCard("Meditate", Modifier.weight(1f), Icons.Default.Favorite) { onNavigate("Meditate") }
            ActionCard("Journal", Modifier.weight(1f), Icons.Default.Edit) { onNavigate("Journal") }
        }

        // Today's quote
        QuoteCard(quote = quote, onNext = { quoteIndex++ })
    }
}