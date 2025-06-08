package sk.cernava.fishingjournal.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import sk.cernava.fishingjournal.R
import sk.cernava.fishingjournal.ui.record.TextField
import sk.cernava.fishingjournal.ui.state.AppViewModel
import sk.cernava.fishingjournal.ui.theme.md_theme_light_background

@Composable
fun HighScoreScreen(appViewModel: AppViewModel)
{
    appViewModel.findLargest()
    val uiState = appViewModel.uiState.collectAsState()
    val heaviest = uiState.value.heaviestFish
    val longest = uiState.value.longestFish
    LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp).padding(top = 120.dp),
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = md_theme_light_background.copy(0.8f)),
                modifier = Modifier.fillMaxWidth())
            {
                TextField("${stringResource(R.string.top_length)} ${longest.name}")
                Spacer(Modifier.height(10.dp))
                TextField("Druh: ${stringResource(longest.species.fullName)}")
                Spacer(Modifier.height(10.dp))
                TextField("Dĺžka: ${longest.length} cm")
            }
        }
        item {
            Spacer(Modifier.height(40.dp))
        }
        item {
            Card(elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = md_theme_light_background.copy(0.8f)),
                modifier = Modifier.fillMaxWidth())
            {
                TextField("${stringResource(R.string.top_weight)} ${heaviest.name}")
                TextField("Druh: ${stringResource(heaviest.species.fullName)}")
                Spacer(Modifier.height(10.dp))
                TextField("Váha: ${heaviest.weight} kg")
            }
        }
    }
}