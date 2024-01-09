plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("io.javalin:javalin:5.4.2")
    implementation("io.javalin:javalin-rendering:5.4.2")
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation ("org.thymeleaf:thymeleaf:3.1.1.RELEASE")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")

    // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt
    implementation ("io.jsonwebtoken:jjwt:0.9.1")
    implementation ("javax.xml.bind:jaxb-api:2.3.1")


}

tasks.test {
    useJUnitPlatform()
}