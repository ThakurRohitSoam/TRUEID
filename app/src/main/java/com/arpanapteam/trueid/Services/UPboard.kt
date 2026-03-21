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

// Desktop style purple
val PortalupboardPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPBoardScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UP Board (UPMSP)") },
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
                            "About UP Board",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Board of High School & Intermediate Education, Uttar Pradesh (UPMSP) conducts Class 10th and 12th examinations in Uttar Pradesh. It provides syllabus, results, model papers and academic resources for students."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://upmsp.edu.in")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalupboardPurple
                            )
                        ) {
                            Text("Official UPMSP Website")
                        }
                    }
                }
            }

            // -------- SERVICES --------
            item {
                UPboardCard("Results (10th & 12th)") {
                    open("https://upmsp.edu.in/Results.aspx")
                }
            }

            item {
                UPboardCard("Results (Current Year - NIC)") {
                    open("https://results.nic.in")
                }
            }

            item {
                UPboardCard("Latest Syllabus") {
                    open("https://upmsp.edu.in/Syllabus.html")
                }
            }

            item {
                UPboardCard("Model Papers") {
                    open("https://upmsp.edu.in/ModelPaper.html")
                }
            }

            item {
                UPboardCard("Academic Calendar") {
                    open("https://upmsp.edu.in/AcademicCalendar.html")
                }
            }
        }
    }
}

// -------- UNIFORM CARD --------
@Composable
fun UPboardCard(
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
fun UPBoardPreview() {
    TRUEIDTheme {
        UPBoardScreen()
    }
}
*/