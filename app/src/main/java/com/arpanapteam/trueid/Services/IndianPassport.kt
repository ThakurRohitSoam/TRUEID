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

data class PassportService(
    val title: String,
    val url: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassportScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current
    val activity = context as? Activity

    fun open(url: String){
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    val services = listOf(
        PassportService(
            "Passport Seva Online Portal",
            "https://www.passportindia.gov.in/psp"
        ),
        PassportService(
            "New User Registration",
            "https://services1.passportindia.gov.in/forms/registration"
        ),
        PassportService(
            "Existing User Login",
            "https://services1.passportindia.gov.in/forms/PreLogin"
        ),
        PassportService(
            "Check Application Status",
            "https://www.passportindia.gov.in/psp/trackApplicationService"
        )
    )

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Indian Passport") },
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
                            "What is Indian Passport?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        if(expanded){
                            Spacer(Modifier.height(8.dp))

                            Text(
                                "An Indian passport is an official document issued by the Ministry of External Affairs to Indian citizens. " +
                                        "It acts as proof of nationality and a travel document for international journeys.\n\n" +

                                        "It allows citizens to travel abroad and is mandatory for leaving India.\n\n" +

                                        "Types:\n" +
                                        "• Blue – Ordinary travel\n" +
                                        "• White – Government officials\n" +
                                        "• Maroon – Diplomats\n" +
                                        "• Orange – ECR category\n\n" +

                                        "Key Features:\n" +
                                        "• Validity: 10 years (adults), 5 years (minors)\n" +
                                        "• Issued by MEA via Passport Seva Kendras\n" +
                                        "• High security with chips & holograms\n" +
                                        "• Only for Indian citizens"
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
fun PassportPreview(){
    TRUEIDTheme {
        PassportScreen()
    }
}
