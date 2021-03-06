import org.opencv.core.*
import org.opencv.core.Core.*
import org.opencv.core.CvType.CV_8UC1
import org.opencv.highgui.HighGui.*
import org.opencv.imgcodecs.Imgcodecs.*
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
//    imageThresholding()

    // 4.4 图像平滑
//    imageSmoothing()

    // 4.5 形态转换
//    morphologicalTransformations()

    // 4.6 图像梯度
//    imageGradients()

    // 4.7 Canny边缘检测
//    cannyEdgeDetection()

    // 4.8 图像金字塔
//    imagePyramids()

    // 4.9.1 图像轮廓
//    imgContours()

    // 4.9.2 轮廓特征
    contourFeatures()
}

// 学习找到轮廓的不同特征，如面积、周长、边界矩形等。
fun contourFeatures() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    // 1 特征矩
    val img = imread(resourcesPath + "L4/contour.png", IMREAD_GRAYSCALE)
    val threshImg = Mat()
    threshold(img, threshImg, 127.0, 255.0, 0)
    val contour = mutableListOf<MatOfPoint>()
    val hierarchy = Mat()
    findContours(threshImg, contour, hierarchy, RETR_LIST, CHAIN_APPROX_SIMPLE)
    val cnt = contour[0]
    println("cnt: $cnt")
    val M = moments(cnt)
    println("M: $M")

    // 2 轮廓面积
    val area = contourArea(cnt)
    println("面积: $area")

    // 3 轮廓周长
    val perimeter = arcLength(MatOfPoint2f(*cnt.toArray()), true)
    println("周长: $perimeter")

    // 4 轮廓近似
    val approxMatList = mutableListOf<MatOfPoint>()
    kotlin.repeat(contour.size) {
        val epsilon = perimeter * 0.01
        val approxMat = MatOfPoint2f()
        approxPolyDP(MatOfPoint2f(*cnt.toArray()), approxMat, epsilon, true)
        val tmp = MatOfPoint(*approxMat.toArray())
        approxMatList.add(tmp)
    }

    // 绘制轮廓
    val final = Mat()
    img.copyTo(final)
    drawContours(final, approxMatList, 0, Scalar(127.0, 0.0, 0.0), 3)
    imshow("final", final)
    waitKey()
    System.exit(0)
}

// 学习寻找和绘制轮廓
fun imgContours() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    // 二进制图像轮廓
    val img = imread(resourcesPath + "L4/star_2.png")
    println("img ${img.size()}")
    val gray = Mat()
    cvtColor(img, gray, COLOR_BGR2GRAY)
    val thresholdGray = Mat()
    threshold(gray, thresholdGray, 127.0, 255.0, 0)
    val contour = mutableListOf<MatOfPoint>()
    val hierarchy = Mat()
    findContours(thresholdGray, contour, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE)
    val final = Mat()
    img.copyTo(final)
    println("contour ${contour.size}")
    drawContours(final, contour, 0, Scalar(0.0, 255.0, 0.0), 3)
    imshow("final", final)
    waitKey()
    System.exit(0)
}

// 学习图像金字塔 - 我们将使用图像金字塔创建一个新的水果“Orapple”
fun imagePyramids() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    // 金字塔
//    val origin = imread(resourcesPath + "panda.jpg")
//    val higher_reso2 = Mat()
//    pyrDown(origin, higher_reso2)
//    imshow("higher_reso2", higher_reso2)
//    waitKey()

    // apple orange 融合
    val apple = imread(resourcesPath + "L4/apple.png")
    val orange = imread(resourcesPath + "L4/orange.png")

    // direct image
    val real_apple = Mat()
    apple.copyTo(real_apple)
    val real_orange = Mat()
    orange.copyTo(real_orange)

    // apple 高斯金字塔
    val appleCopy = Mat()
    apple.copyTo(appleCopy)
    val gpApple = mutableListOf(appleCopy)
    for (i in 1..6) {
        val G = apple
        pyrDown(G, G)
        val tmp = Mat()
        G.copyTo(tmp)
        gpApple.add(tmp)
    }

    // orange 高斯金字塔
    val orangeCopy = Mat()
    orange.copyTo(orangeCopy)
    val gpOrange = mutableListOf(orangeCopy)
    for (i in 1..6) {
        val G = orange
        pyrDown(G, G)
        val tmp = Mat()
        G.copyTo(tmp)
        gpOrange.add(tmp)
    }
    // apple 拉普拉斯金字塔
    val lpApple = mutableListOf(gpApple[5])
    for (i in 5 downTo 1) {
        val GE = Mat()
        pyrUp(gpApple[i], GE)
        val L = Mat()

        println("GE $GE")
        println("gpApple[${i - 1}] ${gpApple[i - 1]}")
        println("======")
        subtract(gpApple[i - 1], GE, L)
        lpApple.add(L)
    }
    // orange 拉普拉斯金字塔
    val lpOrange = mutableListOf(gpOrange[5])
    for (i in 5 downTo 1) {
        val GE = Mat()
        pyrUp(gpOrange[i], GE)
        val L = Mat()
        subtract(gpOrange[i - 1], GE, L)
        lpOrange.add(L)
    }
    // 在每个级别中添加左右两半图像
    val LS = mutableListOf<Mat>()
    val zip = lpApple.zip(lpOrange)
    for ((la, lb) in zip) {
        val rows = la.rows()
        val cols = la.cols()
        val left = Mat(la, Range(0, rows), Range(0, cols / 2))
        val right = Mat(lb, Range(0, rows), Range(cols / 2, cols))
        val merge = Mat()
        hconcat(listOf(left, right), merge)
        LS.add(merge)
    }
    for (ls in LS) {
        println("ls -> $ls")
    }

    // 重建
    var ls = LS[0]
    for (i in 1..5) {
        pyrUp(ls, ls)
        val addRes = Mat()
        add(ls, LS[i], addRes)
        ls = addRes
    }
    imshow("pyramid_blending", ls)
    waitKey()

    // 直接连接每一半的图像
    val real_left = Mat(real_apple, Range(0, real_apple.rows()), Range(0, real_apple.cols() / 2))
    val real_right = Mat(real_orange, Range(0, real_orange.rows()), Range(real_orange.cols() / 2, real_orange.cols()))
    val real = Mat()
    hconcat(listOf(real_left, real_right), real)
    imshow("direct_blending", real)
    waitKey()

    exitProcess(0)
}

