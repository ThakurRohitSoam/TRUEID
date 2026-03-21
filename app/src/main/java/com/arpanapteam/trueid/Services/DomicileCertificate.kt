/*
package com.arpanapteam.trueid.Services

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomicileCertificateScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String){
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Domicile Certificate") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack,"Back")
                    }
                }
            )
        },
        containerColor = OffWhite
    ) { pad ->

        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // STEP 1
            DomicileStepCard(
                "Step 1: Register on Portal",
                "Register yourself on the e-Sathi portal.",
                "GO"
            ){
                open("https://esathi.up.gov.in/")
            }

            // STEP 2
            DomicileStepCard(
                "Step 2: Prepare Documents",
                """
• Applicant Photo  
• Aadhaar Card  
• Self-Declaration Form  
• Ration Card / Family ID
                """.trimIndent(),
                "FORM"
            ){
                open("https://esathi.up.gov.in/")
            }

            // STEP 3
            DomicileStepCard(
                "Step 3: Apply Online",
                "Login → Fill form → Upload docs → Pay fee → Submit",
                "APPLY"
            ){
                open("https://esathi.up.gov.in/")
            }

            // STEP 4
            DomicileStepCard(
                "Step 4: Track Status",
                "Track application using application number.",
                "STATUS"
            ){
                open("https://esathi.up.gov.in/citizenservices/login/login.aspx")
            }
        }
    }
}

@Composable
fun DomicileStepCard(
    title: String,
    desc: String,
    buttonText: String,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Text(title, fontSize = 18.sp)

            Text(desc, fontSize = 14.sp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo)
                ){
                    Text(buttonText)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DomicilePreview(){
    TRUEIDTheme {
        DomicileCertificateScreen()
    }
}
*/