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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme

val PortalBhulekhPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BhulekhScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bhulekh UP") },
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

            // -------- ABOUT --------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "About Bhulekh",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Bhulekh is the official digital portal of the Uttar Pradesh Revenue Department designed to computerize and provide easy online access to land records like Khatauni (record of rights) and Khasra (plot details). Launched in 2016, it allows citizens to verify ownership, check plot data and view transaction history online — reducing disputes and office visits."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://upbhulekh.gov.in/#/home")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalPurple
                            )
                        ) {
                            Text("Official Bhulekh Portal")
                        }
                    }
                }
            }

            // -------- SERVICES --------

            item {
                ServiceCard("View Khatauni (Record of Rights)") {
                    open("https://upbhulekh.gov.in/#/selection")
                }
            }

            item {
               BhulekhCard("View Khasra (Plot Details)") {
                    open("https://ekhasra.up.gov.in/#/home")
                }
            }

            item {
                BhulekhCard("Check Land Ownership") {
                    open("https://upbhulekh.gov.in/#/home")
                }
            }

            item {
                BhulekhCard("View Transaction History") {
                    open("https://upbhulekh.gov.in/#/home")
                }
            }

        }
    }
}


// -------- UNIFORM CARD --------
@Composable
fun BhulekhCard(
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
                    containerColor = PortalBhulekhPurple
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
fun BhulekhPreview() {
    TRUEIDTheme {
        BhulekhScreen()
    }
}
*/