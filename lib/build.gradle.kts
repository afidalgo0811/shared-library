/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.3/userguide/building_java_projects.html in the Gradle documentation.
 */
import com.diffplug.gradle.spotless.SpotlessExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
  id("org.jetbrains.kotlin.jvm") version "1.9.0"
  id("com.diffplug.spotless") version "6.19.0"
  id("com.glovoapp.semantic-versioning") version "1.1.10"
  // Apply the java-library plugin for API and implementation separation.
  `java-library`
  `maven-publish`
}

// reading from gradle.properties file vs getting the value from system env in the pipeline
val spaceUsername: String? by project
val spacePassword: String? by project
val userName: String? = System.getenv("SPACE_USERNAME")
val passWord: String? = System.getenv("SPACE_PASSWORD")
val usr = userName ?: spaceUsername // checks env first
val psw = passWord ?: spacePassword // checks env first
val urlArtifactRepository = ext["jetbrains.url"].toString()

repositories {
  // Use Maven Central for resolving dependencies.
  mavenCentral()
  maven {
    url = uri(urlArtifactRepository)
    authentication { create<BasicAuthentication>("basic") }
    credentials {
      username = usr
      password = psw
    }
  }
}

dependencies {
  // Use the Kotlin JUnit 5 integration.
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

  // Use the JUnit 5 integration.
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")

  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  // This dependency is exported to consumers, that is to say found on their compile classpath.
  api("org.apache.commons:commons-math3:3.6.1")

  // This dependency is used internally, and not exposed to consumers on their own compile
  // classpath.
  implementation("com.google.guava:guava:32.1.1-jre")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.named<Test>("test") {
  // Use JUnit Platform for unit tests.
  useJUnitPlatform()
}

configure<SpotlessExtension> {
  kotlin {
    // by default the target is every '.kt' and '.kts` file in the java sourcesets
    ktfmt() // has its own section below
  }
  kotlinGradle {
    target("*.gradle.kts")
    ktfmt()
  }
}

tasks.jar {
  manifest {
    attributes(
        mapOf("Implementation-Title" to project.name, "Implementation-Version" to project.version))
  }
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      groupId = "com.afidalgo"
      artifactId = "sample"
      version = "${version}-SNAPSHOT"
      from(components["java"])
    }
  }
  repositories {
    maven {
      url = uri(urlArtifactRepository)
      credentials {
        username = usr
        password = psw
        println("usr $usr")
      }
    }
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "17"
  }
}

tasks.create("printVersion") {
  doLast {
    println("The project current version is ${project.semanticVersion.version.get()}")
    println("url $urlArtifactRepository")
  }
}
