package com.arpanapteam.trueid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Home

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*

import com.arpanapteam.trueid.Feedback.MultiStepFeedbackScreen
import com.arpanapteam.trueid.Services.*
import com.arpanapteam.trueid.ui.theme.*

import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        setContent {

            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            val themeFlow = remember { ThemePreference.themeFlow(context) }
            val isDarkTheme by themeFlow.collectAsState(initial = false)

            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView)
                    .isAppearanceLightStatusBars = !isDarkTheme
            }

            TRUEIDTheme {
                MainAppUI(
                    isDarkTheme,
                    onToggleTheme = {
                        scope.launch {
                            ThemePreference.saveTheme(context, it)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MainAppUI(
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val openDrawer: () -> Unit = {
        scope.launch {
            drawerState.open()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                navController,
                { scope.launch { drawerState.close() } },
                isDarkTheme,
                onToggleTheme
            )
        }
    ) {

        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = { TrueIdBottomBar(navController) }
        ) { padding ->

            NavHost(
                navController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {

                composable("home") {
                    TrueIdHomeScreen(openDrawer)
                }

                composable("services") {
                    ServicesScreen(navController)
                }

                composable("income_certificate") {
                    IncomeCertificateScreen(navController as () -> Unit)
                }

                composable("railway") {
                    RailwayLinksScreen()
                }

                composable("flight") {
                    AirlineTicketScreen { navController.popBackStack() }
                }

                composable("metro") {
                    MetroTicketScreen { navController.popBackStack() }
                }

                composable("bus") {
                    BusTicketScreen { navController.popBackStack() }
                }

                composable("feedback") {
                    MultiStepFeedbackScreen(
                        { navController.popBackStack() },
                        { navController.popBackStack() }
                    )
                }

                composable("news") {
                    NewsScreen(navController)
                }

                composable("about") {
                    AboutScreen(navController)
                }

                composable("terms") {
                    TermsAndConditionsScreen(navController)
                }
            }
        }
    }
}

@Composable
fun TrueIdBottomBar(navController: NavController) {

    val items = listOf(
        BottomNavItem("Home", Icons.Outlined.Home, "home"),
        BottomNavItem("Services", Icons.Outlined.Apps, "services"),
        BottomNavItem("News", Icons.AutoMirrored.Outlined.Article, "news"),
        BottomNavItem("Feedback", Icons.Outlined.Feedback, "feedback")
    )

    val backStack by navController.currentBackStackEntryAsState()
    val current = backStack?.destination

    NavigationBar(containerColor = Color.White) {

        items.forEach { item ->
            NavigationBarItem(
                selected = current?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(item.label, fontSize = 12.sp) },
                icon = { Icon(item.icon, item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Indigo,
                    selectedTextColor = Indigo,
                    indicatorColor = OffWhite
                )
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)