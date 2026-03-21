package com.arpanapteam.trueid.Services

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arpanapteam.trueid.AdminServiceModel
import com.arpanapteam.trueid.supabase
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.TextGray
import io.github.jan.supabase.postgrest.postgrest

// ==========================================
// 1. OFFLINE DATA (AAPKI 30 SERVICES YAHAN SAFE HAIN)
// ==========================================
data class ServiceData(val title: String, val description: String, val icon: ImageVector, val route: String?, val urlKey: String? = null)
data class ServiceCategory(val name: String, val items: List<ServiceData>)

val offlineCategories = listOf(
    ServiceCategory("Important Documents", listOf(
        ServiceData("Aadhar Card Update", "Update demographic or biometric details in your Aadhar card.", Icons.Outlined.PersonPin, "aadhar"),
        ServiceData("PAN Card Application", "Apply for a new Permanent Account Number (PAN) card.", Icons.Outlined.WorkOutline, "pan"),
        ServiceData("Driving License", "Apply for a new or renew your driving license.", Icons.Outlined.Badge, "dl"),
        ServiceData("Indian Passport", "Apply for a new Passport", Icons.Outlined.Work, "passport"),
        ServiceData("Voter Id", "Apply for a new Voter Id ", Icons.Outlined.HowToVote, "voter")
    )),
    ServiceCategory("E-District Services", listOf(
        ServiceData("Income Certificate", "Apply for new income certificate.", Icons.Outlined.Description, "income_certificate"),
        ServiceData("Domicile Certificate", "Obtain a certificate of residency.", Icons.Outlined.Home, "domicile"),
        ServiceData("Caste Certificate", "Apply for caste certificate.", Icons.Outlined.VerifiedUser, "caste"),
        ServiceData("Ration Card", "Apply for New Ration Card", Icons.Outlined.VerifiedUser, "ration"),
        ServiceData("Family Id", "Get your Family Id.", Icons.Outlined.VerifiedUser, "family")
    )),
    ServiceCategory("Government Schemes", listOf(
        ServiceData("PM-Kisan", "₹6000 benefit yearly", Icons.Outlined.HomeWork, "pmkisan"),
        ServiceData("PMKVY", "Skill training scheme", Icons.AutoMirrored.Outlined.Assignment, "pmkvy"),
        ServiceData("UP Pension", "Pension schemes", Icons.AutoMirrored.Outlined.Assignment, "uppension")
    )),
    ServiceCategory("Scholarship", listOf(
        ServiceData("UP Scholarship", "UP Scholarship portal", Icons.Outlined.School, "upscholarship"),
        ServiceData("National Scholarship", "Central Govt scholarship", Icons.Outlined.HomeWork, "nsp"),
        ServiceData("Saksham Scholarship", "AICTE scheme", Icons.AutoMirrored.Outlined.Assignment, "saksham")
    )),
    ServiceCategory("Education Board & Universities", listOf(
        ServiceData("UP Board", "UPMSP", Icons.AutoMirrored.Outlined.Assignment, "upboard"),
        ServiceData("CBSE", "CBSE Board", Icons.AutoMirrored.Outlined.Assignment, "cbse"),
        ServiceData("BTEUP", "Technical Board", Icons.AutoMirrored.Outlined.Assignment, "bteup"),
        ServiceData("ICSE", "CISCE Board", Icons.AutoMirrored.Outlined.Assignment, "cisce"),
        ServiceData("AKTU", "University", Icons.AutoMirrored.Outlined.LibraryBooks, "aktu"),
        ServiceData("CCSU", "University", Icons.AutoMirrored.Outlined.MenuBook, "ccsu")
    )),
    ServiceCategory("Property and Land Records", listOf(
        ServiceData("UP Bhulekh", "Land records", Icons.Outlined.DocumentScanner, "bhulekh"),
        ServiceData("Property Registration", "Register property", Icons.Outlined.PendingActions, "property_registration"),
        ServiceData("Property Maps", "Geo maps", Icons.AutoMirrored.Outlined.Assignment, "property_maps")
    )),
    ServiceCategory("Travel & Ticket Bookings", listOf(
        ServiceData("Indian Railway", "Book Indian Railway Tickets", Icons.Outlined.Train, "railway"),
        ServiceData("Flight Booking", "Book Flight Tickets", Icons.Outlined.Flight, "flight"),
        ServiceData("Metro Services", "Book Metro Tickets", Icons.Outlined.DirectionsRailwayFilled, "metro"),
        ServiceData("Bus Booking", "Book Bus Tickets", Icons.Outlined.DirectionsBus, "bus")
    ))
)

