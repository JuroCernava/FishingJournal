package sk.cernava.fishingjournal.ui.state

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import sk.cernava.fishingjournal.data.FishRecord
import sk.cernava.fishingjournal.data.FishSpecies
import sk.cernava.fishingjournal.data.Fishery
import sk.cernava.fishingjournal.data.JournalRepository
import sk.cernava.fishingjournal.ui.navigation.ApplicationScreen

class AppViewModel(private val journalRepository: JournalRepository): ViewModel() {
    val initFish: FishRecord = FishRecord(-1,"Zatial neexistuje", FishSpecies.NotPicked, Fishery.NotPicked, 0.0,0.0,0.0,0.0)
    val recordsState: StateFlow<RecordsState> =
        journalRepository.getAllRecordsStream().map { RecordsState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RecordsState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    private val _uiState = MutableStateFlow(AppStateUI(false, recordsState, ApplicationScreen.Home,
                                            longestFish = initFish,
                                            heaviestFish =  initFish,
                                            selectedRecord = initFish))
    val uiState: StateFlow<AppStateUI> = _uiState.asStateFlow()
    fun changeButtonState() {
        _uiState.update { currentState ->
            currentState.copy(menuActivated = !currentState.menuActivated)
        }
    }
    suspend fun clearDatabase()
    {
        journalRepository.clearAllRecords()
        Log.d("DATABASE CLEAR", "Cleaning records...")
    }

    suspend fun saveRecord(fishRecord: FishRecord) {
        Log.d("Saving", "${fishRecord.id}${fishRecord.name}${fishRecord.species.fullName}")
        journalRepository.insertRecord(fishRecord)
    }
    suspend fun updateRecord(oldFishRecord: FishRecord, newFishRecord: FishRecord) {
        journalRepository.deleteRecord(oldFishRecord)
        journalRepository.insertRecord(newFishRecord)
    }
    suspend fun deleteRecord(fishRecord: FishRecord) {
        journalRepository.deleteRecord(fishRecord)
    }

    fun getSelectedRecord() : FishRecord
    {
        return _uiState.value.selectedRecord
    }
    fun selectRecord(record: FishRecord) {
        _uiState.update { currentState ->
            currentState.copy(selectedRecord = record)
        }
        Log.d("SELECTED RECORD: ", "${record.name}${record.species}${record.length}")
    }
    fun screenChanged(screen: ApplicationScreen)
    {
        _uiState.update { currentState ->
            currentState.copy(currentScreen = screen)
        }
    }
    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    fun findLargest()
    {
        val initFish = FishRecord(-1,"Zatial neexistuje", FishSpecies.NotPicked, Fishery.NotPicked, 0.0,0.0,0.0,0.0)
        var longest: FishRecord = initFish
        var heaviest: FishRecord = initFish
        val fishes = _uiState.asStateFlow().value.recordsList.collectAsState().value.container
        Log.d("LIST IS OF SIZE", "SIZE:"+fishes.size)
        fishes.forEach { record ->
                longest = if (record.length > longest.length) record  else longest
                heaviest = if (record.weight > heaviest.weight) record else heaviest
        }
        _uiState.update { currentState ->
            currentState.copy(heaviestFish = heaviest, longestFish = longest)
        }
    }
    data class RecordsState(val container: List<FishRecord> = listOf())
}