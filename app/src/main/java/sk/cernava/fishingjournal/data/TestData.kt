package sk.cernava.fishingjournal.data

data class TestData(val number: Int = 3)
{

    val testRecords : List<FishRecord>  = listOf(FishRecord(0,"potocak", FishSpecies.Brownie, Fishery.Kysuca, 32.0, 0.7, 48.1, 19.2),
        FishRecord(1,"lipen", FishSpecies.GrayLing, Fishery.Hron2, 37.0, 0.6, 48.2, 19.4),
        FishRecord(2,"duhak", FishSpecies.Rainbow, Fishery.Rajcianka, 42.0, 1.3, 48.15, 19.18),
        FishRecord(3,"sumec", FishSpecies.CatFish, Fishery.Hricov, 91.8, 7.3, 48.15, 19.17),
        FishRecord(4,"stuka", FishSpecies.Pike, Fishery.Vah, 74.0, 4.2, 48.14, 19.18),
        FishRecord(5,"zubac", FishSpecies.Zander, Fishery.Vah, 70.8, 4.4, 48.13, 19.18),
        FishRecord(6,"jalec", FishSpecies.Chub, Fishery.Slatina, 52.0, 2.25, 48.225, 19.38),
        FishRecord(7,"kaper", FishSpecies.Carp, Fishery.Slatina, 62.3, 6.1, 48.225, 19.38),
        FishRecord(5,"zubac", FishSpecies.Zander, Fishery.Vah, 70.8, 4.4, 48.13, 19.18),
        FishRecord(6,"jalec", FishSpecies.Chub, Fishery.Slatina, 52.0, 2.25, 48.225, 19.38),
        FishRecord(7,"kaper", FishSpecies.Carp, Fishery.Slatina, 62.3, 6.1, 48.225, 19.38),)
    fun getData():List<FishRecord>
    {
        return testRecords.subList(0,number - 1)
    }
}