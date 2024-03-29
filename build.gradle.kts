plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.10.1"
}

group = "eu.pm"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2022.1.4")
    type.set("IC") // Target IDE Platform

    plugins.set(
        listOf(
            "org.jetbrains.plugins.gradle",
            "org.jetbrains.idea.maven.model"/*"org.jetbrains.idea.maven.model"*//* Plugin Dependencies */
        )
    )
}

dependencies {
    implementation("org.apache.commons:commons-text:1.10.0")
//    implementation("org.jetbrains".?)
}

tasks {

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("221")
        untilBuild.set("IU-232.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
