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

val PortalbtupPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BteupScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BTEUP") },
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
                            "About BTEUP",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Board of Technical Education, Uttar Pradesh (BTEUP) conducts diploma and technical education examinations in UP. It manages results, syllabus, courses and student academic records."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://bteup.ac.in")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalbtupPurple
                            )
                        ) {
                            Text("Official BTEUP Website")
                        }
                    }
                }
            }

            // -------- SERVICES --------

            item {
                BTEUPCard("Check Result") {
                    open("https://bteup.ac.in/webapp/Results.aspx")
                }
            }

            item {
                BTEUPCard("Check Syllabus") {
                    open("https://bteup.ac.in/webapp/Syllabus.aspx")
                }
            }

            item {
                BTEUPCard("List of Courses Offered") {
                    open("https://bteup.ac.in/webapp/Courses.aspx")
                }
            }

            item {
                BTEUPCard("Student One View (Engineering)") {
                    open("https://bteup.ac.in/webapp/StudentLogin.aspx")
                }
            }

            item {
                BTEUPCard("Student One View (Pharmacy)") {
                    open("https://bteup.ac.in/webapp/StudentLogin.aspx")
                }
            }
        }
    }
}


// -------- UNIFORM CARD --------
@Composable
fun BTEUPCard(
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
                    containerColor = PortalPurple
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
fun BteupPreview() {
    TRUEIDTheme {
        BteupScreen()
    }
}