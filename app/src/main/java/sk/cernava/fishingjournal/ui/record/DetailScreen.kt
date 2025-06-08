package sk.cernava.fishingjournal.ui.record

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import sk.cernava.fishingjournal.R
import sk.cernava.fishingjournal.data.FishRecord
import sk.cernava.fishingjournal.ui.navigation.ApplicationScreen
import sk.cernava.fishingjournal.ui.state.AppViewModel
import sk.cernava.fishingjournal.ui.theme.md_theme_light_background

@Composable
fun DetailScreen(
    appViewModel: AppViewModel,
    navController: NavHostController
) {
    val selectedFishRecord = appViewModel.getSelectedRecord()
    val coroutineScope = rememberCoroutineScope()
    val onRemove: (FishRecord) -> Unit = {coroutineScope.launch {
        try {
            Log.d("DEBUG", "Saving record: ${it.name}")
            appViewModel.deleteRecord(selectedFishRecord)
            navController.navigate(ApplicationScreen.Journal.name)
        } catch (e: Exception) {
            Log.e("SAVE_ERROR", "Crash in saveRecord", e)
        }
    }}
    val onBack: () -> Unit = { navController.navigate(ApplicationScreen.Journal.name) }
    val onEdit: (FishRecord) -> Unit = { navController.navigate(ApplicationScreen.EditRecord.name)}

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp).padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = md_theme_light_background.copy(0.8f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(top = 60.dp)) {
                    TextField("Meno: ${selectedFishRecord.name}")
                    TextField("Druh: ${stringResource(id = selectedFishRecord.species.fullName)}")
                    TextField("Revír: ${stringResource(id = selectedFishRecord.fishery.fullName)}")
                    TextField("Dĺžka: ${selectedFishRecord.length} cm")
                    TextField("Hmotnosť: ${selectedFishRecord.weight} kg")
                    TextField("Poloha: ${selectedFishRecord.xCoord}, ${selectedFishRecord.yCoord}", Modifier.padding(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            BottomBar(selectedRecord = selectedFishRecord, onBack, onEdit,onRemove)
        }
    }
}
@Composable
fun TextField(text: String, modifier: Modifier = Modifier)
{
    Text(text= text, fontSize = 32.sp, lineHeight = 48.sp, modifier = Modifier.padding(start = 5.dp))
    Spacer(Modifier.height(15.dp))
}
@Composable
fun BottomBar(selectedRecord: FishRecord,
              onBack: () -> Unit = {},
              onEdit: (FishRecord) -> Unit = {},
              onRemove: (FishRecord) -> Unit = {})
{
    Box(modifier = Modifier.fillMaxWidth()) {
    FloatingActionButton(
        onClick = onBack,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(40.dp),

        ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.detail_info)
        )
    }
        FloatingActionButton(
            onClick = {onEdit(selectedRecord)},
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(40.dp),

            ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit_info)
            )
        }
            FloatingActionButton(
                onClick = {onRemove(selectedRecord)},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(40.dp),

                ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_info)
                )
            }
        }
}
