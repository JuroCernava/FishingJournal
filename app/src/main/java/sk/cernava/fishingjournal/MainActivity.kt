package sk.cernava.fishingjournal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import sk.cernava.fishingjournal.ui.theme.FishingJournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FishingJournalTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Log.d("MAINACTIVITY_TAG", "message")
                    JournalApp()
                }
            }
        }
    }

}