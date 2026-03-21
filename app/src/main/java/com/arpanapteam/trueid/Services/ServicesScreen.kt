
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
import kotlinx.coroutines.launch

// --- 1. OFFLINE DATA STORE ---
data class ServiceData(val title: String, val description: String, val icon: ImageVector, val route: String)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<ServiceCategory?>(null) } // View All state
    var adminUpdates by remember { mutableStateOf<List<AdminServiceModel>>(emptyList()) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        try { adminUpdates = supabase.postgrest["services"].select().decodeList<AdminServiceModel>() }
        catch (e: Exception) {}
    }

    // --- 4. VIEW ALL SCREEN LOGIC (✅ CRASH FIXED HERE) ---
    val category = selectedCategory // State ko safe variable me save kiya
    if (category != null) {
        BackHandler { selectedCategory = null }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(category.name, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { selectedCategory = null }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            containerColor = OffWhite
        ) { padding ->
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding), contentPadding = PaddingValues(16.dp)) {
                items(category.items) { service ->
                    ServiceItemCard(service) { navController.navigate(service.route) }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
        return
    }

    // --- MAIN SERVICES SCREEN ---
    Scaffold(
        containerColor = OffWhite,
        topBar = { ServiceTopAppBar(navController) },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // --- 3. SEARCH BAR ---
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search for services.......") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.White, unfocusedContainerColor = Color.White
                    )
                )
            }

            if (searchQuery.isNotEmpty()) {
                // --- SEARCH FILTER RESULTS ---
                val allServices = offlineCategories.flatMap { it.items }
                val filtered = allServices.filter {
                    it.title.contains(searchQuery, true) || it.description.contains(searchQuery, true)
                }

                item { Text("Search Results", fontWeight = FontWeight.Bold, fontSize = 18.sp) }

                if (filtered.isEmpty()) {
                    item { Text("No services found.", color = Color.Gray) }
                } else {
                    items(filtered) { service ->
                        ServiceItemCard(service) { navController.navigate(service.route) }
                        Spacer(Modifier.height(12.dp))
                    }
                }
            } else {
                // --- 5. ADMIN UPDATES ---
                if (adminUpdates.isNotEmpty()) {
                    item {
                        ServiceCategorySection("New Updates", onViewAllClick = { }) {
                            adminUpdates.forEach { update ->
                                val fakeServiceData = ServiceData(update.title, update.description, Icons.Outlined.NewReleases, "")
                                ServiceItemCard(fakeServiceData) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(update.link_url))
                                    context.startActivity(intent)
                                }
                                Spacer(Modifier.height(12.dp))
                            }
                        }
                    }
                }

                // --- NORMAL CATEGORIES ---
                items(offlineCategories) { cat ->
                    ServiceCategorySection(title = cat.name, onViewAllClick = { selectedCategory = cat }) {
                        cat.items.take(3).forEach { service ->
                            ServiceItemCard(service) { navController.navigate(service.route) }
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text(text = "Services", fontWeight = FontWeight.Bold, color = Color.Black) },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun ServiceCategorySection(title: String, onViewAllClick: () -> Unit, content: @Composable () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            // Hide "View All" for the New Updates section
            if (title != "New Updates") {
                Text(
                    text = "View All",
                    fontSize = 14.sp,
                    color = Indigo,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onViewAllClick() }.padding(4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column { content() }
    }
}

@Composable
fun ServiceItemCard(service: ServiceData, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Indigo.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = service.icon,
                    contentDescription = service.title,
                    tint = Indigo,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = service.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = service.description,
                    fontSize = 13.sp,
                    color = TextGray
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                contentDescription = "Go",
                tint = Color.Gray
            )
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

