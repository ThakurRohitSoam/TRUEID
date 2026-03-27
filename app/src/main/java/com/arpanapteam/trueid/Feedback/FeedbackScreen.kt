package com.arpanapteam.trueid.Feedback

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.FeedbackModel
import com.arpanapteam.trueid.supabase
import com.arpanapteam.trueid.ui.theme.Indigo
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.net.ConnectException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiStepFeedbackScreen(onBack: () -> Unit, onHomeNavigate: () -> Unit) {
    var rating by remember { mutableIntStateOf(0) }
    var comments by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Feedback", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->

        if (isSubmitted) {
            // 🟢 SUCCESS "THANK YOU" SCREEN
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color(0xFF4CAF50).copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Success", tint = Color(0xFF388E3C), modifier = Modifier.size(50.dp))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Text("Thank You!", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Your feedback helps us improve TRUEID.\nWe appreciate your time!",
                    fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onHomeNavigate,
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo)
                ) {
                    Text("Back to Home", fontSize = 18.sp)
                }
            }
        } else {

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 20.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(10.dp))

                // RATING SECTION
                Text("How would you rate us?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Pick a rate *", color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))

                RatingRow(rating = rating, onRatingChange = { rating = it })

                Spacer(modifier = Modifier.height(24.dp))

                //  COMMENTS SECTION
                Text("Tell us more", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = comments,
                    onValueChange = { comments = it },
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    shape = RoundedCornerShape(14.dp),
                    placeholder = { Text("Write here...", color = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Indigo, unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // USER DETAILS SECTION
                Text("Please share your details for a possible follow-up.", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("What's your name?", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Indigo, unfocusedBorderColor = Color.LightGray)
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it; emailError = false },
                    placeholder = { Text("What's your email? *", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    isError = emailError,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Indigo, unfocusedBorderColor = Color.LightGray)
                )
                if (emailError) {
                    Text("Please provide a valid email address", color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(start = 4.dp, top = 4.dp))
                }

                Spacer(Modifier.height(32.dp))

                // SUBMIT BUTTON
                Button(
                    onClick = {
                        if (rating == 0) {
                            Toast.makeText(context, "Please give a rating first!", Toast.LENGTH_SHORT).show()
                        } else if (!email.contains("@") || !email.contains(".")) {
                            emailError = true
                        } else {
                            isLoading = true
                            scope.launch {
                                try {
                                    val feedback = FeedbackModel(
                                        name = name.ifBlank { "Anonymous" },
                                        email = email,
                                        rating = rating,
                                        message = comments
                                    )
                                    supabase.postgrest["feedback"].insert(feedback)

                                    isLoading = false
                                    isSubmitted = true
                                } catch (e: UnknownHostException) {
                                    //SPECIAL CATCH: Internet band hone par
                                    isLoading = false
                                    Toast.makeText(context, "You are offline. Feedback could not be sent.", Toast.LENGTH_LONG).show()
                                } catch (e: ConnectException) {
                                    // SPECIAL CATCH: Connection fail hone par
                                    isLoading = false
                                    Toast.makeText(context, "Connection failed. Please check your internet.", Toast.LENGTH_LONG).show()
                                } catch (e: Exception) {
                                    // DUSRA KOI ERROR HUA TOH
                                    isLoading = false
                                    val errorMsg = e.message ?: "Unknown error"
                                    if(errorMsg.contains("Unable to resolve host") || errorMsg.contains("No address associated")) {
                                        Toast.makeText(context, "You are offline. Feedback could not be sent.", Toast.LENGTH_LONG).show()
                                    } else {
                                        Toast.makeText(context, "Failed to send: $errorMsg", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Submit", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

//RATING STARS COMPONENT
@Composable
fun RatingRow(rating: Int, onRatingChange: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        (1..5).forEach { index ->
            val isSelected = index <= rating
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .background(
                        if (isSelected) Color(0xFF4CAF50).copy(alpha = 0.2f) else Color.LightGray.copy(alpha = 0.2f),
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { onRatingChange(index) }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (isSelected) Color(0xFF388E3C) else Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
        Text("Poor", color = Color.Gray, fontSize = 12.sp)
        Text("Excellent", color = Color.Gray, fontSize = 12.sp)
    }
}