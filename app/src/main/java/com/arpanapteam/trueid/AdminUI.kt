package com.arpanapteam.trueid

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arpanapteam.trueid.ui.theme.Indigo
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import com.arpanapteam.trueid.BuildConfig

// ==========================================
// 🔴 IMPORTANT: HIDDEN KEYS INTEGRATED
// ==========================================
val supabase = createSupabaseClient(
    supabaseUrl = BuildConfig.SUPABASE_URL, // 🟢 Hidden URL
    supabaseKey = BuildConfig.SUPABASE_KEY  // 🟢 Hidden Key
) {
    install(Postgrest)
}

// --- DATA MODELS ---
@Serializable
data class AdminServiceModel(val id: Int? = null, val title: String, val description: String, val category: String? = null, val service_key: String, val created_at: String? = null)

@Serializable
data class AdminHomeServiceModel(val id: Int? = null, val title: String, val category: String? = null, val service_key: String, val created_at: String? = null)

@Serializable
data class AdminNewsModel(val id: Int? = null, val tag: String, val date: String, val title: String, val short_content: String, val full_content: String, val image_url: String, val created_at: String? = null)

@Serializable
data class FeedbackModel(
    val id: Int? = null,
    val name: String,
    val email: String,
    val rating: Int,
    val message: String,
    val created_at: String? = null
)

@Serializable
data class ServiceLinkModel(val id: Int? = null, val service_key: String, val button_title: String, val url: String, val created_at: String? = null)

@Serializable
data class ServiceInfoModel(val id: Int? = null, val service_key: String, val heading: String, val details: String, val created_at: String? = null)

// --- 1. ADMIN LOGIN SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Admin Access") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back") } }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Admin Login", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())

            if (errorMessage.isNotEmpty()) { Spacer(modifier = Modifier.height(8.dp)); Text(errorMessage, color = Color.Red, fontSize = 14.sp) }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    // 🟢 Hidden Admin Details se check karega
                    if (email == BuildConfig.ADMIN_EMAIL && password == BuildConfig.ADMIN_PASSWORD) {
                        errorMessage = ""
                        navController.navigate("admin_dashboard") { popUpTo("admin_login") { inclusive = true } }
                    } else {
                        errorMessage = "Invalid credentials. Unauthorized access."
                    }
                }, modifier = Modifier.fillMaxWidth().height(50.dp)
            ) { Text("Login") }
        }
    }
}

// --- 2. ADMIN DASHBOARD ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Admin Dashboard") }, navigationIcon = { IconButton(onClick = { navController.navigate("home") { popUpTo(0) } }) { Icon(Icons.Default.ArrowBack, "Logout") } }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AdminMenuCard("Manage Home Services", "Add special cards ONLY for Home Screen") { navController.navigate("manage_home_services") }
            AdminMenuCard("Manage Services", "Create Category & Add App Service Cards") { navController.navigate("manage_services") }
            AdminMenuCard("Manage News", "Post or delete news alerts") { navController.navigate("manage_news") }
            AdminMenuCard("Manage Inside Links", "Add inside buttons/links for inside pages.") { navController.navigate("manage_service_links") }
            AdminMenuCard("Manage Intro Info", "Add Introduction inside the page (e.g., What is Aadhaar?)") { navController.navigate("manage_service_info") }
            AdminMenuCard("View Feedback", "Read user feedback") { navController.navigate("view_feedback") }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun AdminMenuCard(title: String, subtitle: String, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onClick() }, elevation = CardDefaults.cardElevation(4.dp)) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, color = Color.Gray)
        }
    }
}

