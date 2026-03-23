package com.arpanapteam.trueid.Services

import android.content.Intent
import android.net.Uri
import android.widget.Toast // 🟢 Toast ke liye import
import androidx.browser.customtabs.CustomTabsIntent // 🟢 Custom Tabs (App ke andar Chrome) ke liye import
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arpanapteam.trueid.supabase
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import com.arpanapteam.trueid.ServiceLinkModel
import com.arpanapteam.trueid.ServiceInfoModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicServiceScreen(
    navController: NavController,
    serviceKey: String, // e.g. "aadhar", "pan", "helth card"
    pageTitle: String   // e.g. "Aadhaar Services"
) {
    val context = LocalContext.current
    var links by remember { mutableStateOf<List<ServiceLinkModel>>(emptyList()) }
    var serviceInfo by remember { mutableStateOf<ServiceInfoModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(serviceKey) {
        // 🟢 SUPER SMART FIX: Spaces hatao aur sabko small letters me kar do (e.g. "Aadhar Card" -> "aadharcard")
        val safeSearchKey = serviceKey.trim().lowercase().replace(" ", "")

        try {
            // 1. Fetch ALL Service Info
            val allIntros = supabase.postgrest["service_info"].select().decodeList<ServiceInfoModel>()

            // Kotlin ke andar Smart Matching: Aadha word (half-word) idhar ya udhar, kahin bhi mile toh pakad lo!
            serviceInfo = allIntros.firstOrNull { info ->
                val dbKey = info.service_key.trim().lowercase().replace(" ", "")
                dbKey.isNotEmpty() && safeSearchKey.isNotEmpty() &&
                        (dbKey.contains(safeSearchKey) || safeSearchKey.contains(dbKey))
            }
        } catch (e: Exception) {
            println("Info Error: ${e.message}")
        }

        try {
            // 2. Fetch ALL Service Links
            val allLinks = supabase.postgrest["service_links"].select().decodeList<ServiceLinkModel>()

            // Kotlin ke andar Smart Matching for Links
            links = allLinks.filter { link ->
                val dbKey = link.service_key.trim().lowercase().replace(" ", "")
                dbKey.isNotEmpty() && safeSearchKey.isNotEmpty() &&
                        (dbKey.contains(safeSearchKey) || safeSearchKey.contains(dbKey))
            }
        } catch (e: Exception) {
            println("Links Error: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pageTitle, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = OffWhite
    ) { pad ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Indigo)
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(pad).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // --- 🟢 INTRODUCTION CARD ---
                serviceInfo?.let { info ->
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF2EFF6)),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {
                            Column(Modifier.padding(20.dp)) {
                                Text(
                                    text = info.heading,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.Black
                                )
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    text = info.details,
                                    fontSize = 15.sp,
                                    lineHeight = 24.sp,
                                    color = Color(0xFF202020)
                                )
                            }
                        }
                    }
                }

                if (links.isEmpty()) {
                    item {
                        Text("Links coming soon...", color = Color.Gray, modifier = Modifier.padding(16.dp))
                    }
                } else {
                    // --- 🔵 LINKS CARDS ---
                    items(links) { link ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(Modifier.padding(18.dp)) {
                                Text(
                                    text = link.button_title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Spacer(Modifier.height(16.dp))
                                Button(
                                    onClick = {
                                        try {
                                            // 🟢 NAYA TARIQA: App ke andar website kholne ke liye (Custom Tabs)
                                            val builder = CustomTabsIntent.Builder()
                                            builder.setShowTitle(true) // Website ka naam upar dikhayega
                                            val customTabsIntent = builder.build()

                                            // Ye line website ko app ke andar ek overlay mein khol degi
                                            customTabsIntent.launchUrl(context, Uri.parse(link.url))

                                        } catch (e: Exception) {
                                            // Agar Custom Tabs fail hua (jese purane phones me), toh browser mein khulega
                                            Toast.makeText(context, "Opening link...", Toast.LENGTH_SHORT).show()
                                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link.url)))
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth().height(48.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Indigo)
                                ) {
                                    Text("GO", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}