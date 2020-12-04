@file: JvmName("ToolFile")

package com.chuzi.android.libs.tool

import android.os.Build
import android.text.TextUtils
import android.webkit.MimeTypeMap
import java.io.*
import java.math.BigInteger
import java.nio.file.Files

/**
 * 1KB  Long型
 */
const val ONE_KB: Long = 1024

/**
 * 1KB  BigInteger型
 */
val ONE_KB_BI: BigInteger = BigInteger.valueOf(ONE_KB)
/**
 * 1MB  Long型
 */
const val ONE_MB = ONE_KB * ONE_KB
/**
 * 1MB BigInteger型
 */
val ONE_MB_BI: BigInteger = ONE_KB_BI.multiply(ONE_KB_BI)
/**
 * 1GB
 */
val ONE_GB_BI: BigInteger = ONE_KB_BI.multiply(ONE_MB_BI)
/**
 * 1TB
 */
val ONE_TB_BI: BigInteger = ONE_KB_BI.multiply(ONE_GB_BI)
/**
 * 1PB
 */
val ONE_PB_BI: BigInteger = ONE_KB_BI.multiply(ONE_TB_BI)
/**
 * 1EB
 */
val ONE_EB_BI: BigInteger = ONE_KB_BI.multiply(ONE_PB_BI)

/**
 * 根据文件路径构建文件
 *
 * @param directory 父目录
 * @param names     子目录  子目录传入null 则返回 null
 * @return          返回的文件可能不存在 也可能为 null
 */
@JvmOverloads
fun getFile(directory: File? = null, vararg names: String?): File? {
    var file: File? = directory
    for (name in names) {
        if (name == null) {
            return null
        }

        file = if (file == null)
            File(name)
        else
            File(file, name)
    }

    return file
}

/**
 * 读取文件内容并转化为字符串
 * @param file 文件 确保传入的文件可读&存在&不是文件夹
 */
@Throws(IOException::class)
fun readFileToString(file: File): String =
    openInputStream(file).use { inputStream -> toString(inputStream) }

/**
 * 一行行读取文件内容并转化为 List<String>
 * @param file 文件
 */
@Throws(IOException::class)
fun readLines(file: File): List<String> =
    openInputStream(file).use { inputStream -> readLines(inputStream) }

/**
 * 获取指定文件输入流 {@link FileInputStream}
 * @param file 文件
 */
@Throws(IOException::class)
private fun openInputStream(file: File): FileInputStream {
    if (file.exists()) {
        if (file.isDirectory) {
            throw IOException("File '$file' exists but is a directory")
        }
        if (!file.canRead()) {
            throw IOException("File '$file' cannot be read")
        }
    } else {
        throw FileNotFoundException("File is not find")
    }
    return FileInputStream(file)
}

/**
 * 将字符串写入文件中，若该文件不存在则创建
 * @param file   要写入的文件
 * @param data   写入内容
 * @param append true：在文件中追加内容 false：重写文件内容
 */
@JvmOverloads
@Throws(IOException::class)
fun writeStringToFile(file: File, data: String, append: Boolean = false) {
    openOutputStream(file, append).use { out -> write(data, out) }
}

/**
 * 获取文件输出流, 若父目录不存在则检查创建
 *
 * @param file   指定文件, 若指定文件不存在&父路径存在则创建该文件
 * @param append true：字节追加到文件末尾而不是重写文件
 */
@Throws(IOException::class)
private fun openOutputStream(file: File, append: Boolean): FileOutputStream {
    if (file.exists()) {
        if (file.isDirectory) {
            throw IOException("File '$file' exists but is a directory")
        }
        if (!file.canWrite()) {
            throw IOException("File '$file' cannot be written to")
        }
    } else {
        val parent = file.parentFile
        if (parent != null && (!parent.mkdirs() && !parent.isDirectory)) {
            throw IOException("Directory '$parent' could not be created")
        }
    }
    return FileOutputStream(file, append)
}

/**
 * 将输入流写入指定的文件中
 * @param inputString 输入流
 * @param filePath    文件全路径 若文件不存在 则不处理
 * @return 生成的文件
 */
