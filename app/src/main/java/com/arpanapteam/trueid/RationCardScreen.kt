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
import androidx.compose.material.icons.filled.ArrowBack

// Data class to hold the service link information
data class ServiceLink(val index: Int, val serviceName: String, val linkText: String)

// List of services shown on the screen
private val rationCardServiceLinks = listOf(
    ServiceLink(1, "UP Ration Card Official Website (FCS)", "Go to Website"),
    ServiceLink(2, "National Food Security Act (NFSA) Portal", "Go to Website"),
    ServiceLink(3, "Check Eligibility in List", "Check Here"),
    ServiceLink(4, "Check Application Status", "Track Here"),
    ServiceLink(5, "Search & Download Your Ration Card", "Search Here")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RationCardScreen(navController: NavHostController) {
    Scaffold(
        topBar = { RationCardTopAppBar(navController) },
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
                    text = "Ration Card Services (UP)",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                RationCardServiceLinksCard()
            }

            // TODO: Add steps for this service later as requested
            /*
            item {
                Step(1, "Title for step 1") {
                    Text("Content for step 1")
                }
            }
            item {
                Step(2, "Title for step 2") {
                    Text("Content for step 2")
                }
            }
            */
        }
    }
}

@Composable
private fun RationCardServiceLinksCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            ServiceLinkHeader()
            Divider(color = Color.LightGray.copy(alpha = 0.5f))
            rationCardServiceLinks.forEach { serviceLink ->
                ServiceLinkRow(
                    index = serviceLink.index.toString(),
                    service = serviceLink.serviceName,
                    linkText = serviceLink.linkText,
                    onLinkClick = {
                        // val url = when (serviceLink.index) {
                        //     1 -> "https://fcs.up.gov.in/"
                        //     2 -> "https://nfsa.gov.in/"
                        //     3 -> "https://fcs.up.gov.in/Food/citizen/NFSASearch.aspx"
                        //     4 -> "https://apps.uppwd.gov.in/main-page/status.html" // Example URL, replace with actual
                        //     5 -> "https://fcs.up.gov.in/Food/citizen/NFSASearch.aspx"
                        //     else -> ""
                        // }
                        // onLinkClick(url)
                    }
                )
                if (serviceLink.index < rationCardServiceLinks.size) {
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
private fun RationCardTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Ration Card") },
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
fun RationCardScreenPreview() {
    TRUEIDTheme {
//        RationCardScreen()
    }
}