package com.arpanapteam.trueid.Services

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.MetroTicketScreen
import com.arpanapteam.trueid.ui.theme.*

data class AadhaarLink(
    val title: String,
    val url: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AadhaarServicesScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    val links = listOf(
        AadhaarLink("Official UIDAI Website", "https://uidai.gov.in/"),
        AadhaarLink("myAadhaar Portal (Update/Download)", "https://myaadhaar.uidai.gov.in/"),
        AadhaarLink("Check Aadhaar Update Status", "https://myaadhaar.uidai.gov.in/CheckAadhaarStatus"),
        AadhaarLink("Book an Appointment", "https://appointments.uidai.gov.in/bookappointment.aspx")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Aadhaar Services") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        },
        containerColor = OffWhite
    ) { pad ->

        LazyColumn(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // 🔹 Description Card
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "What is Aadhaar?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            """
It is a 12-digit unique identification number for residents of India, issued by the government (UIDAI).

Key Features:

• Proof of ID: Valid identity & address proof nationwide  
• Biometrics: Fingerprint & iris based (no duplicates)  
• Essential Uses: Bank, SIM, taxes etc  
• Government Benefits: Subsidies directly in bank  
• Valid Formats: Physical & e-Aadhaar both valid  
• Free for All: Enrollment is free for everyone
""".trimIndent(),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // 🔹 Service Cards
            items(links.size) { i ->

                val item = links[i]

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { open(item.url) },
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(18.dp)) {

                        Text(
                            "Step ${i + 1}: ${item.title}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Click to open",
                            color = Indigo,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}