import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.highgui.HighGui.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc.GaussianBlur

fun main(args: Array<String>) {
    // 一定要载入静态库 "/usr/local/Cellar/opencv/4.5.4_1/share/java/opencv4/libopencv_java454.dylib"
    println(Core.NATIVE_LIBRARY_NAME)
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    println("Using OpenCV v${Core.VERSION}")

    println("start read img")
    val imgA: Mat = Imgcodecs.imread("/Users/tinytinycn/IdeaProjects/learn-kotlin-opencv/src/main/resources/tiny-logo.jpeg")
    println("process img :blur...")
    GaussianBlur(imgA, imgA, Size(115.0, 115.0), 0.0)
    println("process img :end!")

    println("sub thread start: ui")

    imshow("test", imgA)
    waitKey(0)
    destroyWindow("test")
    println("main thead end!!!")
}

