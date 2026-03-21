/*
package com.arpanapteam.trueid.Services

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.*

data class PanService(
    val title: String,
    val links: List<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanCardScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    val services = listOf(

        PanService(
            "Apply for New PAN Card",
            listOf(
                "https://www.onlineservices.nsdl.com/paam/endUserRegisterContact.html",
                "https://www.pan.utiitsl.com/panonline_ipg/forms/pan.html/preForm",
                "https://eportal.incometax.gov.in/iec/foservices/#/pre-login/instant-e-pan"
            )
        ),

        PanService(
            "Check Application Status",
            listOf(
                "https://tin.tin.nsdl.com/pantan/StatusTrack.html",
                "https://www.trackpan.utiitsl.com/PANONLINE/forms/TrackPan/trackApp#forward"
            )
        ),

        PanService(
            "Download e-PAN Card",
            listOf(
                "https://www.onlineservices.nsdl.com/paam/requestAndDownloadEPAN.html",
                "https://www.pan.utiitsl.com/PAN_ONLINE/ePANCardHom"
            )
        ),

        PanService(
            "Update / Correct PAN Details",
            listOf(
                "https://www.pan.utiitsl.com/PAN/csf.html"
            )
        ),

        PanService(
            "Link PAN with Aadhaar",
            listOf(
                "https://eportal.incometax.gov.in/iec/foservices/#/pre-login/bl-link-aadhaar"
            )
        ),

        PanService(
            "Official Portals",
            listOf(
                "https://www.onlineservices.nsdl.com/paam/endUserRegisterContact.html",
                "https://www.pan.utiitsl.com/PAN/"
            )
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PAN Card Services") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        },
        containerColor = OffWhite
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🔹 Description Card
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "What is a PAN Card?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "A Permanent Account Number (PAN) is a ten-digit alphanumeric number issued by the Income Tax Department of India. It is mandatory for filing income tax returns, opening bank accounts, and many financial transactions.",
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // 🔹 Service Cards
            services.forEach { service ->

                item {
                    ServiceCard(
                        title = service.title,
                        links = service.links,
                        onGo = { url -> open(url) }
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceCard(
    title: String,
    links: List<String>,
    onGo: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(Modifier.height(10.dp))

            links.forEach { link ->

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        "Open Link",
                        fontSize = 14.sp
                    )

                    Button(
                        onClick = { onGo(link) },
                        colors = ButtonDefaults.buttonColors(Indigo)
                    ) {
                        Text("GO")
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PanPreview() {
    TRUEIDTheme {
        PanCardScreen()
    }
}

*/