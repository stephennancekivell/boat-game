scalaVersion := "2.11.8"

name := "boat-game"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
	"com.softwaremill.quicklens" %% "quicklens" % "1.4.7",
	"org.specs2" %% "specs2-core" % "3.8.4" % Test
)

parallelExecution in Test := true

scalacOptions in Test ++= Seq("-Yrangepos")
