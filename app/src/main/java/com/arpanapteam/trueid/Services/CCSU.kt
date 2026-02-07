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

val PortalCCSUPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CcsuScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CCSU") },
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
                            "About CCSU",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Chaudhary Charan Singh University (CCSU), Meerut is a state university in Uttar Pradesh offering undergraduate, postgraduate and professional courses across multiple disciplines."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://www.ccsuniversity.ac.in")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalCCSUPurple
                            )
                        ) {
                            Text("Official University Website")
                        }
                    }
                }
            }

            // ---------- SERVICES ----------

            item {
                CCSUCard("Online Results") {
                    open("https://results.ccsuniversity.ac.in")
                }
            }

            item {
                CCSUCard("Admit Card Download") {
                    open("https://www.ccsuniversity.ac.in/admitcard")
                }
            }

            item {
                CCSUCard("Exam Form / Registration") {
                    open("https://www.ccsuniversity.ac.in/examform")
                }
            }

            item {
                CCSUCard("Syllabus") {
                    open("https://www.ccsuniversity.ac.in/syllabus")
                }
            }

            item {
                CCSUCard("Affiliated College List") {
                    open("https://www.ccsuniversity.ac.in/college")
                }
            }

            // ---------- CONTACT ----------
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

                        Text("Chaudhary Charan Singh University")
                        Text("Meerut, Uttar Pradesh")
                        Text("Website: www.ccsuniversity.ac.in")
                    }
                }
            }
        }
    }
}


// ---------- UNIFORM SERVICE CARD ----------
@Composable
fun CCSUCard(
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


// ---------- PREVIEW ----------
@Preview(showBackground = true)
@Composable
fun CcsuPreview() {
    TRUEIDTheme {
        CcsuScreen()
    }
}
