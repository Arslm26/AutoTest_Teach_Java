

plugins {
    id("java")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Test>("runAllTests") {
    group = "custom"
    description = "Запускаются все тесты"
    useJUnitPlatform()
    finalizedBy("printTestEndMessage")
}


tasks.register<Test>("runAssertionTests") {
    group = "custom"
    description = "Запускает тесты с тегом 'task2'"

    useJUnitPlatform {
        // Используем прямое присваивание множества вместо .add()
        includeTags = setOf("task2")
    }

    finalizedBy("printTestEndMessage")
}

tasks.register("printTestEndMessage") {
    group = "custom"
    description = "Завершение тестов"
    doLast {
        println("Test run is over")
    }

}