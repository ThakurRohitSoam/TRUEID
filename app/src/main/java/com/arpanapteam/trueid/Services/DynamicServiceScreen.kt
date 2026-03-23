package com.arpanapteam.trueid.Services

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.Train
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arpanapteam.trueid.supabase
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import io.github.jan.supabase.postgrest.postgrest
import com.arpanapteam.trueid.ServiceLinkModel
import com.arpanapteam.trueid.ServiceInfoModel
import kotlinx.serialization.Serializable

// --- 🟢 NAYA DATABASE MODEL ---
@Serializable
data class TravelServiceModel(
    val id: Int? = null,
    val service_type: String, // "bus", "flight", "metro"
    val title: String,
    val subtitle: String? = null,
    val link_1: String? = null,
    val link_2: String? = null,
    val link_3: String? = null,
    val link_4: String? = null
)

// --- PURANE UI DATA CLASSES (Ab Data inme aayega Database se) ---
data class BusInfo(val name: String, val website: String, val booking: String, val status: String, val app: String)
data class AirlineInfo(val name: String, val website: String, val booking: String, val checkin: String)
data class MetroInfo(val name: String, val city: String, val appLink: String? = null, val whatsapp: String? = null)
data class RailwayLink(val title: String, val url: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicServiceScreen(navController: NavController, serviceKey: String, pageTitle: String) {
    val context = LocalContext.current
    var links by remember { mutableStateOf<List<ServiceLinkModel>>(emptyList()) }
    var serviceInfo by remember { mutableStateOf<ServiceInfoModel?>(null) }

    // 🟢 NAYA STATE: Travel data ke liye
    var travelData by remember { mutableStateOf<List<TravelServiceModel>>(emptyList()) }

    var isLoading by remember { mutableStateOf(true) }
    var isOffline by remember { mutableStateOf(false) }
    var retryTrigger by remember { mutableIntStateOf(0) }

    val safeSearchKey = serviceKey.trim().lowercase().replace(" ", "").replace("_", "")

    // FETCH LOGIC
    LaunchedEffect(serviceKey, retryTrigger) {
        isLoading = true
        isOffline = false
        try {
            // Intro & Normal Links fetch
            val allIntros = supabase.postgrest["service_info"].select().decodeList<ServiceInfoModel>()
            serviceInfo = allIntros.firstOrNull { calculateSimilarityPercentage(it.service_key.trim().lowercase().replace(" ", "").replace("_", ""), safeSearchKey) >= 80.0 }

            val allLinks = supabase.postgrest["service_links"].select().decodeList<ServiceLinkModel>()
            links = allLinks.filter { calculateSimilarityPercentage(it.service_key.trim().lowercase().replace(" ", "").replace("_", ""), safeSearchKey) >= 80.0 }

            // 🟢 NAYA FETCH: Travel Services uthao
            if (safeSearchKey.contains("bus") || safeSearchKey.contains("metro") || safeSearchKey.contains("flight") || safeSearchKey.contains("airline")) {
                travelData = supabase.postgrest["travel_services"].select().decodeList<TravelServiceModel>()
            }
        } catch (e: Exception) {
            isOffline = true
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageTitle, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = OffWhite
    ) { pad ->
        Box(modifier = Modifier.padding(pad).fillMaxSize(), contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator(color = Indigo)
            } else if (isOffline) {
                OfflineUI { retryTrigger++ }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    serviceInfo?.let { info ->
                        item {
                            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF2EFF6))) {
                                Column(Modifier.padding(20.dp)) {
                                    Text(info.heading, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Spacer(Modifier.height(12.dp))
                                    Text(info.details, fontSize = 15.sp, color = Color(0xFF202020))
                                }
                            }
                        }
                    }

                    // 🟢 MAPPING DATABASE TO AWESOME UI
                    item {
                        when {
                            safeSearchKey.contains("bus") -> {
                                val busList = travelData.filter { it.service_type.lowercase() == "bus" }.map {
                                    BusInfo(it.title, it.link_1 ?: "", it.link_2 ?: "", it.link_3 ?: "", it.link_4 ?: "")
                                }
                                BusSection(busList)
                            }
                            safeSearchKey.contains("metro") -> {
                                val metroList = travelData.filter { it.service_type.lowercase() == "metro" }.map {
                                    MetroInfo(it.title, it.subtitle ?: "", it.link_1, it.link_3)
                                }
                                MetroSection(metroList)
                            }
                            safeSearchKey.contains("flight") || safeSearchKey.contains("airline") -> {
                                val flightList = travelData.filter { it.service_type.lowercase() == "flight" }.map {
                                    AirlineInfo(it.title, it.link_1 ?: "", it.link_2 ?: "", it.link_3 ?: "")
                                }
                                FlightSection(flightList)
                            }
                            safeSearchKey.contains("railway") || safeSearchKey.contains("train") -> {
                                RailwaySection() // Railway ke links pehle jese 'service_links' se hi control ho jayenge (items(links) me dikh jayega)
                            }
                        }
                    }

                    // REGULAR LINKS (Aadhar wagera ke liye)
                    items(links) { link ->
                        ActionRowListStyle(title = link.button_title) { safeOpenUrl(context, link.url) }
                    }
                }
            }
        }
    }
}

