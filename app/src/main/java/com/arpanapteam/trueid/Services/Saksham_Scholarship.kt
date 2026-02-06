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
val PortalssPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SakshamScholarshipScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saksham Scholarship") },
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

            // ---------- ABOUT ----------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "About the Scheme",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Saksham Scholarship Scheme is an AICTE initiative for specially-abled students pursuing technical education. The aim is to encourage differently-abled students to continue higher education and build a successful career."
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "Benefit: ₹50,000 per year for every selected student."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://www.aicte-india.org/schemes/students-development-schemes/Saksham")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalPurple
                            )
                        ) {
                            Text("Official Saksham Website")
                        }
                    }
                }
            }

            // ---------- SERVICES ----------
            item {
                SakshamCard("Apply for Scholarship") {
                    open("https://www.aicte-india.org/schemes/students-development-schemes/Saksham")
                }
            }

            item {
                SakshamCard("Eligibility Details") {
                    open("https://www.aicte-india.org/schemes/students-development-schemes/Saksham")
                }
            }

            item {
                SakshamCard("Guidelines / Documents") {
                    open("https://www.aicte-india.org/schemes/students-development-schemes/Saksham")
                }
            }
        }
    }
}

// ---------- UNIFORM CARD ----------
@Composable
fun SakshamCard(
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
                    containerColor = PortalssPurple
                )
            ) {
                Text("GO")
            }
        }
    }
}

// ---------- PREVIEW ----------
@Preview(showBackground = true)
@Composable
fun SakshamPreview() {
    TRUEIDTheme {
        SakshamScholarshipScreen()
    }
}