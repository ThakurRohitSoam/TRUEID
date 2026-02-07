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

// Desktop purple color
val ScholarshipPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScholarshipScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url:String){
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UP Scholarship") },
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

            // ---------- ABOUT SECTION ----------
            item{
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp)
                ){
                    Column(Modifier.padding(16.dp)){

                        Text(
                            "About the Scheme",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "This is a Scholarship and Fee Reimbursement Program to help students continue their studies. The objective is to ensure timely disbursement of funds via Direct Benefit Transfer (DBT)."
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "Requirement: Student must have an Aadhaar linked with a phone number and a bank account seeded with Aadhaar."
                        )

                        Spacer(Modifier.height(12.dp))

                        Button(
                            onClick = {
                                open("https://scholarship.up.gov.in/")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ScholarshipPurple
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text("Official Website")
                        }
                    }
                }
            }

            // ---------- SERVICES ----------
            item{
                ScholarshipCard("New Student Registration"){
                    open("https://scholarship.up.gov.in/")
                }
            }

            item{
                ScholarshipCard("Download Eligibility Note"){
                    open("https://scholarship.up.gov.in/")
                }
            }

            item{
                ScholarshipCard("Check Payment Status (PFMS)"){
                    open("https://pfms.nic.in/")
                }
            }
        }
    }
}

// ---------- UNIFORM CARD ----------

@Composable
fun ScholarshipCard(
    title:String,
    onClick:()->Unit
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            Text(title, Modifier.weight(1f))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = ScholarshipPurple
                )
            ){
                Text("GO")
            }
        }
    }
}

// ---------- PREVIEW ----------

@Preview(showBackground = true)
@Composable
fun ScholarshipPreview(){
    TRUEIDTheme {
        ScholarshipScreen()
    }
}
