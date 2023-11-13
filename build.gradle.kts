plugins {
    `maven-publish`
}



allprojects {
    val ver = extra["lib.version"] as String
    version = "$ver-SNAPSHOT"
}
