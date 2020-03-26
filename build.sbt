version := "0.0.1-SNAPSHOT"
scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server" % "0.21.1",
  "org.http4s"     %% "http4s-blaze-client" % "0.21.1",
  "org.http4s"     %% "http4s-circe"        % "0.21.1",
  "org.http4s"     %% "http4s-dsl"          % "0.21.1",
  "io.circe"       %% "circe-generic"       % "0.13.0",
  "ch.qos.logback" % "logback-classic"      % "1.2.3",
  "io.circe"       %% "circe-parser"        % "0.13.0",
  "co.fs2" %% "fs2-io" % "2.2.1",
  "co.fs2" %% "fs2-core" % "2.2.1",
  "co.fs2" %% "fs2-reactive-streams" % "2.2.1",
  "io.circe" %% "circe-fs2" % "0.13.0"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-Xfatal-warnings"
)
