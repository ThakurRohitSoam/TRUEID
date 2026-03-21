package com.arpanapteam.trueid.Services

import android.content.Intent
import android.net.Uri
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
import com.arpanapteam.trueid.ServiceInfoModel // Naya model

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicServiceScreen(
    navController: NavController,
    serviceKey: String, // e.g. "aadhar", "pan"
    pageTitle: String   // e.g. "Aadhaar Services"
) {
    val context = LocalContext.current
    var links by remember { mutableStateOf<List<ServiceLinkModel>>(emptyList()) }
    var serviceInfo by remember { mutableStateOf<ServiceInfoModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch Introduction & Links simultaneously
    LaunchedEffect(serviceKey) {
        try {
            // 1. Fetch Service Info (Introduction Card)
            val infoResult = supabase.postgrest["service_info"]
                .select { filter { eq("service_key", serviceKey) } }
                .decodeList<ServiceInfoModel>()
            serviceInfo = infoResult.firstOrNull()

            // 2. Fetch Service Links (Go Buttons)
            val linksResult = supabase.postgrest["service_links"]
                .select { filter { eq("service_key", serviceKey) } }
                .decodeList<ServiceLinkModel>()
            links = linksResult

        } catch (e: Exception) {
            println("Error: ${e.message}")
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
                verticalArrangement = Arrangement.spacedBy(16.dp) // Gap between cards
            ) {

                // --- 🟢 INTRODUCTION CARD (WHAT IS AADHAAR?) ---
                serviceInfo?.let { info ->
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            // Aapke screenshot jaisa light purple background color
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
                                    lineHeight = 24.sp, // Line height for readable paragraphs
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
                                    onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link.url))) },
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