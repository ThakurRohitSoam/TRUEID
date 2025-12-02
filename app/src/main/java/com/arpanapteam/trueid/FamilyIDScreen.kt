package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme

// Data class to hold the service link information
private data class ServiceLink(
    val index: Int,
    val serviceName: String,
    val linkText: String
)

// List of services shown on the screen
private val serviceLinks = listOf(
    ServiceLink(1, "Official Family ID Portal", "Go to Website"),
    ServiceLink(2, "New Family ID Registration", "Register Here"),
    ServiceLink(3, "Track Application Status", "Check Status"),
    ServiceLink(4, "Download User Manual", "Download PDF")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyIDScreen(navController: NavHostController) {
    Scaffold(
        topBar = { FamilyIDTopAppBar(navController) },
        containerColor = OffWhite
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "UP Family ID (एक परिवार एक पहचान)",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ServiceLinksCard()
            }

            // TODO: Add Instruction for this service later if requested
            item {
                Text(
                    text = "HOW TO APPLY PROCEDURE",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                Step(1, "Register Yourself") {
                    Text("1) Visit the FamilyID Portal")
                    Text("2) Click on the 'Registration' tab on the homepage.")
                    Text("3) Read the 'Registration Instructions' and Enter 'Name & Aadhar Registered Mobile Number'")
                    Text("4) Click on 'Send OTP'")
                    Text("5) Enter OTP received on the Mobile No.")
                    Text("6) Enter Captcha & click on Submit")
                    Text("7) If OTP is not received to the registered mobile number click on 'Resend OTP' or repeat Step 2")
                }
            }
            item {
                Step(2, "Sign In") {
                    Text("1) After successful registration, Click on Sign In")
                    Text("2) Enter Registered Mobile No.")
                    Text("3) Click on Send OTP")
                    Text("4) Enter OTP received on the Mobile No")
                    Text("5) Enter Captcha & click on Login")
                    Text("6) If OTP is not received to the registered mobile number click on 'Resend OTP' or repeat Step 3")
                    Text("7) Click on 'Proceed to register Family ID' ")
                }
            }

            item {
                Text(
                    text = "HOW TO UPDATE PROCEDURE",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp,top = 4.dp)
                )

                Step(1, "Update Your Family ID"){
                    Text("1) Visit the FamilyID Portal")
                    Text("2) After Sign In, enter your Aadhaar number to view your Family ID")
                    Text("3) To update your Family ID, click 'Family ID Update/Access Passbook' and enter the OTP sent to your Aadhaar-linked number")
                    Text("4) Use the 'Update Module' to add a member (due to birth, marriage, or missing during creation)")
                    Text("5) You can delete a member if they've passed away, married, or moved out. If the SPOC is removed, assign a new SPOC and link all members to them")
                    Text("6) If a member's Aadhaar details have changed, update Family ID using OTP-based Aadhaar e-KYC")
                }
            
            }
        }
    }
}

@Composable
private fun ServiceLinksCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            ServiceLinkHeader()
            Divider(color = Color.LightGray.copy(alpha = 0.5f))
            serviceLinks.forEach { serviceLink ->
                ServiceLinkRow(
                    index = serviceLink.index.toString(),
                    service = serviceLink.serviceName,
                    linkText = serviceLink.linkText,
                    onLinkClick = { /*
                        Example
                       val url = when (serviceLink.index) {
                            1 -> "https://fcs.up.gov.in/"
                            2 -> "https://nfsa.gov.in/"
                            3 -> "https://fcs.up.gov.in/Food/citizen/NFSASearch.aspx"
                            4 -> "https://apps.uppwd.gov.in/main-page/status.html" // Example URL, replace with actual
                            5 -> "https://fcs.up.gov.in/Food/citizen/NFSASearch.aspx"
                            else -> ""
                        }
                        onLinkClick(url)
                    */ }
                )
                if (serviceLink.index < serviceLinks.size) {
                    Divider(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ServiceLinkHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Indigo.copy(alpha = 0.08f),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "#",
            modifier = Modifier.weight(0.15f),
            fontWeight = FontWeight.Bold,
            color = Indigo,
            fontSize = 14.sp
        )
        Text(
            text = "SERVICE",
            modifier = Modifier.weight(0.55f),
            fontWeight = FontWeight.Bold,
            color = Indigo,
            fontSize = 14.sp
        )
        Text(
            text = "LINK",
            modifier = Modifier.weight(0.3f),
            fontWeight = FontWeight.Bold,
            color = Indigo,
            textAlign = TextAlign.Start,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ServiceLinkRow(index: String, service: String, linkText: String, onLinkClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = index,
            modifier = Modifier.weight(0.15f),
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        Text(
            text = service,
            modifier = Modifier.weight(0.55f),
            fontSize = 14.sp,
            color = Color.DarkGray
        )
        TextButton(
            onClick = onLinkClick,
            modifier = Modifier.weight(0.3f)
        ) {
            Text(linkText, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FamilyIDTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Family ID") },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp()}) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OffWhite,
            scrolledContainerColor = OffWhite
        )
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FamilyIDScreenPreview() {
    TRUEIDTheme {
//        FamilyIDScreen()
    }
}