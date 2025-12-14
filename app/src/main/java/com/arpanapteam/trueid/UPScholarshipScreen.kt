
package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
fun UPScholarshipScreen(navController: NavHostController) {
    Scaffold(
        topBar = { UPScholarshipTopAppBar(navController) },
        containerColor = OffWhite,
        bottomBar = {
            Button(
                onClick = { /* TODO: Handle navigation to official website */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Indigo)
            ) {
                Text("Go to Official UP Scholarship Website", modifier = Modifier.padding(8.dp))
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
                ActionableLinks()
            }
            item {
                Step(1, "How to Apply") {
                    // TODO: Add steps for application here
                    Text("Detailed instructions on how to apply for the scholarship will be listed here.")
                }
            }
            item {
                Step(2, "Required Documents") {
                    // TODO: Add required documents here
                    Text("A list of required documents for the application process will be provided here.")
                }
            }
        }
    }
}

@Composable
private fun ActionableLinks() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            ServiceLinkItem("1", "New Student Registration", "Register Here")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            ServiceLinkItem("2", "Download Eligibility Note (2023-24)", "Download PDF")
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            ServiceLinkItem("3", "Check Payment Status (via PFMS)", "Track Here")
        }
    }
}

@Composable
private fun ServiceLinkItem(index: String, serviceName: String, linkText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "$index.", fontWeight = FontWeight.Bold, color = Indigo)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = serviceName, modifier = Modifier.padding(start = 8.dp))
        }
        TextButton(onClick = { /* TODO: Handle specific link click */ }) {
            Text(linkText)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPScholarshipTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("UP Scholarship") },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UPScholarshipScreenPreview() {
    TRUEIDTheme {
//        UPScholarshipScreen()
    }
}
