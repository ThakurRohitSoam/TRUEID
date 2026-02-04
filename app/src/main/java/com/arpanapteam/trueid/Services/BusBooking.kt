package com.arpanapteam.trueid.Services

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

data class BusInfo(
    val name: String,
    val website: String,
    val booking: String,
    val extra1: String,
    val offer: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusTicketScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    val buses = listOf(

        BusInfo(
            "RedBus",
            "https://www.redbus.in/",
            "https://www.redbus.in/bus-tickets",
            "https://www.redbus.in/information/government-bus-booking",
            "https://www.redbus.in/offers"
        ),

        BusInfo(
            "AbhiBus",
            "https://www.abhibus.com/",
            "https://www.abhibus.com/operator/1606/abhi-travels-bus-booking",
            "https://www.abhibus.com/cancellation",
            "https://www.abhibus.com/bus-ticket-offers"
        ),

        BusInfo(
            "MakeMyTrip",
            "https://www.makemytrip.com/",
            "https://www.makemytrip.com/bus-tickets/",
            "https://www.makemytrip.com/holidays-india/",
            "https://www.makemytrip.com/hotels/"
        ),

        BusInfo(
            "ZingBus",
            "https://www.zingbus.com/",
            "https://www.zingbus.com/",
            "https://www.zingbus.com/",
            "https://www.zingbus.com/"
        ),

        BusInfo(
            "IRCTC Bus",
            "https://www.bus.irctc.co.in/home",
            "https://www.bus.irctc.co.in/home",
            "https://www.bus.irctc.co.in/user/printTicket?txnId=3000718456",
            "https://www.bus.irctc.co.in/home"
        )
    )

    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(buses[0]) }

    fun open(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bus Booking") },
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

            // Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    value = selected.name,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Default.KeyboardArrowDown,null)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    label = { Text("Select Bus Service") }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    buses.forEach {
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

            BusActionRow("Official Website") {
                open(selected.website)
            }

            Spacer(Modifier.height(16.dp))

            BusActionRow("Book Bus Ticket") {
                open(selected.booking)
            }

            Spacer(Modifier.height(16.dp))

            BusActionRow("Extra Service") {
                open(selected.extra1)
            }

            Spacer(Modifier.height(16.dp))

            BusActionRow("Offers") {
                open(selected.offer)
            }
        }
    }
}

@Composable
fun BusActionRow(
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
fun BusPreview() {
    TRUEIDTheme {
        BusTicketScreen()
    }
}
