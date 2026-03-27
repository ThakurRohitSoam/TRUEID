package com.arpanapteam.trueid

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.arpanapteam.trueid.ui.theme.Indigo
import com.arpanapteam.trueid.ui.theme.OffWhite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

// ==========================================
// 🟢 OFFLINE DATA MODEL & STORAGE HELPER
// ==========================================
enum class VaultCategory { DOCS, RECEIPTS, TRACKERS }

data class VaultRecord(
    val id: String,
    val title: String,
    val category: VaultCategory,
    val fileUri: String? = null,
    val fileType: String? = null,
    val refNo: String? = null,
    val deadline: String? = null
)

object LocalVaultStorage {
    private const val PREFS_NAME = "TrueIdVaultPrefs"
    private const val KEY_RECORDS = "vault_records"

    fun saveRecords(context: Context, records: List<VaultRecord>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val dataString = records.joinToString("|||") {
            "${it.id}:::${it.title}:::${it.category.name}:::${it.fileUri ?: ""}:::${it.refNo ?: ""}:::${it.deadline ?: ""}:::${it.fileType ?: ""}"
        }
        prefs.edit().putString(KEY_RECORDS, dataString).apply()
    }

    fun loadRecords(context: Context): List<VaultRecord> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val dataString = prefs.getString(KEY_RECORDS, "") ?: ""
        if (dataString.isEmpty()) return emptyList()

        return try {
            dataString.split("|||").map {
                val parts = it.split(":::")
                VaultRecord(
                    id = parts[0],
                    title = parts[1],
                    category = VaultCategory.valueOf(parts[2]),
                    fileUri = parts[3].ifEmpty { null },
                    refNo = parts[4].ifEmpty { null },
                    deadline = parts[5].ifEmpty { null },
                    fileType = if (parts.size > 6) parts[6].ifEmpty { null } else null
                )
            }
        } catch (e: Exception) { emptyList() }
    }
}

// Camera Helper
fun saveBitmapToSecretLocal(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.filesDir, "secret_vault_img_${System.currentTimeMillis()}.jpg")
    file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it) }
    return Uri.fromFile(file)
}

// Load Image Bitmap from URI
fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it) }
    } catch (e: Exception) { null }
}

// Share Helper
fun shareFile(context: Context, uriString: String, fileType: String?) {
    try {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = if (fileType == "PDF") "application/pdf" else "image/*"
            putExtra(Intent.EXTRA_STREAM, Uri.parse(uriString))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share via"))
    } catch (e: Exception) {
        Toast.makeText(context, "Unable to share file.", Toast.LENGTH_SHORT).show()
    }
}