// --- 3. MANAGE SERVICES SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageServicesScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var serviceKeyInput by remember { mutableStateOf("") }

    var servicesList by remember { mutableStateOf<List<AdminServiceModel>>(emptyList()) }
    // 🟢 DELETE CONFIRMATION STATE
    var itemToDelete by remember { mutableStateOf<AdminServiceModel?>(null) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) { try { servicesList = supabase.postgrest["services"].select().decodeList<AdminServiceModel>() } catch (e: Exception) {} }

    Scaffold(topBar = { TopAppBar(title = { Text("Create Service Card") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "") } }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            item {
                Text("Add New Service Card", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Card Title (e.g. Health Card)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Card Description") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category (e.g. Medical Services)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = serviceKeyInput, onValueChange = { serviceKeyInput = it }, label = { Text("Service Key (e.g. health_card)") }, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    scope.launch {
                        try {
                            val newService = AdminServiceModel(title = title, description = description, category = category.ifEmpty { "New Updates" }, service_key = serviceKeyInput)
                            supabase.postgrest["services"].insert(newService)
                            servicesList = supabase.postgrest["services"].select().decodeList<AdminServiceModel>()
                            title = ""; description = ""; category = ""; serviceKeyInput = ""
                            Toast.makeText(context, "Card Created Successfully!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) { Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show() }
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text("Add Card to App") }
                Spacer(Modifier.height(24.dp))
                Text("Existing Cards", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
            }

            items(servicesList) { service ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(service.title, fontWeight = FontWeight.Bold)
                            Text("Category: ${service.category ?: "New Updates"}", color = Color.Gray, fontSize = 12.sp)
                            Text("Key: ${service.service_key}", color = Indigo, fontSize = 12.sp)
                        }
                        IconButton(onClick = { itemToDelete = service }) { Icon(Icons.Default.Delete, "Delete", tint = Color.Red) }
                    }
                }
            }
        }

        // 🟢 DELETE CONFIRMATION DIALOG
        if (itemToDelete != null) {
            AlertDialog(
                onDismissRequest = { itemToDelete = null },
                title = { Text("Confirm Deletion") },
                text = { Text("Are you sure you want to delete this entry? This cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = {
                        val target = itemToDelete
                        itemToDelete = null
                        scope.launch {
                            try { target?.id?.let { id -> supabase.postgrest["services"].delete { filter { eq("id", id) } }; servicesList = servicesList.filter { it.id != id }; Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show() } } catch (e: Exception) {}
                        }
                    }) { Text("Yes, Delete", color = Color.Red) }
                },
                dismissButton = {
                    TextButton(onClick = { itemToDelete = null }) { Text("Cancel") }
                }
            )
        }
    }
}

// --- 8. MANAGE HOME SERVICES SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageHomeServicesScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var serviceKeyInput by remember { mutableStateOf("") }

    var homeServicesList by remember { mutableStateOf<List<AdminHomeServiceModel>>(emptyList()) }
    var itemToDelete by remember { mutableStateOf<AdminHomeServiceModel?>(null) } // 🟢 CONFIRMATION STATE

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) { try { homeServicesList = supabase.postgrest["home_services"].select().decodeList<AdminHomeServiceModel>() } catch (e: Exception) {} }

    Scaffold(topBar = { TopAppBar(title = { Text("Home Screen Cards") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "") } }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            item {
                Text("Add Card to Home Screen", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Card Title (e.g. Ration Card)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category (e.g. Important Documents)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = serviceKeyInput, onValueChange = { serviceKeyInput = it }, label = { Text("Service Key (e.g. ration_card)") }, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    scope.launch {
                        try {
                            val newHomeService = AdminHomeServiceModel(title = title, category = category.ifEmpty { "New Updates" }, service_key = serviceKeyInput)
                            supabase.postgrest["home_services"].insert(newHomeService)
                            homeServicesList = supabase.postgrest["home_services"].select().decodeList<AdminHomeServiceModel>()
                            title = ""; category = ""; serviceKeyInput = ""
                            Toast.makeText(context, "Added to Home Screen!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) { Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show() }
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text("Add to Home") }
                Spacer(Modifier.height(24.dp))
                Text("Existing Home Cards", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
            }

            items(homeServicesList) { service ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(service.title, fontWeight = FontWeight.Bold)
                            Text("Category: ${service.category ?: "New Updates"}", color = Color.Gray, fontSize = 12.sp)
                        }
                        IconButton(onClick = { itemToDelete = service }) { Icon(Icons.Default.Delete, "Delete", tint = Color.Red) }
                    }
                }
            }
        }

        if (itemToDelete != null) {
            AlertDialog(
                onDismissRequest = { itemToDelete = null },
                title = { Text("Confirm Deletion") },
                text = { Text("Are you sure you want to delete this entry?") },
                confirmButton = {
                    TextButton(onClick = {
                        val target = itemToDelete
                        itemToDelete = null
                        scope.launch {
                            try { target?.id?.let { id -> supabase.postgrest["home_services"].delete { filter { eq("id", id) } }; homeServicesList = homeServicesList.filter { it.id != id }; Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show() } } catch (e: Exception) {}
                        }
                    }) { Text("Yes, Delete", color = Color.Red) }
                },
                dismissButton = {
                    TextButton(onClick = { itemToDelete = null }) { Text("Cancel") }
                }
            )
        }
    }
}

