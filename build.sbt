version := "0.0.1-SNAPSHOT"
scalaVersion := "2.13.1"
libraryDependencies ++= Seq(
  "org.http4s"     %% "http4s-blaze-server" % "0.21.1",
  "org.http4s"     %% "http4s-blaze-client" % "0.21.1",
  "org.http4s"     %% "http4s-circe"        % "0.21.1",
  "org.http4s"     %% "http4s-dsl"          % "0.21.1",
  "io.circe"       %% "circe-generic"       % "0.13.0",
  "ch.qos.logback" % "logback-classic"      % "1.2.3"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-Xfatal-warnings"
)
