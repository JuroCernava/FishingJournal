package sk.cernava.fishingjournal.ui.navigation

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import sk.cernava.fishingjournal.BackgroundScreen
import sk.cernava.fishingjournal.R
import sk.cernava.fishingjournal.ui.AppViewModelProvider
import sk.cernava.fishingjournal.ui.record.DetailScreen
import sk.cernava.fishingjournal.ui.record.EditRecordScreen
import sk.cernava.fishingjournal.ui.record.NewRecordScreen
import sk.cernava.fishingjournal.ui.screens.AboutScreen
import sk.cernava.fishingjournal.ui.screens.HighScoreScreen
import sk.cernava.fishingjournal.ui.screens.HomeScreen
import sk.cernava.fishingjournal.ui.screens.JournalScreen
import sk.cernava.fishingjournal.ui.state.AppViewModel

enum class ApplicationScreen(@StringRes val title: Int) {
    Home(title = R.string.home),
    HighScore(title = R.string.highScore),
    About(title = R.string.about),
    Journal(title = R.string.journal),
    NewRecord(title = R.string.newRecord),
    EditRecord(title = R.string.editRecord),
    RecordDetail(title = R.string.recordDetail)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun JournalNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier)
{
    val appViewModel: AppViewModel = viewModel(factory = AppViewModelProvider.Factory)
//    val coroutineScope = rememberCoroutineScope()
//    coroutineScope.launch {
//        homeViewModel.clearDatabase()
//    }
    //var currentScreen =
    BackgroundScreen(appViewModel = appViewModel,
                     navController = navController) {
    NavHost(
        navController = navController,
        startDestination = ApplicationScreen.Home.name,
        modifier = Modifier.Companion.fillMaxSize()
    ) {
        composable(ApplicationScreen.Home.name) {
            appViewModel.screenChanged(ApplicationScreen.Home)
            HomeScreen()
        }
        composable(ApplicationScreen.About.name) {
            appViewModel.screenChanged(ApplicationScreen.About)
            AboutScreen()
        }
        composable(ApplicationScreen.HighScore.name) {
            appViewModel.screenChanged(ApplicationScreen.HighScore)
            HighScoreScreen(appViewModel)
        }
        composable(ApplicationScreen.Journal.name) {
            appViewModel.screenChanged(ApplicationScreen.Journal)
            JournalScreen(
                contentPadding = PaddingValues(10.dp),
                navigateToRecordEntry = { navController.navigate(ApplicationScreen.NewRecord.name) },
                navigateToRecordDetail = { navController.navigate(ApplicationScreen.RecordDetail.name) },
                modifier = Modifier.Companion,
                appViewModel = appViewModel
            )
        }
        composable(ApplicationScreen.NewRecord.name) {
            appViewModel.screenChanged(ApplicationScreen.NewRecord)
            NewRecordScreen(appViewModel, navController)
        }
        composable(ApplicationScreen.RecordDetail.name) {
            appViewModel.screenChanged(ApplicationScreen.RecordDetail)
            DetailScreen(
                appViewModel,
                navController)
        }
        composable(ApplicationScreen.EditRecord.name) {
            appViewModel.screenChanged(ApplicationScreen.EditRecord)
            EditRecordScreen(appViewModel, navController)
        }
    }
    }
}