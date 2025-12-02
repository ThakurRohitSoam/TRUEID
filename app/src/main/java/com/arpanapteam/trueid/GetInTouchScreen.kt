package com.arpanapteam.trueid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arpanapteam.trueid.ui.theme.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetInTouchScreen(navController: NavHostController) {
    Scaffold(
        topBar = { GetInTouchTopAppBar(navController) },
        containerColor = OffWhite
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ContactCard(
                    icon = Icons.Outlined.AccountBox,
                    title ="Instagram",
                    subtitle = "Follow us on Instagram for latest updates and news."
                )
            }
            item {
                ContactCard(
                    icon = Icons.Outlined.Face,
                    title = "Facebook",
                    subtitle = "Connect with us on Facebook for community support."
                )
            }
            item {
                ContactCard(
                    icon = Icons.Outlined.Email,
                    title = "Email",
                    subtitle = "Send us an email for general inquiries."
                )
            }
            item {
                ContactCard(
                    icon = Icons.Outlined.ArrowDownward,
                    title = "YouTube",
                    subtitle = "Subscribe to our YouTube channel for tutorials and guides."
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetInTouchTopAppBar(navController: NavHostController) {
    TopAppBar(
        title = { Text("Get In Touch",color=Color.Black, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            })
            {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = OffWhite)
    )
}

@Composable
fun ContactCard(icon: ImageVector, title: String, subtitle: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)

    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium, color = Color.Black)
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.Black)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GetInTouchScreenPreview() {
    TRUEIDTheme {
//        GetInTouchScreen()
    }
}