package com.deepudinesh.tusmindspace.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ButtonIcon(icon: ImageVector, iconSize: Int)
{
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = Modifier.size(iconSize.dp)
    )
}

//Reusable button icon function
@Composable
fun SolidCircleWithImageVectorIcon(icon: ImageVector, iconSize: Int, circleSize: Int) {
    Box(
        modifier = Modifier
            .size(circleSize.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(iconSize.dp)
        )
    }
}

fun serviceIcon(key: String): ImageVector = when (key) {
    "counselling" -> Icons.Filled.Psychology
    "health" -> Icons.Filled.LocalHospital
    "access" -> Icons.Filled.Accessible
    "su" -> Icons.Filled.Groups
    "academic" -> Icons.Filled.School
    else -> Icons.Filled.Phone
}
