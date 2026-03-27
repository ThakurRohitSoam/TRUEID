package com.arpanapteam.trueid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
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
                        scope.launch { ThemePreference.saveTheme(context, themeValue) }
                    }
                )
            }
        }
    }
}

// 🟢 STATIC SERVICE ROUTES LIST (Isse NavHost clean rahega)
val staticServiceRoutes = listOf(
    // Documents
    "aadhar" to "Aadhaar Services", "pan" to "PAN Card Services",
    "dl" to "Driving Licence", "passport" to "Indian Passport", "voter" to "Voter ID",
    // E-District
    "income_certificate" to "Income Certificate", "domicile" to "Domicile Certificate",
    "caste" to "Caste Certificate", "ration" to "Ration Card", "family" to "Family ID",
    // Schemes & Scholarships
    "pmkisan" to "PM-Kisan Scheme", "pmkvy" to "PMKVY Training",
    "uppension" to "UP Pension Services", "upscholarship" to "UP Scholarship",
    "nsp" to "National Scholarship", "saksham" to "Saksham Scholarship",
    // Boards & Education
    "upboard" to "UP Board (UPMSP)", "cbse" to "CBSE Board", "bteup" to "BTEUP",
    "cisce" to "ICSE Board", "aktu" to "AKTU University", "ccsu" to "CCSU University",
    // Property & Transport
    "bhulekh" to "UP Bhulekh", "property_registration" to "Property Registration",
    "property_maps" to "Property Maps", "railway" to "Indian Railway",
    "flight" to "Flight Booking", "metro" to "Metro Services", "bus" to "Bus Ticket Booking"
)

@Composable
fun MainAppUI(isDarkTheme: Boolean, onToggleTheme: (Boolean) -> Unit) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isDrawerGestureEnabled = currentRoute == "home"

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isDrawerGestureEnabled,
        drawerContent = { AppDrawer(navController, { scope.launch { drawerState.close() } }, isDarkTheme, onToggleTheme) }
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = { TrueIdBottomBar(navController) } // Bottom bar ab hamesha dikhega
        ) { padding ->

            NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(padding)) {

                // --- CORE APP SCREENS ---
                composable("home") { TrueIdHomeScreen(openDrawer = { scope.launch { drawerState.open() } }, navController = navController) }
                composable("services") { ServicesScreen(navController) }
                composable("news") { NewsScreen(navController) }
                composable("my_vault") { MyVaultScreen(navController) }
                composable("about") { AboutScreen(navController) }
                composable("terms") { TermsAndConditionsScreen(navController) }
                composable("contact") { ContactUsScreen(navController = navController, onBack = { navController.popBackStack() }) }
                composable("feedback") { MultiStepFeedbackScreen({ navController.popBackStack() }, { navController.popBackStack() }) }

                composable(route = "news_detail/{newsId}", arguments = listOf(navArgument("newsId") { type = NavType.IntType })) {
                    val newsId = it.arguments?.getInt("newsId") ?: return@composable
                    NewsDetailScreen(newsId = newsId, onBack = { navController.popBackStack() })
                }

                // ==========================================
                // 🚀 DYNAMIC SERVICES ROUTING (CLEANED UP)
                // ==========================================

                // 1. Loop chala kar saare static routes ek baar mein declare kar diye
                staticServiceRoutes.forEach { (routeKey, title) ->
                    composable(routeKey) { DynamicServiceScreen(navController, routeKey, title) }
                }

                // 2. The Master Route for future dynamic services
                composable(
                    route = "dynamic_service/{serviceKey}/{title}",
                    arguments = listOf(navArgument("serviceKey") { type = NavType.StringType }, navArgument("title") { type = NavType.StringType })
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
        BottomNavItem("My Vault", Icons.Outlined.Folder, "my_vault"),
    )

    val backStack by navController.currentBackStackEntryAsState()
    val current = backStack?.destination

    NavigationBar(containerColor = Color.White) {
        items.forEach { item ->
            NavigationBarItem(
                selected = current?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = false }
                        launchSingleTop = true
                    }
                },
                label = { Text(item.label, fontSize = 11.sp) },
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

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)