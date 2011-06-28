import sbt._
import java.io.File

class Project(info: ProjectInfo) extends ParentProject(info){

  lazy val mig4android = project("mig4android")
  lazy val examples = project("examples", "Mig4Android Exapmles", new ExamplesProject(_), mig4android)

  class ExamplesProject(info: ProjectInfo) extends AndroidProject(info){
    override def dependencyPath = "jars"
    override def androidPlatformName = "android-7"

    override lazy val androidSdkPath = Path.userHome / "android" / "android-sdk-linux_86"
    lazy val c = super.compileAction
    lazy val e = super.startEmulatorAction
    lazy val d = super.startDeviceAction
  }
}