fun writeFile(inputString: InputStream?, filePath: String?): File? {
    if (null == inputString || filePath.isNullOrBlank()) {
        return null
    }

    val file = File(filePath)
    var outputStream: OutputStream? = null

    try {
        outputStream = FileOutputStream(file)
        val b = ByteArray(1024)
        var len: Int
        while ((inputString.read(b).also { len = it }) != -1) {
            outputStream.write(b, 0, len)
        }
    } catch (ignored: IOException) {
        ignored.printStackTrace()
    } finally {
        if (null != outputStream) {
            try {
                outputStream.close()
            } catch (ignored: IOException) {
            }

        }
        try {
            inputString.close()
        } catch (ignored: IOException) {
            ignored.printStackTrace()
        }
    }

    return file
}

/**
 * 比较二个文件内容是否相同
 * @param file1 第一个文件
 * @param file2 第二个文件
 * @return true: 二个文件内容相同或是二个文件都不存在 false：其它
 */
fun contentEquals(file1: File, file2: File): Boolean {
    val file1Exists = file1.exists()
    if (file1Exists != file2.exists()) {
        return false
    }

    if (!file1Exists) {
        // two not existing files are equal
        return true
    }

    if (file1.isDirectory || file2.isDirectory) {
        // don't want to compare directory contents
        return false
    }

    if (file1.length() != file2.length()) {
        // lengths differ, cannot be equal
        return false
    }

    //唯一路径值, 所以若相同则可视为同一个文件
    if (file1.canonicalFile == file2.canonicalFile) {
        // same file
        return true
    }

    FileInputStream(file1).use { input1 ->
        FileInputStream(file2).use { input2 -> return contentEquals(input1, input2) }
    }
}

/**
 * 递归删除文件夹下所有内容
 * @param directory 要删除的文件夹
 *                  未成功删除会抛异常
 */
@Throws(IOException::class)
fun deleteDirectory(directory: File) {
    if (!directory.exists()) {
        return
    }

    if (!isSymlink(directory)) {
        cleanDirectory(directory)
    }

    if (!directory.delete()) {
        val message = "Unable to delete directory $directory."
        throw IOException(message)
    }
}

/**
 * 不抛出任何异常地删除文件，若是个文件夹，则删除所有子目录
 * @param file  要删除的文件 or 文件夹
 * @return true 文件 or 文件夹被删除， 否则 false
 */
fun deleteQuietly(file: File?): Boolean {
    if (file == null) {
        return false
    }
    try {
        if (file.isDirectory) {
            cleanDirectory(file)
        }
    } catch (ignored: Exception) {
    }

    return try {
        file.delete()
    } catch (ignored: Exception) {
        false
    }
}

/**
 * 清除该文件夹下所有子内容，保留空文件夹
 */
@Throws(IOException::class)
fun cleanDirectory(directory: File) {
    val files = verifiedListFiles(directory)

    var exception: IOException? = null
    for (file in files) {
        try {
            forceDelete(file)
        } catch (ioe: IOException) {
            exception = ioe
        }
    }

    if (null != exception) {
        throw exception
    }
}

/**
 * 获取文件夹中的文件列表
 */
@Throws(IOException::class)
private fun verifiedListFiles(directory: File): Array<File> {
    if (!directory.exists()) {
        val message = "$directory does not exist"
        throw IOException(message)
    }

    if (!directory.isDirectory) {
        val message = "$directory is not a directory"
        throw IOException(message)
    }

    return directory.listFiles() ?: throw IOException("Failed to list contents of $directory")
}

/**
 * 删除文件，若该文件是文件夹，则删除其所有子目录
 * @param file 需要删除的文件
 */
@Throws(IOException::class)
fun forceDelete(file: File) {
    if (file.isDirectory) {
        deleteDirectory(file)
    } else {
        val filePresent = file.exists()
        if (!file.delete()) {
            if (!filePresent) {
                return
            }
            val message = "Unable to delete file: $file"
            throw IOException(message)
        }
    }
}

/**
 * 判断是否是符号链接而不是实体文件集
 * @param  file:  检查文件
 * @return true:  该文件是符号链接
 */
private fun isSymlink(file: File?): Boolean {
    if (file == null) {
        throw NullPointerException("File must not be null")
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Files.isSymbolicLink(file.toPath())
    } else false
}


