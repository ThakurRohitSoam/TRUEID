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
val PortalLibPurple = Color(0xFF5B4AE3)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalLibraryScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    fun open(url:String){
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Digital Library") },
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

            item{
                LibraryCard("Swayam"){
                    open("https://swayam.gov.in/")
                }
            }

            item{
                LibraryCard("National Digital Library (NDLI)"){
                    open("https://ndl.iitkgp.ac.in/")
                }
            }

            item{
                LibraryCard("AKTU Digital Library"){
                    open("https://aktu.ac.in/")
                }
            }

            item{
                LibraryCard("DU Digital Library"){
                    open("https://library.du.ac.in/")
                }
            }

            item{
                LibraryCard("NPTEL"){
                    open("https://nptel.ac.in/")
                }
            }

            item{
                LibraryCard("UP Digital Library"){
                    open("https://uplib.up.nic.in/")
                }
            }

            item{
                LibraryCard("e-Granthalaya"){
                    open("https://egranthalaya.nic.in/")
                }
            }
        }
    }
}

// ---------- UNIFORM CARD ----------

@Composable
fun LibraryCard(
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
                    containerColor = PortalLibPurple
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
fun DigitalLibraryPreview(){
    TRUEIDTheme {
        DigitalLibraryScreen()
    }
}