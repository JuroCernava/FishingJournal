package sk.cernava.fishingjournal.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.cernava.fishingjournal.R
import sk.cernava.fishingjournal.data.FishRecord
import sk.cernava.fishingjournal.ui.state.AppViewModel
import sk.cernava.fishingjournal.ui.theme.md_theme_light_background

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun JournalScreen(
    contentPadding: PaddingValues,
    navigateToRecordEntry: () -> Unit,
    navigateToRecordDetail: () -> Unit,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier

) {
    val recordsList = appViewModel.uiState.collectAsState().value.recordsList.collectAsState().value.container
    val onRecordClick: (FishRecord) ->Unit =  { appViewModel.selectRecord(it) }
    Box(modifier = modifier.padding(20.dp)) {
        if (recordsList.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxWidth().padding(contentPadding).padding(top = 120.dp)
            ) {
                Text(
                    text = stringResource(R.string.emptyList),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.background(Color.White.copy(0.8f))
                )
            }
        } else {
            RecordsList(
                recordsList = recordsList,
                appViewModel = appViewModel,
                onRecordClick = onRecordClick,
                contentPadding = contentPadding,
                modifier = modifier
            )
        }
        FloatingActionButton(
            onClick = navigateToRecordEntry,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(40.dp),

            ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_info)
            )
        }
        FloatingActionButton(
            onClick = navigateToRecordDetail,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(40.dp),

            ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.detail_info)
            )
        }
    }
}
@Composable
fun RecordsList(recordsList: List<FishRecord>,
                appViewModel: AppViewModel,
                onRecordClick: (FishRecord) -> Unit,
                contentPadding: PaddingValues,
                modifier: Modifier = Modifier)
{
    val selectedRecord = appViewModel.uiState.collectAsState().value.selectedRecord
    Box(modifier = Modifier.fillMaxSize().padding(vertical = 90.dp)) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,

        ) {
            items(items = recordsList, key = { it.id }) { record ->
                JournalRecord(record = record,
                    highlighted = record.id == selectedRecord.id,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                        .clickable { onRecordClick(record) })
            }

        }
    }
}
@Composable
private fun JournalRecord(
    record: FishRecord,
    highlighted: Boolean = false,
    modifier: Modifier = Modifier
) {
    val bgColor = if (highlighted) Color(0xFFE3F2FD) else md_theme_light_background.copy(0.8f)
    val borderColor = if (highlighted) Color(0xFF448AFF) else Color.Transparent
    Card(
        modifier = modifier.border(2.dp, borderColor, MaterialTheme.shapes.medium),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = record.name,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Text(
                text = stringResource(record.species.fullName),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(record.fishery.fullName),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
