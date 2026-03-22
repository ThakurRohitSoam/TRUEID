/*
package com.arpanapteam.trueid.Feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Step2UserDetails(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    onPrevious: () -> Unit,
    onSubmit: () -> Unit
) {

    var showError by remember { mutableStateOf(false) }

    Text(
        "Please share your email if you agree to a possible follow-up.",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(20.dp))

    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("What's your name?") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    )

    Spacer(Modifier.height(16.dp))

    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
            showError = false
        },
        label = { Text("What's your email? *") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        isError = showError
    )

    if (showError) {
        Text(
            "Please provide a valid email address",
            color = Color.Red,
            fontSize = 12.sp
        )
    }

    Spacer(Modifier.height(30.dp))

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        // Previous Button
        OutlinedButton(
            onClick = onPrevious,
            modifier = Modifier.weight(1f)
        ) {
            Text("← Previous")
        }

        Spacer(Modifier.width(12.dp))

        // Submit Button
        Button(
            onClick = {
                if (email.contains("@") && email.contains(".")) {
                    onSubmit()
                } else {
                    showError = true
                }
            },
            modifier = Modifier
                .weight(1f)
                .height(55.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Send ↑", fontSize = 18.sp)
        }
    }
}
*/