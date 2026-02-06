package com.arpanapteam.trueid.Services

import android.content.Intent
import android.net.Uri
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
import com.arpanapteam.trueid.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeCertificateScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Income Certificate") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // STEP 1
            item {
                StepCard(
                    title = "Step 1: Register on Portal",
                    desc = "If you are a new user, first register on e-Sathi portal.",
                    buttonText = "GO",
                    onClick = {
                        open("https://esathi.up.gov.in/")
                    }
                )
            }

            // STEP 2
            item {
                StepCard(
                    title = "Step 2: Prepare Documents",
                    desc =
                        "• Applicant Photo\n" +
                                "• Aadhaar Card\n" +
                                "• Self-Declaration Form\n" +
                                "• Ration Card / Family ID\n" +
                                "• Salary Slip (if applicable)",
                    buttonText = "Download Form",
                    onClick = {
                        open("https://esathi.up.gov.in/citizenservices/login/login.aspx")
                    }
                )
            }

            // STEP 3
            item {
                StepCard(
                    title = "Step 3: Login & Apply",
                    desc =
                        "Login → Select Income Certificate → Fill form → Upload documents → Pay fee → Submit.",
                    buttonText = "GO",
                    onClick = {
                        open("https://esathi.up.gov.in/")
                    }
                )
            }

            // STEP 4
            item {
                StepCard(
                    title = "Step 4: Check Status",
                    desc = "Track your application using application number.",
                    buttonText = "Check Status",
                    onClick = {
                        open("https://esathi.up.gov.in/citizenservices/login/login.aspx")
                    }
                )
            }
        }
    }
}

@Composable
fun StepCard(
    title: String,
    desc: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                desc,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Indigo)
            ) {
                Text(buttonText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncomePreview() {
    TRUEIDTheme {
        IncomeCertificateScreen()
    }
}