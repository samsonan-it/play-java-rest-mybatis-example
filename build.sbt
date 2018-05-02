name := """play-java-rest-mybatis-example"""
organization := "com.samsonan"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += javaJdbc
libraryDependencies += "org.mybatis" % "mybatis" % "3.4.6"
libraryDependencies += "org.mybatis" % "mybatis-guice" % "3.10"
libraryDependencies += "com.h2database" % "h2" % "1.4.197"
libraryDependencies += "org.flywaydb" %% "flyway-play" % "5.0.0"