// ==========================================
// 2. MAIN SCREEN LOGIC
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<ServiceCategory?>(null) }

    var onlineUpdates by remember { mutableStateOf<List<AdminServiceModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        try { onlineUpdates = supabase.postgrest["services"].select().decodeList<AdminServiceModel>() }
        catch (e: Exception) {}
    }

    // 🟢 Category-wise Group karna aur service_key (urlKey) setup karna
    val dynamicCategories = remember(onlineUpdates) {
        onlineUpdates.groupBy { it.category ?: "New Services" }.map { (catName, services) ->
            ServiceCategory(
                name = catName,
                items = services.map { adminService ->
                    ServiceData(
                        title = adminService.title,
                        description = adminService.description,
                        icon = Icons.Outlined.Apps,
                        route = null,
                        urlKey = adminService.service_key // ✅ Updated to match the clean model
                    )
                }
            )
        }
    }

    // ✅ FIX: Offline pehle, uske baad online naye categories
    val allCategoriesList = offlineCategories + dynamicCategories

    // --- VIEW ALL PAGE LOGIC ---
    val category = selectedCategory
    if (category != null) {
        BackHandler { selectedCategory = null }
        Scaffold(
            topBar = { TopBarUI(title = category.name) { selectedCategory = null } },
            containerColor = OffWhite
        ) { padding ->
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp)) {
                items(category.items) { service ->
                    ServiceItemCard(service, navController)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
        return
    }

    // --- MAIN PAGE LOGIC ---
    Scaffold(
        containerColor = OffWhite,
        topBar = { TopBarUI("Services") { navController.navigateUp() } },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { SearchBarUI(searchQuery) { searchQuery = it } }

            if (searchQuery.isNotEmpty()) {
                val filtered = allCategoriesList.flatMap { it.items }.filter {
                    it.title.contains(searchQuery, true) || it.description.contains(searchQuery, true)
                }
                item { Text("Search Results", fontWeight = FontWeight.Bold, fontSize = 18.sp) }
                if (filtered.isEmpty()) {
                    item { Text("No services found.", color = Color.Gray) }
                } else {
                    items(filtered) { service ->
                        ServiceItemCard(service, navController)
                        Spacer(Modifier.height(12.dp))
                    }
                }
            } else {
                items(allCategoriesList) { cat ->
                    ServiceCategorySection(title = cat.name, onViewAllClick = { selectedCategory = cat }) {
                        cat.items.take(3).forEach { service ->
                            ServiceItemCard(service, navController)
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 3. UI COMPONENTS
// ==========================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUI(title: String, onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.Bold, color = Color.Black) },
        navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun SearchBarUI(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query, onValueChange = onQueryChange, modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search for services.......") }, leadingIcon = { Icon(Icons.Default.Search, "") },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color.White, unfocusedContainerColor = Color.White
        )
    )
}

@Composable
fun ServiceCategorySection(title: String, onViewAllClick: () -> Unit, content: @Composable () -> Unit) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "View All", fontSize = 14.sp, color = Indigo, fontWeight = FontWeight.Medium, modifier = Modifier.clickable { onViewAllClick() }.padding(4.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column { content() }
    }
}

// 🔵 CARD CLICK HONE PAR NAYA PAGE KHULEGA
@Composable
fun ServiceItemCard(service: ServiceData, navController: NavHostController) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth().clickable {
            if (service.route != null) {
                // Offline service (e.g. "aadhar", "pan")
                navController.navigate(service.route)
            } else if (service.urlKey != null) {
                // Online service -> Sirf Dynamic Page Khulega service_key (urlKey) ka use karke
                if (service.urlKey.startsWith("http")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(service.urlKey))
                    context.startActivity(intent)
                } else {
                    navController.navigate("dynamic_service/${service.urlKey}/${service.title}")
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(Indigo.copy(alpha = 0.1f)), contentAlignment = Alignment.Center) {
                Icon(imageVector = service.icon, contentDescription = service.title, tint = Indigo, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = service.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = service.description, fontSize = 13.sp, color = TextGray)
            }
            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = "Go", tint = Color.Gray)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ServicesScreenPreview() {
    TRUEIDTheme {
        ServicesScreen(rememberNavController())
    }
}