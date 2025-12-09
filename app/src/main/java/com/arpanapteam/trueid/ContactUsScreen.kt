package com.arpanapteam.trueid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Menu", fontSize = 16.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Back",
                            modifier = Modifier.size(28.dp)   // ⬅️ Bigger Back Button
                        )
                    }
                }
            )
        }
    ) { padding ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color(0xFFE0F2E9))
                .padding(20.dp)
        ) {

            Text(
                "Contact Us",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Don’t hesitate to contact us whether you have a suggestion on our improvement, a complain to discuss or an issue to solve.",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(25.dp))

            // ---------------- CALL + EMAIL ROW ----------------
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                ContactTopCard(
                    iconRes = R.drawable.contact,
                    title = "Call us",
                    subtitle = "Our team is on the line\nMon–Fri • 9–17",
                    modifier = Modifier.weight(1f)
                )

                ContactTopCard(
                    iconRes = R.drawable.email,
                    title = "Email us",
                    subtitle = "Our team is online\nMon–Fri • 9–17",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(25.dp))

            Text(
                "Social Media",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(Modifier.height(12.dp))

            // ----------- SOCIAL LINKS LIST --------------
            SocialItem(
                icon = R.drawable.arattai,
                title = "Arattai",
                subtitle = "Available Mon–Fri • 9–17",
                url ="https://aratt.ai/@trueid"
            )
            SocialItem(
                icon = R.drawable.telegram,
                title = "Telegram",
                subtitle = "0 Followers • 0 Posts",
                url = "https://t.me/+XmlbmJdZ_ZJkZTU1"
            )
            SocialItem(
                icon = R.drawable.instagram,
                title = "Instagram",
                subtitle = "0 Followers • 0 Posts",
                url = "https://www.instagram.com/egovsahayta?igsh=dDViMDMybTN4Y3B0"
            )

            SocialItem(
                icon = R.drawable.whatsapp,
                title = "WhatsApp",
                subtitle = "Available Mon–Fri • 9–17",
                url ="https://whatsapp.com/channel/0029VbBS0fbDzgT9GFgZEe3x"
            )

            SocialItem(
                icon = R.drawable.facebook,
                title = "Facebook",
                subtitle = "0 Followers • 0 Posts"
            )
        }
    }
}

@Composable
fun ContactTopCard(
    iconRes: Int,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(20.dp)
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Black, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(subtitle, fontSize = 13.sp, color = Color.Gray)
    }
}

@Composable
fun SocialItem(
    icon: Int,
    title: String,
    subtitle: String,
    url: String? = null
) {
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .clickable {
                url?.let { uriHandler.openUri(it) }
            }
            .padding(16.dp)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFF0F9F2), RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )

            Spacer(Modifier.width(15.dp))

            Column {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.share),
            contentDescription = "Share",
            modifier = Modifier
                .size(30.dp)
                .background(Color(0xFFE8F4EC), CircleShape)
                .padding(6.dp)
        )
    }

    Spacer(Modifier.height(12.dp))
}
