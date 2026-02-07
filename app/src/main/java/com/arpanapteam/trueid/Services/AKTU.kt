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

val PortalAKTUPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AktuScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AKTU") },
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

            // -------- ABOUT AKTU --------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "About AKTU",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Dr. A.P.J. Abdul Kalam Technical University (AKTU) is a premier technical university of Uttar Pradesh offering engineering, management, pharmacy and other professional courses."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://aktu.ac.in")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalAKTUPurple
                            )
                        ) {
                            Text("Official University Website")
                        }
                    }
                }
            }

            // -------- QUICK LINKS --------

            item {
               AKTUCard("AKTU One View (Result)") {
                    open("https://erp.aktu.ac.in/WebPages/OneView/OneView.aspx")
                }
            }

            item {
                AKTUCard("AKTU ERP Login") {
                    open("https://erp.aktu.ac.in")
                }
            }

            item {
                AKTUCard("KYC (Know Your College)") {
                    open("https://aktu.ac.in/collegeinformation.html")
                }
            }

            item {
                AKTUCard("Affiliated College List") {
                    open("https://aktu.ac.in/affiliated-institutes.html")
                }
            }

            item {
                AKTUCard("Syllabus (Latest)") {
                    open("https://aktu.ac.in/syllabus.html")
                }
            }

            // -------- CONTACT INFO --------
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "Contact Information",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(8.dp))

                        Text("Public Information Officer (PIO)")
                        Text("Dr. Rajiv K. Singh, Deputy Registrar")
                        Text("ar@aktu.ac.in")

                        Spacer(Modifier.height(10.dp))

                        Text("Appellate Authority")
                        Text("Shri Nand Lal Singh, Registrar")
                        Text("registrar@aktu.ac.in")
                    }
                }
            }
        }
    }
}


// -------- UNIFORM CARD --------
@Composable
fun AKTUCard(
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
fun AktuPreview() {
    TRUEIDTheme {
        AktuScreen()
    }
}
