package com.deepudinesh.tusmindspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.deepudinesh.tusmindspace.ui.theme.TUSMindSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TUSMindSpaceTheme {
                    Surface{
                        val navController = rememberNavController();

                        NavHost(navController, startDestination = "home")
                        {
                            composable("home") { HomeScreen() }
                        }
                }
            }
        }
    }
}