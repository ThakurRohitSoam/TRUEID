package com.arpanapteam.trueid

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

// ==========================================
// 1. DATA MODELS & OFFLINE HOME DATA
// ==========================================
data class HomeServiceItem(val name: String, val icon: ImageVector, val route: String?, val urlKey: String? = null)
data class HomeServiceCategory(val title: String, val items: List<HomeServiceItem>)

val offlineHomeCategories = listOf(
    HomeServiceCategory(
        "Important Documents",
        listOf(
            HomeServiceItem("Aadhar Card", Icons.Outlined.QrCodeScanner, "aadhar"),
            HomeServiceItem("PAN Card", Icons.Outlined.CreditCard, "pan"),
            HomeServiceItem("Indian Passport", Icons.Outlined.Book, "passport")
        )
    ),
    HomeServiceCategory(
        "Uttar Pradesh E-District Services",
        listOf(
            HomeServiceItem("Income Certificate", Icons.AutoMirrored.Outlined.ReceiptLong, "income_certificate"),
            HomeServiceItem("Domicile Certificate", Icons.Outlined.HomeWork, "domicile"),
            HomeServiceItem("Caste Certificate", Icons.Outlined.Groups, "caste")
        )
    ),
    HomeServiceCategory(
        "Ticket Booking",
        listOf(
            HomeServiceItem("Indian Railway", Icons.Outlined.Train, "railway"),
            HomeServiceItem("Flight Booking", Icons.Outlined.Flight, "flight"),
            HomeServiceItem("Metro Services", Icons.Outlined.Tram, "metro")
        )
    )
)

// ==========================================
// 2. MAIN HOME SCREEN
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrueIdHomeScreen(
    openDrawer: () -> Unit,
    navController: NavHostController
) {
    var onlineHomeUpdates by remember { mutableStateOf<List<AdminHomeServiceModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        try {
            onlineHomeUpdates = supabase.postgrest["home_services"].select().decodeList<AdminHomeServiceModel>()
        } catch (e: Exception) {
            // Offline safe
        }
    }

    // 🟢 MAGIC FIX: Chunking (Max 3 items per card)
    val dynamicCategories = remember(onlineHomeUpdates) {
        onlineHomeUpdates.groupBy { it.category ?: "New Services" }.flatMap { (catName, services) ->
            // services ko 3-3 ke hisso (chunks) mein baant diya
            services.chunked(3).mapIndexed { index, chunkedServices ->
                // Agar ek hi category mein 3 se zyada hain, toh 'Part 2', 'Part 3' likh kar naya card banega
                val displayTitle = if (index == 0) catName else "$catName (Part ${index + 1})"

                HomeServiceCategory(
                    title = displayTitle,
                    items = chunkedServices.map { adminService ->
                        HomeServiceItem(
                            name = adminService.title,
                            icon = Icons.Outlined.Apps,
                            route = null,
                            urlKey = adminService.service_key
                        )
                    }
                )
            }
        }
    }

    val allHomeCategories = offlineHomeCategories + dynamicCategories

    Scaffold(
        containerColor = OffWhite,
        topBar = {
            TrueIdTopAppBar(
                onMenuClick = openDrawer,
                onAdminAccess = { navController.navigate("admin_login") }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { HeroCard() }

            items(allHomeCategories) { category ->
                ServiceCategoryCard(
                    title = category.title,
                    items = category.items,
                    navController = navController
                )
            }
        }
    }
}

// ==========================================
// 3. UI COMPONENTS
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrueIdTopAppBar(onMenuClick: () -> Unit, onAdminAccess: () -> Unit) {
    var tapCount by remember { mutableIntStateOf(0) }
    var lastTapTime by remember { mutableLongStateOf(0L) }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastTapTime < 500) {
                        tapCount++
                        if (tapCount >= 5) {
                            tapCount = 0
                            onAdminAccess()
                        }
                    } else {
                        tapCount = 1
                    }
                    lastTapTime = currentTime
                }
            ) {
                Icon(Icons.Outlined.VerifiedUser, null, tint = Indigo)
                Spacer(Modifier.width(8.dp))
                Text("TRUEID", fontWeight = FontWeight.Bold)
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) { Icon(Icons.Default.Menu, null) }
        },
        actions = {
            IconButton(onClick = {}) { Icon(Icons.Default.Search, null) }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun HeroCard() {
    val gradient = Brush.linearGradient(listOf(PinkishPurple, LightPurple))

    Box(
        Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gradient)
            .padding(24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text("Trusted Reliable Unified E-Governance Interface for Data", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Your one-stop portal for all government services in Uttar Pradesh", color = Color.White.copy(.9f))
        }
    }
}

@Composable
fun ServiceCategoryCard(
    title: String,
    items: List<HomeServiceItem>,
    navController: NavHostController
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box(Modifier.fillMaxWidth().background(Indigo, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)).padding(16.dp)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Column(Modifier.padding(vertical = 8.dp)) {
                items.forEach { ServiceItemRow(it, navController) }
            }
        }
    }
}

// 🔵 CARD CLICK LOGIC FIX
@Composable
fun ServiceItemRow(
    item: HomeServiceItem,
    navController: NavHostController
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(item.icon, contentDescription = null, tint = Indigo, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Text(text = item.name, modifier = Modifier.weight(1f), color = TextGray, fontSize = 15.sp)

        Button(
            onClick = {
                try {
                    // Navigation Logic
                    if (item.route != null) {
                        navController.navigate(item.route)
                    } else if (!item.urlKey.isNullOrEmpty()) {
                        if (item.urlKey.startsWith("http")) {
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.urlKey)))
                        } else {
                            // 🟢 CRASH FIX: URL Encoding kiya gaya hai taaki Space (" ") aane par crash na ho
                            val safeKey = Uri.encode(item.urlKey)
                            val safeName = Uri.encode(item.name)
                            navController.navigate("dynamic_service/$safeKey/$safeName")
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Page not found", Toast.LENGTH_SHORT).show()
                }
            },
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 2.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo),
            elevation = ButtonDefaults.buttonElevation(2.dp)
        ) {
            Text("GO", fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrueIdHomeScreenPreview() {
    TRUEIDTheme { TrueIdHomeScreen(openDrawer = {}, navController = rememberNavController()) }
}