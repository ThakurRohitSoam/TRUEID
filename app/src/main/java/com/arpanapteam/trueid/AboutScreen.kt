package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.TextGray

data class Service(val name: String, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    val services = listOf(
        Service("Aadhar Card", "A 12-digit unique identity number for Indian citizens, storing biometric and demographic data."),
        Service("PAN Card", "A 10-digit alphanumeric number assigned to all taxpayers in India for tracking financial transactions."),
        Service("Indian Passport", "An official document for Indian citizens for the purpose of international travel."),
        Service("Voter ID", "An identity document for Indian citizens to cast their ballot in elections."),
        Service("Driving License", "An official document that authorizes its holder to operate various types of motor vehicles."),
        Service("Indian Railway", "Services related to India's national railway system, including booking and inquiries."),
        Service("PM Kisan", "A scheme for income support scheme for small and marginal farmer families."),
        Service("PM KVY", "A skill development scheme to encourage aptitude towards employable skills."),
        Service("UP Pension", "Financial assistance in the form of pension to the elderly, widows, and disabled persons.")
    )

    Scaffold(
        containerColor = OffWhite,
        topBar = { AboutTopAppBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- ABOUT TRUEID INTRO CARD ---
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    // ✅ Center alignment hata kar default (Left/Start) kar diya hai
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "About TRUEID",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Indigo,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Welcome to our Self-Service Portal. Our mission is to provide comprehensive and easy-to-understand information about services and schemes offered by the Government of India. We offer not just information, but also direct links and step-by-step instructions to help you access these services without hassle. Everything you need is right here in one place.",
                            textAlign = TextAlign.Start,
                            fontSize = 15.sp,
                            color = TextGray,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            // SERVICES WE COVER HEADING
            item {
                Text(
                    text = "Services We Cover",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            // LIST OF SERVICES AS MODERN CARDS
            itemsIndexed(services) { index, service ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Serial Number Box
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Indigo.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = (index + 1).toString(),
                                fontWeight = FontWeight.Bold,
                                color = Indigo,
                                fontSize = 16.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Text Details
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = service.name,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = service.description,
                                color = TextGray,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTopAppBar(navController: NavController) {
    TopAppBar(
        title = { Text("About", fontWeight = FontWeight.Bold, color = Color.Black) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    val navController = rememberNavController()
    TRUEIDTheme {
        AboutScreen(navController)
    }
}