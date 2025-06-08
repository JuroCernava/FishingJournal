package sk.cernava.fishingjournal

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import sk.cernava.fishingjournal.ui.navigation.JournalNavHost


@Composable
fun JournalApp(navController: NavHostController = rememberNavController()) {
    JournalNavHost(navController = navController)
}
