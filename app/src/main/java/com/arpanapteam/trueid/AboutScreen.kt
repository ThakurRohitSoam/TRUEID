package com.arpanapteam.trueid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arpanapteam.trueid.ui.theme.*

data class Service(val name: String, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    val services = listOf(
        Service("Aadhar Card", "A 12-digit unique identity number for Indian citizens, storing biometric and demographic data."),
        Service("PAN Card", "A 10-digit alphanumeric number assigned to all taxpayers in India for tracking financial transactions."),
        Service("Indian Passport", "An official document for Indian citizens for the purpose of international travel."),
        Service("Voter ID", "An identity document for Indian citizens to cast their ballot in elections."),
        Service("Driving License", "An official document that authorizes its holder to operate various types of motor vehicles."),
        Service("Indian Railway", "Services related to India's national railway system, including booking and inquiries."),
        Service("PM Kisan", "A scheme for income support scheme for small and marginal farmer families."),
        Service("PM KVY", "A skill development scheme to encourage aptitude towards employable skills."),
        Service("UP Pension", "Financial assistance in the form of pension to the elderly, widows, and disabled persons.")
    )

    Scaffold(
        topBar = { AboutTopAppBar(navController) }
        ,containerColor = OffWhite
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(bottom = 24.dp)) {
                    Text(
                        text = "About TRUEID",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Welcome to our Self-Service Portal. Our mission is to provide comprehensive and easy-to-understand information about services and schemes offered by the Government of India. We offer not just information, but also direct links and step-by-step instructions to help you access these services without hassle. Everything you need is right here in one place.",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color= Color.Black
                    )

                }
            }

            item {
                Text(
                    text = "Services We Cover",
                    fontSize = 20.sp,
//                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color= Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }

            item {
                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    Text("#", modifier = Modifier.width(32.dp), color = Color.Black,fontWeight = FontWeight.Medium)
                    Text("SERVICE", modifier = Modifier.weight(1f), color = Color.Black,fontWeight = FontWeight.Medium)
                    Text("DESCRIPTION", modifier = Modifier.weight(2f), color = Color.Black,fontWeight = FontWeight.Medium)
                }
            }

            items(services.size) { index ->
                val service = services[index]
                Row(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text((index + 1).toString(), modifier = Modifier.width(32.dp),color=Color.Black,fontWeight = FontWeight.Medium)
                    Text(service.name, modifier = Modifier.weight(1f),color = Color.Black,fontWeight = FontWeight.Medium)
                    Text(service.description, modifier = Modifier.weight(2f), fontSize = 14.sp,color= Color.Black)
                }
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTopAppBar(navController: NavHostController) {
    TopAppBar(
//        Text
        title = { Text("About",color = Color.Black, fontWeight = FontWeight.Black) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OffWhite)

    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    TRUEIDTheme {
//        AboutScreen()
    }
}
