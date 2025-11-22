package com.arpanapteam.trueid

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeCertificateScreen(navController: NavHostController) {

    Scaffold(
        topBar = { IncomeCertificateTopAppBar(onBackClick = { navController.popBackStack() }) },
        containerColor = OffWhite,
        bottomBar = {
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Indigo)
            ) {
                Text("Go to e-Sathi Portal", modifier = Modifier.padding(8.dp))
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Step(1, "Register on e-Sathi Portal") {
                    Text("If you are a new user, you must first register yourself. Click here to register.")
                }
            }

            item {
                Step(2, "Prepare Your Documents") {
                    Text("Keep scanned copies of the following documents ready:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("- Applicant’s Photo (under 50KB)")
                    Text("- Aadhar Card (under 100KB)")
                    Text("- Self-Declaration Form (under 100KB) - Download here")
                    Text("- Ration Card / Family ID (under 100KB)")
                    Text("- Salary Slip (if applicable, under 100KB)")
                }
            }

            item {
                Step(3, "Login and Apply") {
                    Text("Follow these instructions after logging in:")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("1. Login to the portal with your credentials.")
                    Text("2. Select \"Application For Income Certificate\" from the dashboard.")
                    Text("3. Fill in all the required details accurately.")
                    Text("4. Upload the prepared documents.")
                    Text("5. Submit the form and note down the Application Number.")
                    Text("6. Make the payment of ₹15 (service charge).")
                    Text("7. Download the final Acknowledgement Slip.")
                }
            }

            item {
                Step(4, "Check Application Status") {
                    Text("You can track the status of your application using your application number. Click here to check status.")
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.income_certificate_tutorial),
                            contentDescription = "Income Certificate Tutorial",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = "Play Video",
                            modifier = Modifier.size(64.dp),
                            tint = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "How to Apply for Income Certificate",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { /* TODO */ },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo)
                    ) {
                        Text("Watch Tutorial", modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeCertificateTopAppBar(onBackClick: () -> Unit) {

    TopAppBar(
        title = { Text("Income Certificate") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun Step(stepNumber: Int, title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Step $stepNumber: $title",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Indigo
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IncomeCertificateScreenPreview() {
    val navController = rememberNavController()
    TRUEIDTheme {
        IncomeCertificateScreen(navController)
    }
}
