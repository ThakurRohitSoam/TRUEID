/*
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

// Desktop jaisa purple
val PortalseenPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PMKisanScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url: String){
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PM-KISAN") },
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
                Card(
                    shape = RoundedCornerShape(18.dp)
                ){
                    Column(Modifier.padding(16.dp)) {

                        Text("About the Scheme",
                            style = MaterialTheme.typography.titleMedium)

                        Spacer(Modifier.height(8.dp))

                        Text(
                            """
• PM-KISAN is a Central Sector scheme with 100% funding from Government of India.

• Provides ₹6,000 per year in three equal installments to farmer families.

• Money is directly transferred to bank accounts.
""".trimIndent()
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://pmkisan.gov.in/")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PortalseenPurple
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text("Official Website")
                        }
                    }
                }
            }

            // SERVICES
            item{
                ServiceCard(
                    "New Farmer Registration"
                ){
                    open("https://pmkisan.gov.in/RegistrationFormNew.aspx")
                }
            }

            item{
                ServiceCard(
                    "eKYC (Aadhaar OTP)"
                ){
                    open("https://pmkisan.gov.in/aadharekyc.aspx")
                }
            }

            item{
                ServiceCard(
                    "Check Beneficiary / Payment Status"
                ){
                    open("https://pmkisan.gov.in/BeneficiaryStatus.aspx")
                }
            }

            item{
                ServiceCard(
                    "Update Self-Registered Farmer"
                ){
                    open("https://pmkisan.gov.in/UpdateSelfRegisteredFarmer.aspx")
                }
            }

            item{
                ServiceCard(
                    "Check Beneficiary List"
                ){
                    open("https://pmkisan.gov.in/Rpt_BeneficiaryStatus_pub.aspx")
                }
            }

        }
    }
}

// ONE StepCard only → no overload error
@Composable
fun ServiceCard(
    title: String,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ){
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(title,
                modifier = Modifier.weight(1f))

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

@Preview(showBackground = true)
@Composable
fun PMKisanPreview(){
    TRUEIDTheme {
        PMKisanScreen()
    }
}
*/