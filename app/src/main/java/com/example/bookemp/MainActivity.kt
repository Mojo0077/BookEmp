package com.example.bookemp

import android.os.Build
import android.os.Bundle
import androidx.core.view.WindowCompat
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.example.bookemp.ui.screens.*
import com.example.bookemp.ui.theme.BookempTheme
import androidx.compose.runtime.saveable.rememberSaveable
import android.view.WindowInsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Teljes képernyő beállítása
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val auth = FirebaseAuth.getInstance()


        setContent {
            BookempTheme {
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.insetsController?.let { controller ->
                            controller.hide(WindowInsets.Type.systemBars())
                            controller.systemBarsBehavior =
                                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                        }
                    } else {
                        @Suppress("DEPRECATION")
                        window.decorView.systemUiVisibility =
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    }
                }

                var isLoggedIn by rememberSaveable { mutableStateOf(auth.currentUser != null) }
                var currentScreen by rememberSaveable { mutableStateOf("main") }


                if (!isLoggedIn) {
                    LoginScreen(
                        auth = auth,
                        onSuccess = {
                            isLoggedIn = true
                            currentScreen = "main"
                        }
                    )
                } else {
                    when (currentScreen) {
                        "main" -> MainMenuScreen(
                            onOpenLibrary = { currentScreen = "library" },
                            onCreateLibrary = { currentScreen = "create" },
                            onLogout = {
                                auth.signOut()
                                isLoggedIn = false
                            }
                        )
                        "create" -> CreateLibraryScreen(
                            onLibraryCreated = {
                                currentScreen = "main"
                            },
                            onCancel = {
                                currentScreen = "main"
                            }
                        )
                        "library" -> LibraryListScreen(
                            onBack = { currentScreen = "main" }
                        )
                    }
                }
            }
        }
    }

}
