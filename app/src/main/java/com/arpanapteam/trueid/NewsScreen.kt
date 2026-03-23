package com.arpanapteam.trueid

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CloudOff // 🟢 CloudOff Icon Import
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign // 🟢 TextAlign Import
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.arpanapteam.trueid.ui.theme.Indigo // 🟢 Indigo Color Import
import com.arpanapteam.trueid.ui.theme.OffWhite
import com.arpanapteam.trueid.ui.theme.TextGray
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

// ==========================================
// 📰 MAIN NEWS LIST SCREEN
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController) {
    var newsItems by remember { mutableStateOf<List<AdminNewsModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    var isOffline by remember { mutableStateOf(false) } // 🟢 OFFLINE CHECKER
    var retryTrigger by remember { mutableIntStateOf(0) } // 🟢 RETRY BUTTON KE LIYE

    // Screen khulte hi ya Retry dabane par data fetch karo
    LaunchedEffect(retryTrigger) {
        isLoading = true
        isOffline = false
        try {
            newsItems = supabase.postgrest["news"].select().decodeList<AdminNewsModel>()
        } catch (e: Exception) {
            isOffline = true // 🟢 Error aane par offline set ho jayega
            println("Error fetching news: ${e.message}")
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = { NewsTopAppBar(onBack = { navController.popBackStack() }) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize(), contentAlignment = Alignment.Center) {

            if (isLoading) {
                CircularProgressIndicator(color = Indigo)
            }
            // 🟢 AGAR NET BAND HAI TOH YEH DIKHEGA
            else if (isOffline) {
                OfflineUI { retryTrigger++ }
            }
            else if (newsItems.isEmpty()) {
                Text("No news available right now.", color = Color.Gray)
            }
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(newsItems) { article ->
                        NewsCard(
                            article = article,
                            onReadMore = { navController.navigate("news_detail/${article.id}") }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text("News", fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Back") }
        },
        actions = {
            Icon(Icons.Outlined.VerifiedUser, "TRUEID", modifier = Modifier.padding(end = 16.dp).size(24.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = OffWhite)
    )
}

// ==========================================
// 🃏 SINGLE NEWS CARD
// ==========================================
@Composable
fun NewsCard(
    article: AdminNewsModel,
    onReadMore: () -> Unit
) {
    val context = LocalContext.current
    val tagColor = if (article.tag.contains("Alert", ignoreCase = true)) Color.Red else Color(0xFF2E7D32)

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(400)) + slideInVertically(initialOffsetY = { 40 }, animationSpec = tween(400))
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(tagColor.copy(alpha = 0.1f)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                        Text(text = article.tag, color = tagColor, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = article.date, color = TextGray, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.Top) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = article.title, fontWeight = FontWeight.Bold, fontSize = 18.sp, lineHeight = 24.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = article.short_content, color = TextGray, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    AsyncImage(
                        model = article.image_url,
                        contentDescription = article.title,
                        modifier = Modifier.size(64.dp).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    OutlinedButton(onClick = {
                        val shareText = "TRUEID NEWS\n\nTitle: ${article.title}\nDate: ${article.date}\n\nRead more in the TRUEID app."
                        val sendIntent = Intent(Intent.ACTION_SEND).apply { type = "text/plain"; putExtra(Intent.EXTRA_TEXT, shareText) }
                        context.startActivity(Intent.createChooser(sendIntent, "Share via"))
                    }) {
                        Icon(Icons.Outlined.Share, "Share", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onReadMore, colors = ButtonDefaults.buttonColors(containerColor = Indigo)) {
                        Text("Read More")
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Outlined.ArrowForward, "Read More", modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

// ==========================================
// 📖 DETAIL SCREEN (Read More -> opens this)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    newsId: Int,
    onBack: () -> Unit
) {
    var article by remember { mutableStateOf<AdminNewsModel?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    var isOffline by remember { mutableStateOf(false) } // 🟢 OFFLINE CHECKER
    var retryTrigger by remember { mutableIntStateOf(0) } // 🟢 RETRY BUTTON KE LIYE

    LaunchedEffect(newsId, retryTrigger) {
        isLoading = true
        isOffline = false
        try {
            val result = supabase.postgrest["news"].select { filter { eq("id", newsId) } }.decodeList<AdminNewsModel>()
            article = result.firstOrNull()
        } catch (e: Exception) {
            isOffline = true // 🟢 Error aane par offline set ho jayega
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(article?.title ?: "News Detail", maxLines = 1, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {

            if (isLoading) {
                CircularProgressIndicator(color = Indigo)
            }
            // 🟢 AGAR NET BAND HAI TOH YEH DIKHEGA
            else if (isOffline) {
                OfflineUI { retryTrigger++ }
            }
            else if (article == null) {
                Text("Article not found.", color = Color.Gray)
            }
            else {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    AsyncImage(
                        model = article!!.image_url,
                        contentDescription = article!!.title,
                        modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val tagColor = if (article!!.tag.contains("Alert", ignoreCase = true)) Color.Red else Color(0xFF2E7D32)
                    Text(text = article!!.tag.uppercase(), color = tagColor, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = article!!.date, color = TextGray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = article!!.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = article!!.full_content, fontSize = 15.sp, lineHeight = 22.sp, color = Color(0xFF202020))
                }
            }
        }
    }
}

// ==========================================
// ☁️ OFFLINE UI COMPONENT
// ==========================================
@Composable
fun OfflineUI(onRetry: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
        Icon(Icons.Outlined.CloudOff, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(80.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Oops! You are offline", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Please connect to the internet to view latest news.", color = Color.Gray, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo),
            modifier = Modifier.height(50.dp).padding(horizontal = 32.dp)
        ) {
            Text("Retry", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}