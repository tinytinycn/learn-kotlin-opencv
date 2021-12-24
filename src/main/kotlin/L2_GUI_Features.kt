import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.highgui.HighGui.*
import org.opencv.imgcodecs.Imgcodecs.imread
import org.opencv.imgcodecs.Imgcodecs.imwrite

fun main() {
    println("载入 native library: ${Core.NATIVE_LIBRARY_NAME}")
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    println("使用 OpenCV 版本: v${Core.VERSION}")

    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("图片资源路径: $resourcesPath")

    val img: Mat = imread(resourcesPath + "not_found.jpeg")
    println(img)

    val img2: Mat = imread(resourcesPath + "tiny-logo.jpeg")
    println(img2)

    imshow("展示窗口", img2)
    val k = waitKey(0)
    println("> $k")

    if (k == 83) {
        println("您按下了 's' 键")
        imwrite(testResultOutPath + "save_tiny_logo.png", img2)
    }
}