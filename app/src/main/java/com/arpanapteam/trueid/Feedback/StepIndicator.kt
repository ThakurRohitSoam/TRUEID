package com.arpanapteam.trueid.Feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StepIndicator(step: Int) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        // Step 1 Circle
        StepCircle(
            number = "1",
            isActive = step == 1,
            isCompleted = step > 1
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(2.dp)
                .background(if (step == 2) Color(0xFF4CAF50) else Color.LightGray)
        )

        // Step 2 Circle
        StepCircle(
            number = "2",
            isActive = step == 2,
            isCompleted = false
        )
    }
}

@Composable
fun StepCircle(number: String, isActive: Boolean, isCompleted: Boolean) {

    val bgColor = when {
        isCompleted -> Color(0xFF4CAF50)
        isActive -> Color(0xFF4CAF50).copy(alpha = 0.3f)
        else -> Color.LightGray.copy(alpha = 0.4f)
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .background(bgColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number,
            fontWeight = FontWeight.Bold,
            color = if (isCompleted) Color.White else Color.Black
        )
    }
}
