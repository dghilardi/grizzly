plugins {
    kotlin("jvm") version ("1.2.30")
    id("java-gradle-plugin")
    id("maven-publish")
}



dependencies {

    implementation(gradleApi())
    implementation(localGroovy())

}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compile("org.springframework.boot:spring-boot-gradle-plugin:2.1.4.RELEASE")
    compile("io.spring.gradle:dependency-management-plugin:1.0.7.RELEASE")

    testImplementation("org.assertj:assertj-core:3.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.1.0")
}

(tasks.findByName("test") as Test).useJUnitPlatform()

group = "org.d71"
version = "0.1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        create("grizzly") {
            id = "org.d71.grizzly"
            implementationClass = "org.d71.grizzly.plugin.GrizzlyPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("../../consuming/maven-repo")
        }
    }
}
