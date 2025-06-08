package sk.cernava.fishingjournal.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import sk.cernava.fishingjournal.JournalApplication
import sk.cernava.fishingjournal.ui.state.AppViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            AppViewModel(journalApplication().container.journalRepository)
        }
    }
}

fun CreationExtras.journalApplication(): JournalApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as JournalApplication)