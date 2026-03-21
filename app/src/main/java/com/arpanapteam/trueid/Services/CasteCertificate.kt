/*
package com.arpanapteam.trueid.Services

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CasteCertificateScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Caste Certificate") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) { pad ->

        LazyColumn(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // STEP 1
            item {
                CasteStepCard(
                    "Step 1: Register on Portal",
                    "Register yourself on the e-Sathi portal.",
                    "GO"
                ) {
                    open("https://esathi.up.gov.in/")
                }
            }

            // STEP 2
            item {
                StepCard(
                    "Step 2: Prepare Documents",
                    """
• Applicant Photo  
• Aadhaar Card  
• Self-Declaration Form  
• Ration Card / Family ID  
• Caste Letter from Gram Pradhan
                    """.trimIndent(),
                    "Download Form"
                ) {
                    open("https://esathi.up.gov.in/")
                }
            }

            // STEP 3
            item {
                CasteStepCard(
                    "Step 3: Apply Online",
                    "Fill the form and upload documents.",
                    "Apply"
                ) {
                    open("https://esathi.up.gov.in/")
                }
            }

            // STEP 4
            item {
                CasteStepCard(
                    "Step 4: Track Status",
                    "Track application using application number.",
                    "Check"
                ) {
                    open("https://esathi.up.gov.in/citizenservices/login/login.aspx")
                }
            }
        }
    }
}

@Composable
fun CasteStepCard(
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
            Text(title, style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(8.dp))

            Text(desc)

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor =Indigo
                )
            ) {
                Text(buttonText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CastePreview() {
    TRUEIDTheme {
        CasteCertificateScreen()
    }
}
*/