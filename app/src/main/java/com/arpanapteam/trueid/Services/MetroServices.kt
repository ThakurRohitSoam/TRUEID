/*
package com.arpanapteam.trueid

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arpanapteam.trueid.ui.theme.*

data class MetroInfo(
    val name: String,
    val city: String,
    val appLink: String? = null,
    val whatsapp: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetroTicketScreen(onBack: () -> Unit = {}) {

    val context = LocalContext.current

    val metros = listOf(

        MetroInfo("Delhi Metro","Delhi NCR",
            "https://play.google.com/store/apps/details?id=com.sraoss.dmrc",
            "9650855800"),

        MetroInfo("Namma Metro","Bengaluru",
            "https://play.google.com/store/apps/details?id=com.aum.nammametro",
            "8105556677"),

        MetroInfo("Mumbai Metro","Mumbai",
            "https://play.google.com/store/apps/details?id=com.mmrda"),

        MetroInfo("Kolkata Metro","Kolkata",
            "https://play.google.com/store/apps/details?id=org.cris.kmmts"),

        MetroInfo("Hyderabad Metro","Hyderabad",
            "https://play.google.com/store/apps/details?id=com.minfy.tsavaari"),

        MetroInfo("Chennai Metro","Chennai",
            "https://play.google.com/store/apps/details?id=org.chennaimetrorail.appv1"),

        MetroInfo("Pune Metro","Pune",
            "https://play.google.com/store/apps/details?id=org.mahametro.punemobileapp"),

        MetroInfo("Nagpur Metro","Nagpur",
            "https://play.google.com/store/apps/details?id=nagpur.scsoft.com.nagpurapp"),

        // No app/whatsapp
        MetroInfo("Lucknow Metro","Lucknow"),
        MetroInfo("Aqua Line","Greater Noida"),
        MetroInfo("Kanpur Metro","Kanpur"),
        MetroInfo("Kochi Metro","Kochi"),
        MetroInfo("Jaipur Metro","Jaipur"),
        MetroInfo("Rapid Metro","Gurugram"),
        MetroInfo("Navi Mumbai Metro","Navi Mumbai"),
        MetroInfo("Agra Metro","Agra"),
        MetroInfo("Bhopal Metro","Bhopal"),
        MetroInfo("Indore Metro","Indore"),
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedMetro by remember { mutableStateOf(metros[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Metro Ticket") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack,"Back")
                    }
                }
            )
        },
        containerColor = OffWhite
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize()
        ) {

            // 🔽 Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {

                OutlinedTextField(
                    value = "${selectedMetro.name} (${selectedMetro.city})",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Metro") },
                    trailingIcon = {
                        Icon(Icons.Default.KeyboardArrowDown,null)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    metros.forEach {
                        DropdownMenuItem(
                            text = { Text("${it.name} (${it.city})") },
                            onClick = {
                                selectedMetro = it
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            // 📲 WhatsApp Row
            ActionRow(
                title = "WhatsApp Ticket",
                buttonText = "GO"
            ) {
                selectedMetro.whatsapp?.let {
                    val url = "https://wa.me/91$it"
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                } ?: Toast.makeText(
                    context,
                    "You will get the ticket from the counter",
                    Toast.LENGTH_LONG
                ).show()
            }

            Spacer(Modifier.height(16.dp))

            // 📱 App Row
            ActionRow(
                title = "Official App",
                buttonText = "GET"
            ) {
                selectedMetro.appLink?.let {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                } ?: Toast.makeText(
                    context,
                    "You will get the ticket from the counter",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

@Composable
fun ActionRow(
    title: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontSize = 16.sp)

            Button(
                onClick = onClick,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Indigo)
            ) {
                Text(buttonText)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MetroPreview() {
    TRUEIDTheme {
        MetroTicketScreen()
    }
}
*/
