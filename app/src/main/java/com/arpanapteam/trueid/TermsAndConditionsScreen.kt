package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsScreen(navController: NavController) {
    Scaffold(
        containerColor = OffWhite, // ✅ Matched theme background
        topBar = { TermsAndConditionsTopAppBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // ✅ Proper spacing between cards
        ) {

            // --- HEADER INTRO ---
            item {
                Text(
                    text = "Terms and Conditions",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Indigo,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // --- TERMS CARDS ---
            item {
                Term(number = 1, title = "Introduction") {
                    Text(
                        text = "Welcome to our TRUEID App. These Terms and Conditions govern your use of our application. By using our app, you accept these terms and conditions in full. If you disagree with these terms and conditions or any part of these terms and conditions, you must not use our application.",
                        color = TextGray, fontSize = 14.sp, lineHeight = 22.sp
                    )
                }
            }
            item {
                Term(number = 2, title = "Non-Affiliation and Educational Purpose") {
                    Text(
                        text = "Our application is a non-governmental, independent entity. We are not affiliated, associated, authorized, endorsed by, or in any way officially connected with any government agency. The information provided in this application is for educational purposes only. We do not provide any government services.",
                        color = TextGray, fontSize = 14.sp, lineHeight = 22.sp
                    )
                }
            }
            item {
                Term(number = 3, title = "Information Sources") {
                    Text(
                        text = "The information and resources provided in our application are sourced from various publicly available government websites, including but not limited to official government portals and news outlets. We strive to provide accurate and up-to-date information, but we do not guarantee the completeness, accuracy, or timeliness of the information.",
                        color = TextGray, fontSize = 14.sp, lineHeight = 22.sp
                    )
                }
            }
            item {
                Term(number = 4, title = "Use of Information") {
                    Text(
                        text = "The content of this application is for general information and educational purposes only. Any reliance you place on such information is therefore strictly at your own risk. We do not provide legal, financial, or any other professional advice. Every effort is made to keep the application up and running smoothly. However, we take no responsibility for, and will not be liable for, the application being temporarily unavailable due to technical issues beyond our control.",
                        color = TextGray, fontSize = 14.sp, lineHeight = 22.sp
                    )
                }
            }
            item {
                Term(number = 5, title = "Data Accuracy and Liability Disclaimer") {
                    Text(
                        text = "While we strive to keep the information up to date and correct, we make no representations or warranties of any kind, express or implied, about the completeness, accuracy, reliability, suitability, or availability with respect to the application or the information, products, services, or related graphics contained in the application for any purpose. Any reliance you place on such information is therefore strictly at your own risk.",
                        color = TextGray, fontSize = 14.sp, lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "In no event will we be liable for any loss or damage including without limitation, indirect or consequential loss or damage, or any loss or damage whatsoever arising from loss of data or profits arising out of, or in connection with, the use of this application.",
                        color = TextGray, fontSize = 14.sp, lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsTopAppBar(navController: NavController) {
    TopAppBar(
        title = { Text("Terms and Conditions", fontWeight = FontWeight.Bold, color = Color.Black) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") // ✅ Fixed Icon
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White) // ✅ Matched Theme
    )
}

// ✅ PREMIUM CARD DESIGN ADDED HERE
@Composable
fun Term(number: Int, title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Serial Number Box
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Indigo.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Indigo,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Title
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TermsAndConditionsScreenPreview() {
    val navController = rememberNavController()
    TRUEIDTheme {
        TermsAndConditionsScreen(navController)
    }
}