// ==============================================================
// 🚌 BUS SECTION
// ==============================================================
@Composable
fun BusSection(buses: List<BusInfo>) {
    val context = LocalContext.current
    var selectedBus by remember { mutableStateOf<BusInfo?>(null) }

    if (buses.isEmpty()) {
        Text("No buses available. Please check later.", color = Color.Gray, modifier = Modifier.padding(16.dp))
        return
    }

    if (selectedBus == null) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            buses.forEach { bus ->
                ActionRowListStyle(title = bus.name, icon = Icons.Outlined.DirectionsBus) { selectedBus = bus }
            }
        }
    } else {
        DetailViewLayout(title = selectedBus!!.name, onBack = { selectedBus = null }) {
            if(selectedBus!!.website.isNotEmpty()) ActionRowListStyle("Official Website") { safeOpenUrl(context, selectedBus!!.website) }
            if(selectedBus!!.booking.isNotEmpty()) ActionRowListStyle("Book Ticket") { safeOpenUrl(context, selectedBus!!.booking) }
            if(selectedBus!!.status.isNotEmpty()) ActionRowListStyle("Check Ticket Status") { safeOpenUrl(context, selectedBus!!.status) }
            if(selectedBus!!.app.isNotEmpty()) ActionRowListStyle("Official App") { safeOpenUrl(context, selectedBus!!.app) }
        }
    }
}

// ==============================================================
// ✈️ FLIGHT SECTION
// ==============================================================
@Composable
fun FlightSection(airlines: List<AirlineInfo>) {
    val context = LocalContext.current
    var selectedAirline by remember { mutableStateOf<AirlineInfo?>(null) }

    if (airlines.isEmpty()) {
        Text("No flights available. Please check later.", color = Color.Gray, modifier = Modifier.padding(16.dp))
        return
    }

    if (selectedAirline == null) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            airlines.forEach { airline ->
                ActionRowListStyle(title = airline.name, icon = Icons.Outlined.Flight) { selectedAirline = airline }
            }
        }
    } else {
        DetailViewLayout(title = selectedAirline!!.name, onBack = { selectedAirline = null }) {
            if(selectedAirline!!.website.isNotEmpty()) ActionRowListStyle("Official Website") { safeOpenUrl(context, selectedAirline!!.website) }
            if(selectedAirline!!.booking.isNotEmpty()) ActionRowListStyle("Book Ticket") { safeOpenUrl(context, selectedAirline!!.booking) }
            if(selectedAirline!!.checkin.isNotEmpty()) ActionRowListStyle("Web Check-in & Status") { safeOpenUrl(context, selectedAirline!!.checkin) }
        }
    }
}

