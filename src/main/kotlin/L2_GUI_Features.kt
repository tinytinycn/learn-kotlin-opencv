import org.opencv.core.*
import org.opencv.core.Core.flip
import org.opencv.highgui.HighGui.*
import org.opencv.imgcodecs.Imgcodecs.imread
import org.opencv.imgcodecs.Imgcodecs.imwrite
import org.opencv.imgproc.Imgproc.*
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.VideoWriter
import java.awt.Color.gray
import kotlin.system.exitProcess


fun load_display_save_img(originImgName: String) {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    val img: Mat = imread(resourcesPath + "not_found.jpeg")
    println(img)

    val img2: Mat = imread(resourcesPath + originImgName)
    println(img2)

    imshow("展示窗口", img2)
    val k = waitKey(0)
    println("> $k")

    if (k == 83) {
        println("您按下了 's' 键")
        imwrite(testResultOutPath + "save_tiny_logo.png", img2)
    }

    // 需要手动关闭展示窗口
}

fun capture_video_from_camera() {
    val videoCapture = VideoCapture(0)
    if (!videoCapture.isOpened) {
        println("摄像头未打开")
        exitProcess(-1)
    }
    while (true) {
        val frame = Mat()
        val isRead = videoCapture.read(frame)
        if (!isRead) {
            println("无法接收到视频frame数据, 即将退出")
            break
        }
        val gray = Mat()
        cvtColor(frame, gray, COLOR_BGR2GRAY)
        imshow("frame", gray)
        val k = waitKey(25)
        println("k: $k")
        if (k == 27) {
            break
        }
    }
    videoCapture.release()
    destroyAllWindows() // 似乎没用起作用
}

fun play_video_from_file() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    val videoCapture = VideoCapture(resourcesPath + "test.mov")
    while (videoCapture.isOpened) {
        val frame = Mat()
        val isRead = videoCapture.read(frame)
        if (!isRead) {
            println("无法接收到视频frame数据, 即将退出")
            break
        }
        val gray = Mat()
        cvtColor(frame, gray, COLOR_BGR2GRAY)
        imshow("frame", gray)
        val k = waitKey(25)
        println("k: $k")
        if (k == 27) {
            break
        }
    }
    videoCapture.release()
    destroyAllWindows()
    waitKey(1) // 似乎可以通过此方式，强制退出
}

fun save_a_video() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    val videoCapture = VideoCapture(0)

    val fourcc = VideoWriter.fourcc("MP4V"[0], "MP4V"[1], "MP4V"[2], "MP4V"[3])
    val out = VideoWriter(testResultOutPath + "/save_test_video.mov", fourcc, 20.0, Size(1280.0, 720.0))
    while (videoCapture.isOpened) {
        val frame = Mat()
        val isRead = videoCapture.read(frame)
        if (!isRead) {
            println("无法接收到视频frame数据, 即将退出")
            break
        }
        println("frame: ${frame.width()}, ${frame.height()}, ${frame.type()}")
        // write the flip frame
        flip(frame, frame, 0)
        // To write an image frame (note that the imgFrame must be the same size as capSize above or updates will fail)
        // 注意: 捕获画面大小必须和写入画面大小一样，否则会出现写入不成功的情况!!!
        out.write(frame)

        imshow("frame", frame)
        if (waitKey(20) == 27) {
            break
        }
    }
    videoCapture.release()
    out.release()
    destroyAllWindows()
    waitKey(1) // 似乎可以通过此方式，强制退出
}

fun draw_functions() {
    // 创建显示图片，矩阵类型为CvType.CV_8UC3,RGB颜色为全红色
    val img = Mat(512, 512, CvType.CV_8UC3)
    line(img, Point(0.0, 0.0), Point(511.0, 511.0), Scalar(255.0, 0.0, 0.0), 5)
    println("img: $img")

    rectangle(img, Point(384.0, 0.0), Point(510.0, 128.0), Scalar(0.0, 255.0, 0.0), 3)

    circle(img, Point(447.0, 63.0), 63, Scalar(0.0, 0.0, 255.0), -1)

    ellipse(img, Point(256.0, 256.0), Size(100.0, 50.0), 0.0, 0.0, 180.0, Scalar(255.0, 0.0, 0.0), -1)

//    val pts = mk.ndarray(mk[mk[10, 5], mk[20, 30], mk[70, 20], mk[50, 10]])
//    val intD3NDArray = pts.reshape(-1, 1, 2)

//    val points = listOf(Point(10.0, 5.0), Point(20.0, 30.0), Point(70.0, 20.0), Point(50.0, 10.0))
    val matOfPoints = mutableListOf<MatOfPoint>()
    matOfPoints.add(MatOfPoint(Point(10.0, 5.0), Point(20.0, 30.0), Point(70.0, 20.0), Point(50.0, 10.0)))
    polylines(img, matOfPoints, true, Scalar(0.0, 255.0, 255.0))


    putText(img, "opencv", Point(10.0, 500.0), FONT_HERSHEY_SIMPLEX, 4.0, Scalar(255.0, 255.0, 255.0), 2, LINE_AA)

    imshow("draw", img)
    waitKey(0)
    destroyAllWindows()
    waitKey(1)
}

fun main() {
    println("载入 native library: ${Core.NATIVE_LIBRARY_NAME}")
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    println("使用 OpenCV 版本: v${Core.VERSION}")

    // 1. Learn to load an image, display it, and save it back
//    load_display_save_img("tiny-logo.jpeg")

    // 2. Learn to play videos, capture videos from a camera, and write videos
//    capture_video_from_camera()
//    play_video_from_file()
//    save_a_video()

    // 3. Learn to draw lines, rectangles, ellipses, circles, etc with OpenCV
    draw_functions()

}