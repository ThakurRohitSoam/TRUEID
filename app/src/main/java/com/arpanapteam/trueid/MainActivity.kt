package com.arpanapteam.trueid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arpanapteam.trueid.Feedback.MultiStepFeedbackScreen
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TRUEIDTheme {

                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val openDrawer: () -> Unit = {
                    scope.launch { drawerState.open() }
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawer(
                            navController = navController,
                            onClose = { scope.launch { drawerState.close() } }
                        )
                    }
                ) {

                    Scaffold(
                        contentWindowInsets = WindowInsets(0, 0, 0, 0),
                        bottomBar = { TrueIdBottomBar(navController) }
                    ) { innerPadding ->

                        NavHost(
                            navController = navController,
                            startDestination = "home",
                            modifier = Modifier.padding(innerPadding)
                        ) {

                            composable("home") {
                                TrueIdHomeScreen(openDrawer = openDrawer)
                            }

                            composable("services") {
                                ServicesScreen(navController)
                            }

                            composable("income_certificate") {
                                IncomeCertificateScreen(navController)
                            }

                            composable("about") {
                                AboutScreen(navController)
                            }

                            composable("terms") {
                                TermsAndConditionsScreen(navController)
                            }

                            composable("news") {
                                NewsScreen()
                            }
                            composable("contact") { ContactUsScreen { navController.popBackStack() } }


                            // Correct Feedback Screen Navigation
                            composable("feedback") {
                                MultiStepFeedbackScreen(
                                    onBack = { navController.popBackStack() },
                                    onSubmit = {
                                        navController.popBackStack()    // Go back after submitting
                                    }
                                )
                            }
                        }
                    }
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(containerColor = Color.White) {

        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(item.label, fontSize = 12.sp) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Indigo,
                    selectedTextColor = Indigo,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = OffWhite
                )
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)
