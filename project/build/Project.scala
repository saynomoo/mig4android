import _root_.sbt.Path
import sbt._
import java.io.File

class Project(info: ProjectInfo) extends ParentProject(info){

  lazy val mig4android = project("mig4android", "mig4android", new Mig4AndroidProject(_))
  lazy val examples = project("examples", "examples", new ExamplesProject(_), mig4android)

  class Mig4AndroidProject(info: ProjectInfo) extends DefaultProject(info){
    override def disableCrossPaths = true
    override def unmanagedClasspath = super.unmanagedClasspath +++ (Path.fromFile(System.getenv("ANDROID_SDK_HOME")) / "platforms" / "android-7" / "android.jar")
    override def packageSrcJar= defaultJarPath("-sources.jar")
    val sourceArtifact = Artifact.sources(artifactID)
    override def packageToPublishActions = super.packageToPublishActions ++ Seq(packageSrc)
    lazy val publishTo = Resolver.file("GitHub", new File((Path.userHome / "saynomoo.github.com" / "maven").toString))
  }

  class ExamplesProject(info: ProjectInfo) extends AndroidProject(info){
    override def disableCrossPaths = true
    def androidPlatformName = "android-7"
  }
}

