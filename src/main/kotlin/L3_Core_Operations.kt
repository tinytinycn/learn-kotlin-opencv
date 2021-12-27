import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat


fun create_mat() {
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

    // 矩阵操作
    println("矩阵行数: ${mat1.rows()}")
    println("矩阵列数：${mat1.cols()}")
    println("矩阵元素个数：${mat1.total()}")//3行+3列
    println("矩阵大小：${mat1.size()}")
    println("矩阵某个元素的值：${mat1.get(1,2).asList()}")
    println("矩阵某个元素的值：${mat1.get(1,2)[0]}")
    println("遍历矩阵: ${mat1.dump()}")
}

/**
 * 在本节中，您将学习图像的基本操作，如像素编辑、几何变换、代码优化、一些数学工具等。
 * In this section you will learn basic operations on image like pixel editing,
 * geometric transformations, code optimization, some mathematical tools etc.*/
fun main() {
    println("载入 native library: ${Core.NATIVE_LIBRARY_NAME}")
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    println("使用 OpenCV 版本: v${Core.VERSION}")

    //创建矩阵Mat
    create_mat()
}