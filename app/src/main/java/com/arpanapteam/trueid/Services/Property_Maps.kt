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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyMapsScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Property Maps") },
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
                            "About Property Maps",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Property maps are detailed, scaled drawings that define legal land boundaries, plot sizes, and ownership. They may include structures, easements, and nearby roads. These maps are used by buyers, developers, and government agencies to verify land records, ensure zoning compliance, and calculate property taxes."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://upbhulekh.gov.in/GeoDashboard/")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF5B4AE3) // desktop purple
                            )
                        ) {
                            Text("Open GeoDashboard")
                        }
                    }
                }
            }

            // -------- SERVICE CARDS --------
            item {
                MapServiceCard("View Property Maps") {
                    open("https://upbhulekh.gov.in/GeoDashboard/")
                }
            }

            item {
                MapServiceCard("Search Land Records on Map") {
                    open("https://upbhulekh.gov.in/GeoDashboard/")
                }
            }

            item {
                MapServiceCard("Verify Plot Boundaries") {
                    open("https://upbhulekh.gov.in/GeoDashboard/")
                }
            }

            item {
                MapServiceCard("Check Ownership Details") {
                    open("https://upbhulekh.gov.in/GeoDashboard/")
                }
            }
        }
    }
}

// -------- UNIFORM CARD --------
@Composable
fun MapServiceCard(
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
                    containerColor = Color(0xFF5B4AE3)
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
fun PropertyMapsPreview() {
    TRUEIDTheme {
        PropertyMapsScreen()
    }
}