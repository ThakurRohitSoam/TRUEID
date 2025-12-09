package com.arpanapteam.trueid

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.TextGray
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme

// DATA MODEL

data class NewsArticle(
    val id: Int,
    val tag: String,
    val date: String,
    val title: String,
    val shortContent: String,
    val fullContent: String,
    val imageRes: Int,
    val tagColor: Color
)


// SAMPLE DATA (use your own images in drawable)

val sampleNewsItems = listOf(
    NewsArticle(
        id = 1,
        tag = "Deadline Alert",
        date = "December 15, 2024",
        title = "Last Day for PM-Kisan Scheme Application",
        shortContent = "Farmers are reminded that today is the final day to submit their applications...",
        fullContent = "Farmers are reminded that today is the final day to submit their applications for the PM-Kisan scheme. "
                + "Under this scheme, eligible farmer families receive financial support directly into their bank accounts. "
                + "Please ensure all required documents are uploaded correctly and your bank details are verified on the official portal.",
        imageRes = R.drawable.pm_kisan,
        tagColor = Color.Red
    ),
    NewsArticle(
        id = 2,
        tag = "Important Update",
        date = "December 14, 2024",
        title = "Digitalizing Government Services: A New Era of Accessibility",
        shortContent = "Government services are rapidly moving online for faster and more transparent delivery...",
        fullContent = "Government services are rapidly moving online for faster and more transparent delivery. "
                + "Citizens can now apply for certificates, update personal details, and track application status online "
                + "without visiting government offices. This helps save both time and effort, especially for people in rural areas.",
        imageRes = R.drawable.digital_india,
        tagColor = Color(0xFF2E7D32) // Green
    ),
    NewsArticle(
        id = 3,
        tag = "Latest News",
        date = "December 13, 2024",
        title = "E-District Services Portal Now Faster and More Secure",
        shortContent = "The E-District portal has received a major technical upgrade to improve performance...",
        fullContent = "The E-District portal has received a major technical upgrade to improve performance and security. "
                + "Users can now access services with reduced load time, better reliability, and enhanced data protection. "
                + "The upgrade also includes an improved user interface for easier navigation.",
        imageRes = R.drawable.e_district,
        tagColor = Color(0xFF2E7D32)
    ),
    NewsArticle(
        id = 4,
        tag = "Public Notice",
        date = "December 12, 2024",
        title = "Applications Open for Skill Development Programs",
        shortContent = "Government-sponsored skill development programs are now open for students and job seekers...",
        fullContent = "Government-sponsored skill development programs are now open for students and job seekers. "
                + "These programs offer training in various trades such as computer literacy, tailoring, electrical work, and more. "
                + "Certificates will be issued upon completion, helping candidates in future employment opportunities.",
        imageRes = R.drawable.skill_development,
        tagColor = Color(0xFF2E7D32)
    )
)


// MAIN NEWS LIST SCREEN
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController) {

    Scaffold(
        topBar = { NewsTopAppBar(onBack = { navController.popBackStack() }) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(sampleNewsItems) { article ->
                NewsCard(
                    article = article,
                    onReadMore = {
                        navController.navigate("news_detail/${article.id}")
                    }
                )
            }
        }
    }
}


// TOP APP BAR WITH BACK

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text("News", fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            Icon(
                imageVector = Icons.Outlined.VerifiedUser,
                contentDescription = "TRUEID",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(24.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OffWhite
        )
    )
}

// SINGLE NEWS CARD
@Composable
fun NewsCard(
    article: NewsArticle,
    onReadMore: () -> Unit
) {
    val context = LocalContext.current

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(400)) +
                slideInVertically(initialOffsetY = { 40 }, animationSpec = tween(400))
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // Tag + date row
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

                // Title + image
                Row(verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = article.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            lineHeight = 24.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = article.shortContent,
                            color = TextGray,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Image(
                        painter = painterResource(id = article.imageRes),
                        contentDescription = article.title,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Share + Read More buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    OutlinedButton(
                        onClick = {
                            val shareText = """
                                TRUEID NEWS
                                
                                Title: ${article.title}
                                Date: ${article.date}
                                
                                Read more in the TRUEID app.
                            """.trimIndent()

                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            val chooser = Intent.createChooser(sendIntent, "Share via")
                            context.startActivity(chooser)
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Share,
                            contentDescription = "Share",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = onReadMore) {
                        Text("Read More")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowForward,
                            contentDescription = "Read More",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}


// DETAIL SCREEN (Read More -> opens this)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    newsId: Int,
    onBack: () -> Unit
) {
    val article = remember(newsId) {
        sampleNewsItems.find { it.id == newsId }
    }

    if (article == null) {
        // If ID not found, show simple error UI
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("News Detail") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Article not found.")
            }
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(article.title, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = article.imageRes),
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = article.tag.uppercase(),
                color = article.tagColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = article.date,
                color = TextGray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = article.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = article.fullContent,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )
        }
    }
}


// PREVIEW
@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    val navController = rememberNavController()
    TRUEIDTheme(darkTheme = false) {
        NewsScreen(navController = navController)
    }
}
