val Http4sVersion = "0.23.14"

val CirceVersion = "0.14.2"

val MunitVersion = "0.7.29"

val LogbackVersion = "1.2.11"

val MunitCatsEffectVersion = "1.0.7"

val DoobieVersion = "1.0.0-RC1"

lazy val root = (project in file("."))
  .settings(
    organization := "com.schracksolutions",
    name         := "finance",
    version      := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.8",
    libraryDependencies ++= Seq(
      "org.http4s"            %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"            %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"            %% "http4s-circe"        % Http4sVersion,
      "org.http4s"            %% "http4s-dsl"          % Http4sVersion,
      "io.circe"              %% "circe-generic"       % CirceVersion,
      "org.tpolecat"          %% "doobie-core"         % DoobieVersion,
      "org.tpolecat"          %% "doobie-postgres"     % DoobieVersion,
      "org.tpolecat"          %% "doobie-specs2"       % DoobieVersion,
      "com.github.pureconfig" %% "pureconfig"          % "0.17.1",
      "org.flywaydb"           % "flyway-core"         % "7.15.0",
      "org.scalameta"         %% "munit"               % MunitVersion           % Test,
      "org.typelevel"         %% "munit-cats-effect-3" % MunitCatsEffectVersion % Test,
      "org.tpolecat"          %% "doobie-specs2"       % DoobieVersion          % Test, // Specs2 support for typechecking statements.
      "org.tpolecat"          %% "doobie-scalatest"    % DoobieVersion          % Test, // ScalaTest support for typechecking statements.
      "ch.qos.logback"         % "logback-classic"     % LogbackVersion         % Runtime
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
    testFrameworks += new TestFramework("munit.Framework")
  )
