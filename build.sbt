val dottyVersion = "0.6.0-RC1"

lazy val root = (project in file(".")).
  settings(
    name := "dottyhlist",
    version := "0.1.0",
    scalaVersion := dottyVersion
  )
