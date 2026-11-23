package com.deepudinesh.tusmindspace.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deepudinesh.tusmindspace.components.PageDots
import com.deepudinesh.tusmindspace.components.SolidCircleWithImageVectorIcon
import com.deepudinesh.tusmindspace.data.ProfilePrefs
import kotlinx.coroutines.launch

private data class TutorialPage(val icon: ImageVector, val title: String, val body: String)

private val pages = listOf(
    TutorialPage(Icons.Filled.SelfImprovement,"Welcome to TUS MindSpace", "A quiet space to check in with yourself, breathe and reflect."),
    TutorialPage(Icons.Filled.Favorite, "Check in & meditate", "Log your mood each day, build a streak and take a few minutes for a guided breathing session."),
    TutorialPage(Icons.Filled.Edit, "Journal", "Write down what's on your mind. Your entries stay private on this device."),
    TutorialPage(Icons.Filled.LocationOn, "Campus resources", "Find student services and wellbeing events on the campus map."),
    TutorialPage(Icons.Filled.Call, "Reach out", "Contact counselling or crisis lines in one tap whenever you need support.")
)

@Composable
fun TutorialScreen(onFinish: () -> Unit) {
    val context = LocalContext.current
    val pagerState = rememberPagerState { pages.size }
    val scope = rememberCoroutineScope()
    val isLast = pagerState.currentPage == pages.lastIndex

    val finish = {
        ProfilePrefs.setTutorialSeen(context)
        onFinish()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            if (!isLast) TextButton(onClick = finish) { Text("Skip") }
        }

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
            val page = pages[index]
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                SolidCircleWithImageVectorIcon(icon = page.icon, iconSize = 64, circleSize = 140)
                Text(page.title, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
                Text(
                    page.body,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        PageDots(count = pages.size, current = pagerState.currentPage)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                if (isLast) finish()
                else scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLast) "Get started" else "Next")
        }
    }
}