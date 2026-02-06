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

val PortalPropertyPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyRegistrationScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Property Registration") },
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
                            "About Property Registration",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Property registration is the mandatory legal process of recording ownership of immovable property with government authorities. It is governed by Section 17 of the Indian Registration Act, 1908. This process legally transfers ownership from seller to buyer, prevents fraud, and provides legal validity to the transaction. It requires payment of stamp duty and registration fees and creates a permanent public record."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://igrsup.gov.in/igrsup/defaultAction.action")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalPurple
                            )
                        ) {
                            Text("Official IGRSUP Portal")
                        }
                    }
                }
            }

            // -------- SERVICES --------

            item {
                PropertyCard("New Property Registration") {
                    open("https://igrsup.gov.in/igrsupPropertyRegistration/")
                }
            }

            item {
                PropertyCard("Property Valuation & Stamp Duty") {
                    open("https://igrsup.gov.in/igrsup/defaultAction.action")
                }
            }

            item {
                PropertyCard("Search Registered Property") {
                    open("https://igrsup.gov.in/igrsup/defaultAction.action")
                }
            }

            item {
                PropertyCard("Download Registered Deed") {
                    open("https://igrsup.gov.in/igrsup/defaultAction.action")
                }
            }
        }
    }
}


// -------- UNIFORM CARD --------
@Composable
fun PropertyCard(
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
                    containerColor =PortalPropertyPurple
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
fun PropertyPreview() {
    TRUEIDTheme {
        PropertyRegistrationScreen()
    }
}