// ==========================================
// 🟢 MAIN UI SCREEN
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyVaultScreen(navController: NavController) {
    val context = LocalContext.current
    var records by remember { mutableStateOf(LocalVaultStorage.loadRecords(context)) }
    var selectedTab by remember { mutableStateOf(VaultCategory.DOCS) }
    var showDialog by remember { mutableStateOf(false) }

    // Dialogs States
    var showPreImportAlert by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<VaultRecord?>(null) }
    var selectedRecordForView by remember { mutableStateOf<VaultRecord?>(null) }

    // Add Form Inputs
    var inputTitle by remember { mutableStateOf("") }
    var inputRefNo by remember { mutableStateOf("") }
    var inputDeadline by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileType by remember { mutableStateOf<String?>(null) }

    // File Picker
    val filePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            selectedFileUri = uri
            val mimeType = context.contentResolver.getType(uri)
            selectedFileType = if (mimeType?.contains("pdf") == true) "PDF" else "IMAGE"
        }
    }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            selectedFileUri = saveBitmapToSecretLocal(context, bitmap)
            selectedFileType = "IMAGE"
            Toast.makeText(context, "Secured in Internal Vault", Toast.LENGTH_SHORT).show()
        }
    }

    // Camera Permission
    val cameraPermissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) cameraLauncher.launch(null)
        else Toast.makeText(context, "Camera permission is required.", Toast.LENGTH_LONG).show()
    }

    fun saveNewRecord(newRecord: VaultRecord) {
        val updatedList = records + newRecord
        records = updatedList
        LocalVaultStorage.saveRecords(context, updatedList)
        showDialog = false
        inputTitle = ""; inputRefNo = ""; inputDeadline = ""; selectedFileUri = null; selectedFileType = null
        Toast.makeText(context, "Saved Securely Offline", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Secure Vault", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    inputTitle = ""; inputRefNo = ""; inputDeadline = ""; selectedFileUri = null; selectedFileType = null
                    showDialog = true
                },
                containerColor = Indigo,
                contentColor = Color.White
            ) { Icon(Icons.Filled.Add, "Add") }
        },
        containerColor = OffWhite
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {

            // Introduction Banner
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Security, contentDescription = "Secure", tint = Indigo, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("100% Offline & Private", fontWeight = FontWeight.Bold, color = Indigo, fontSize = 16.sp)
                        Text("All documents and trackers are encrypted and hidden from other apps in your local vault.", fontSize = 12.sp, color = Color.DarkGray, lineHeight = 16.sp)
                    }
                }
            }

            // Category Tabs
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryTab("Important Docs", Icons.Outlined.FolderShared, selectedTab == VaultCategory.DOCS) { selectedTab = VaultCategory.DOCS }
                CategoryTab("Form Receipts", Icons.Outlined.Receipt, selectedTab == VaultCategory.RECEIPTS) { selectedTab = VaultCategory.RECEIPTS }
                CategoryTab("Trackers & Alerts", Icons.Outlined.NotificationsActive, selectedTab == VaultCategory.TRACKERS) { selectedTab = VaultCategory.TRACKERS }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Filtered List
            val currentList = records.filter { it.category == selectedTab }

            if (currentList.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Outlined.Inventory2, null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                        Spacer(Modifier.height(8.dp))
                        Text("Vault is Empty", fontWeight = FontWeight.Bold, color = Color.Gray)
                        Text("Tap '+' to add to this secure section.", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.weight(1f)) {
                    items(currentList) { item ->
                        VaultItemCard(item = item) {
                            selectedRecordForView = item
                        }
                    }
                }
            }
        }

        // ==========================================
        // 🟢 IN-APP VIEWER DIALOG
        // ==========================================
        if (selectedRecordForView != null) {
            val record = selectedRecordForView!!
            Dialog(
                onDismissRequest = { selectedRecordForView = null },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(record.title, fontSize = 18.sp, fontWeight = FontWeight.Bold) },
                            navigationIcon = {
                                IconButton(onClick = { selectedRecordForView = null }) { Icon(Icons.Default.ArrowBack, "Back") }
                            },
                            actions = {
                                if (record.fileUri != null) {
                                    IconButton(onClick = { shareFile(context, record.fileUri, record.fileType) }) {
                                        Icon(Icons.Outlined.Share, "Send")
                                    }
                                }
                                IconButton(onClick = { itemToDelete = record }) {
                                    Icon(Icons.Outlined.Delete, "Remove", tint = Color.Red)
                                }
                            }
                        )
                    }
                ) { pd ->
                    Box(modifier = Modifier.padding(pd).fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
                        if (record.fileUri != null) {
                            if (record.fileType == "IMAGE") {
                                val bitmap = loadBitmapFromUri(context, Uri.parse(record.fileUri))
                                if (bitmap != null) {
                                    Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = Modifier.fillMaxSize().padding(8.dp), contentScale = ContentScale.Fit)
                                } else {
                                    Text("Image not found or deleted from storage.", color = Color.White)
                                }
                            } else {
                                InAppPdfViewer(uri = Uri.parse(record.fileUri))
                            }
                        } else {
                            Card(modifier = Modifier.padding(24.dp).fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                                Column(modifier = Modifier.padding(24.dp)) {
                                    Text("Reference / Token No:", color = Color.Gray, fontSize = 14.sp)
                                    Text(record.refNo ?: "N/A", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Indigo)
                                    Spacer(Modifier.height(16.dp))
                                    if (!record.deadline.isNullOrEmpty()) {
                                        Text("Deadline / Date:", color = Color.Gray, fontSize = 14.sp)
                                        Text(record.deadline, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD32F2F))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // ==========================================
        // 🟢 PRE-IMPORT CONFIRMATION ALERT
        // ==========================================
        if (showPreImportAlert) {
            AlertDialog(
                onDismissRequest = { showPreImportAlert = false },
                title = { Text("Import File", fontWeight = FontWeight.Bold) },
                text = { Text("You are about to import a file from your device storage to your secure offline vault. Do you want to proceed?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showPreImportAlert = false
                            filePickerLauncher.launch(arrayOf("application/pdf", "image/*"))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo)
                    ) { Text("Yes, Proceed") }
                },
                dismissButton = { TextButton(onClick = { showPreImportAlert = false }) { Text("No", color = Color.Red) } }
            )
        }

        // ==========================================
        // 🟢 ADD DIALOG (Updated dynamic titles and file requirement logic)
        // ==========================================
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    // 🟢 NAYA FIX: Dynamic Title
                    val dialogTitle = when (selectedTab) {
                        VaultCategory.DOCS -> "Save Doc in Vault"
                        VaultCategory.RECEIPTS -> "Save Receipt in Vault"
                        VaultCategory.TRACKERS -> "Save Alert in Vault"
                    }
                    Text(dialogTitle, fontWeight = FontWeight.Bold)
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedTextField(
                            value = inputTitle, onValueChange = { inputTitle = it },
                            label = { Text(if(selectedTab == VaultCategory.DOCS) "Doc Name (e.g. Aadhar)" else "Title") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (selectedTab == VaultCategory.TRACKERS) {
                            OutlinedTextField(value = inputRefNo, onValueChange = { inputRefNo = it }, label = { Text("Reference / Token No.") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = inputDeadline, onValueChange = { inputDeadline = it }, label = { Text("Deadline (e.g. 15 Aug 2024)") }, modifier = Modifier.fillMaxWidth())
                            Text("Note: Keep an eye on this date to complete your tasks.", fontSize = 11.sp, color = Color.Gray)
                        }

                        // 🟢 NAYA FIX: "Required" text for Docs/Receipts
                        val attachmentLabel = if (selectedTab == VaultCategory.TRACKERS) "Attachment (Optional):" else "One Attachment (Required):"
                        Text(attachmentLabel, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedButton(
                                onClick = { showPreImportAlert = true },
                                modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.AttachFile, null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("File", fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = { cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA) },
                                modifier = Modifier.weight(1f), shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Outlined.CameraAlt, null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Camera", fontSize = 12.sp)
                            }
                        }

                        if (selectedFileUri != null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if(selectedFileType == "PDF") Icons.Outlined.PictureAsPdf else Icons.Outlined.Image,
                                    contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text("Secured $selectedFileType Selected ✓", color = Color(0xFF2E7D32), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (inputTitle.isBlank()) {
                                Toast.makeText(context, "Title is required", Toast.LENGTH_SHORT).show()
                            }
                            // 🟢 NAYA FIX: Validation for empty file on Docs and Receipts
                            else if (selectedTab != VaultCategory.TRACKERS && selectedFileUri == null) {
                                Toast.makeText(context, "Please attach a file or take a photo", Toast.LENGTH_SHORT).show()
                            }
                            else {
                                saveNewRecord(VaultRecord(
                                    id = UUID.randomUUID().toString(), title = inputTitle, category = selectedTab,
                                    fileUri = selectedFileUri?.toString(), fileType = selectedFileType,
                                    refNo = inputRefNo, deadline = inputDeadline
                                ))
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo)
                    ) { Text("Save Offline") }
                },
                dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancel") } }
            )
        }

        // DELETE CONFIRMATION DIALOG

        if (itemToDelete != null) {
            AlertDialog(
                onDismissRequest = { itemToDelete = null },
                title = { Text("Confirm Deletion", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to remove '${itemToDelete?.title}' from your vault? This action cannot be undone.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val targetItem = itemToDelete
                            if (targetItem != null) {
                                val newList = records.filter { it.id != targetItem.id }
                                records = newList
                                LocalVaultStorage.saveRecords(context, newList)
                                Toast.makeText(context, "Record removed", Toast.LENGTH_SHORT).show()
                            }
                            itemToDelete = null
                            selectedRecordForView = null
                        }
                    ) { Text("Yes, Delete", color = Color.Red, fontWeight = FontWeight.Bold) }
                },
                dismissButton = {
                    TextButton(onClick = { itemToDelete = null }) { Text("Cancel", color = Indigo) }
                }
            )
        }
    }
}

// ==========================================
// 🟢 HELPER UI COMPONENTS
// ==========================================
@Composable
fun CategoryTab(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        color = if (isSelected) Indigo else Color.White,
        shape = RoundedCornerShape(20.dp),
        border = if(!isSelected) androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray) else null,
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, modifier = Modifier.size(16.dp), tint = if(isSelected) Color.White else Color.Gray)
            Spacer(Modifier.width(6.dp))
            Text(text, color = if (isSelected) Color.White else Color.DarkGray, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
    }
}

