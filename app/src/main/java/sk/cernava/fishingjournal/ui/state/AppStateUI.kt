package sk.cernava.fishingjournal.ui.state

import kotlinx.coroutines.flow.StateFlow
import sk.cernava.fishingjournal.data.FishRecord
import sk.cernava.fishingjournal.data.FishSpecies
import sk.cernava.fishingjournal.data.Fishery
import sk.cernava.fishingjournal.ui.navigation.ApplicationScreen
import sk.cernava.fishingjournal.ui.state.AppViewModel.RecordsState

data class AppStateUI(
    val menuActivated: Boolean = false,
    val recordsList: StateFlow<RecordsState>,
    val currentScreen: ApplicationScreen,
    val selectedRecord: FishRecord = FishRecord(
        -1,
        "",
        FishSpecies.NotPicked,
        Fishery.NotPicked,
        0.0,
        0.0,
        0.0,
        0.0
    ),
    val longestFish: FishRecord,
    val heaviestFish: FishRecord
)