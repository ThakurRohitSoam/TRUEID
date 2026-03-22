/*
package com.arpanapteam.trueid.Feedback


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Step1Rating(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    comments: String,
    onCommentsChange: (String) -> Unit,
    onNext: () -> Unit
) {

    Text("How would you rate us?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(10.dp))

    Text("Pick a rate *", color = Color.Gray)
    Spacer(modifier = Modifier.height(12.dp))

    RatingRow(rating = rating, onRatingChange = onRatingChange)

    Spacer(modifier = Modifier.height(20.dp))

    Text("Tell us more", fontWeight = FontWeight.Medium)
    OutlinedTextField(
        value = comments,
        onValueChange = onCommentsChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(14.dp),
        placeholder = { Text("Write here...") }
    )

    Spacer(modifier = Modifier.height(25.dp))

    Button(
        onClick = onNext,
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Text("Next →", fontSize = 18.sp)
    }
}


 */