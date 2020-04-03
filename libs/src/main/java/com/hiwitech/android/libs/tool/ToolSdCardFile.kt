@file: JvmName("ToolSdCardFile")

package com.hiwitech.android.libs.tool

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * 外部文件是否可写
 *
 * @return true: 外设存储设备可写；<br></br>
 */
fun isExternalStorageWriteable(): Boolean {
    // Environment.MEDIA_MOUNTED_READ_ONLY.equals()//只读
    return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
}

/**
 * 创建文件夹
 *
 * @param rootFileStr 二级根目录名称，值为：<br></br>
 * @return File
 */
fun Context.createRootFile(rootFileStr: String): File {
    //创建文件夹
    var fileDir = this.getExternalFilesDir(rootFileStr)
    if (fileDir == null) {
        fileDir = File(this.filesDir, rootFileStr)
        fileDir.mkdirs()
    }
    return fileDir
}

/**
 * 创建文件  (内部创建文件)
 *
 * @param rootFileStr 二级根目录名称，值为：<br></br>
 * @param fileName    文件名称
 * @return File
 */
fun Context.createFile(rootFileStr: String, fileName: String): File? {
    val file = File(createRootFile(rootFileStr), fileName)

    //创建文件
    var isCreateSuccess = file.exists()
    if (!isCreateSuccess) {
        try {
            isCreateSuccess = file.createNewFile()
        } catch (e: IOException) {
            printStackTrace("ToolSdCardFile", "createFile()", e)
        }

    }

    return if (isCreateSuccess) file else null
}

/**
 * 创建文件夹  (内部创建文件)
 *
 * @param rootFileStr 二级根目录名称，值为：<br></br>
 * @param fileDirName 文件夹名称
 * @return File
 */
fun Context.createFileDir(rootFileStr: String, fileDirName: String): File? {
    val file = File(createRootFile(rootFileStr), fileDirName)

    //创建文件夹
    var isCreateSuccess = file.exists()
    if (!isCreateSuccess) {
        isCreateSuccess = file.mkdirs()
    }

    return if (isCreateSuccess) file else null
}

/**
 * 根据传入的二级根目录，获取其绝对路径
 *
 * @param rootFileStr 二级根目录名称，值为：<br></br>
 * @return 绝对路径
 */
fun Context.getRootFileAbsolutePath(rootFileStr: String): String {
    return createRootFile(rootFileStr).absolutePath
}

/**
 * 根据传入的参数，获取绝对路径
 *
 * @param rootFileStr 二级根目录名称，值为：<br></br>
 * @param fileName    文件名称
 * @return 绝对路径
 */
fun Context.getFileAbsolutePath(rootFileStr: String, fileName: String): String {
    return getRootFileAbsolutePath(rootFileStr) + "/" + fileName
}

/**
 * 存储图片
 *
 * @param bitmap      图片
 * @param rootFileStr 二级根目录名称，值为：<br></br>
 * @param fileName    文件名称
 */
fun Context.saveBitmap(bitmap: Bitmap?, rootFileStr: String, fileName: String): File? {
    var fos: FileOutputStream? = null
    var fileImg: File? = null

    if (bitmap == null) {
        return null
    }
    try {
        fileImg = createFile(rootFileStr, fileName)
        if (fileImg == null) {
            return null
        }

        fos = FileOutputStream(fileImg)
        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fos)) {
            fos.flush()
            fos.close()
        }
    } catch (e: Exception) {
        printStackTrace("ToolSdCardFile", "saveBitmap()", e)
    } finally {
        if (fos != null) {
            try {
                fos.close()
            } catch (e: IOException) {
                printStackTrace("ToolSdCardFile", "saveBitmap() finally", e)
            }

        }
    }

    return fileImg
}

/**
 * Get a file path from a Uri. This will get the the path for Storage Access
 * Framework Documents, as well as the _data field for the MediaStore and
 * other file-based ContentProviders.
 *
 * @param context The context.
 * @param uri     The Uri to query.
 */
