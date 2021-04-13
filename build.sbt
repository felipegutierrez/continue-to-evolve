name := "continue-to-evolve"

version := "0.1"

scalaVersion := "2.12.7"

val scalaBinVersion = "2.12"
val scalaTestVersion = "3.2.0"
val scalaLoggingVersion = "3.9.2"
val junitVersion = "4.13"
val junitJupiter = "5.7.0"
val junitPlatform = "1.7.0"
val junitVintage = "5.7.0"
val scalaTestPlusVersion = "3.2.3.0"
val logbackVersion = "1.2.3"
val lombokVersion = "1.16.16"
lazy val mockitoAllVersion = "1.10.19"
lazy val mockitoScalaVersion = "1.16.5"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  // Scala test
  "org.scalatest" %% "scalatest" % scalaTestVersion,

  // junit + scala test
  "junit" % "junit" % junitVersion % Test,
  "org.scalatestplus" %% "junit-4-13" % scalaTestPlusVersion % "test",
  "org.junit.platform" % "junit-platform-launcher" % junitPlatform,
  "org.junit.jupiter" % "junit-jupiter-engine" % junitJupiter,
  "org.junit.jupiter" % "junit-jupiter-params" % junitJupiter % Test,
  "org.junit.vintage" % "junit-vintage-engine" % junitVintage % Test,

  // Mockito
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.mockito" %% "mockito-scala" % mockitoScalaVersion,
  "org.mockito" % "mockito-core" % "3.9.0" % Test,
  "org.mockito" % "mockito-junit-jupiter" % "3.9.0" % Test,

  // Lombok to generate boilerplate code for Java
  "org.projectlombok" % "lombok" % lombokVersion % Compile,

  "org.apache.commons" % "commons-lang3" % "3.12.0",

  // logging
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
)
