package sk.cernava.fishingjournal.data

import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getAllRecordsStream(): Flow<List<FishRecord>>

    fun getRecordStream(id: Int): Flow<FishRecord?>

    suspend fun insertRecord(record: FishRecord)

    suspend fun deleteRecord(record: FishRecord)

    suspend fun updateRecord(record: FishRecord)

    suspend fun clearAllRecords()
}