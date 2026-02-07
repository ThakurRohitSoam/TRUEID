package com.arpanapteam.trueid.Services

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
        topBar = { ServiceTopAppBar(navController) },
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
                ServiceCategorySection(title = "Important Documents") {

                    ServiceItemCard(
                        ServiceData(
                            "Aadhar Card Update",
                            "Update demographic or biometric details in your Aadhar card.",
                            Icons.Outlined.PersonPin
                        ),
                        onClick = { navController.navigate("aadhar") }
                    )

                    ServiceItemCard(
                        ServiceData(
                            "PAN Card Application",
                            "Apply for a new Permanent Account Number (PAN) card.",
                            Icons.Outlined.WorkOutline
                        ),
                        onClick = { navController.navigate("pan") }
                    )

                    ServiceItemCard(
                        ServiceData(
                            "Driving License",
                            "Apply for a new or renew your driving license.",
                            Icons.Outlined.Badge
                        ),
                        onClick = { navController.navigate("dl") }
                    )

                    ServiceItemCard(
                        ServiceData(
                            "Indian Passport",
                            "Apply for a new Passport",
                            Icons.Outlined.Work
                        ),
                        onClick = { navController.navigate("passport") }
                    )

                    ServiceItemCard(
                        ServiceData(
                            "Voter Id",
                            "Apply for a new Voter Id ",
                            Icons.Outlined.HowToVote
                        ),
                        onClick = { navController.navigate("voter") }
                    )
                }

            }

            item {
                ServiceCategorySection(title = "E-District Services") {

                    ServiceItemCard(
                        ServiceData("Income Certificate","Apply for new income certificate.",Icons.Outlined.Description)
                    ) { navController.navigate("income_certificate") }

                    ServiceItemCard(
                        ServiceData("Domicile Certificate","Obtain a certificate of residency.",Icons.Outlined.Home)
                    ) { navController.navigate("domicile") }

                    ServiceItemCard(
                        ServiceData("Caste Certificate","Apply for caste certificate.",Icons.Outlined.VerifiedUser)
                    ) { navController.navigate("caste") }

                    ServiceItemCard(
                        ServiceData("Ration Card","Apply for New Ration Card",Icons.Outlined.VerifiedUser)
                    ) { navController.navigate("ration") }

                    ServiceItemCard(
                        ServiceData("Family Id","Get your Family Id.",Icons.Outlined.VerifiedUser)
                    ) { navController.navigate("family") }
                }
            }

            item {
                ServiceCategorySection(title = "Government Schemes") {

                    ServiceItemCard(
                        ServiceData("PM-Kisan","₹6000 benefit yearly",Icons.Outlined.HomeWork)
                    ) { navController.navigate("pmkisan") }

                    ServiceItemCard(
                        ServiceData("PMKVY","Skill training scheme",Icons.AutoMirrored.Outlined.Assignment)
                    ) { navController.navigate("pmkvy") }

                    ServiceItemCard(
                        ServiceData("UP Pension","Pension schemes",Icons.AutoMirrored.Outlined.Assignment)
                    ) { navController.navigate("uppension") }
                }
            }

            item {
                ServiceCategorySection(title = "Scholarship") {

                    ServiceItemCard(
                        ServiceData("UP Scholarship","UP Scholarship portal",Icons.Outlined.School)
                    ) { navController.navigate("upscholarship") }

                    ServiceItemCard(
                        ServiceData("National Scholarship","Central Govt scholarship",Icons.Outlined.HomeWork)
                    ) { navController.navigate("nsp") }

                    ServiceItemCard(
                        ServiceData("Saksham Scholarship","AICTE scheme",Icons.AutoMirrored.Outlined.Assignment)
                    ) { navController.navigate("saksham") }
                }
            }

            item {
                ServiceCategorySection(title = "Education Board & Universities") {

                    ServiceItemCard(ServiceData("UP Board","UPMSP",Icons.AutoMirrored.Outlined.Assignment)) {
                        navController.navigate("upboard")
                    }

                    ServiceItemCard(ServiceData("CBSE","CBSE Board",Icons.AutoMirrored.Outlined.Assignment)) {
                        navController.navigate("cbse")
                    }

                    ServiceItemCard(ServiceData("BTEUP","Technical Board",Icons.AutoMirrored.Outlined.Assignment)) {
                        navController.navigate("bteup")
                    }

                    ServiceItemCard(ServiceData("ICSE","CISCE Board",Icons.AutoMirrored.Outlined.Assignment)) {
                        navController.navigate("cisce")
                    }

                    ServiceItemCard(ServiceData("AKTU","University",Icons.AutoMirrored.Outlined.LibraryBooks)) {
                        navController.navigate("aktu")
                    }

                    ServiceItemCard(ServiceData("CCSU","University",Icons.AutoMirrored.Outlined.MenuBook)) {
                        navController.navigate("ccsu")
                    }
                }
            }


            item {
                ServiceCategorySection(title = "Property and Land Records") {

                    ServiceItemCard(ServiceData("UP Bhulekh","Land records",Icons.Outlined.DocumentScanner)) {
                        navController.navigate("bhulekh")
                    }

                    ServiceItemCard(ServiceData("Property Registration","Register property",Icons.Outlined.PendingActions)) {
                        navController.navigate("property_registration")
                    }

                    ServiceItemCard(ServiceData("Property Maps","Geo maps",Icons.AutoMirrored.Outlined.Assignment)) {
                        navController.navigate("property_maps")
                    }
                }
            }
            // Travel & Ticket Bookings section ONLY updated

            item {
                ServiceCategorySection(title = "Travel & Ticket Bookings") {

                    ServiceItemCard(
                        ServiceData("Indian Railway","Book Indian Railway Tickets",Icons.Outlined.Train)
                    ) {
                        navController.navigate("railway")
                    }

                    ServiceItemCard(
                        ServiceData("Flight Booking","Book Flight Tickets",Icons.Outlined.Flight)
                    ) {
                        navController.navigate("flight")
                    }

                    ServiceItemCard(
                        ServiceData("Metro Services","Book Metro Tickets",Icons.Outlined.DirectionsRailwayFilled)
                    ) {
                        navController.navigate("metro")
                    }

                    ServiceItemCard(
                        ServiceData("Bus Booking","Book Bus Tickets",Icons.Outlined.DirectionsBus)
                    ) {
                        navController.navigate("bus")
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
        title = {
            Text(
                text = "Services",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
        modifier = Modifier.fillMaxWidth().clickable{onClick()},
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
    TRUEIDTheme() {
        ServicesScreen(rememberNavController())
    }
}
