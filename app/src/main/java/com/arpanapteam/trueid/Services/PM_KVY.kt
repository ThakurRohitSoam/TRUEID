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

// Desktop purple button color
val PortaluiPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PMKVYScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String){
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PMKVY Scheme") },
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

            // ABOUT SCHEME
            item{
                Card(shape = RoundedCornerShape(18.dp)){
                    Column(Modifier.padding(16.dp)) {

                        Text("About the Scheme",
                            style = MaterialTheme.typography.titleMedium)

                        Spacer(Modifier.height(8.dp))

                        Text(
                            "PMKVY is the flagship scheme of the Ministry of Skill Development & Entrepreneurship (MSDE) implemented by NSDC. The objective is to enable Indian youth to take up industry-relevant skill training to secure a better livelihood."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = { open("https://www.pmkvyofficial.org") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalPurple
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text("PMKVY Official")
                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = { open("https://upsdm.gov.in") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalPurple
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text("UP Skill Development Mission")
                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = { open("https://nsdcindia.org") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalPurple
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text("NSDC Official")
                        }
                    }
                }
            }

            // ELIGIBILITY
            item{
                InfoCard(
                    "Eligibility Criteria",
                    """
• Indian national between 15-45 years  
• Valid ID proof (Aadhaar, Voter ID etc.)  
• Unemployed youth or school/college dropouts
"""
                )
            }

            // BENEFITS
            item{
                InfoCard(
                    "Benefits of Scheme",
                    """
• Free skill training  
• Industry-relevant curriculum  
• Valid certificate & Skill Card  
• Placement assistance
"""
                )
            }

            // TRAINING CENTER
            item{
                KVYCard("Find Training Center"){
                    open("https://www.pmkvyofficial.org/Training-Center")
                }
            }

        }
    }
}

// Uniform info card
@Composable
fun InfoCard(title:String, desc:String){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ){
        Column(Modifier.padding(16.dp)){
            Text(title,
                style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(8.dp))

            Text(desc)
        }
    }
}

// GO button row card
@Composable
fun KVYCard(
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
                    containerColor = PortaluiPurple
                )
            ){
                Text("GO")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PMKVYPreview(){
    TRUEIDTheme {
        PMKVYScreen()
    }
}
