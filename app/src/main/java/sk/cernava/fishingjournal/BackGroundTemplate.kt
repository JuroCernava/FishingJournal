package sk.cernava.fishingjournal


import android.content.Context
import android.content.res.Configuration
import android.hardware.display.DisplayManager
import android.view.Display
import android.view.Surface
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import sk.cernava.fishingjournal.ui.navigation.ApplicationScreen
import sk.cernava.fishingjournal.ui.state.AppViewModel


@Composable
fun BackgroundScreen (
    appViewModel: AppViewModel,
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val currOrientation = LocalConfiguration.current.orientation
    val uiState by appViewModel.uiState.collectAsState()
    val currentScreen = uiState.currentScreen
    val bgRes = if (currOrientation == Configuration.ORIENTATION_PORTRAIT) {
        R.drawable.gling_bground_portrait} else { R.drawable.gling_bground_landscp}
    val bgImg = painterResource(bgRes)
    val imModif = if (currOrientation == 1) {
        Modifier.fillMaxWidth()
    } else {
        Modifier.fillMaxHeight()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp)
            .background(color = colorResource(id = R.color.bgColorGrayling)),
            contentAlignment = Alignment.Center
    ) {
        Image(
            painter = bgImg,
            contentDescription = null,
            modifier = imModif,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(vertical = 20.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuButton(
                onMenuClicked = { appViewModel.changeButtonState() },
                navController = navController
            )
            Text(
                text = stringResource(currentScreen.title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

    }
        content()
}

@Composable
fun MenuButton(
    onMenuClicked: () -> Unit,
    navController: NavHostController
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(
            onClick = {
                menuExpanded = !menuExpanded
                onMenuClicked()
            },
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
            offset = DpOffset(x = 0.dp, y = 8.dp),
            properties = PopupProperties(focusable = true),
        ) {
            DropdownMenuItem(
                text = { Text("Domov") },
                onClick = {
                    menuExpanded = false
                    navController.navigate(ApplicationScreen.Home.name) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
            DropdownMenuItem(
                text = { Text("Denník") },
                onClick = {
                    menuExpanded = false
                    navController.navigate(ApplicationScreen.Journal.name) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.highScore)) },
                onClick = {
                    menuExpanded = false
                    navController.navigate(ApplicationScreen.HighScore.name) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
            DropdownMenuItem(
                text = { Text("O aplikácii") },
                onClick = {
                    menuExpanded = false
                    navController.navigate(ApplicationScreen.About.name) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )


        }
    }
}
fun getDisplayRotation(context: Context): Int {
    val displayManager = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    val display = displayManager.getDisplay(Display.DEFAULT_DISPLAY)
    return display?.rotation ?: Surface.ROTATION_0
}