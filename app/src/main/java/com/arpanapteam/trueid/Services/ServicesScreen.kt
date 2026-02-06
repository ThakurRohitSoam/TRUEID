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
                            Icons.Outlined.Work
                        ),
                        onClick = { navController.navigate("pan") }
                    )

                    ServiceItemCard(
                        ServiceData(
                            "Driving License",
                            "Apply for a new or renew your driving license.",
                            Icons.Outlined.CarRental
                        ),
                        onClick = { navController.navigate("dl") }
                    )

                    ServiceItemCard(
                        ServiceData(
                            "Indian Passport",
                            "Apply for a new Passport",
                            Icons.Outlined.CarRental
                        ),
                        onClick = { navController.navigate("passport") }
                    )

                    ServiceItemCard(
                        ServiceData(
                            "Voter Id",
                            "Apply for a new Voter Id ",
                            Icons.Outlined.CarRental
                        ),
                        onClick = { navController.navigate("voter") }
                    )
                }

            }

            item {
                ServiceCategorySection(title = "E-District Services") {
                    ServiceItemCard(ServiceData("Income Certificate", "Apply for new income certificate.", Icons.Outlined.Description),onClick = {
                        navController.navigate("income_certificate")
                    })

                    ServiceItemCard(ServiceData("Domicile Certificate", "Obtain a certificate of residency in Uttar Pradesh.", Icons.Outlined.Home))

                    ServiceItemCard(ServiceData("Caste Certificate", "Apply for your caste certificate for government benefits.", Icons.Outlined.VerifiedUser))

                    ServiceItemCard(ServiceData("Ration Card", "Apply for New Ration Card", Icons.Outlined.VerifiedUser))

                    ServiceItemCard(ServiceData("Family Id", "Get your Family Id by simple steps.", Icons.Outlined.VerifiedUser))

                }
            }

            item {
                ServiceCategorySection(title = "Government Schemes") {

                    ServiceItemCard(ServiceData("PM-Kisaan Samman Nidhi Yojna", "Get a benefit of 6000 rupees annually Under Pm Kisaan Samman Nidhi ", Icons.Outlined.HomeWork))

                    ServiceItemCard(ServiceData("Pradhan Mantri Kaushal Vikash Yojna", "Information and application for state university admissions.",
                        Icons.AutoMirrored.Outlined.Assignment
                    ))
                    ServiceItemCard(ServiceData("UP Pension Schemes", "Information and application for state university admissions.",
                        Icons.AutoMirrored.Outlined.Assignment
                    ))
                }

            }

            item {
                ServiceCategorySection(title = "Scholarship") {
                    ServiceItemCard(ServiceData("UP Scholarship", "Apply for Scholarship program in Uttar Pradesh.", Icons.Outlined.School))
                    ServiceItemCard(ServiceData("National Scholarship", "Apply for National Scholarship by Central Government", Icons.Outlined.HomeWork))
                    ServiceItemCard(ServiceData("Saksham Scholarship", "Apply for Saksham Scholarship program ",
                        Icons.AutoMirrored.Outlined.Assignment
                    ))
                }

            }

            item {
                ServiceCategorySection(title = "Education Board & Universities") {

                    ServiceItemCard(ServiceData("UP Board", "Check your Results and Course Curriculum",
                        Icons.AutoMirrored.Outlined.Assignment
                    ))

                    ServiceItemCard(ServiceData("CBSE Board", "Check your Results and Course Curriculum",
                        Icons.AutoMirrored.Outlined.Assignment
                    ))
                    ServiceItemCard(ServiceData("UP Board of Technical Education", "Check your Results and Course Curriculum",
                        Icons.AutoMirrored.Outlined.Assignment
                    ))

                    ServiceItemCard(ServiceData("ICSE Board", "Check your Results and Course Curriculum",
                        Icons.AutoMirrored.Outlined.Assignment
                    ))
                    ServiceItemCard(ServiceData("AKTU", "Check your Student Profile Results and Course Curriculum",
                        Icons.AutoMirrored.Outlined.LibraryBooks
                    ))
                    ServiceItemCard(ServiceData("CCSU", "Check your Results and Course Curriculum",
                        Icons.AutoMirrored.Outlined.MenuBook
                    ))
                }

            }


            item {
                ServiceCategorySection(title = "Property and Land Records") {

                    ServiceItemCard(ServiceData("UP Bhulekh", "View your Khasra Khatauni online ", Icons.Outlined.DocumentScanner))

                    ServiceItemCard(ServiceData("Property Registration", "Register property deeds and obtain land records.", Icons.Outlined.PendingActions))

                    ServiceItemCard(ServiceData("Property Maps", "View property maps and obtain land records",
                        Icons.AutoMirrored.Outlined.Assignment
                    ))
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
