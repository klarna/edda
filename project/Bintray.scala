import sbt._
import sbt.Keys._
import bintray.BintrayPlugin._
import bintray.BintrayKeys._

object Bintray {

  lazy val now = System.currentTimeMillis
  lazy val isPullRequest = sys.env.getOrElse("TRAVIS_PULL_REQUEST", "false") != "false"

  private def get(k: String): String = {
    if (isPullRequest) s"dummy$k" else sys.env.getOrElse(s"bintray$k", s"missing$k")
  }

  lazy val user = get("User")
  lazy val pass = get("Key")

  lazy val settings: Seq[Def.Setting[_]] = bintraySettings ++ Seq(
    bintrayRepository := "maven",
    bintrayPackage := "edda",
    bintrayOrganization := Some("klarna"),
    bintrayReleaseOnPublish := false,
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.txt")),
    credentials += Credentials("Artifactory Realm", "oss.jfrog.org", user, pass),

    pomExtra :=
      <url>https://github.com/netflix/edda/wiki</url>
      <scm>
        <url>git@github.com:netflix/edda.git</url>
        <connection>scm:git:git@github.com:netflix/edda.git</connection>
      </scm>
      <developers>
        <developer>
          <id>brharrington</id>
          <name>Brian Harrington</name>
          <email>brharrington@netflix.com</email>
        </developer>
      </developers>
  )
}
