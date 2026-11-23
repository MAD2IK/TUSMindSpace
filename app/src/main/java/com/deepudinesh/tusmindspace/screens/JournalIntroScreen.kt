package com.deepudinesh.tusmindspace.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.deepudinesh.tusmindspace.components.TutorialPage
import com.deepudinesh.tusmindspace.components.TutorialPager
import com.deepudinesh.tusmindspace.data.ProfilePrefs

private val journalPages = listOf(
    TutorialPage(Icons.Filled.Edit, "Your journal", "Journalling helps you notice patterns in how you feel. A few lines a day is plenty."),
    TutorialPage(Icons.Filled.FormatQuote, "Start from a prompt", "Stuck on what to write? Use the quote at the top as a starting point, or just write freely."),
    TutorialPage(Icons.Filled.History, "Look back", "Open History to read past entries, grouped by day."),
    TutorialPage(Icons.Filled.Lock, "Private to you", "Entries are stored only on this device. Nobody else can read them.")
)

@Composable
fun JournalIntroScreen(onFinish: () -> Unit) {
    val context = LocalContext.current
    TutorialPager(pages = journalPages, finishLabel = "Start writing") {
        ProfilePrefs.setJournalIntroSeen(context)
        onFinish()
    }
}