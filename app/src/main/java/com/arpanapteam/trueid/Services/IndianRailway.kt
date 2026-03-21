/*
package com.arpanapteam.trueid

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Train
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme
import com.arpanapteam.trueid.ui.theme.Indigo

// ---------- DATA ----------
data class RailwayLink(
    val title: String,
    val url: String
)

// ---------- SCREEN ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RailwayLinksScreen() {

    val context = LocalContext.current

    // ✅ SYSTEM BACK FIX
    val backDispatcher =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val links = listOf(
        RailwayLink(
            "Register New User",
            "https://www.irctc.co.in/nget/profile/user-signup"
        ),
        RailwayLink(
            "Book Railway Ticket",
            "https://www.irctc.co.in/nget/train-search"
        ),
        RailwayLink(
            "PNR Inquiry",
            "https://www.indianrail.gov.in/enquiry/PNR/PnrEnquiry.html?locale=en"
        ),
        RailwayLink(
            "IRCTC Official App",
            "https://play.google.com/store/apps/details?id=cris.org.in.prs.ima"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Railway Services") },

                // ✅ WORKING BACK BUTTON
                navigationIcon = {
                    IconButton(onClick = {
                        backDispatcher?.onBackPressed()
                    }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(links.size) { index ->

                val item = links[index]

                RailwayRow(
                    title = item.title,
                    onGoClick = {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

// ---------- ROW UI ----------
@Composable
fun RailwayRow(
    title: String,
    onGoClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Outlined.Train,
                    contentDescription = null
                )

                Spacer(Modifier.width(16.dp))

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Button(
                onClick = onGoClick,
                colors = ButtonDefaults.buttonColors(Indigo)
            ) {
                Text("GO")
            }
        }
    }
}

// ---------- PREVIEW ----------
@Preview(showBackground = true)
@Composable
fun RailwayPreview() {
    TRUEIDTheme {
        RailwayLinksScreen()
    }
}
*/