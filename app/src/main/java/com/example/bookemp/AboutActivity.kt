package com.example.bookemp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import com.example.bookemp.ui.theme.BookempTheme
import com.example.bookemp.R

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BookempTheme {
                val customFont = FontFamily(Font(R.font.cat_childs))

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
                            .padding(32.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = "Névjegy",
                            fontSize = 36.sp,
                            fontFamily = customFont,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFDFB148),
                            modifier = Modifier.padding(bottom = 24.dp)
                        )

                        Text(
                            text = "BookEmp – Otthoni könyvrendszerező\n\nKészült mobilalkalmazás-fejlesztés tantárgy keretében.\n\nVerzió: 1.0\nFejlesztő: Jónás Mónika",
                            color = Color.White,
                            fontSize = 18.sp,
                            lineHeight = 28.sp,
                            fontFamily = customFont
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = { finish() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C1B00))
                        ) {
                            Text("⬅️ Vissza", color = Color(0xFFDFB148), fontFamily = customFont, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}
