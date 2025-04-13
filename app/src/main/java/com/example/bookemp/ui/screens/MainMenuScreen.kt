package com.example.bookemp.ui.screens

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookemp.R
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainMenuScreen(
    onOpenLibrary: () -> Unit = {},
    onCreateLibrary: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val customFont = FontFamily(Font(R.font.cat_childs))
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    // 💫 ANIMÁLT ARANYSÁRGA szín csillogás effektus
    val colorList = listOf(
        Color(0xFFFFD700),
        Color(0xFFFFC107),
        Color(0xFFFFB300),
        Color(0xFFFFD700)
    )
    var colorIndex by remember { mutableStateOf(0) }
    val animatedColor by animateColorAsState(targetValue = colorList[colorIndex], label = "")
    val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    // Időzített animáció ciklus
    LaunchedEffect(Unit) {
        while (true) {
            delay(600)
            colorIndex = (colorIndex + 1) % colorList.size
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.kezdo_oldal),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(start = 32.dp, end = 32.dp, top = 48.dp, bottom = navBarPadding + 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Text(
                    text = "BookEmp",
                    fontSize = 62.sp,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F1503),
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .offset(x = 2.dp, y = 2.dp)
                )
                Text(
                    text = "BookEmp",
                    fontSize = 60.sp,
                    fontFamily = customFont,
                    fontWeight = FontWeight.Bold,
                    color = animatedColor,
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = onOpenLibrary,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C1B00)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("📂 Könyvtáram", color = Color(0xFFDFB148), fontFamily = customFont, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onCreateLibrary,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C1B00)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("➕ Új könyvtár létrehozása", color = Color(0xFFDFB148), fontFamily = customFont, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C1B00)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("🚪 Kilépés", color = Color(0xFFDFB148), fontFamily = customFont, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))

                       Button(
                onClick = {

                    context.startActivity(android.content.Intent(context, com.example.bookemp.AboutActivity::class.java))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C1B00)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("ℹ️ Névjegy", color = Color(0xFFDFB148), fontFamily = customFont, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
