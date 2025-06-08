package sk.cernava.fishingjournal.ui.record

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import sk.cernava.fishingjournal.data.FishRecord
import sk.cernava.fishingjournal.ui.navigation.ApplicationScreen
import sk.cernava.fishingjournal.ui.state.AppViewModel

@Composable
fun EditRecordScreen(appViewModel: AppViewModel,
                     navController: NavHostController)
{
    val selectedRecord = appViewModel.getSelectedRecord()
    val coroutineScope = rememberCoroutineScope()
    val onSave: (FishRecord) -> Unit = {coroutineScope.launch {
    try {
        Log.d("DEBUG", "Saving record: ${it.name}")
        appViewModel.updateRecord(selectedRecord, it)
        navController.navigate(ApplicationScreen.Journal.name)
    } catch (e: Exception) {
        Log.e("SAVE_ERROR", "Crash in saveRecord", e)
    }
}}
    EditRecord( initialName = selectedRecord.name,
                initialSpecies = selectedRecord.species,
                initialFishery = selectedRecord.fishery,
                initialLength = selectedRecord.length,
                initialWeight = selectedRecord.weight,
                initialXCoord = selectedRecord.xCoord,
                initialYCoord = selectedRecord.yCoord,
                onSave = onSave)
}