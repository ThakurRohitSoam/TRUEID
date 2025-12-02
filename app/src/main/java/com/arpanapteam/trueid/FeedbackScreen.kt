package com.arpanapteam.trueid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(navController: NavHostController) {
    Scaffold(
        topBar = { FeedbackTopAppBar(navController) },
        containerColor = OffWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Feedback Matters",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp),
                color = Color.Black
            )

            FeedbackForm()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Feedback", fontWeight = FontWeight.Bold, color = Color.Black) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OffWhite
        )
    )
}

@Composable
fun FeedbackForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(0) }
    var comments by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            placeholder = { Text("Your Name") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("your.email@example.com") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Column {
            Text("Service Rating", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                (1..5).forEach { index ->
                    IconButton(onClick = { rating = index }) {
                        Icon(
                            imageVector = if (index <= rating) Icons.Outlined.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Star $index",
                            tint = if (index <= rating) Color.Yellow else Color.Gray,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = comments,
            onValueChange = { comments = it },
            label = { Text("Comments") },
            placeholder = { Text("Share your thoughts...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo)
        ) {
            Text("Submit Feedback", modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FeedbackScreenPreview() {
    TRUEIDTheme {
//        FeedbackScreen()
    }
}