resolvers ++= Seq(
  "Web plugin repo" at "http://siasia.github.com/maven2",
  Resolver.url("Typesafe repository", new java.net.URL("http://typesafe.artifactoryonline.com/typesafe/ivy-releases/"))(Resolver.defaultIvyPatterns)
)

//Following means libraryDependencies += "com.github.siasia" %% "xsbt-web-plugin" % "0.1.1-<sbt version>""
libraryDependencies ++= Seq(
    "com.github.siasia" %% "xsbt-web-plugin" % ("0.1.1-"+"0.10.1"),
    "com.github.siasia" %% "xsbt-proguard-plugin" % "0.10.1",
    "com.eed3si9n" % "sbt-assembly_2.8.1" % "sbt0.10.1_0.5"
    )
