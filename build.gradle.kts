buildscript {
    extra.apply {
        set("room_version", "2.6.1")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}