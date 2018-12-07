package com.blackmirror.dongda.utils

import java.io.File
import java.io.FileInputStream
import java.text.DecimalFormat

/**
 * Create By Ruge at 2018-12-07
 */

private val TAG = "FileUtils"

val SIZETYPE_B = 1//获取文件大小单位为B的double值
val SIZETYPE_KB = 2//获取文件大小单位为KB的double值
val SIZETYPE_MB = 3//获取文件大小单位为MB的double值
val SIZETYPE_GB = 4//获取文件大小单位为GB的double值


/**
 * 获取文件指定文件的指定单位的大小
 *
 * @param filePath 文件路径
 * @return double值的大小
 */
fun getFileOrFilesSize(filePath: String): Double {
    val file = File(filePath)
    var blockSize: Long = 0
    try {
        if (file.isDirectory) {
            blockSize = getFileSizes(file)
        } else {
            blockSize = getFileSize(file)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        logD(TAG, "获取文件大小失败!")
    }

    return FormetFileSize(blockSize, SIZETYPE_MB)
}

/**
 * 获取文件指定文件的指定单位的大小
 *
 * @param filePath 文件路径
 * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
 * @return double值的大小
 */
fun getFileOrFilesSize(filePath: String, sizeType: Int): Double {
    val file = File(filePath)
    var blockSize: Long = 0
    try {
        if (file.isDirectory) {
            blockSize = getFileSizes(file)
        } else {
            blockSize = getFileSize(file)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        logD(TAG, "获取文件大小失败!")
    }

    return FormetFileSize(blockSize, sizeType)
}

/**
 * 调用此方法自动计算指定文件或指定文件夹的大小
 *
 * @param filePath 文件路径
 * @return 计算好的带B、KB、MB、GB的字符串
 */
fun getAutoFileOrFilesSize(filePath: String): String {
    val file = File(filePath)
    var blockSize: Long = 0
    try {
        if (file.isDirectory) {
            blockSize = getFileSizes(file)
        } else {
            blockSize = getFileSize(file)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        logD(TAG, "获取文件大小失败!")
    }

    return FormetFileSize(blockSize)
}

/**
 * 获取指定文件大小
 *
 * @param file
 * @return
 * @throws Exception
 */
@Throws(Exception::class)
private fun getFileSize(file: File): Long {
    var size: Long = 0
    if (file.exists()) {
        var fis: FileInputStream? = null
        fis = FileInputStream(file)
        size = fis.available().toLong()
    } else {
        file.createNewFile()
        logD(TAG, "获取文件大小不存在!")
    }
    return size
}

/**
 * 获取指定文件夹
 *
 * @param f
 * @return
 * @throws Exception
 */
@Throws(Exception::class)
private fun getFileSizes(f: File): Long {
    var size: Long = 0
    val flist = f.listFiles()
    for (i in flist.indices) {
        if (flist[i].isDirectory) {
            size = size + getFileSizes(flist[i])
        } else {
            size = size + getFileSize(flist[i])
        }
    }
    return size
}

/**
 * 转换文件大小
 *
 * @param fileS
 * @return
 */
private fun FormetFileSize(fileS: Long): String {
    val df = DecimalFormat("#.00")
    var fileSizeString = ""
    val wrongSize = "0B"
    if (fileS == 0L) {
        return wrongSize
    }
    if (fileS < 1024) {
        fileSizeString = df.format(fileS.toDouble()) + "B"
    } else if (fileS < 1048576) {
        fileSizeString = df.format(fileS.toDouble() / 1024) + "KB"
    } else if (fileS < 1073741824) {
        fileSizeString = df.format(fileS.toDouble() / 1048576) + "MB"
    } else {
        fileSizeString = df.format(fileS.toDouble() / 1073741824) + "GB"
    }
    return fileSizeString
}

/**
 * 转换文件大小,指定转换的类型
 *
 * @param fileS
 * @param sizeType
 * @return
 */
private fun FormetFileSize(fileS: Long, sizeType: Int): Double {
    val df = DecimalFormat("#.00")
    var fileSizeLong = 0.0
    when (sizeType) {
        SIZETYPE_B -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble()))!!
        SIZETYPE_KB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1024))!!
        SIZETYPE_MB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1048576))!!
        SIZETYPE_GB -> fileSizeLong = java.lang.Double.valueOf(df.format(fileS.toDouble() / 1073741824))!!
        else -> {
        }
    }
    return fileSizeLong
}

fun delAllFile(path: String): Boolean {
    var flag = false
    val file = File(path)
    if (!file.exists()) {
        return flag
    }
    if (!file.isDirectory) {
        return flag
    }
    val tempList = file.list()
    var temp: File? = null
    for (i in tempList.indices) {
        if (path.endsWith(File.separator)) {
            temp = File(path + tempList[i])
        } else {
            temp = File(path + File.separator + tempList[i])
        }
        if (temp.isFile) {
            temp.delete()
        }
        if (temp.isDirectory) {
            delAllFile(path + "/" + tempList[i])//先删除文件夹里面的文件
            flag = true
        }
    }
    return flag
}

fun deleteDir(dir: File?): Boolean {
    if (dir != null && dir.isDirectory) {
        val children = dir.list()
        for (i in children.indices) {
            val success = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
    }
    return dir?.delete()?:true
}