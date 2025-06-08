package sk.cernava.fishingjournal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.cernava.fishingjournal.R

@Composable
fun AboutScreen() {
    Column(modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.background(Color.White.copy(0.8f))) {
            Text(
                text = stringResource(R.string.aboutText1),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.background(Color.White.copy(0.8f))) {
            Text(
                text = stringResource(R.string.aboutText2),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
