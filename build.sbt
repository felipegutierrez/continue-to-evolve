name := "continue-to-evolve"

version := "0.1"

scalaVersion := "2.12.7"

val scalaBinVersion = "2.12"
val scalaTestVersion = "3.2.0"
val scalaLoggingVersion = "3.9.2"
val junitVersion = "4.13"
val scalaTestPlusVersion = "3.2.3.0"
val logbackVersion = "1.2.3"
lazy val mockitoAllVersion = "1.10.19"
lazy val mockitoScalaVersion = "1.16.5"

resolvers += Resolver.jcenterRepo

// only Scala source directory is available for sbt because Lombok does not work with sbt+Scala
Compile / unmanagedSourceDirectories := (Compile / scalaSource).value :: Nil
Test / unmanagedSourceDirectories := (Test / scalaSource).value :: Nil

libraryDependencies ++= Seq(
  // Scala test
  "org.scalatest" %% "scalatest" % scalaTestVersion,

  // junit + scala test
  "junit" % "junit" % junitVersion % Test,
  "org.scalatestplus" %% "junit-4-13" % scalaTestPlusVersion % "test",

  // Mockito
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.mockito" %% "mockito-scala" % mockitoScalaVersion,

  // logging
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
)
