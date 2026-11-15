package com.deepudinesh.tusmindspace.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.SentimentDissatisfied as FilledDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral as FilledNeutral
import androidx.compose.material.icons.filled.SentimentSatisfied as FilledSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied as FilledVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied as FilledVerySatisfied
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentNeutral
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material3.ButtonDefaults

data class MoodOption(
    val value: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val label: String,
    val color: Color,
    val onColor: Color
)

val moodOptions = listOf(
    MoodOption(1, Icons.Outlined.SentimentVeryDissatisfied, Icons.Filled.FilledVeryDissatisfied, "Very poor", Color(0xFFD32F2F), Color.White),
    MoodOption(2, Icons.Outlined.SentimentDissatisfied, Icons.Filled.FilledDissatisfied, "Poor", Color(0xFFFB8C00), Color(0xFF1A1C16)),
    MoodOption(3, Icons.Outlined.SentimentNeutral, Icons.Filled.FilledNeutral, "Okay", Color(0xFFFFB300), Color(0xFF1A1C16)),
    MoodOption(4, Icons.Outlined.SentimentSatisfied, Icons.Filled.FilledSatisfied, "Good", Color(0xFF7CB342), Color(0xFF1A1C16)),
    MoodOption(5, Icons.Outlined.SentimentVerySatisfied, Icons.Filled.FilledVerySatisfied, "Very good", Color(0xFF388E3C), Color.White)
)
@Composable
fun <T> ToggleablePills( // Made this a template so it can be reused in resources screen
    options: List<T>,
    selected: T,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: (T) -> String = { it.toString() }
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        options.forEach { option ->
            val isSelected = option == selected
            FilterChip(
                modifier = Modifier.weight(1f),
                selected = isSelected,
                onClick = { onSelect(option) },
                label = {
                    Text(
                        text = label(option),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}

@Composable
fun MoodFacePicker(
    selected: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        moodOptions.forEach { mood ->
            val isSelected = mood.value == selected
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isSelected) {
                    Button(
                        onClick = { onSelect(mood.value) },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = mood.color,
                            contentColor = mood.onColor
                        ),
                        modifier = Modifier.size(56.dp)
                    ) { Icon(mood.selectedIcon, contentDescription = mood.label, modifier = Modifier.size(36.dp)) }
                } else {
                    OutlinedButton(
                        onClick = { onSelect(mood.value) },
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(56.dp)
                    ) { Icon(mood.icon, contentDescription = mood.label, modifier = Modifier.size(36.dp)) }
                }
                Text(
                    mood.label,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

