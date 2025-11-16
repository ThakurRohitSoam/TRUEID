package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.Indigo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(onClose: () -> Unit) {
    ModalDrawerSheet(
        modifier = Modifier.width(300.dp),
        drawerContainerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.VerifiedUser,
                        contentDescription = "App Logo",
                        tint = Indigo,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Indigo.copy(alpha = 0.1f))
                            .padding(8.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = "TRUEID",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
                IconButton(onClick = onClose) {
                    Icon(Icons.Outlined.Close, contentDescription = "Close Drawer")
                }
            }
            Spacer(Modifier.height(32.dp))

            // Menu Items
            DrawerMenuItem(icon = Icons.Outlined.Info, text = "About Us")
            DrawerMenuItem(icon = Icons.AutoMirrored.Outlined.Article, text = "Terms & Conditions", hasNotification = true)
            ThemeMenuItem()
            DrawerMenuItem(icon = Icons.Outlined.Feedback, text = "Feedback")
            DrawerMenuItem(icon = Icons.Outlined.Chat, text = "Contact Us")

            Spacer(Modifier.weight(1f))

            // Footer
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.VerifiedUser,
                    contentDescription = "App Logo",
                    tint = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Indigo)
                        .padding(10.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("TRUEID", fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("trueid@gmail.com", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun DrawerMenuItem(icon: ImageVector, text: String, hasNotification: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = Color.Gray)
        Spacer(Modifier.width(16.dp))
        Text(text, fontSize = 16.sp, color = Color.Black)
        if (hasNotification) {
            Spacer(Modifier.weight(1f))

        }
    }
}

@Composable
fun ThemeMenuItem() {
    var isDarkTheme by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Outlined.DarkMode, contentDescription = "Theme", tint = Color.Gray)
        Spacer(Modifier.width(16.dp))
        Text("Theme", fontSize = 16.sp, color = Color.Black)
        Spacer(Modifier.weight(1f))
        Switch(
            checked = isDarkTheme,
            onCheckedChange = { isDarkTheme = it }
        )
    }
}
@Preview
@Composable
fun AppDrawerPreview() {
    AppDrawer(onClose = {})
}