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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme

// Desktop purple button color
val NSPPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NSPScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("National Scholarship Portal") },
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

            // -------- ABOUT SECTION --------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "About the Portal",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "The National Scholarships Portal (NSP) is a one-stop solution for various services, from student scholarship applications to the disbursal of funds. Its objective is to ensure timely scholarship distribution and apply Direct Benefit Transfer (DBT)."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = { open("https://scholarships.gov.in/") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NSPPurple
                            )
                        ) {
                            Text("Official NSP Website")
                        }
                    }
                }
            }

            // -------- SERVICES --------

            item {
                NSPCard("New Student Registration") {
                    open("https://scholarships.gov.in/")
                }
            }

            item {
                NSPCard("Check Your Eligibility") {
                    open("https://scholarships.gov.in/")
                }
            }

            item {
                NSPCard("Track NSP Payments (PFMS)") {
                    open("https://pfms.nic.in/")
                }
            }
        }
    }
}

// -------- UNIFORM CARD --------

@Composable
fun NSPCard(
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                title,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NSPPurple
                )
            ) {
                Text("GO")
            }
        }
    }
}

// -------- PREVIEW --------

@Preview(showBackground = true)
@Composable
fun NSPPreview() {
    TRUEIDTheme {
        NSPScreen()
    }
}
