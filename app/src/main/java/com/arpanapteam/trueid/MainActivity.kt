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
import androidx.navigation.NavType
import androidx.navigation.navArgument
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
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { themeValue ->
                        scope.launch {
                            ThemePreference.saveTheme(context, themeValue)
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

    // 🟢 CURRENT ROUTE CHECKER
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 1. Drawer Sirf 'home' screen pe slide hoke khulega
    val isDrawerGestureEnabled = currentRoute == "home"

    // 2. Admin routes ki list (jahan bottom bar hide karna hai)
    val adminRoutes = listOf(
        "admin_login", "admin_dashboard", "manage_services",
        "manage_news", "view_feedback", "manage_service_links",
        "manage_service_info", "manage_home_services"
    )
    val showBottomBar = currentRoute !in adminRoutes

    val openDrawer: () -> Unit = {
        scope.launch { drawerState.open() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isDrawerGestureEnabled, // ✅ DRAWER FIX APPLIED
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
            contentWindowInsets = WindowInsets(0,0,0,0),
            bottomBar = {
                // ✅ ADMIN PANEL LOCK (Bottom bar tabhi dikhega jab admin panel mein na ho)
                if (showBottomBar) {
                    TrueIdBottomBar(navController)
                }
            }
        ) { padding ->

            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {
                // --- MAIN APP SCREENS ---
                composable("home") { TrueIdHomeScreen(openDrawer = openDrawer, navController = navController) }
                composable("services") { ServicesScreen(navController) }
                composable("news") { NewsScreen(navController) }
                composable("feedback") { MultiStepFeedbackScreen({ navController.popBackStack() }, { navController.popBackStack() }) }
                composable("about") { AboutScreen(navController) }
                composable("terms") { TermsAndConditionsScreen(navController) }
                composable("contact") { ContactUsScreen(navController = navController, onBack = { navController.popBackStack() }) }

                composable(
                    route = "news_detail/{newsId}",
                    arguments = listOf(navArgument("newsId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val newsId = backStackEntry.arguments?.getInt("newsId") ?: return@composable
                    NewsDetailScreen(newsId = newsId, onBack = { navController.popBackStack() })
                }

                // --- ADMIN ROUTES ---
                composable("admin_login") { AdminLoginScreen(navController) }
                composable("admin_dashboard") { AdminDashboardScreen(navController) }
                composable("manage_services") { ManageServicesScreen(navController) }
                composable("manage_news") { ManageNewsScreen(navController) }
                composable("view_feedback") { ViewFeedbackScreen(navController) }
                composable("manage_service_links") { ManageServiceLinksScreen(navController) }
                composable("manage_service_info") { ManageServiceInfoScreen(navController) }
                composable("manage_home_services") { ManageHomeServicesScreen(navController) }

                // ==========================================
                // 🚀 DYNAMIC SERVICES ROUTING
                // ==========================================

                // Documents
                composable("aadhar") { DynamicServiceScreen(navController, "aadhar", "Aadhaar Services") }
                composable("pan") { DynamicServiceScreen(navController, "pan", "PAN Card Services") }
                composable("dl") { DynamicServiceScreen(navController, "dl", "Driving License") }
                composable("passport") { DynamicServiceScreen(navController, "passport", "Indian Passport") }
                composable("voter") { DynamicServiceScreen(navController, "voter", "Voter ID") }

                // E-District
                composable("income_certificate") { DynamicServiceScreen(navController, "income_certificate", "Income Certificate") }
                composable("domicile") { DynamicServiceScreen(navController, "domicile", "Domicile Certificate") }
                composable("caste") { DynamicServiceScreen(navController, "caste", "Caste Certificate") }
                composable("ration") { DynamicServiceScreen(navController, "ration", "Ration Card") }
                composable("family") { DynamicServiceScreen(navController, "family", "Family ID") }

                // Schemes & Scholarship
                composable("pmkisan") { DynamicServiceScreen(navController, "pmkisan", "PM-Kisan Scheme") }
                composable("pmkvy") { DynamicServiceScreen(navController, "pmkvy", "PMKVY Training") }
                composable("uppension") { DynamicServiceScreen(navController, "uppension", "UP Pension Services") }
                composable("upscholarship") { DynamicServiceScreen(navController, "upscholarship", "UP Scholarship") }
                composable("nsp") { DynamicServiceScreen(navController, "nsp", "National Scholarship") }
                composable("saksham") { DynamicServiceScreen(navController, "saksham", "Saksham Scholarship") }

                // Boards
                composable("upboard") { DynamicServiceScreen(navController, "upboard", "UP Board (UPMSP)") }
                composable("cbse") { DynamicServiceScreen(navController, "cbse", "CBSE Board") }
                composable("bteup") { DynamicServiceScreen(navController, "bteup", "BTEUP") }
                composable("cisce") { DynamicServiceScreen(navController, "cisce", "ICSE Board") }
                composable("aktu") { DynamicServiceScreen(navController, "aktu", "AKTU University") }
                composable("ccsu") { DynamicServiceScreen(navController, "ccsu", "CCSU University") }

                // Property & Transport
                composable("bhulekh") { DynamicServiceScreen(navController, "bhulekh", "UP Bhulekh") }
                composable("property_registration") { DynamicServiceScreen(navController, "property_registration", "Property Registration") }
                composable("property_maps") { DynamicServiceScreen(navController, "property_maps", "Property Maps") }
                composable("railway") { DynamicServiceScreen(navController, "railway", "Indian Railway") }
                composable("flight") { DynamicServiceScreen(navController, "flight", "Flight Booking") }
                composable("metro") { DynamicServiceScreen(navController, "metro", "Metro Services") }
                composable("bus") { DynamicServiceScreen(navController, "bus", "Bus Ticket Booking") }

                // 🚀 THE MASTER ROUTE FOR NEW DYNAMIC SERVICES
                composable(
                    route = "dynamic_service/{serviceKey}/{title}",
                    arguments = listOf(
                        navArgument("serviceKey") { type = NavType.StringType },
                        navArgument("title") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val serviceKey = backStackEntry.arguments?.getString("serviceKey") ?: ""
                    val title = backStackEntry.arguments?.getString("title") ?: "Service"
                    DynamicServiceScreen(navController, serviceKey, title)
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
                        popUpTo(navController.graph.startDestinationId){ saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(item.label, fontSize = 12.sp) },
                icon = { Icon(item.icon, item.label) },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = Indigo, selectedTextColor = Indigo, indicatorColor = OffWhite)
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)