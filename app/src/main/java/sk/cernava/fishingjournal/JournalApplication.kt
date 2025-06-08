package sk.cernava.fishingjournal

import android.app.Application
import sk.cernava.fishingjournal.data.RecordDataContainer
import sk.cernava.fishingjournal.data.RecordsContainer

class JournalApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: RecordsContainer

    override fun onCreate() {
        super.onCreate()
        container = RecordDataContainer(this)
    }
}