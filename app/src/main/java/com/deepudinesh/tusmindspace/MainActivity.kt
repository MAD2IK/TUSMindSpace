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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.deepudinesh.tusmindspace.data.ProfilePrefs
import com.deepudinesh.tusmindspace.screens.LoginScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.deepudinesh.tusmindspace.components.ButtonIcon
import com.deepudinesh.tusmindspace.components.ReadableWidth
import com.deepudinesh.tusmindspace.components.isWideScreen
import com.deepudinesh.tusmindspace.data.AuthManager
import com.deepudinesh.tusmindspace.screens.CheckInScreen
import com.deepudinesh.tusmindspace.screens.CheckInDoneScreen
import com.deepudinesh.tusmindspace.screens.ContactScreen
import com.deepudinesh.tusmindspace.screens.HomeScreen
import com.deepudinesh.tusmindspace.screens.JournalEntryScreen
import com.deepudinesh.tusmindspace.screens.JournalHistoryScreen
import com.deepudinesh.tusmindspace.screens.JournalIntroScreen
import com.deepudinesh.tusmindspace.screens.JournalScreen
import com.deepudinesh.tusmindspace.screens.MeditateScreen
import com.deepudinesh.tusmindspace.screens.ProfileScreen
import com.deepudinesh.tusmindspace.screens.ResourcesScreen
import com.deepudinesh.tusmindspace.screens.SessionTimerScreen
import com.deepudinesh.tusmindspace.screens.TutorialScreen
import com.deepudinesh.tusmindspace.ui.theme.AppTheme
import com.deepudinesh.tusmindspace.viewmodels.HomeViewModel
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))
        Configuration.getInstance().userAgentValue = packageName

        setContent {
            AppTheme {
                Surface {
                    val navController = rememberNavController()
                    val context = LocalContext.current
                    val startRoute = remember {
                        when {
                            !ProfilePrefs.tutorialSeen(context) -> "Tutorial"
                            !(ProfilePrefs.isSignedIn(context) && AuthManager.isSignedIn()) -> "Login"
                            else -> "Home"
                        }
                    }
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route
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
                    val showNav = currentRoute != "Login" && currentRoute != "Tutorial"
                    val wide = isWideScreen()

                    Scaffold(
                        bottomBar = {
                            if (showNav && !wide) {
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
                        }
                    ) { innerPadding ->
                        Row(Modifier.fillMaxSize().padding(innerPadding)) {
                            if (showNav && wide) {
                                NavigationRail {
                                    navTabs.forEach { (label, icon) ->
                                        NavigationRailItem(
                                            selected = label == selectedTab,
                                            onClick = { navigateTo(label) },
                                            icon = { ButtonIcon(icon, 24) },
                                            label = { Text(label) }
                                        )
                                    }
                                }
                            }
                            NavHost(
                                navController,
                                startDestination = startRoute,
                                modifier = Modifier.weight(1f),
                                enterTransition = { fadeIn(tween(300)) },
                                exitTransition = { fadeOut(tween(300)) },
                                popEnterTransition = { fadeIn(tween(300)) },
                                popExitTransition = { fadeOut(tween(300)) }
                            ) {
                                composable("Tutorial") {
                                    ReadableWidth {
                                        TutorialScreen(onFinish = {
                                            navController.navigate("Login") {
                                                popUpTo("Tutorial") { inclusive = true }
                                            }
                                        })
                                    }
                                }
                                composable("Login") {
                                    ReadableWidth {
                                        LoginScreen(onContinue = {
                                            navController.navigate("Home") {
                                                popUpTo("Login") { inclusive = true }
                                            }
                                        })
                                    }
                                }
                                composable("Home") {
                                    HomeScreen(
                                        onNavigate = navigateTo,
                                        onCheckIn = { navController.navigate("CheckIn") },
                                        onProfile = { navController.navigate("Profile") }
                                    )
                                }
                                composable("CheckIn") {
                                    val homeEntry =
                                        remember(it) { navController.getBackStackEntry("Home") }
                                    val homeViewModel: HomeViewModel = viewModel(homeEntry)
                                    ReadableWidth {
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
                                }
                                composable("CheckInDone") {
                                    ReadableWidth {
                                        CheckInDoneScreen(
                                            onJournal = { navigateTo("Journal") },
                                            onMeditate = { navigateTo("Meditate") },
                                            onHome = { navController.popBackStack() }
                                        )
                                    }
                                }
                                composable("Meditate") {
                                    ReadableWidth {
                                        MeditateScreen(onStart = { title, mins ->
                                            navController.navigate("Timer/${Uri.encode(title)}/$mins")
                                        })
                                    }
                                }
                                composable(
                                    route = "Timer/{title}/{mins}",
                                    arguments = listOf(
                                        navArgument("title") { type = NavType.StringType },
                                        navArgument("mins") { type = NavType.IntType }
                                    )
                                ) { entry ->
                                    ReadableWidth {
                                        SessionTimerScreen(
                                            title = entry.arguments?.getString("title") ?: "Session",
                                            mins = entry.arguments?.getInt("mins") ?: 3,
                                            onBack = { navController.popBackStack() }
                                        )
                                    }
                                }
                                composable("Journal") {
                                    var introSeen by remember {
                                        mutableStateOf(
                                            ProfilePrefs.journalIntroSeen(
                                                context
                                            )
                                        )
                                    }
                                    ReadableWidth {
                                        if (introSeen) {
                                            JournalScreen(onHistory = { navController.navigate("JournalHistory") })
                                        } else {
                                            JournalIntroScreen(onFinish = { introSeen = true })
                                        }
                                    }
                                }
                                composable("JournalHistory") {
                                    ReadableWidth {
                                        JournalHistoryScreen(
                                            onBack = { navController.popBackStack() },
                                            onOpen = { id -> navController.navigate("JournalEntry/$id") }
                                        )
                                    }
                                }
                                composable(
                                    route = "JournalEntry/{id}",
                                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                                ) { entry ->
                                    ReadableWidth {
                                        JournalEntryScreen(
                                            entryId = entry.arguments?.getInt("id") ?: 0,
                                            onBack = { navController.popBackStack() }
                                        )
                                    }
                                }
                                composable("Resources") { ResourcesScreen() }
                                composable("Contact") { ReadableWidth { ContactScreen() } }
                                composable("Profile") {
                                    ReadableWidth { ProfileScreen(onBack = { navController.popBackStack() }) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}