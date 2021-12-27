import org.opencv.core.*
import org.opencv.highgui.HighGui.*
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgcodecs.Imgcodecs.imread
import org.opencv.imgcodecs.Imgcodecs.imwrite
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.*
import java.time.Clock
import java.time.ZoneId
import kotlin.time.Duration


fun mat_operations() {
    // 创建一个空矩阵
    val mat: Mat = Mat()
    println(mat)

    // 矩阵元素类型
    // 灰度图像 通常1通道，灰度值 [0,255]
    // 彩色图像 通常3通道，RGB颜色空间，每个通道取值[0,255]
    // 元素类型 CV_<bit-depth>{U|S|F}C<number_of_channels>
    // bit-depth 比特深度 8位，16位，32位，64位 可选
    // U 无符号整数 S有符号整数 F浮点型 C通道数
    // number_of_channels 通道数 1，2，3，4 可选
    // 创建3行3列，矩阵元素类型 1通道 8位无符号
    val mat1: Mat = Mat(3, 3, CvType.CV_8UC1)//8UC1灰度图像，8UC3彩色图像
    mat1.put(0, 0, byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
    println(mat1)
    println("====")

    // 矩阵操作
    println("矩阵行数: ${mat1.rows()}")
    println("矩阵列数：${mat1.cols()}")
    println("矩阵元素个数：${mat1.total()}")//3行+3列
    println("矩阵大小：${mat1.size()}")
    println("矩阵某个元素的值：${mat1.get(1, 2).asList()}")
    println("矩阵某个元素的值：${mat1.get(1, 2)[0]}")
    println("遍历矩阵: ${mat1.dump()}")
    println("====")

    // 矩阵线性代数操作
    // 计算逆矩阵，元素类型必须是 CV_32FC1 CV_64_FC1
    val mat2 = Mat(2, 2, CvType.CV_32FC1)
    mat2.put(0, 0, floatArrayOf(1F, 2F, 3F, 4F))
    println("矩阵：${mat2.dump()}")
    println("转置矩阵：${mat2.t().dump()}")
    println("逆矩阵：${mat2.inv().dump()}")
    val mat3 = Mat()
    mat2.push_back(mat3)//元素类型必须相同
    mat2.push_back(mat3)
    println("push_back两次后的矩阵：${mat2.dump()}")
    println("2*2单位矩阵： ${Mat.eye(2, 2, CvType.CV_8UC1).dump()}")
    println("全1矩阵：${Mat.ones(3, 3, CvType.CV_8UC1).dump()}")
    println("全0矩阵：${Mat.zeros(4, 4, CvType.CV_8UC1).dump()}")
    println("====")

    // 矩阵线性代数加减乘操作
    val mat4 = Mat(2, 2, CvType.CV_32FC1)
    mat4.put(0, 0, floatArrayOf(1F, 2F, 3F, 4F))
    val mat5 = mat4.clone()//克隆
    println("mat4: ${mat4.dump()}")
    println("mat5: ${mat5.dump()}")
    val mat6 = Mat()
    Core.add(mat4, mat5, mat6)//加
    println("mat4 + mat5: ${mat6.dump()}")
    val mat7 = Mat()
    Core.subtract(mat4, mat5, mat7)//减
    println("mat4 - mat5: ${mat7.dump()}")
    val mat8 = Mat()
    Core.gemm(mat4, mat5, 1.0, Mat(), 0.0, mat8) // dst=alpha*src1*src2 + beta*src3 第三个矩阵可以忽略它
    println("mat4 * mat5: ${mat8.dump()}")
    println("====")
}

fun image_basic_operations() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    val img: Mat = Imgcodecs.imread(resourcesPath + "opencv.png", Imgcodecs.IMREAD_COLOR)
    println(img)
    // 通过坐标访问元素
    val px = img[5, 25]
    println("px: ${px.asList()}")
    // 访问蓝色像素
    val blue = img[5, 25][0]
    println("blue: $blue")
    // 修改像素
    var changingPx = img[5, 25]
    changingPx = doubleArrayOf(255.0, 255.0, 255.0)
    println("changePx: ${changingPx.asList()}")
    // 访问图像属性: 行 列 通道数 大小 数据类型
    println("(行, 列, 通道, 大小, 类型)：(${img.rows()}, ${img.cols()}, ${img.channels()}, ${img.total()}, ${img.type()})")

    // roi
//    val range = Range(5, 35)
//    val rect = Rect(10, 10, 30, 30)
//    imshow("range roi img", Mat(img, range))
//    waitKey()
//    imshow("rect roi img", Mat(img, rect))
//    waitKey()
//    destroyAllWindows()
//    waitKey(1)

    // 拆分&合并 图像通道
//    val matList = mutableListOf<Mat>()
//    Core.split(img, matList)
//    matList.forEach { mat: Mat ->
//        Thread.sleep(100)
//        imwrite(testResultOutPath + "split_" + System.currentTimeMillis() + ".png", mat)
//    }
//    val mergeMat = Mat()
//    Core.merge(matList, mergeMat)
//    imwrite(testResultOutPath + "merge_" + System.currentTimeMillis() + ".png", mergeMat)

    // 填充边框
//    val borderMat = Mat()
//    Core.copyMakeBorder(img, borderMat, 2, 2, 2, 2, Core.BORDER_DEFAULT)
//    imshow("填充边框", borderMat)
//    waitKey()

}

