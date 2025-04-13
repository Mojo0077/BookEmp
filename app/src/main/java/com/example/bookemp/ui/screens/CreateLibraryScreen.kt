package com.example.bookemp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.bookemp.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

@Composable
fun CreateLibraryScreen(
    onLibraryCreated: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val context = LocalContext.current
    val customFont = FontFamily(Font(R.font.cat_childs))
    var libraryName by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.kezdo_oldal),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // ✅ görgethető
                .padding(start = 32.dp, end = 32.dp, top = 80.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cím dupla réteg árnyékkal
            Box {
                Text(
                    text = "Új könyvtár létrehozása",
                    fontSize = 32.sp,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F1503),
                    modifier = Modifier.offset(x = 2.dp, y = 2.dp)
                )
                Text(
                    text = "Új könyvtár létrehozása",
                    fontSize = 30.sp,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDFB148),
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = libraryName,
                onValueChange = { libraryName = it },
                label = { Text("Könyvtár neve", color = Color(0xFFDFB148)) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0x33DFB148),
                    unfocusedContainerColor = Color(0x33DFB148)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (libraryName.isNotBlank()) {
                        val db = FirebaseFirestore.getInstance()
                        val libraryData = hashMapOf(
                            "name" to libraryName,
                            "createdAt" to Date()
                        )
                        db.collection("libraries")
                            .add(libraryData)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Könyvtár létrehozva!", Toast.LENGTH_SHORT).show()
                                onLibraryCreated()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Hiba: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Írj be egy könyvtárnevet!", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3C2C1F)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Létrehozás", fontFamily = customFont, color = Color(0xFFDFB148), fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onCancel) {
                Text("Mégsem", color = Color.White, fontFamily = customFont, fontSize = 18.sp)
            }
        }
    }
}
