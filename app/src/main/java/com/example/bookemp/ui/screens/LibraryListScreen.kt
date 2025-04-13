package com.example.bookemp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookemp.R
import com.example.bookemp.models.Library
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

@Composable
fun LibraryListScreen(
    onBack: () -> Unit = {}
) {
    val db = FirebaseFirestore.getInstance()
    val libraries = remember { mutableStateListOf<Library>() }
    val customFont = FontFamily(Font(R.font.cat_childs))
    val context = LocalContext.current
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    var registration: ListenerRegistration? = null

    DisposableEffect(userId) {
        if (userId != null) {
            registration = db.collection("users")
                .document(userId)
                .collection("libraries")
                .addSnapshotListener { snapshots, error ->
                    libraries.clear()
                    if (error != null) {
                        Toast.makeText(context, "Hiba: ${error.message}", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    snapshots?.forEach { doc ->
                        val library = doc.toObject(Library::class.java).copy(id = doc.id)
                        libraries.add(library)
                    }
                }
        }
        onDispose {
            registration?.remove()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.kezdo_oldal),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 32.dp, end = 32.dp, top = 80.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "📚 Könyvtáraim",
                    fontFamily = customFont,
                    fontSize = 32.sp,
                    color = Color(0xFFDFB148),
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            items(libraries) { library ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF3C2C1F))
                ) {
                    Text(
                        text = library.name,
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFFDFB148),
                        fontFamily = customFont,
                        fontSize = 20.sp
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C1B00))
                ) {
                    Text("Vissza", fontFamily = customFont, color = Color(0xFFDFB148))
                }
            }
        }
    }
}
