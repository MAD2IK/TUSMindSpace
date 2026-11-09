package com.deepudinesh.tusmindspace.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MoodBarChart(
    values: List<Pair<String, Int>>,   // day label and mood (1-3)
    maxValue: Int = 3,
    chartHeight: Dp = 140.dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().height(chartHeight + 48.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEach { (label, value) ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                moodOptions.find { it.value == value }?.let {
                    Icon(it.icon, contentDescription = it.label, modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(chartHeight * (value.toFloat() / maxValue))
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                        )
                )
                Spacer(Modifier.height(4.dp))
                Text(label, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}