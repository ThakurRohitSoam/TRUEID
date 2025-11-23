package com.arpanapteam.trueid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CasteCertificateScreen() {
    Scaffold(
        topBar = { CasteCertificateTopAppBar() },
        containerColor = OffWhite,
        bottomBar = {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Indigo)
            ) {
                Text("Go to e-Sathi Portal", modifier = Modifier.padding(8.dp))
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Step(1, "Register on e-Sathi Portal") {
                    Text("If you are a new user, you must first register yourself. Click here to register.")
                }
            }
            item {
                Step(2, "Prepare Your Documents") {
                    Text("Keep scanned copies of the following documents ready:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("- Applicant’s Photo (under 50KB)")
                    Text("- Aadhar Card (under 100KB)")
                    Text("- Self-Declaration Form (under 100KB) - Download Form")
                    Text("- Ration Card / Family ID (under 100KB)")
                    Text("- Caste Letter from Gram Pradhan/Parshad/Warden (under 100KB)")
                }
            }
            item {
                Step(3, "Login and Apply") {
                    Text("Follow these instructions after logging in:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("1. Login to the portal with your credentials.")
                    Text("2. Select \"Application For Caste Certificate\" from the dashboard.")
                    Text("3. Fill in all the required details accurately.")
                    Text("4. Upload the prepared documents.")
                    Text("5. Submit the form and note down the Application Number.")
                    Text("6. Make the payment of ₹15 (service charge).")
                    Text("7. Download the final Acknowledgement Slip.")
                }
            }
            item {
                Step(4, "Check Application Status") {
                    Text("You can track the status of your application using your application number. Click here to check status.")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CasteCertificateTopAppBar() {
    TopAppBar(
        title = { Text("Caste Certificate") },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CasteCertificateScreenPreview() {
    TRUEIDTheme {
        CasteCertificateScreen()
    }
}
