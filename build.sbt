name := "continue-to-evolve"

version := "0.1"

scalaVersion := "2.12.7"

val scalaBinVersion = "2.12"
val scalaTestVersion = "3.2.0"

libraryDependencies ++= Seq(
  // Scala test
  "org.scalatest" %% "scalatest" % scalaTestVersion,

  // junit + scala test
  "junit" % "junit" % "4.13" % Test,
  "org.scalatestplus" %% "junit-4-13" % "3.2.3.0" % "test",
)

