package com.arpanapteam.trueid.Services

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.from

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceCardScreen(
    supabaseClient: io.github.jan.supabase.SupabaseClient,
    onBackClick: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var serviceKey by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Service Card", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Add New Service Card",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 1. Card Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = { Text("Card Title (e.g. Health Card)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Card Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Card Description") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Category (Yeh magic karega!)
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                placeholder = { Text("Category (e.g. Medical Services)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Service Key
            OutlinedTextField(
                value = serviceKey,
                onValueChange = { serviceKey = it },
                placeholder = { Text("Service Key (e.g. health_card)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Add Card Button
            Button(
                onClick = {
                    if (title.isNotBlank() && category.isNotBlank() && serviceKey.isNotBlank()) {
                        coroutineScope.launch {
                            isLoading = true
                            try {
                                val newCard = mapOf(
                                    "title" to title.trim(),
                                    "subtitle" to description.trim(),
                                    "category" to category.trim(),
                                    "service_key" to serviceKey.trim().lowercase() // URL safe
                                )
                                supabaseClient.from("app_services").insert(newCard)
                                snackbarMessage = "Card Added Successfully!"

                                // Fields clear kar do taki naya card add kar sake
                                title = ""
                                description = ""
                                category = ""
                                serviceKey = ""
                            } catch (e: Exception) {
                                snackbarMessage = "Error: ${e.localizedMessage}"
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        snackbarMessage = "Please fill all required fields"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5)), // Indigo/Blue color matching your screenshot
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Add Card to App", fontSize = 16.sp, color = Color.White)
                }
            }

            if (snackbarMessage.isNotEmpty()) {
                Text(
                    text = snackbarMessage,
                    color = if (snackbarMessage.contains("Error")) Color.Red else Color(0xFF4CAF50),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Existing Cards Heading
            Text(
                text = "Existing Cards",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

        }
    }
}