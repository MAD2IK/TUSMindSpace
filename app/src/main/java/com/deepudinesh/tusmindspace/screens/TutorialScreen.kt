package com.deepudinesh.tusmindspace.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.deepudinesh.tusmindspace.components.TutorialPage
import com.deepudinesh.tusmindspace.components.TutorialPager
import com.deepudinesh.tusmindspace.data.ProfilePrefs

private val appPages = listOf(
    TutorialPage(Icons.Filled.SelfImprovement, "Welcome to TUS MindSpace", "A quiet space to check in with yourself, breathe and reflect."),
    TutorialPage(Icons.Filled.Favorite, "Check in & meditate", "Log your mood each day, build a streak and take a few minutes for a guided breathing session."),
    TutorialPage(Icons.Filled.Edit, "Journal", "Write down what's on your mind. Your entries stay private on this device."),
    TutorialPage(Icons.Filled.LocationOn, "Campus resources", "Find student services and wellbeing events on the campus map."),
    TutorialPage(Icons.Filled.Call, "Reach out", "Contact counselling or crisis lines in one tap whenever you need support.")
)

@Composable
fun TutorialScreen(onFinish: () -> Unit) {
    val context = LocalContext.current
    TutorialPager(pages = appPages, finishLabel = "Get started") {
        ProfilePrefs.setTutorialSeen(context)
        onFinish()
    }
}