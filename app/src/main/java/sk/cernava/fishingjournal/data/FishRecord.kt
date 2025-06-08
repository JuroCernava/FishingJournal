package sk.cernava.fishingjournal.data

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.cernava.fishingjournal.R


enum class FishSpecies(@StringRes val fullName: Int) {
    Brownie(fullName = R.string.brownie),
    Rainbow(fullName = R.string.rainbow),
    Carp(fullName = R.string.carp),
    Chub(fullName = R.string.chub),
    GrayLing(fullName = R.string.grayling),
    CatFish(fullName = R.string.catfish),
    Pike(fullName = R.string.pike),
    Zander(fullName = R.string.zander),
    Perch(fullName = R.string.perch),
    NotPicked(fullName = R.string.notPicked);
    companion object {
        fun fromName(name: String): FishSpecies =
            entries.find { it.name.equals(name, ignoreCase = true) } ?: NotPicked
    }
}
enum class Fishery(@StringRes val fullName: Int) {
    Hron1(fullName = R.string.hron1),
    Hron2(fullName = R.string.hron2),
    Slatina(fullName = R.string.slatina),
    Rajcianka(fullName = R.string.rajcanka),
    Kysuca(fullName = R.string.kysuca),
    Hricov(fullName = R.string.hricov),
    Vah(fullName = R.string.vah1),
    NotPicked(fullName = R.string.noFishery);
    companion object {
        fun fromName(name: String): Fishery =
            Fishery.entries
                .find { it.name.equals(name, ignoreCase = true) } ?: NotPicked
    }
}

@Entity(tableName = "records")
data class FishRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val species: FishSpecies,
    val fishery: Fishery,
    val length: Double,
    val weight: Double,
    val xCoord: Double,
    val yCoord: Double,
)
