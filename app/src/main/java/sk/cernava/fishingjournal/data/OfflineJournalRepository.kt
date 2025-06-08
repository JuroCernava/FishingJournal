package sk.cernava.fishingjournal.data

import kotlinx.coroutines.flow.Flow

class OfflineJournalRepository(private val journalDao: JournalDao) : JournalRepository {

    override fun getAllRecordsStream(): Flow<List<FishRecord>> = journalDao.getAllRecords()

    override fun getRecordStream(id: Int): Flow<FishRecord?> = journalDao.getRecord(id)

    override suspend fun insertRecord(record: FishRecord) =
        journalDao.insert(record)

    override suspend fun deleteRecord(record: FishRecord) =
        journalDao.delete(record)

    override suspend fun updateRecord(record: FishRecord) =
        journalDao.update(record)

    override suspend fun clearAllRecords() =
        journalDao.deleteAll()
}