package com.arpanapteam.trueid.Services

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arpanapteam.trueid.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeCertificateScreen(navController: NavController) {

    Scaffold(
        containerColor = OffWhite,
        topBar = {
            TopAppBar(
                title = { Text("Income Certificate") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Step(1, "Register on Portal") {
                    Text("Register yourself on the e-Sathi portal.")
                }
            }

            item {
                Step(2, "Prepare Documents                         ") {
                    Text("- Aadhaar Card")
                    Text("- Income Proof")
                    Text("- Family ID / Ration Card")
                }
            }

            item {
                Step(3, "Apply Online           ") {
                    Text("Fill the form and upload documents.")
                }
            }

            item {
                Step(4, "Track Status") {
                    Text("Track application using application number.")
                }
            }
        }
    }
}

@Composable
fun Step(
    stepNo: Int,
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Step $stepNo: $title",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IncomeCertificatePreview() {
    TRUEIDTheme {
        IncomeCertificateScreen(rememberNavController())
    }
}