fun image_arithmetic_operations() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    // 图像融合
//    val img1 = imread(resourcesPath + "opencv.png")
//    val img2 = imread(resourcesPath + "opencv_2.png")
//    val dstMat = Mat()
//    Core.addWeighted(img1, 0.7, img2, 0.3, 0.0, dstMat)
//    imshow("融合后", dstMat)
//    waitKey()

    // 按位运算
    val img3 = imread(resourcesPath + "tiny-logo.jpeg")
    val img4 = imread(resourcesPath + "opencv.png")
    // 创建roi, 把opencv放置在图片左上角
    val roiRect = Rect(150, 150, img4.cols(), img4.rows())// 宽对应cols, 高对应rows
    val roi = Mat(img3, roiRect)

    // 创建logo的掩码，并同时创建其相反掩码
    val img4Gray = Mat()
    cvtColor(img4, img4Gray, COLOR_BGR2GRAY)

    val mask = Mat()
    threshold(img4Gray, mask, 10.0, 255.0, THRESH_BINARY)
    val mask_inv = Mat()
    Core.bitwise_not(mask, mask_inv)

    // 将roi中logo的区域涂黑
    val img3Bg = Mat()
    Core.bitwise_and(roi, roi, img3Bg, mask_inv)
    // 仅从logo图像中提取logo区域
    val img4Bg = Mat()
    Core.bitwise_and(img4, img4, img4Bg, mask)
    // 将logo放入ROI并修改主图像
    val dstMat2 = Mat()
    Core.add(img3Bg, img4Bg, dstMat2)
    // 替换原图部分区域
    val submat = img3.submat(roiRect)
    dstMat2.copyTo(submat)
    imshow("res", img3)
    waitKey()

    destroyAllWindows()
    waitKey(1)
}

fun performance_measurement() {
    val resourcesPath = System.getProperty("user.dir") + "/src/main/resources/"
    val testResultOutPath = resourcesPath + "test_result_out/"
    println("资源路径: $resourcesPath")

    val tickCount = Core.getTickCount()
    val img = imread(resourcesPath + "tiny-logo.jpeg")
    for (i in 5..49 step 2) {
        medianBlur(img, img, i)
    }
    val tickCount1 = Core.getTickCount()
    val t = (tickCount1 - tickCount) / Core.getTickFrequency()
    println("t: $t")
}

/**
 * 在本节中，您将学习图像的基本操作，如像素编辑、几何变换、代码优化、一些数学工具等。
 * In this section you will learn basic operations on image like pixel editing,
 * geometric transformations, code optimization, some mathematical tools etc.*/
fun main() {
    println("载入 native library: ${Core.NATIVE_LIBRARY_NAME}")
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    println("使用 OpenCV 版本: v${Core.VERSION}")
    println("====")

    // 矩阵操作
//    mat_operations()

    // 图像的基本操作
    // 学习读取和编辑像素值，使用图像 ROI 和其他基本操作
//    image_basic_operations()

    // 图像的算术操作
    // 对图像执行算术运算
//    image_arithmetic_operations()

    // 性能测量和改进技术
    // 获得解决方案很重要。但以最快的方式获得它更重要。学习检查代码的速度，优化代码等。
    performance_measurement()

}