// --- 4. MANAGE NEWS SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageNewsScreen(navController: NavController) {
    var tag by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var shortContent by remember { mutableStateOf("") }
    var fullContent by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    var newsList by remember { mutableStateOf<List<AdminNewsModel>>(emptyList()) }
    var itemToDelete by remember { mutableStateOf<AdminNewsModel?>(null) } // 🟢 CONFIRMATION STATE

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) { try { newsList = supabase.postgrest["news"].select().decodeList<AdminNewsModel>() } catch (e: Exception) {} }

    Scaffold(topBar = { TopAppBar(title = { Text("Manage News") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "") } }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            item {
                Text("Post New Alert/News", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                OutlinedTextField(value = tag, onValueChange = { tag = it }, label = { Text("Tag (e.g. Deadline Alert)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date (e.g. Dec 15, 2024)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Heading (News Title)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = shortContent, onValueChange = { shortContent = it }, label = { Text("Short Description") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = fullContent, onValueChange = { fullContent = it }, label = { Text("Full Description") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("Image URL (Link)") }, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(8.dp))
                Button(onClick = {
                    scope.launch {
                        try {
                            val newNews = AdminNewsModel(tag = tag, date = date, title = title, short_content = shortContent, full_content = fullContent, image_url = imageUrl)
                            supabase.postgrest["news"].insert(newNews)
                            newsList = supabase.postgrest["news"].select().decodeList<AdminNewsModel>()
                            tag = ""; date = ""; title = ""; shortContent = ""; fullContent = ""; imageUrl = ""
                            Toast.makeText(context, "News Posted Successfully!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) { Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show() }
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text("Post News") }
                Spacer(Modifier.height(24.dp))
                Text("Existing News", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
            }
            items(newsList) { news ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) { Text(news.title, fontWeight = FontWeight.Bold); Text(news.tag, color = Color.Red, fontSize = 12.sp) }
                        IconButton(onClick = { itemToDelete = news }) { Icon(Icons.Default.Delete, "Delete", tint = Color.Red) }
                    }
                }
            }
        }

        if (itemToDelete != null) {
            AlertDialog(
                onDismissRequest = { itemToDelete = null },
                title = { Text("Confirm Deletion") },
                text = { Text("Are you sure you want to delete this news entry?") },
                confirmButton = {
                    TextButton(onClick = {
                        val target = itemToDelete
                        itemToDelete = null
                        scope.launch {
                            try { target?.id?.let { id -> supabase.postgrest["news"].delete { filter { eq("id", id) } }; newsList = newsList.filter { it.id != id }; Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show() } } catch (e: Exception) {}
                        }
                    }) { Text("Yes, Delete", color = Color.Red) }
                },
                dismissButton = {
                    TextButton(onClick = { itemToDelete = null }) { Text("Cancel") }
                }
            )
        }
    }
}

// 🟢 --- 5. VIEW FEEDBACK SCREEN (NO DELETE BUTTON HERE, SO NO ALERT DIALOG NEEDED) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewFeedbackScreen(navController: NavController) {
    var feedbackList by remember { mutableStateOf<List<FeedbackModel>>(emptyList()) }

    LaunchedEffect(Unit) {
        try { feedbackList = supabase.postgrest["feedback"].select().decodeList<FeedbackModel>() } catch (e: Exception) {}
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("User Feedback") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "") } }) }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            items(feedbackList) { feedback ->
                val ratingColor = when {
                    feedback.rating >= 4 -> Color(0xFF2E7D32)
                    feedback.rating == 3 -> Color(0xFFF57F17)
                    else -> Color(0xFFD32F2F)
                }

                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(feedback.name, fontWeight = FontWeight.Bold, color = Indigo, fontSize = 16.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Star, contentDescription = null, tint = ratingColor, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("${feedback.rating}/5", fontWeight = FontWeight.Bold, color = ratingColor)
                            }
                        }
                        Text(feedback.email, color = Color.Gray, fontSize = 12.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(feedback.message, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

// --- 6. MANAGE INSIDE LINKS SCREEN ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageServiceLinksScreen(navController: NavController) {
    var serviceKey by remember { mutableStateOf("") }
    var buttonTitle by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    var linksList by remember { mutableStateOf<List<ServiceLinkModel>>(emptyList()) }
    var itemToDelete by remember { mutableStateOf<ServiceLinkModel?>(null) } // 🟢 CONFIRMATION STATE

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) { try { linksList = supabase.postgrest["service_links"].select().decodeList<ServiceLinkModel>() } catch (e: Exception) {} }

    Scaffold(topBar = { TopAppBar(title = { Text("Manage Inside Links") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "") } }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            item {
                Text("Add Link to a Service", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                OutlinedTextField(value = serviceKey, onValueChange = { serviceKey = it }, label = { Text("Service Key (e.g. aadhar, pan, railway)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = buttonTitle, onValueChange = { buttonTitle = it }, label = { Text("Button Title (e.g. Official Website)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("URL Link") }, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(8.dp))
                Button(onClick = {
                    scope.launch {
                        try {
                            val newLink = ServiceLinkModel(service_key = serviceKey, button_title = buttonTitle, url = url)
                            supabase.postgrest["service_links"].insert(newLink)
                            linksList = supabase.postgrest["service_links"].select().decodeList<ServiceLinkModel>()
                            buttonTitle = ""; url = ""
                            Toast.makeText(context, "Link Added!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) { Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show() }
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text("Add Link") }
                Spacer(Modifier.height(24.dp))
                Text("Existing Links", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
            }

            items(linksList) { link ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(link.button_title, fontWeight = FontWeight.Bold)
                            Text("Key: ${link.service_key}", color = Color.Gray, fontSize = 12.sp)
                        }
                        IconButton(onClick = { itemToDelete = link }) { Icon(Icons.Default.Delete, "Delete", tint = Color.Red) }
                    }
                }
            }
        }

        if (itemToDelete != null) {
            AlertDialog(
                onDismissRequest = { itemToDelete = null },
                title = { Text("Confirm Deletion") },
                text = { Text("Are you sure you want to delete this link?") },
                confirmButton = {
                    TextButton(onClick = {
                        val target = itemToDelete
                        itemToDelete = null
                        scope.launch {
                            try { target?.id?.let { id -> supabase.postgrest["service_links"].delete { filter { eq("id", id) } }; linksList = linksList.filter { it.id != id }; Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show() } } catch (e: Exception) {}
                        }
                    }) { Text("Yes, Delete", color = Color.Red) }
                },
                dismissButton = {
                    TextButton(onClick = { itemToDelete = null }) { Text("Cancel") }
                }
            )
        }
    }
}

// --- 7. MANAGE SERVICE INTRODUCTION INFO ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageServiceInfoScreen(navController: NavController) {
    var serviceKey by remember { mutableStateOf("") }
    var heading by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    var infoList by remember { mutableStateOf<List<ServiceInfoModel>>(emptyList()) }
    var itemToDelete by remember { mutableStateOf<ServiceInfoModel?>(null) } // 🟢 CONFIRMATION STATE

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) { try { infoList = supabase.postgrest["service_info"].select().decodeList<ServiceInfoModel>() } catch (e: Exception) {} }

    Scaffold(topBar = { TopAppBar(title = { Text("Manage Intro Details") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "") } }) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
            item {
                Text("Add Intro to a Service", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                OutlinedTextField(value = serviceKey, onValueChange = { serviceKey = it }, label = { Text("Service Key (e.g. aadhar, pan)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = heading, onValueChange = { heading = it }, label = { Text("Heading (e.g. What is Aadhaar?)") }, modifier = Modifier.fillMaxWidth())

                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    label = { Text("Full Details (Paragraphs & Bullets)") },
                    modifier = Modifier.fillMaxWidth().height(150.dp)
                )

                Spacer(Modifier.height(8.dp))
                Button(onClick = {
                    scope.launch {
                        try {
                            val newInfo = ServiceInfoModel(service_key = serviceKey, heading = heading, details = details)
                            supabase.postgrest["service_info"].insert(newInfo)
                            infoList = supabase.postgrest["service_info"].select().decodeList<ServiceInfoModel>()
                            heading = ""; details = ""
                            Toast.makeText(context, "Intro Added!", Toast.LENGTH_SHORT).show()
                        } catch (e: Exception) { Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show() }
                    }
                }, modifier = Modifier.fillMaxWidth()) { Text("Add Info") }
                Spacer(Modifier.height(24.dp))
                Text("Existing Intros", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
            }

            items(infoList) { info ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(info.heading, fontWeight = FontWeight.Bold)
                            Text("Key: ${info.service_key}", color = Color.Gray, fontSize = 12.sp)
                        }
                        IconButton(onClick = { itemToDelete = info }) { Icon(Icons.Default.Delete, "Delete", tint = Color.Red) }
                    }
                }
            }
        }

        if (itemToDelete != null) {
            AlertDialog(
                onDismissRequest = { itemToDelete = null },
                title = { Text("Confirm Deletion") },
                text = { Text("Are you sure you want to delete this intro detail?") },
                confirmButton = {
                    TextButton(onClick = {
                        val target = itemToDelete
                        itemToDelete = null
                        scope.launch {
                            try { target?.id?.let { id -> supabase.postgrest["service_info"].delete { filter { eq("id", id) } }; infoList = infoList.filter { it.id != id }; Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show() } } catch (e: Exception) {}
                        }
                    }) { Text("Yes, Delete", color = Color.Red) }
                },
                dismissButton = {
                    TextButton(onClick = { itemToDelete = null }) { Text("Cancel") }
                }
            )
        }
    }
}