//  学习Canny边缘检测的概念
fun cannyEdgeDetection() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    val img = imread(resourcesPath + "panda.jpg")
    val canny = Mat()
    Canny(img, canny, 100.0, 200.0)
    imshow("canny", canny)
    waitKey()

    exitProcess(0)
}

// 学习查找图像梯度，边缘等
fun imageGradients() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    val gray = imread(resourcesPath + "L4/origin.jpg", IMREAD_GRAYSCALE)
    println("gray img depth: ${gray.depth()}")
    val laplacian = Mat()
    Laplacian(gray, laplacian, gray.depth())
    val sobelX = Mat()
    Sobel(gray, sobelX, gray.depth(), 1, 0, 5)
    val sobelY = Mat()
    Sobel(gray, sobelY, gray.depth(), 0, 1, 5)

    val final = Mat()
    hconcat(listOf(gray, laplacian, sobelX, sobelY), final)
    imshow("final", final)
    waitKey()

    exitProcess(0)
}

// 学习不同的形态学操作，例如侵蚀，膨胀，开运算，闭运算等。
fun morphologicalTransformations() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    // 侵蚀
//    val img = imread(resourcesPath + "L4/j.png")
//    val kernel = Mat.ones(5, 5, CvType.CV_8UC1)
//    val erosion = Mat()
//    erode(img, erosion, kernel)
//    imshow("erosion", erosion)
//    waitKey()

    // 扩张
//    val img = imread(resourcesPath + "L4/j.png")
//    val kernel = Mat.ones(5, 5, CvType.CV_8UC1)
//    val dilation = Mat()
//    dilate(img, dilation, kernel)
//    imshow("dilation", dilation)
//    waitKey()

    // 开运算， 先侵蚀再扩张
//    val img = imread(resourcesPath + "L4/opening.png")
//    val kernel = Mat.ones(5, 5, CvType.CV_8UC1)
//    val opening = Mat()
//    morphologyEx(img, opening, MORPH_OPEN, kernel)
//    imshow("opening", opening)
//    waitKey()

    // 闭运算， 先扩展在侵蚀
//    val img = imread(resourcesPath + "L4/closing.png")
//    val kernel = Mat.ones(5, 5, CvType.CV_8UC1)
//    val closing = Mat()
//    morphologyEx(img, closing, MORPH_CLOSE, kernel)
//    imshow("closing", closing)
//    waitKey()

    // 形态学梯度
    val img = imread(resourcesPath + "L4/j.png")
    val kernel = Mat.ones(5, 5, CvType.CV_8UC1)
    val gradient = Mat()
    morphologyEx(img, gradient, MORPH_GRADIENT, kernel)
    // 顶帽
    val tophat = Mat()
    morphologyEx(img, tophat, MORPH_TOPHAT, kernel)
    // 黑帽
    val blackhat = Mat()
    morphologyEx(img, blackhat, MORPH_BLACKHAT, kernel)

    val final = Mat()
    vconcat(listOf(img, gradient, tophat, blackhat), final)
    imshow("final", final)
    waitKey()

    // exit
    exitProcess(0)
}

// 学习使用各种低通滤镜模糊图像 - 将定制的滤镜应用于图像（2D卷积）
fun imageSmoothing() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")
    val img = imread(resourcesPath + "L4/opencv_3.png")

    // 平均
//    val kernel = Mat.ones(5, 5, IMREAD_COLOR)
//    val kernel_avg = Mat()
//    divide(0.04, kernel, kernel_avg, CvType.CV_32FC1)
//    println("kernel_avg: ${kernel_avg.dump()}")
//    val res = Mat()
//    filter2D(img, res, -1, kernel_avg)
//    val final = Mat()
//    hconcat(listOf(img, res), final)
//    imshow("final", final)
//    waitKey()

    // 高斯模糊
//    val blur = Mat()
//    GaussianBlur(img, blur, Size(5.0, 5.0), 0.0)
//    val final = Mat()
//    hconcat(listOf(img, blur), final)
//    imshow("final", final)
//    waitKey()

    // 中位模糊
//    val blur = Mat()
//    medianBlur(img, blur, 5)
//    val final = Mat()
//    hconcat(listOf(img, blur), final)
//    imshow("final", final)
//    waitKey()

    // 双边滤波
    val img2 = imread(resourcesPath + "L4/wenli.png")
    val blur = Mat()
    bilateralFilter(img2, blur, 9, 75.0, 75.0)
    val final = Mat()
    hconcat(listOf(img2, blur), final)
    imshow("final", final)
    waitKey()

    exitProcess(0)
}

// 学习简单阈值，自适应阈值和Otsu阈值。
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