/**
 * 创建文件夹
 * @param directory 需要创建的文件夹
 * @throws IOException 若文件夹不能被创建 or 此文件已存在但不是文件夹
 */
@Throws(IOException::class)
fun forceMkDir(directory: File) {
    if (directory.exists()) {
        if (!directory.isDirectory) {
            val message = ("File "
                    + directory
                    + " exists and is "
                    + "not a directory. Unable to create directory.")
            throw IOException(message)
        }
    } else {
        if (!directory.mkdirs() && !directory.isDirectory) {
            val message = "Unable to create directory $directory"
            throw IOException(message)
        }
    }
}

/**
 * 返回传入文件 or 文件夹的大小，若传入的文件 or 文件目录有安全限制，则不计入文件大小
 * @param file 合法文件or 文件夹
 * @return 文件长度 or 文件夹所有不受限制的文件大小
 */
fun sizeOf(file: File): Long {

    if (!file.exists()) {
        return 0L
    }

    return if (file.isDirectory) {
        sizeOfDirectory0(file) // private method; expects directory
    } else file.length()

}

/**
 * 文件夹大小
 * @param directory 检查的文件
 */
private fun sizeOfDirectory0(directory: File): Long {
    val files = directory.listFiles()
        ?: // null if security restricted
        return 0L
    var size: Long = 0

    for (file in files) {
        if (!isSymlink(file)) {
            size += sizeOf0(file) // internal method
            if (size < 0) {
                break
            }
        }
    }

    return size
}

/**
 * 文件大小
 * @param file 要检查的文件
 */
private fun sizeOf0(file: File): Long {
    return if (file.isDirectory) {
        sizeOfDirectory0(file)
    } else file.length() // will be 0 if file does not exist
}

/**
 * 根据字节数转化为 EB, PB, TB, GB, MB, KB or bytes
 * 只保留整数
 */
fun byteCountToDisplaySize(size: Long): String {
    return byteCountToDisplaySize(BigInteger.valueOf(size))
}

/**
 * 将字节数转化EB, PB, TB, GB, MB, KB or bytes
 * @param size 字节数
 * @return  units - EB, PB, TB, GB, MB, KB or bytes
 */
fun byteCountToDisplaySize(size: BigInteger): String {
    return when {
        size.divide(ONE_EB_BI) > BigInteger.ZERO -> size.divide(ONE_EB_BI).toString() + " EB"
        size.divide(ONE_PB_BI) > BigInteger.ZERO -> size.divide(ONE_PB_BI).toString() + " PB"
        size.divide(ONE_TB_BI) > BigInteger.ZERO -> size.divide(ONE_TB_BI).toString() + " TB"
        size.divide(ONE_GB_BI) > BigInteger.ZERO -> size.divide(ONE_GB_BI).toString() + " GB"
        size.divide(ONE_MB_BI) > BigInteger.ZERO -> size.divide(ONE_MB_BI).toString() + " MB"
        size.divide(ONE_KB_BI) > BigInteger.ZERO -> size.divide(ONE_KB_BI).toString() + " KB"
        else -> "$size bytes"
    }
}

/**
 * 根据字节数转化为 EB, PB, TB, GB, MB, KB or bytes
 * 保留两位小数点
 */
fun byteCountToDisplaySizeTwo(size: Long): String {
    return byteCountToDisplaySizeTwo(BigInteger.valueOf(size))
}


/**
 * 保留两位小数
 * 将字节数转化EB, PB, TB, GB, MB, KB or bytes
 * @param size 字节数
 * @return  units - EB, PB, TB, GB, MB, KB or bytes
 */
fun byteCountToDisplaySizeTwo(size: BigInteger): String {
    return when {
        size.divide(ONE_EB_BI) > BigInteger.ZERO -> size.toFloat().div(ONE_EB_BI.toFloat()).toStringTwo() + " EB"
        size.divide(ONE_PB_BI) > BigInteger.ZERO -> size.toFloat().div(ONE_PB_BI.toFloat()).toStringTwo() + " PB"
        size.divide(ONE_TB_BI) > BigInteger.ZERO -> size.toFloat().div(ONE_TB_BI.toFloat()).toStringTwo() + " TB"
        size.divide(ONE_GB_BI) > BigInteger.ZERO -> size.toFloat().div(ONE_GB_BI.toFloat()).toStringTwo() + " GB"
        size.divide(ONE_MB_BI) > BigInteger.ZERO -> size.toFloat().div(ONE_MB_BI.toFloat()).toStringTwo() + " MB"
        size.divide(ONE_KB_BI) > BigInteger.ZERO -> size.toFloat().div(ONE_KB_BI.toFloat()).toStringTwo() + " KB"
        else -> "$size bytes"
    }
}