// ==============================================================
// 🚇 METRO SECTION
// ==============================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetroSection(metros: List<MetroInfo>) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedMetro by remember { mutableStateOf<MetroInfo?>(null) }

    val filteredMetros = metros.filter { it.name.contains(searchQuery, ignoreCase = true) || it.city.contains(searchQuery, ignoreCase = true) }

    if (metros.isEmpty()) {
        Text("No metros available. Please check later.", color = Color.Gray, modifier = Modifier.padding(16.dp))
        return
    }

    if (selectedMetro == null) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search Metro (e.g., Delhi, Pune)") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Indigo)
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (filteredMetros.isEmpty()) {
                    Text("No metro service found for this search.", color = Color.Gray, modifier = Modifier.padding(8.dp))
                } else {
                    filteredMetros.forEach { metro ->
                        val displayTitle = if(metro.city.isNotEmpty()) "${metro.name} (${metro.city})" else metro.name
                        ActionRowListStyle(title = displayTitle, icon = Icons.Outlined.Train) { selectedMetro = metro }
                    }
                }
            }
        }
    } else {
        val displayTitle = if(selectedMetro!!.city.isNotEmpty()) "${selectedMetro!!.name} Services" else "${selectedMetro!!.name} Services"
        DetailViewLayout(title = displayTitle, onBack = { selectedMetro = null }) {

            ActionRowListStyle("WhatsApp Ticket") {
                selectedMetro!!.whatsapp?.let {
                    if(it.isNotEmpty()) safeOpenUrl(context, "https://wa.me/91$it")
                    else Toast.makeText(context, "WhatsApp ticket not available. Please inquire at the counter.", Toast.LENGTH_LONG).show()
                } ?: Toast.makeText(context, "WhatsApp ticket not available. Please inquire at the counter.", Toast.LENGTH_LONG).show()
            }

            ActionRowListStyle("Official App") {
                selectedMetro!!.appLink?.let {
                    if(it.isNotEmpty()) safeOpenUrl(context, it)
                    else Toast.makeText(context, "Official app not available. Please get the ticket from the counter.", Toast.LENGTH_LONG).show()
                } ?: Toast.makeText(context, "Official app not available. Please get the ticket from the counter.", Toast.LENGTH_LONG).show()
            }
        }
    }
}

// ==============================================================
// 🚂 RAILWAY SECTION (Already handled by generic Links above, kept for placeholder)
// ==============================================================
@Composable
fun RailwaySection() {
    // Railway ke links normal "links" list se hi aa jayenge ab!
}


// ==============================================================
// 🛠 REUSABLE UI COMPONENTS & HELPERS
// ==============================================================

@Composable
fun DetailViewLayout(title: String, onBack: () -> Unit, content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6))) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack, modifier = Modifier.size(32.dp).background(Color.White, RoundedCornerShape(8.dp))) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Indigo, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Indigo)
            }
            Divider(color = Color.LightGray)
            content()
        }
    }
}

@Composable
fun ActionRowListStyle(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector? = null, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                if (icon != null) {
                    Icon(imageVector = icon, contentDescription = null, tint = Indigo)
                    Spacer(Modifier.width(12.dp))
                }
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(Modifier.width(12.dp))
            Button(onClick = onClick, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = Indigo), contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                Text("GO", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun OfflineUI(onRetry: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
        Icon(Icons.Outlined.CloudOff, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Oops! You are offline", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Please connect to the internet to view these services.", color = Color.Gray, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = Indigo)) { Text("Retry") }
    }
}

fun safeOpenUrl(context: Context, url: String) {
    try {
        if (url.contains("play.google.com") || url.contains("wa.me")) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } else {
            val customTabsIntent = CustomTabsIntent.Builder().setShowTitle(true).build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Wait...", Toast.LENGTH_SHORT).show()
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}

fun calculateSimilarityPercentage(s1: String, s2: String): Double {
    if (s1.isEmpty() && s2.isEmpty()) return 100.0
    if (s1.isEmpty() || s2.isEmpty()) return 0.0
    val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
    for (i in 0..s1.length) dp[i][0] = i
    for (j in 0..s2.length) dp[0][j] = j
    for (i in 1..s1.length) {
        for (j in 1..s2.length) {
            val cost = if (s1[i - 1] == s2[j - 1]) 0 else 1
            dp[i][j] = minOf(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost)
        }
    }
    val distance = dp[s1.length][s2.length]
    return (1.0 - (distance.toDouble() / maxOf(s1.length, s2.length))) * 100.0
}