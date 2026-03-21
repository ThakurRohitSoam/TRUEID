/*
package com.arpanapteam.trueid.Services

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.*

data class VoterService(
    val title: String,
    val url: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoterIdScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current
    val activity = context as? Activity

    fun open(url: String){
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    val services = listOf(

        VoterService(
            "Voters' Service Portal (ECI)",
            "https://voters.eci.gov.in/"
        ),

        VoterService(
            "Apply for New Voter ID (Form 6)",
            "https://voters.eci.gov.in/signup"
        ),

        VoterService(
            "Check Application Status",
            "https://voters.eci.gov.in/login"
        ),

        VoterService(
            "Download e-EPIC",
            "https://voters.eci.gov.in/login"
        ),

        VoterService(
            "Search in Electoral Roll",
            "https://voters.eci.gov.in/login"
        ),

        VoterService(
            "Update/Correction (Form 8)",
            "https://voters.eci.gov.in/login"
        ),

        VoterService(
            "Know Your Polling Station",
            "https://electoralsearch.eci.gov.in/pollingstation"
        )
    )

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Voter ID Services") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack,null)
                    }
                }
            )
        },
        containerColor = OffWhite
    ) { pad ->

        LazyColumn(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ){

            // -------- DESCRIPTION CARD --------
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded },
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ){
                    Column(Modifier.padding(16.dp)) {

                        Text(
                            "What is Voter ID?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        if(expanded){
                            Spacer(Modifier.height(8.dp))

                            Text(
                                "A Voter ID Card (EPIC) is an official document issued by the Election Commission of India (ECI).\n\n" +

                                        "Purpose:\n" +
                                        "• Used for voting in elections\n\n" +

                                        "Identity Proof:\n" +
                                        "• Acts as ID, age & address proof\n\n" +

                                        "Eligibility:\n" +
                                        "• Indian citizen aged 18+\n\n" +

                                        "EPIC Number:\n" +
                                        "• Unique 10-digit alphanumeric number\n\n" +

                                        "Digital Version:\n" +
                                        "• e-EPIC downloadable PDF version"
                            )
                        } else {
                            Text(
                                "Tap to read full details...",
                                color = TextGray
                            )
                        }
                    }
                }
            }

            // -------- SERVICE CARDS --------
            items(services.size){ i ->

                val item = services[i]

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ){
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            item.title,
                            modifier = Modifier.weight(1f),
                            fontSize = 15.sp
                        )

                        Button(
                            onClick = { open(item.url) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(Indigo)
                        ){
                            Text("GO")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VoterPreview(){
    TRUEIDTheme {
        VoterIdScreen()
    }
}
*/