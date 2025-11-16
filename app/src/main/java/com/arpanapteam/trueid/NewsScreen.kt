package com.arpanapteam.trueid

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.*

data class NewsArticle(
    val tag: String,
    val date: String,
    val title: String,
    val content: String,
    val image: Painter,
    val tagColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen() {
    val newsItems = listOf(
        NewsArticle(
            tag = "Deadline Alert",
            date = "December 15, 2024",
            title = "Last Day for PM-Kisan Scheme Application",
            content = "Farmers are reminded that today is the final day to submit their applications",
            image = painterResource(id = R.drawable.pm_kisan),
            tagColor = Color.Red
        ),
        NewsArticle(
            tag = "Important Update",
            date = "December 14, 2024",
            title = "Digitalizing Government Services: A New Era of Efficiency and Accessibility",
            content = "", // No content in the image
            image = painterResource(id = R.drawable.digital_india),
            tagColor = Color.Yellow
        ),
        NewsArticle(
            tag = "Latest News",
            date = "December 13, 2024",
            title = "E-District Services Portal Now Faster and More Secure",
            content = "The E-District portal for Uttar Pradesh has undergone a major",
            image = painterResource(id = R.drawable.e_district),
            tagColor = Color.Yellow
        ),
        NewsArticle(
            tag = "Public Notice",
            date = "December 12, 2024",
            title = "Applications Open for Skill Development Programs",
            content = "Government-sponsored skill development programs are now",
            image = painterResource(id = R.drawable.skill_development),
            tagColor = Color.Yellow
        )
    )

    Scaffold(
        topBar = { NewsTopAppBar() },
        containerColor = OffWhite
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(newsItems) { article ->
                NewsCard(article = article)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar() {
    TopAppBar(
        title = { Text("News", fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Outlined.VerifiedUser, contentDescription = "User Profile")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OffWhite
        )
    )
}

@Composable
fun NewsCard(article: NewsArticle) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(article.tagColor.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = article.tag,
                        color = article.tagColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = article.date,
                    color = TextGray,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = article.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        lineHeight = 24.sp
                    )
                    if (article.content.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = article.content,
                            color = TextGray,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = article.image,
                    contentDescription = article.title,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.Share, contentDescription = "Share", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Share")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text("Read More")
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = "Read More", modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    TRUEIDTheme {
        NewsScreen()
    }
}
