package sk.cernava.fishingjournal.ui.record

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import sk.cernava.fishingjournal.data.FishRecord
import sk.cernava.fishingjournal.data.FishSpecies
import sk.cernava.fishingjournal.data.Fishery
import sk.cernava.fishingjournal.ui.navigation.ApplicationScreen
import sk.cernava.fishingjournal.ui.state.AppViewModel

@Composable
fun NewRecordScreen(appViewModel: AppViewModel, navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val onSave: (FishRecord) -> Unit = {coroutineScope.launch {
        try {
            Log.d("DEBUG", "Saving record: ${it.name}")
            appViewModel.saveRecord(it)
            navController.navigate(ApplicationScreen.Journal.name)
        } catch (e: Exception) {
            Log.e("SAVE_ERROR", "Crash in saveRecord", e)
        }
    }}
    EditRecord(onSave = onSave)


}
@Composable
fun RecordField(valueField: ()-> String,
                label:String,
                onValueChange: (String) -> Unit,
                keyboardType: KeyboardType = KeyboardType.Text)
{
    OutlinedTextField(
        value = valueField(),
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.background(Color.White.copy(0.8f)).padding(5.dp),

    )
}
@Composable
fun EditRecord(
    initialName: String = "",
    initialSpecies: FishSpecies = FishSpecies.NotPicked,
    initialFishery: Fishery = Fishery.NotPicked,
    initialLength: Double = 0.0,
    initialWeight: Double = 0.0,
    initialXCoord: Double = 0.0,
    initialYCoord: Double = 0.0,
    onSave: (FishRecord) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var species by remember { mutableStateOf(initialSpecies) }
    var fishery by remember { mutableStateOf(initialFishery) }
    var length by remember { mutableStateOf(initialLength.toString()) }
    var weight by remember { mutableStateOf(initialWeight.toString()) }
    var xCoord by remember { mutableStateOf(initialXCoord.toString()) }
    var yCoord by remember { mutableStateOf(initialYCoord.toString()) }

    val isFormValid = name.isNotBlank()
            && species != FishSpecies.NotPicked
            && fishery != Fishery.NotPicked
            && length.toDoubleOrNull() != null
            && weight.toDoubleOrNull() != null
            && xCoord.toDoubleOrNull() != null
            && yCoord.toDoubleOrNull() != null

    val speciesOptions = FishSpecies.entries
    val fisheryOptions = Fishery.entries

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecordField({name},"Name",{ name = it})

        DropdownField(
            label = "Druh ryby",
            options = speciesOptions,
            selectedOption = species,
            onOptionSelected = { species = it },
            displayLabel = { stringResource(it.fullName) },
        )

        DropdownField(
            label = "Revir",
            options = fisheryOptions,
            selectedOption = fishery,
            onOptionSelected = { fishery = it },
            displayLabel = { stringResource(it.fullName) },
        )

        RecordField({ length }, "Dlzka(cm)", { length = it }, KeyboardType.Number)
        RecordField({ weight }, "Vaha(kg)", { weight = it }, KeyboardType.Number)
        RecordField({ xCoord }, "x suradnica", { xCoord = it }, KeyboardType.Number)
        RecordField({ yCoord }, "y suradnica", { yCoord = it }, KeyboardType.Number)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val record = FishRecord(
                    name = name,
                    species = species,
                    fishery = fishery,
                    length = length.toDouble(),
                    weight = weight.toDouble(),
                    xCoord = xCoord.toDouble(),
                    yCoord = yCoord.toDouble()
                )
                onSave(record)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2323FF),
                contentColor = Color.White
            ),
            modifier = Modifier.height(60.dp),
            enabled = isFormValid
        ) {
            Text(text = "Uložiť", fontSize = 24.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropdownField(
    label: String,
    options: List<T>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    displayLabel: @Composable (T) -> String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,

    ) {
        OutlinedTextField(
            value = selectedOption?.let { displayLabel(it) } ?: "",
            onValueChange = {  },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor().background(Color.White.copy(0.8f)).padding(5.dp),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(displayLabel(option)) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}