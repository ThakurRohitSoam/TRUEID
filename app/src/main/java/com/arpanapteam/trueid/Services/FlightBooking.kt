package com.arpanapteam.trueid

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.*

data class AirlineInfo(
    val name: String,
    val website: String,
    val booking: String,
    val checkin: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirlineTicketScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    val airlines = listOf(

        AirlineInfo(
            "IndiGo",
            "https://www.goindigo.in/",
            "https://www.goindigo.in/flights",
            "https://www.goindigo.in/check-flight-status.html"
        ),

        AirlineInfo(
            "Air India",
            "https://www.airindia.com/",
            "https://www.airindia.com/en-in/book-flights/",
            "https://www.airindia.com/in/en/manage/web-checkin.html"
        ),

        AirlineInfo(
            "Air India Express",
            "https://www.airindiaexpress.com/home",
            "https://www.airindiaexpress.com/book-and-manage/book",
            "https://www.airindiaexpress.com/checkin-home"
        ),

        AirlineInfo(
            "Akasa Air",
            "https://www.akasaair.com/",
            "https://www.akasaair.com/flight-booking",
            "https://www.akasaair.com/flight-status"
        ),

        AirlineInfo(
            "SpiceJet",
            "https://www.spicejet.com/",
            "https://www.spicejet.com/",
            "https://www.spicejet.com/"
        )
    )

    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(airlines[0]) }

    fun open(url: String) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flight Booking") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        },
        containerColor = OffWhite
    ) { pad ->

        Column(
            modifier = Modifier
                .padding(pad)
                .padding(20.dp)
                .fillMaxSize()
        ) {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    value = selected.name,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { Icon(Icons.Default.KeyboardArrowDown,null) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    label = { Text("Select Airline") }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    airlines.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                selected = it
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            AirlineActionRow("Official Website") {
                open(selected.website)
            }

            Spacer(Modifier.height(16.dp))

            AirlineActionRow("Book Ticket") {
                open(selected.booking)
            }

            Spacer(Modifier.height(16.dp))

            AirlineActionRow("Web Check-in") {
                open(selected.checkin)
            }
        }
    }
}

@Composable
fun AirlineActionRow(
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontSize = 16.sp)

            Button(
                onClick = onClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Indigo)
            ) {
                Text("GO")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AirlineTicketPreview() {
    TRUEIDTheme {
        AirlineTicketScreen()
    }
}
