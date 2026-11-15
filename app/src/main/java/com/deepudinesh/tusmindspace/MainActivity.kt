package com.deepudinesh.tusmindspace

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.deepudinesh.tusmindspace.components.ButtonIcon
import com.deepudinesh.tusmindspace.screens.CheckInScreen
import com.deepudinesh.tusmindspace.screens.CheckInDoneScreen
import com.deepudinesh.tusmindspace.screens.ContactScreen
import com.deepudinesh.tusmindspace.screens.HomeScreen
import com.deepudinesh.tusmindspace.screens.JournalEntryScreen
import com.deepudinesh.tusmindspace.screens.JournalHistoryScreen
import com.deepudinesh.tusmindspace.screens.JournalScreen
import com.deepudinesh.tusmindspace.screens.MeditateScreen
import com.deepudinesh.tusmindspace.screens.ResourcesScreen
import com.deepudinesh.tusmindspace.screens.SessionTimerScreen
import com.deepudinesh.tusmindspace.ui.theme.AppTheme
import com.deepudinesh.tusmindspace.viewmodels.HomeViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface {
                    val navController = rememberNavController()
                    val navTabs = mapOf(
                        "Home" to Icons.Default.Home,
                        "Meditate" to Icons.Default.Favorite,
                        "Journal" to Icons.Default.Edit,
                        "Resources" to Icons.Default.LocationOn,
                        "Contact" to Icons.Default.Call
                    )
                    var selectedTab by remember { mutableStateOf("Home") }
                    val navigateTo: (String) -> Unit = { route ->
                        selectedTab = route
                        navController.navigate(route) {
                            popUpTo("Home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                navTabs.forEach { (label, icon) ->
                                    NavigationBarItem(
                                        selected = label == selectedTab,
                                        onClick = { navigateTo(label) },
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
                            modifier = Modifier.padding(innerPadding),
                            enterTransition = { fadeIn(tween(300)) },
                            exitTransition = { fadeOut(tween(300)) },
                            popEnterTransition = { fadeIn(tween(300)) },
                            popExitTransition = { fadeOut(tween(300)) }
                        ) {
                            composable("Home") {
                                HomeScreen(
                                    onNavigate = navigateTo,
                                    onCheckIn = { navController.navigate("CheckIn") }
                                )
                            }
                            composable("CheckIn") {
                                val homeEntry = remember(it) { navController.getBackStackEntry("Home") }
                                val homeViewModel: HomeViewModel = viewModel(homeEntry)
                                CheckInScreen(
                                    onBack = { navController.popBackStack() },
                                    onDone = { mood ->
                                        homeViewModel.logCheckIn(mood)
                                        navController.navigate("CheckInDone") {
                                            popUpTo("CheckIn") { inclusive = true }
                                        }
                                    }
                                )
                            }
                            composable("CheckInDone") {
                                CheckInDoneScreen(
                                    onJournal = { navigateTo("Journal") },
                                    onMeditate = { navigateTo("Meditate") },
                                    onHome = { navController.popBackStack() }
                                )
                            }
                            composable("Meditate") {
                                MeditateScreen(onStart = { title, mins ->
                                    navController.navigate("Timer/${Uri.encode(title)}/$mins")
                                })
                            }
                            composable(
                                route = "Timer/{title}/{mins}",
                                arguments = listOf(
                                    navArgument("title") { type = NavType.StringType },
                                    navArgument("mins") { type = NavType.IntType }
                                )
                            ) { entry ->
                                SessionTimerScreen(
                                    title = entry.arguments?.getString("title") ?: "Session",
                                    mins = entry.arguments?.getInt("mins") ?: 3,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                            composable("Journal") {
                                JournalScreen(onHistory = { navController.navigate("JournalHistory") })
                            }
                            composable("JournalHistory") {
                                JournalHistoryScreen(
                                    onBack = { navController.popBackStack() },
                                    onOpen = { id -> navController.navigate("JournalEntry/$id") }
                                )
                            }
                            composable(
                                route = "JournalEntry/{id}",
                                arguments = listOf(navArgument("id") { type = NavType.IntType })
                            ) { entry ->
                                JournalEntryScreen(
                                    entryId = entry.arguments?.getInt("id") ?: 0,
                                    onBack = { navController.popBackStack() }
                                )
                            }
                            composable("Resources") { ResourcesScreen() }
                            composable("Contact") { ContactScreen() }
                        }
                    }
                }
            }
        }
    }
}