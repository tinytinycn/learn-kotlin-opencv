import org.opencv.core.*
import org.opencv.core.Core.*
import org.opencv.highgui.HighGui.*
import org.opencv.imgcodecs.Imgcodecs.IMREAD_GRAYSCALE
import org.opencv.imgcodecs.Imgcodecs.imread
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.*
import org.opencv.videoio.VideoCapture
import kotlin.system.exitProcess

/**
 * 在本节中，您将学习 OpenCV 中不同的图像处理功能。
 * In this section you will learn different image processing functions inside OpenCV.*/
fun main() {
    println("载入 native library: ${Core.NATIVE_LIBRARY_NAME}")
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    println("使用 OpenCV 版本: v${Core.VERSION}")
    println("====")

    // 4.1 改变颜色空间
//    changingColorSpaces()

    // 4.2 图像的几何变换
//    geometricTransformations()

    // 4.3 图像阈值
    imageThresholding()
}

// 在本教程中，您将学习简单阈值，自适应阈值和Otsu阈值。
fun imageThresholding() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")
    val img = imread(resourcesPath + "tiny-logo.jpeg")

    // 简单阈值
//    val res1 = Mat()
//    threshold(img, res1, 127.0, 255.0, THRESH_BINARY)
//    imshow("binary", res1)
//    waitKey()
//    val res2 = Mat()
//    threshold(img, res2, 127.0, 255.0, THRESH_BINARY_INV)
//    imshow("binary_inv", res2)
//    waitKey()
//    val res3 = Mat()
//    threshold(img, res3, 127.0, 255.0, THRESH_TRUNC)
//    imshow("trunc", res3)
//    waitKey()
//    val res4 = Mat()
//    threshold(img, res4, 127.0, 255.0, THRESH_TOZERO)
//    imshow("tozero", res4)
//    waitKey()
//    val res5 = Mat()
//    threshold(img, res5, 127.0, 255.0, THRESH_TOZERO_INV)
//    imshow("tozero_inv", res5)
//    waitKey()

    // 自适应阈值
//    val img2 = imread(resourcesPath + "panda.jpg", IMREAD_GRAYSCALE)
//    medianBlur(img2, img2, 5)
//    imshow("medianBlur", img2)
//    waitKey()
//    val res1 = Mat()
//    threshold(img2, res1, 127.0, 255.0, THRESH_BINARY)
//    val res2 = Mat()
//    adaptiveThreshold(img2, res2, 255.0, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY, 11, 2.0)
//    val res3 = Mat()
//    adaptiveThreshold(img2, res3, 255.0, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY, 11, 2.0)
//    val res4 = Mat()
//    hconcat(listOf(res1, res2, res3), res4)//矩阵合并：vconcat,hconcat
//    imshow("final", res4)
//    waitKey()

    // Otsu二值化
    val img3 = imread(resourcesPath + "sukdo.jpg", IMREAD_GRAYSCALE)
    val res1 = Mat()
    threshold(img3, res1, 127.0, 255.0, THRESH_BINARY)//全局阈值
    val res2 = Mat()
    threshold(img3, res2, 0.0, 255.0, THRESH_BINARY + THRESH_OTSU)//otsu阈值
    val res3 = Mat()
    GaussianBlur(img3, res3, Size(5.0, 5.0), 0.0)//高斯滤波后在otsu阈值
    val res4 = Mat()
    threshold(res3, res4, 0.0, 255.0, THRESH_BINARY + THRESH_OTSU)
    val res5 = Mat()
    hconcat(listOf(res1, res2, res4), res5)//矩阵合并
    imshow("final", res5)
    waitKey()

    exitProcess(0)
}

// 学习将不同的几何变换应用到图像上，如平移、旋转、仿射变换等。
fun geometricTransformations() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")
    val img = imread(resourcesPath + "tiny-logo.jpeg")

    // 缩放
//    val res = Mat()
//    resize(img, res, Size(), 2.0, 2.0, INTER_CUBIC)
//    println("res -> w:${res.width()}, -> h: ${res.height()}")
//    println("res -> cols: ${res.cols()}, -> rows: ${res.rows()}")
//    imshow("res", res)
//    waitKey()

    // 平移
//    val res2 = Mat()
//    val m = Mat(2, 3, CvType.CV_32FC1)
//    m.put(0, 0, floatArrayOf(1F, 0F, 100F))
//    m.put(1, 0, floatArrayOf(0F, 1F, 50F))
//    println("m: ${m.dump()}")
//    warpAffine(img, res2, m, Size(img.cols().toDouble(), img.rows().toDouble()))
//    imshow("res2", res2)
//    waitKey()

    // 旋转
//    val matrix2D = getRotationMatrix2D(Point((img.cols() - 1) / 2.0, (img.rows() - 1) / 2.0), 90.0, 1.0)
//    val res3 = Mat()
//    warpAffine(img, res3, matrix2D, Size(img.cols().toDouble(), img.rows().toDouble()))
//    imshow("res3", res3)
//    waitKey()

    // 仿射变换
//    val pts1 = MatOfPoint2f(Point(50.0, 50.0), Point(200.0, 50.0), Point(50.0, 200.0))
//    val pts2 = MatOfPoint2f(Point(10.0, 100.0), Point(200.0, 50.0), Point(100.0, 250.0))
//    val affineTransform = getAffineTransform(pts1, pts2)
//    val res4 = Mat()
//    warpAffine(img, res4, affineTransform, Size(img.cols().toDouble(), img.rows().toDouble()))
//    imshow("res4", res4)
//    waitKey()

    // 透视变换
    val pts1 = MatOfPoint2f(Point(56.0, 65.0), Point(368.0, 52.0), Point(28.0, 387.0), Point(389.0, 390.0))
    val pts2 = MatOfPoint2f(Point(0.0, 0.0), Point(300.0, 0.0), Point(0.0, 300.0), Point(300.0, 300.0))
    val perspectiveTransform = getPerspectiveTransform(pts1, pts2)
    val res5 = Mat()
    warpPerspective(img, res5, perspectiveTransform, Size(300.0, 300.0))
    imshow("res5", res5)
    waitKey()

    // exit
    exitProcess(0)
}

// 学习在不同颜色空间之间更改图像。另外学习跟踪视频中的彩色物体。
fun changingColorSpaces() {
    // Imgproc 有超过150种颜色空间转换， 以下两种是使用最为广泛的
    println("BGR -> GRAY: $COLOR_BGR2GRAY")
    println("BGR -> HSV: $COLOR_BGR2HSV")
    println("====")

    // 对象追踪
    val cap = VideoCapture(0)
    while (true) {
        // 读取帧
        val frame = Mat()
        cap.read(frame)
        // 转换颜色空间 BGR to HSV
        val hsv = Mat()
        cvtColor(frame, hsv, COLOR_BGR2HSV)
        // 定义 HSV 蓝色范围
        // 设置HSV的阈值使得只取蓝色
        val mask = Mat()
        inRange(hsv, Scalar(100.0, 43.0, 46.0), Scalar(124.0, 255.0, 255.0), mask)
        // 将mask和frame图像逐个像素相加
        val res = Mat()
        bitwise_and(frame, frame, res, mask)
        imshow("frame", frame)
        imshow("mask", mask)
        imshow("res", res)
        val k = waitKey(40) and 0xFF
        println("k: $k")
        if (k == 27) {
            break
        }
    }
    destroyAllWindows()
    waitKey(1)
}
