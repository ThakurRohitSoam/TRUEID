package com.arpanapteam.trueid.Feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                        if (isSelected) Color(0xFF4CAF50).copy(alpha = 0.2f)
                        else Color.LightGray.copy(alpha = 0.2f),
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
