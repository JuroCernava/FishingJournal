package sk.cernava.fishingjournal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record: FishRecord)

    @Update
    suspend fun update(record: FishRecord)

    @Delete
    suspend fun delete(record: FishRecord)

    @Query("SELECT * from records WHERE id = :id")
    fun getRecord(id: Int): Flow<FishRecord>

    @Query("SELECT * from records ORDER BY name ASC")
    fun getAllRecords(): Flow<List<FishRecord>>

    @Query("DELETE FROM records")
    suspend fun deleteAll()
}