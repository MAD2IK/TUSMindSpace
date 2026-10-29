package com.deepudinesh.tusmindspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.deepudinesh.tusmindspace.components.ButtonIcon
import com.deepudinesh.tusmindspace.screens.ContactScreen
import com.deepudinesh.tusmindspace.screens.HomeScreen
import com.deepudinesh.tusmindspace.screens.JournalScreen
import com.deepudinesh.tusmindspace.screens.MeditateScreen
import com.deepudinesh.tusmindspace.screens.ResourcesScreen
import com.deepudinesh.tusmindspace.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface {
                    val navController = rememberNavController()
                    val navTabs = mapOf(
                        "Home" to Icons.Default.Home,
                        "Meditate" to Icons.Default.Favorite,   // Meditate placeholder
                        "Journal" to Icons.Default.Edit,        // Journal placeholder
                        "Resources" to Icons.Default.LocationOn,
                        "Contact" to Icons.Default.Call
                    )
                    var selectedTab by remember { mutableStateOf("Home") }

                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                navTabs.forEach { (label, icon) ->
                                    NavigationBarItem(
                                        selected = label == selectedTab,
                                        onClick = {
                                            selectedTab = label
                                            navController.navigate(label)
                                        },
                                        icon = { ButtonIcon(icon, 24) },
                                        label = { Text(label) }
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController,
                            startDestination = "Home",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("Home") { HomeScreen() }
                            composable("Meditate") { MeditateScreen() }
                            composable("Journal") { JournalScreen() }
                            composable("Resources") { ResourcesScreen() }
                            composable("Contact") { ContactScreen() }
                        }
                    }
                }
            }
        }
    }
}