/**
 * 判断文件是否存在
 * @param file 文件
 * @return true:存在; false:不存在
 */
fun isFileExists(file: File?): Boolean {
    return file != null && file.exists()
}

/**
 * 判断文件是否存在
 * @param filePath 文件路径
 * @return true:存在; false:不存在
 */
fun isFileExists(filePath: String): Boolean {
    return isFileExists(getFileByPath(filePath))
}

/**
 * 根据文件路径返回文件
 * @param filePath 文件路径
 * @return 文件 or null
 */
fun getFileByPath(filePath: String?): File? {
    return if (filePath.isNullOrEmpty()) null else File(filePath)
}

/**
 * 判断文件是否存在
 * @param fileName 文件名
 */
fun hasExtentsion(filename: String): Boolean {
    val dot: Int = filename.lastIndexOf('.')
    return dot > -1 && dot < filename.length - 1
}

/**
 * 获取文件扩展名
 * @param filename  文件名
 */
fun getExtensionName(filename: String): String? {
    if (filename.isNotEmpty()) {
        val dot = filename.lastIndexOf('.')
        if (dot > -1 && dot < filename.length - 1) {
            return filename.substring(dot + 1)
        }
    }
    return null
}

/**
 * 获取文件名
 * @param filepath 文件路径
 */
fun getFileNameFromPath(filepath: String): String? {
    if (filepath.isNotEmpty()) {
        val sep = filepath.lastIndexOf('/')
        if (sep > -1 && sep < filepath.length - 1) {
            return filepath.substring(sep + 1)
        }
    }
    return null
}

/**
 * 获取不带扩展名的文件名
 * @param filename 文件名
 */
fun getFileNameNoEx(filename: String): String? {
    if (filename.isNotEmpty()) {
        val dot = filename.lastIndexOf('.')
        if (dot > -1 && dot < filename.length) {
            return filename.substring(0, dot)
        }
    }
    return null
}

/**
 * 获取文件的MemeType
 * @param filePath 文件路径
 */
fun getMimeType(filePath: String): String? {
    if (TextUtils.isEmpty(filePath)) {
        return null
    }
    var type: String? = null
    val extension: String? = getExtensionName(filePath.toLowerCase())
    if (!TextUtils.isEmpty(extension)) {
        val mime = MimeTypeMap.getSingleton()
        type = mime.getMimeTypeFromExtension(extension)
    }
    if (type.isNullOrEmpty() && filePath.endsWith("aac")) {
        type = "audio/aac"
    }
    return type
}

/**
 * 复制文件.
 */
fun copyFile(from: File, to: File) {
    if (!from.exists()) {
        throw IOException("The source file not exist: " + from.absolutePath)
    }
    FileInputStream(from).use { fis ->
        copyFile(fis, to)
    }
}

/**
 * 从InputStream流复制文件.
 */
fun copyFile(from: InputStream, to: File): Long {
    var totalBytes: Long = 0
    FileOutputStream(to, false).use { fos ->
        val data = ByteArray(1024)
        var len: Int
        while (from.read(data).also { len = it } > -1) {
            fos.write(data, 0, len)
            totalBytes += len.toLong()
        }
        fos.flush()
    }
    return totalBytes
}


/**
 * 复制文件
 *
 * @param srcFilePath  源文件路径
 * @param destFilePath 目标文件路径
 * @return `true`: 复制成功<br></br>`false`: 复制失败
 */
fun copyFile(srcFilePath: String?, destFilePath: String?): Boolean {
    return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), false)
}

/**
 * 复制文件
 *
 * @param srcFile  源文件
 * @param destFile 目标文件
 * @return `true`: 复制成功<br></br>`false`: 复制失败
 */
