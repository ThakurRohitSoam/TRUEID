package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.TextGray

data class ServiceData(val title: String, val description: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(navController: NavController) {

    Scaffold(
        containerColor = OffWhite,
        topBar = { ServiceTopAppBar(navController) },   // FIXED
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Search Box
            item {
                var searchQuery by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search for services.......") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            // Popular Services
            item {
                ServiceCategorySection(title = "Popular Services") {
                    ServiceItemCard(
                        service = ServiceData(
                            "Aadhar Card Update",
                            "Update demographic or biometric details in your Aadhar card.",
                            Icons.Outlined.PersonPin
                        )
                    )
                    ServiceItemCard(
                        service = ServiceData(
                            "PAN Card Application",
                            "Apply for a new Permanent Account Number (PAN) card.",
                            Icons.Outlined.Work
                        )
                    )
                    ServiceItemCard(
                        service = ServiceData(
                            "Scholarship Scheme",
                            "Find and apply for various government Scholarships.",
                            Icons.Outlined.School
                        )
                    )
                }
            }

            // Document Services
            item {
                ServiceCategorySection(title = "Document Services") {

                    ServiceItemCard(
                        service = ServiceData(
                            "Income Certificate",
                            "Apply for or download your income certificate.",
                            Icons.Outlined.Description
                        ),
                        onClick = { navController.navigate("income_certificate") }
                    )

                    ServiceItemCard(
                        service = ServiceData(
                            "Domicile Certificate",
                            "Obtain a certificate of residency in Uttar Pradesh.",
                            Icons.Outlined.Home
                        )
                    )

                    ServiceItemCard(
                        service = ServiceData(
                            "Caste Certificate",
                            "Apply for your caste certificate for government benefits.",
                            Icons.Outlined.VerifiedUser
                        )
                    )
                }
            }

            // Utility Services
            item {
                ServiceCategorySection(title = "Utility Services") {

                    ServiceItemCard(
                        service = ServiceData(
                            "Driving License",
                            "Apply for or renew your driving license.",
                            Icons.Outlined.CarRental
                        )
                    )

                    ServiceItemCard(
                        service = ServiceData(
                            "Property Registration",
                            "Register property deeds and land records.",
                            Icons.Outlined.HomeWork
                        )
                    )

                    ServiceItemCard(
                        service = ServiceData(
                            "University Admissions",
                            "Details & applications for state university admissions.",
                            Icons.Outlined.Assignment
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceTopAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Services",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {   // BACK FIXED 🔥
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun ServiceCategorySection(title: String, content: @Composable () -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "View All",
                fontSize = 14.sp,
                color = Indigo,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { content() }
    }
}

@Composable
fun ServiceItemCard(service: ServiceData, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                imageVector = Icons.Outlined.ArrowForward,
                contentDescription = "Go",
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ServicesScreenPreview() {
    val navController = rememberNavController()
    TRUEIDTheme(darkTheme = false) {
        ServicesScreen(navController)
    }
}
