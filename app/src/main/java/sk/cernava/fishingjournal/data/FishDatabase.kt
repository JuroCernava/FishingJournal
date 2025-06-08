package sk.cernava.fishingjournal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FishRecord::class], version = 1, exportSchema = false)
abstract class FishDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
    companion object {
        @Volatile
        private var Instance: FishDatabase? = null

        fun getDatabase(context: Context): FishDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FishDatabase::class.java, "fishRecords_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}