fun copyFile(srcFile: File?, destFile: File?, isCopy: Boolean): Boolean {
    return copyOrMoveFile(srcFile, destFile, false)
}


/**
 * 复制或移动文件
 *
 * @param srcFilePath  源文件路径
 * @param destFilePath 目标文件路径
 * @param isMove       是否移动
 * @return `true`: 复制或移动成功<br></br>`false`: 复制或移动失败
 */
fun copyOrMoveFile(srcFilePath: String?, destFilePath: String?, isMove: Boolean): Boolean {
    return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), isMove)
}

/**
 * 删除文件
 *
 * @param file 文件
 * @return `true`: 删除成功<br></br>`false`: 删除失败
 */
fun deleteFileToBoolan(file: File?): Boolean {
    return file != null && (!file.exists() || file.isFile && file.delete())
}


/**
 * 复制或移动文件
 *
 * @param srcFile  源文件
 * @param destFile 目标文件
 * @param isMove   是否移动
 * @return `true`: 复制或移动成功<br></br>`false`: 复制或移动失败
 */
fun copyOrMoveFile(srcFile: File?, destFile: File?, isMove: Boolean): Boolean {
    if (srcFile == null || destFile == null) {
        return false
    }
    // 源文件不存在或者不是文件则返回false
    if (!srcFile.exists() || !srcFile.isFile) {
        return false
    }
    // 目标文件存在且是文件则返回false
    if (destFile.exists() && destFile.isFile) {
        return false
    }
    // 目标目录不存在返回false
    return if (!createOrExistsDir(destFile.parentFile)) {
        false
    } else try {
        (writeFileFromIS(destFile, FileInputStream(srcFile), false)
                && !(isMove && !deleteFileToBoolan(srcFile)))
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * 判断目录是否存在，不存在则判断是否创建成功
 *
 * @param dirPath 文件路径
 * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
 */
fun createOrExistsDir(dirPath: String?): Boolean {
    return createOrExistsDir(getFileByPath(dirPath))
}

/**
 * 判断目录是否存在，不存在则判断是否创建成功
 *
 * @param file 文件
 * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
 */
fun createOrExistsDir(file: File?): Boolean {
    // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
    return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
}

/**
 * 将输入流写入文件
 *
 * @param filePath 路径
 * @param is       输入流
 * @param append   是否追加在文件末
 * @return `true`: 写入成功<br></br>`false`: 写入失败
 */
fun writeFileFromIS(filePath: String?, `is`: InputStream?, append: Boolean): Boolean {
    return writeFileFromIS(getFileByPath(filePath), `is`, append)
}

/**
 * 将输入流写入文件
 *
 * @param file   文件
 * @param ios     输入流
 * @param append 是否追加在文件末
 * @return `true`: 写入成功<br></br>`false`: 写入失败
 */
fun writeFileFromIS(file: File?, ios: InputStream?, append: Boolean): Boolean {
    if (file == null || ios == null) return false
    if (!createOrExistsFile(file)) return false
    var os: OutputStream? = null
    return try {
        os = BufferedOutputStream(FileOutputStream(file, append))
        val data = ByteArray(1024)
        var len: Int
        while (ios.read(data, 0, 1024).also { len = it } != -1) {
            os.write(data, 0, len)
        }
        true
    } catch (e: IOException) {
        e.printStackTrace()
        false
    } finally {
        closeIO(ios, os)
    }
}

/**
 * 判断文件是否存在，不存在则判断是否创建成功
 *
 * @param filePath 文件路径
 * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
 */
fun createOrExistsFile(filePath: String?): Boolean {
    return createOrExistsFile(getFileByPath(filePath))
}

/**
 * 判断文件是否存在，不存在则判断是否创建成功
 *
 * @param file 文件
 * @return `true`: 存在或创建成功<br></br>`false`: 不存在或创建失败
 */
fun createOrExistsFile(file: File?): Boolean {
    if (file == null) {
        return false
    }
    // 如果存在，是文件则返回true，是目录则返回false
    if (file.exists()) {
        return file.isFile
    }
    return if (!createOrExistsDir(file.parentFile)) {
        false
    } else try {
        file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }
}