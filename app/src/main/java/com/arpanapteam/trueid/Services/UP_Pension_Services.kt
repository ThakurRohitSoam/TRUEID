package com.arpanapteam.trueid.Services

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arpanapteam.trueid.ui.theme.TRUEIDTheme

// Desktop purple
val PortalUPPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UPPensionScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url:String){
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UP Pension Services") },
                navigationIcon = {
                    IconButton(onClick = onBack){
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,null)
                    }
                }
            )
        }
    ){ pad ->

        LazyColumn(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ){

            // OFFICIAL PORTAL
            item{
                ServiceUPCard("Go to Integrated Pension Portal"){
                    open("https://sspy-up.gov.in/")
                }
            }

            // OLD AGE
            item{
                PensionCard(
                    title = "Old Age Pension",
                    leftText =
                        """
Benefits:
₹1000 per month to pensioners above 60 years.
""",
                    rightText =
                        """
Eligibility:
• Age 60+  
• Below poverty line  
• Income ≤ ₹56,460 (Urban) / ₹46,080 (Rural)
""",
                    openApply = { open("https://sspy-up.gov.in/") },
                    openStatus = { open("https://sspy-up.gov.in/") },
                    openManual = { open("https://sspy-up.gov.in/") }
                )
            }

            // WIDOW
            item{
                PensionCard(
                    title = "Widow Pension",
                    leftText =
                        """
Objective & Benefit:
₹1000/month for destitute widows (18-60).
""",
                    rightText =
                        """
Eligibility:
• Age 18-60  
• Below poverty line  
• Income ≤ ₹2 lakh
""",
                    openApply = { open("https://sspy-up.gov.in/") },
                    openStatus = { open("https://sspy-up.gov.in/") },
                    openManual = { open("https://sspy-up.gov.in/") }
                )
            }

            // DIVYANG
            item{
                PensionCard(
                    title = "Divyang & Leprosy Pension",
                    leftText =
                        """
Objective & Benefit:
₹1000/month for 40%+ disability.
""",
                    rightText =
                        """
Eligibility Note:
Disability certificate required.
""",
                    openApply = { open("https://sspy-up.gov.in/") },
                    openStatus = { open("https://sspy-up.gov.in/") },
                    openManual = { open("https://sspy-up.gov.in/") }
                )
            }

        }
    }
}

// ----------------------------
// UNIFORM CARDS
// ----------------------------

@Composable
fun PensionCard(
    title:String,
    leftText:String,
    rightText:String,
    openApply:()->Unit,
    openStatus:()->Unit,
    openManual:()->Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ){
        Column(Modifier.padding(16.dp)) {

            Text(title, style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(8.dp))

            Row {
                Text(leftText, Modifier.weight(1f))
                Spacer(Modifier.width(8.dp))
                Text(rightText, Modifier.weight(1f))
            }

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                SmallButton("Apply", openApply)
                SmallButton("Status", openStatus)
                SmallButton("Manual", openManual)
            }
        }
    }
}

@Composable
fun SmallButton(text:String, click:()->Unit){
    Button(
        onClick = click,
        colors = ButtonDefaults.buttonColors(
            containerColor = PortalPurple
        )
    ){
        Text(text)
    }
}

@Composable
fun ServiceUPCard(
    title:String,
    onClick:()->Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ){
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(title, Modifier.weight(1f))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PortalPurple
                )
            ){
                Text("GO")
            }
        }
    }
}

// PREVIEW
@Preview(showBackground = true)
@Composable
fun UPPensionPreview(){
    TRUEIDTheme {
        UPPensionScreen()
    }
}