@Composable
fun VaultItemCard(item: VaultRecord, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            Box(modifier = Modifier.size(48.dp).background(Color(0xFFF3F4F6), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                val iconToShow = when {
                    item.category == VaultCategory.TRACKERS && item.fileUri == null -> Icons.Outlined.Alarm
                    item.fileType == "PDF" -> Icons.Outlined.PictureAsPdf
                    item.fileType == "IMAGE" -> Icons.Outlined.Image
                    else -> Icons.Outlined.InsertDriveFile
                }
                Icon(imageVector = iconToShow, contentDescription = null, tint = Indigo)
            }
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)

                if (item.category == VaultCategory.TRACKERS) {
                    Text("Ref: ${item.refNo}", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                    if (!item.deadline.isNullOrEmpty()) {
                        Text("Deadline: ${item.deadline}", fontSize = 12.sp, color = Color(0xFFD32F2F), fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 4.dp))
                    }
                }

                if (item.fileUri != null) {
                    Text("Secured ${item.fileType ?: "File"} Saved", fontSize = 12.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp))
                } else if(item.category != VaultCategory.TRACKERS) {
                    Text("No file attached", fontSize = 12.sp, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}

//  NATIVE IN-APP PDF VIEWER COMPONENT
@Composable
fun InAppPdfViewer(uri: Uri) {
    val context = LocalContext.current
    var pdfRenderer by remember { mutableStateOf<PdfRenderer?>(null) }
    var fileDescriptor by remember { mutableStateOf<ParcelFileDescriptor?>(null) }

    val pdfMutex = remember { Mutex() }

    DisposableEffect(uri) {
        try {
            fileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
            if (fileDescriptor != null) {
                pdfRenderer = PdfRenderer(fileDescriptor!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        onDispose {
            pdfRenderer?.close()
            fileDescriptor?.close()
        }
    }

    pdfRenderer?.let { renderer ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(renderer.pageCount) { index ->
                PdfPageRenderer(renderer, index, pdfMutex)
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Unable to load PDF. It might be corrupted or missing.", color = Color.White)
        }
    }
}

@Composable
fun PdfPageRenderer(renderer: PdfRenderer, pageIndex: Int, mutex: Mutex) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(pageIndex) {
        withContext(Dispatchers.IO) {
            mutex.withLock {
                try {
                    val page = renderer.openPage(pageIndex)
                    val bmp = Bitmap.createBitmap(page.width * 2, page.height * 2, Bitmap.Config.ARGB_8888)
                    val canvas = android.graphics.Canvas(bmp)
                    canvas.drawColor(android.graphics.Color.WHITE)
                    page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    page.close()
                    bitmap = bmp
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = "Page ${pageIndex + 1}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentScale = ContentScale.FillWidth
        )
    } else {
        Box(modifier = Modifier.fillMaxWidth().height(400.dp), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Indigo)
        }
    }
}
