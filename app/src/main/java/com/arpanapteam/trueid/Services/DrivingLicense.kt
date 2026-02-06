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

data class DLService(
    val title: String,
    val url: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrivingLicenseScreen() {

    val context = LocalContext.current
    val activity = context as? Activity

    fun open(url: String){
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    val services = listOf(
        DLService("Ministry Of Road Transport & Highways",
            "https://parivahan.gov.in/"),

        DLService("Driving License Services (Parivahan Sarathi)",
            "https://sarathi.parivahan.gov.in/sarathiservice/stateSelection.do"),

        DLService("Transport Dept. Govt. of Uttar Pradesh",
            "https://sarathi.parivahan.gov.in/sarathiservice/stateSelectBean.do"),

        DLService("Apply For Learner License",
            "https://sarathi.parivahan.gov.in/sarathiservice/newLLDet.do"),

        DLService("Apply For Driving License",
            "https://sarathi.parivahan.gov.in/sarathiservice/newDLDet.do"),

        DLService("Check Your Application Status",
            "https://sarathi.parivahan.gov.in/sarathiservice/applViewStatus.do")
    )

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Driving License") },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
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
                            "What is Driving Licence?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        if(expanded){
                            Spacer(Modifier.height(8.dp))

                            Text(
                                "A driving licence (DL) is an official, government-issued document authorizing an individual to operate motor vehicles on public roads.\n\n" +
                                        "It is issued by the RTO/RTA and serves as legal proof of driving competency and identity.\n\n" +
                                        "Purpose: Ensures a person has passed required tests and can legally drive.\n\n" +
                                        "Components: Photo, signature, address, DL number.\n\n" +
                                        "Types:\n" +
                                        "• Learner Licence\n" +
                                        "• Permanent Licence\n" +
                                        "• Commercial Licence\n\n" +
                                        "Eligibility: 18+ years (16 for <50cc bikes).\n\n" +
                                        "Important:\n" +
                                        "Driving without DL is punishable under Motor Vehicles Act.\n" +
                                        "Licence specifies vehicle class (LMV, MCWG etc).\n" +
                                        "International Driving Permit needed abroad."
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
                        Text(item.title, modifier = Modifier.weight(1f))

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
fun DLPreview(){
    TRUEIDTheme {
        DrivingLicenseScreen()
    }
}