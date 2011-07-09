import _root_.sbt.Path
import sbt._
import java.io.File

class Project(info: ProjectInfo) extends ParentProject(info){

  lazy val mig4android = project("mig4android", "Mig4Android", new Mig4AndroidProject(_))
  lazy val examples = project("examples", "Mig4Android Examples", new ExamplesProject(_), mig4android)

  class Mig4AndroidProject(info: ProjectInfo) extends DefaultProject(info){
    override def disableCrossPaths = true
    override def unmanagedClasspath = super.unmanagedClasspath +++ (Path.fromFile(System.getenv("ANDROID_SDK_HOME")) / "platforms" / "android-7" / "android.jar")
  }

  class ExamplesProject(info: ProjectInfo) extends AndroidProject(info){
    override def disableCrossPaths = true
    def androidPlatformName = "android-7"
  }
}

