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
val PortalButtonPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyIdScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UP Family ID") },
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

            item {
                FamilyServiceCard(
                    "Official Family ID Portal"
                ) {
                    open("https://familyid.up.gov.in/")
                }
            }

            item {
                FamilyServiceCard(
                    "New Family ID Registration"
                ) {
                    open("https://familyid.up.gov.in/registration")
                }
            }

            item {
                FamilyServiceCard(
                    "Track Application Status"
                ) {
                    open("https://familyid.up.gov.in/status")
                }
            }

            item {
                FamilyServiceCard(
                    "Download User Manual"
                ) {
                    open("https://familyid.up.gov.in/docs/UserManual.pdf")
                }
            }
        }
    }
}

@Composable
fun FamilyServiceCard(
    title: String,
    onGoClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onGoClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PortalButtonPurple
                )
            ) {
                Text("GO")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FamilyPreview() {
    TRUEIDTheme {
        FamilyIdScreen()
    }
}
*/