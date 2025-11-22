package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavHostController
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.TextGray

data class ServiceData(val title: String, val description: String, val icon: ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(navController: NavHostController) {
    Scaffold(
        containerColor = OffWhite,
        topBar = { ServiceTopAppBar() },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
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

            item {
                ServiceCategorySection(title = "Popular Services") {
                    ServiceItemCard(ServiceData("Aadhar Card Update", "Update demographic or biometric details in your Aadhar card.", Icons.Outlined.PersonPin),onClick = {
                        navController.navigate("income_certificate")
                    })
                    ServiceItemCard(ServiceData("PAN Card Application", "Apply for a new Permanent Account Number (PAN) card.", Icons.Outlined.Work))
                    ServiceItemCard(ServiceData("Scholarship Scheme", "Find and apply for various government Scholarship programs.", Icons.Outlined.School))
                }
            }

            item {
                ServiceCategorySection(title = "Document Services") {
                    ServiceItemCard(ServiceData("Income Certificate", "Apply for or download your income certificate.", Icons.Outlined.Description))
                    ServiceItemCard(ServiceData("Domicile Certificate", "Obtain a certificate of residency in Uttar Pradesh.", Icons.Outlined.Home))
                    ServiceItemCard(ServiceData("Caste Certificate", "Apply for your caste certificate for government benefits.", Icons.Outlined.VerifiedUser))
                }
            }

            item {
                ServiceCategorySection(title = "Utility Services") {
                    ServiceItemCard(ServiceData("Driving License", "Apply for a new or renew your driving license.", Icons.Outlined.CarRental))
                    ServiceItemCard(ServiceData("Property Registration", "Register property deeds and obtain land records.", Icons.Outlined.HomeWork))
                    ServiceItemCard(ServiceData("University Admissions", "Information and application for state university admissions.", Icons.Outlined.Assignment))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Services",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle back click */ }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
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
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            content()
        }
    }
}

@Composable
fun ServiceItemCard(service: ServiceData,onClick: () -> Unit = {}) {
    Card(

        // change by abhishek
        modifier = Modifier.fillMaxWidth().clickable{onClick},
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
    TRUEIDTheme {
//        ServicesScreen(navController = navController)
    }
}
