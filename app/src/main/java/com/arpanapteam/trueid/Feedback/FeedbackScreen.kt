package com.arpanapteam.trueid.Feedback

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text("Feedback", fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@Composable
fun MultiStepFeedbackScreen(onSubmit: () -> Unit, onBack: () -> Unit = {}) {

    var step by remember { mutableIntStateOf(1) }
    var rating by remember { mutableIntStateOf(0) }
    var comments by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = { FeedbackTopBar(onBack) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
        ) {

            StepIndicator(step = step)

            Spacer(Modifier.height(25.dp))

            when (step) {
                1 -> Step1Rating(
                    rating = rating,
                    onRatingChange = { rating = it },
                    comments = comments,
                    onCommentsChange = { comments = it },
                    onNext = {
                        if (rating > 0 && comments.isNotBlank()) step = 2
                    }
                )

                2 -> Step2UserDetails(
                    name = name,
                    onNameChange = { name = it },
                    email = email,
                    onEmailChange = { email = it },
                    onPrevious = { step = 1 },
                    onSubmit = onSubmit
                )
            }
        }
    }
}
