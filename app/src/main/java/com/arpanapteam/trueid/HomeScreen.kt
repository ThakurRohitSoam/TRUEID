package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TrueIdHomeScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(onClose = { scope.launch { drawerState.close() } }, navController=navController)
        }
    ) {
        Scaffold(
            containerColor = OffWhite,
//            contentWindowInsets = WindowInsets(0,0,0,0),
            topBar = {
                TrueIdTopAppBar(onMenuClick = {
                    scope.launch {
                        drawerState.open()
                    }
                })
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
                        title = "Important Documents",
                        items = listOf(
                            ServiceItem("Aadhar Card", Icons.Outlined.QrCodeScanner),
                            ServiceItem("PAN Card", Icons.Outlined.CreditCard),
                            ServiceItem("Indian Passport", Icons.Outlined.Book)
                        )
                    )
                }
                item {
                    ServiceCategoryCard(
                        title = "Uttar Pradesh E-District Services",
                        items = listOf(
                            ServiceItem("Income Certificate", Icons.AutoMirrored.Outlined.ReceiptLong),
                            ServiceItem("Domicile Certificate", Icons.Outlined.HomeWork),
                            ServiceItem("Caste Certificate", Icons.Outlined.Groups)
                        )
                    )
                }
                item {
                    ServiceCategoryCard(
                        title = "Ticket Booking",
                        items = listOf(
                            ServiceItem("Indian Railway", Icons.Outlined.Train),
                            ServiceItem("Flight Booking", Icons.Outlined.Flight),
                            ServiceItem("Metro Services", Icons.Outlined.Tram)
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrueIdTopAppBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.VerifiedUser,
                    contentDescription = "App Logo",
                    tint = Indigo
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "TRUEID",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
            }
        },
        actions = {
            IconButton(onClick = { /* Handle search click */ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
fun HeroCard() {
    val gradient = Brush.linearGradient(
        colors = listOf(PinkishPurple, LightPurple)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gradient)
            .padding(24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                text = "Trusted Reliable Unified E-Governance Interface for Data",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Your one-stop portal for all government services in Uttar Pradesh",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
        }
    }
}

data class ServiceItem(val name: String, val icon: ImageVector)

@Composable
fun ServiceCategoryCard(title: String, items: List<ServiceItem>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Indigo,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                items.forEach { item ->
                    ServiceItemRow(item = item)
                }
            }
        }
    }
}

@Composable
fun ServiceItemRow(item: ServiceItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.name,
            tint = Indigo
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = item.name,
            color = TextGray,
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrueIdHomeScreenPreview() {
    TRUEIDTheme { // Use your actual theme
//       TrueIdHomeScreen()
//
    }
}
