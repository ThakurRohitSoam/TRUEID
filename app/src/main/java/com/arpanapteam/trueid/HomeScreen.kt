package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrueIdHomeScreen(
    openDrawer: () -> Unit,
    navController: NavHostController
) {
    Scaffold(
        containerColor = OffWhite,
        topBar = {
            TrueIdTopAppBar(
                onMenuClick = openDrawer,
                onAdminAccess = { navController.navigate("admin_login") } // Hidden navigation trigger
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item { HeroCard() }

            item {
                ServiceCategoryCard(
                    "Important Documents",
                    listOf(
                        ServiceItem("Aadhar Card", Icons.Outlined.QrCodeScanner,"aadhar"),
                        ServiceItem("PAN Card", Icons.Outlined.CreditCard,"pan"),
                        ServiceItem("Indian Passport", Icons.Outlined.Book,"passport")
                    ),
                    navController
                )
            }

            item {
                ServiceCategoryCard(
                    "Uttar Pradesh E-District Services",
                    listOf(
                        ServiceItem("Income Certificate", Icons.AutoMirrored.Outlined.ReceiptLong,"income_certificate"),
                        ServiceItem("Domicile Certificate", Icons.Outlined.HomeWork,"domicile"),
                        ServiceItem("Caste Certificate", Icons.Outlined.Groups,"caste")
                    ),
                    navController
                )
            }

            item {
                ServiceCategoryCard(
                    "Ticket Booking",
                    listOf(
                        ServiceItem("Indian Railway", Icons.Outlined.Train,"railway"),
                        ServiceItem("Flight Booking", Icons.Outlined.Flight,"flight"),
                        ServiceItem("Metro Services", Icons.Outlined.Tram,"metro")
                    ),
                    navController
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrueIdTopAppBar(onMenuClick: () -> Unit, onAdminAccess: () -> Unit) {
    // State variables for the hidden 5-tap admin logic
    var tapCount by remember { mutableIntStateOf(0) }
    var lastTapTime by remember { mutableLongStateOf(0L) }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // Hides the ripple effect so the secret remains hidden
                ) {
                    val currentTime = System.currentTimeMillis()
                    // If taps are less than 500ms apart, count them
                    if (currentTime - lastTapTime < 500) {
                        tapCount++
                        if (tapCount >= 5) {
                            tapCount = 0 // Reset counter
                            onAdminAccess() // Trigger the navigation
                        }
                    } else {
                        // Reset if the user taps too slowly
                        tapCount = 1
                    }
                    lastTapTime = currentTime
                }
            ) {
                Icon(Icons.Outlined.VerifiedUser, null, tint = Indigo)
                Spacer(Modifier.width(8.dp))
                Text("TRUEID", fontWeight = FontWeight.Bold)
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, null)
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Search, null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun HeroCard() {
    val gradient = Brush.linearGradient(listOf(PinkishPurple, LightPurple))

    Box(
        Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gradient)
            .padding(24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                "Trusted Reliable Unified E-Governance Interface for Data",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Your one-stop portal for all government services in Uttar Pradesh",
                color = Color.White.copy(.9f)
            )
        }
    }
}

data class ServiceItem(
    val name: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun ServiceCategoryCard(
    title: String,
    items: List<ServiceItem>,
    navController: NavHostController
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {

            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Indigo, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(16.dp)
            ) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
            }

            Column(Modifier.padding(vertical = 8.dp)) {
                items.forEach {
                    ServiceItemRow(it, navController)
                }
            }
        }
    }
}

@Composable
fun ServiceItemRow(
    item: ServiceItem,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp), // less vertical gap
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            item.icon,
            contentDescription = null,
            tint = Indigo,
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.width(14.dp))

        Text(
            text = item.name,
            modifier = Modifier.weight(1f),
            color = TextGray,
            fontSize = 15.sp
        )

        Button(
            onClick = { navController.navigate(item.route) },
            contentPadding = PaddingValues(
                horizontal = 14.dp,
                vertical = 2.dp
            ),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo),
            elevation = ButtonDefaults.buttonElevation(2.dp)
        ) {
            Text(
                "GO",
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrueIdHomeScreenPreview() {
    TRUEIDTheme {
        TrueIdHomeScreen(
            openDrawer = {},
            navController = rememberNavController()
        )
    }
}