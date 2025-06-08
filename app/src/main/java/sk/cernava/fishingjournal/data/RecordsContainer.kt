package sk.cernava.fishingjournal.data

import android.content.Context

interface RecordsContainer
{
    val journalRepository : JournalRepository
}

class RecordDataContainer(private val context : Context) : RecordsContainer
{
    override val journalRepository: JournalRepository by lazy()
    {
        OfflineJournalRepository(FishDatabase.getDatabase(context).journalDao())
    }
}