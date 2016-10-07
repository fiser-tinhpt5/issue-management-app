name := """issue-management-app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalikejdbc" %% "scalikejdbc"       % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc"                  % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "2.4.2",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.flywaydb" %% "flyway-play" % "3.0.1",
  "mysql" % "mysql-connector-java" % "5.1.38"
)