@Suppress("DEPRECATION")
@SuppressLint("NewApi")
fun getPathFromUri(context: Context, uri: Uri): String? {
    val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]

            if ("primary".equals(type, ignoreCase = true)) {
                return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            }
        } else if (isDownloadsDocument(uri)) { // DownloadsProvider
            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
            )
            return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(uri)) {// MediaProvider
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val type = split[0]
            var contentUri: Uri? = null
            when (type) {
                "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])

            return getDataColumn(context, contentUri, selection, selectionArgs)
        }
    } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {// MediaStore (and general)
        return getDataColumn(context, uri, null, null)
    } else if ("file".equals(uri.scheme!!, ignoreCase = true)) { // File
        return uri.path
    }

    return null
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is ExternalStorageProvider.
 */
private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is DownloadsProvider.
 */
private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

/**
 * @param uri The Uri to check.
 * @return Whether the Uri authority is MediaProvider.
 */
private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

/**
 * Get the value of the data column for this Uri. This is useful for
 * MediaStore Uris, and other file-based ContentProviders.
 *
 * @param context       The context.
 * @param uri           The Uri to query.
 * @param selection     (Optional) Filter used in the query.
 * @param selectionArgs (Optional) Selection arguments used in the query.
 * @return The value of the _data column, which is typically a file path.
 */
private fun getDataColumn(
    context: Context, uri: Uri?, selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
        cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(columnIndex)
        }
    } finally {
        cursor?.close()
    }
    return null
}

/**
 * 判断该文件是否是一个图片。
 */
fun isImage(fileName: String): Boolean {
    return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")
}

/**
 * 删除文件或文件夹
 *
 * @param rootFileStr 二级根目录名称，值为：<br></br>
 * @param fileName    要删除的文件名，为空则全部删除
 */
@Synchronized
fun Context.clearFile(rootFileStr: String, fileName: String) {
    val rootFile = createRootFile(rootFileStr)
    var file: File? = null
    if (!TextUtils.isEmpty(fileName)) {
        file = File(rootFile, fileName)
    }
    if (rootFile.exists()) {
        if (file != null) {
            deleteFile(file)
        } else {
            if (rootFile.isDirectory) {
                val fileList = rootFile.listFiles()
                if (fileList != null) {
                    for (targetFile in fileList) {
                        deleteFile(targetFile)
                    }
                }
            } else {
                deleteFile(rootFile)
            }
        }
    }
}

/**
 * 递归删除文件或文件夹
 *
 * @param file 待删除的文件或文件夹
 */
fun deleteFile(file: File) {
    if (file.isDirectory) {
        val fileList = file.listFiles()
        if (fileList != null) {
            for (targetFile in fileList) {
                deleteFile(targetFile)
            }
        }
    }
    file.delete()
}

/**
 * 计算文件大小
 *
 * @param fileOrDir 文件或目录
 * @return 文件大小
 */
fun calculateFileSize(fileOrDir: File): Long {
    if (!fileOrDir.exists()) {
        return 0
    }
    var size: Long = 0
    val fileList = fileOrDir.listFiles() ?: return 0
    for (file in fileList) {
        size += if (file.isDirectory) {
            calculateFileSize(file)
        } else {
            file.length()
        }
    }
    return size
}

/**
 * 获取文件大小，M为单位,保留一位小数
 *
 * @return 文件大小
 */
fun getFileSize(size: Long): String? {
    val fileSizeStr: String
    val formatSize = size.toDouble() / 1048576
    var formatSizeStr = formatSize.toString()
    val i = formatSizeStr.indexOf(".")
    if (i < 0 || i + 2 > formatSizeStr.length) {
        return null
    }
    formatSizeStr = formatSizeStr.substring(0, i + 2)
    fileSizeStr = formatSizeStr + "M"
    return fileSizeStr
}