//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlin.Boolean
import kotlin.ByteArray
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.Count
import org.drinkless.td.libcore.telegram.TdApi.Error
import org.drinkless.td.libcore.telegram.TdApi.File
import org.drinkless.td.libcore.telegram.TdApi.FilePart
import org.drinkless.td.libcore.telegram.TdApi.FileType
import org.drinkless.td.libcore.telegram.TdApi.InputFile
import org.drinkless.td.libcore.telegram.TdApi.InputSticker
import org.drinkless.td.libcore.telegram.TdApi.Location
import org.drinkless.td.libcore.telegram.TdApi.MessageFileType
import org.drinkless.td.libcore.telegram.TdApi.Text

/**
 * Suspend function, which stops the downloading of a file. If a file has already been downloaded,
 * does nothing.
 *
 * @param fileId Identifier of a file to stop downloading.  
 * @param onlyIfPending Pass true to stop downloading only if it hasn't been started, i.e. request
 * hasn't been sent to server.
 */
suspend fun TelegramFlow.cancelDownloadFile(fileId: Int, onlyIfPending: Boolean) =
    this.sendFunctionLaunch(TdApi.CancelDownloadFile(fileId, onlyIfPending))

/**
 * Suspend function, which stops the uploading of a file. Supported only for files uploaded by using
 * uploadFile. For other files the behavior is undefined.
 *
 * @param fileId Identifier of the file to stop uploading.
 */
suspend fun TelegramFlow.cancelUploadFile(fileId: Int) =
    this.sendFunctionLaunch(TdApi.CancelUploadFile(fileId))

/**
 * Suspend function, which removes potentially dangerous characters from the name of a file. The
 * encoding of the file name is supposed to be UTF-8. Returns an empty string on failure. Can be called
 * synchronously.
 *
 * @param fileName File name or path to the file.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.cleanFileName(fileName: String?): Text =
    this.sendFunctionAsync(TdApi.CleanFileName(fileName))

/**
 * Suspend function, which deletes a file from the TDLib file cache.
 *
 * @param fileId Identifier of the file to delete.
 */
suspend fun TelegramFlow.deleteFile(fileId: Int) = this.sendFunctionLaunch(TdApi.DeleteFile(fileId))

/**
 * Suspend function, which downloads a file from the cloud. Download progress and completion of the
 * download will be notified through updateFile updates.
 *
 * @param fileId Identifier of the file to download.  
 * @param priority Priority of the download (1-32). The higher the priority, the earlier the file
 * will be downloaded. If the priorities of two files are equal, then the last one for which
 * downloadFile was called will be downloaded first.  
 * @param offset The starting position from which the file needs to be downloaded.  
 * @param limit Number of bytes which need to be downloaded starting from the &quot;offset&quot;
 * position before the download will be automatically canceled; use 0 to download without a limit.  
 * @param synchronous If false, this request returns file state just after the download has been
 * started. If true, this request returns file state only after the download has succeeded, has failed,
 * has been canceled or a new downloadFile request with different offset/limit parameters was sent.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.downloadFile(
  fileId: Int,
  priority: Int,
  offset: Int,
  limit: Int,
  synchronous: Boolean
): File = this.sendFunctionAsync(TdApi.DownloadFile(fileId, priority, offset, limit, synchronous))

/**
 * Suspend function, which finishes the file generation.
 *
 * @param generationId The identifier of the generation process.  
 * @param error If passed, the file generation has failed and must be terminated; pass null if the
 * file generation succeeded.
 */
suspend fun TelegramFlow.finishFileGeneration(generationId: Long, error: Error?) =
    this.sendFunctionLaunch(TdApi.FinishFileGeneration(generationId, error))

/**
 * Suspend function, which returns information about a file; this is an offline request.
 *
 * @param fileId Identifier of the file to get.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.getFile(fileId: Int): File = this.sendFunctionAsync(TdApi.GetFile(fileId))

/**
 * Suspend function, which returns file downloaded prefix size from a given offset, in bytes.
 *
 * @param fileId Identifier of the file.  
 * @param offset Offset from which downloaded prefix size needs to be calculated.
 *
 * @return [Count] Contains a counter.
 */
suspend fun TelegramFlow.getFileDownloadedPrefixSize(fileId: Int, offset: Int): Count =
    this.sendFunctionAsync(TdApi.GetFileDownloadedPrefixSize(fileId, offset))

/**
 * Suspend function, which returns the extension of a file, guessed by its MIME type. Returns an
 * empty string on failure. Can be called synchronously.
 *
 * @param mimeType The MIME type of the file.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getFileExtension(mimeType: String?): Text =
    this.sendFunctionAsync(TdApi.GetFileExtension(mimeType))

/**
 * Suspend function, which returns the MIME type of a file, guessed by its extension. Returns an
 * empty string on failure. Can be called synchronously.
 *
 * @param fileName The name of the file or path to the file.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getFileMimeType(fileName: String?): Text =
    this.sendFunctionAsync(TdApi.GetFileMimeType(fileName))

/**
 * Suspend function, which returns information about a file with a map thumbnail in PNG format. Only
 * map thumbnail files with size less than 1MB can be downloaded.
 *
 * @param location Location of the map center.  
 * @param zoom Map zoom level; 13-20.  
 * @param width Map width in pixels before applying scale; 16-1024.  
 * @param height Map height in pixels before applying scale; 16-1024.  
 * @param scale Map scale; 1-3.  
 * @param chatId Identifier of a chat, in which the thumbnail will be shown. Use 0 if unknown.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.getMapThumbnailFile(
  location: Location?,
  zoom: Int,
  width: Int,
  height: Int,
  scale: Int,
  chatId: Long
): File = this.sendFunctionAsync(TdApi.GetMapThumbnailFile(location, zoom, width, height, scale,
    chatId))

/**
 * Suspend function, which returns information about a file with messages exported from another app.
 *
 * @param messageFileHead Beginning of the message file; up to 100 first lines.
 *
 * @return [MessageFileType] This class is an abstract base class.
 */
suspend fun TelegramFlow.getMessageFileType(messageFileHead: String?): MessageFileType =
    this.sendFunctionAsync(TdApi.GetMessageFileType(messageFileHead))

/**
 * Suspend function, which returns information about a file by its remote ID; this is an offline
 * request. Can be used to register a URL as a file for further uploading, or sending as a message.
 * Even the request succeeds, the file can be used only if it is still accessible to the user. For
 * example, if the file is from a message, then the message must be not deleted and accessible to the
 * user. If the file database is disabled, then the corresponding object with the file must be
 * preloaded by the application.
 *
 * @param remoteFileId Remote identifier of the file to get.  
 * @param fileType File type; pass null if unknown.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.getRemoteFile(remoteFileId: String?, fileType: FileType?): File =
    this.sendFunctionAsync(TdApi.GetRemoteFile(remoteFileId, fileType))

/**
 * Suspend function, which returns suggested name for saving a file in a given directory.
 *
 * @param fileId Identifier of the file.  
 * @param directory Directory in which the file is supposed to be saved.
 *
 * @return [Text] Contains some text.
 */
suspend fun TelegramFlow.getSuggestedFileName(fileId: Int, directory: String?): Text =
    this.sendFunctionAsync(TdApi.GetSuggestedFileName(fileId, directory))

/**
 * Suspend function, which reads a part of a file from the TDLib file cache and returns read bytes.
 * This method is intended to be used only if the application has no direct access to TDLib's file
 * system, because it is usually slower than a direct read from the file.
 *
 * @param fileId Identifier of the file. The file must be located in the TDLib file cache.  
 * @param offset The offset from which to read the file.  
 * @param count Number of bytes to read. An error will be returned if there are not enough bytes
 * available in the file from the specified position. Pass 0 to read all available data from the
 * specified position.
 *
 * @return [FilePart] Contains a part of a file.
 */
suspend fun TelegramFlow.readFilePart(
  fileId: Int,
  offset: Int,
  count: Int
): FilePart = this.sendFunctionAsync(TdApi.ReadFilePart(fileId, offset, count))

/**
 * Suspend function, which informs TDLib on a file generation progress.
 *
 * @param generationId The identifier of the generation process.  
 * @param expectedSize Expected size of the generated file, in bytes; 0 if unknown.  
 * @param localPrefixSize The number of bytes already generated.
 */
suspend fun TelegramFlow.setFileGenerationProgress(
  generationId: Long,
  expectedSize: Int,
  localPrefixSize: Int
) = this.sendFunctionLaunch(TdApi.SetFileGenerationProgress(generationId, expectedSize,
    localPrefixSize))

/**
 * Suspend function, which asynchronously uploads a file to the cloud without sending it in a
 * message. updateFile will be used to notify about upload progress and successful completion of the
 * upload. The file will not have a persistent remote identifier until it will be sent in a message.
 *
 * @param file File to upload.  
 * @param fileType File type; pass null if unknown.  
 * @param priority Priority of the upload (1-32). The higher the priority, the earlier the file will
 * be uploaded. If the priorities of two files are equal, then the first one for which uploadFile was
 * called will be uploaded first.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.uploadFile(
  file: InputFile?,
  fileType: FileType?,
  priority: Int
): File = this.sendFunctionAsync(TdApi.UploadFile(file, fileType, priority))

/**
 * Suspend function, which uploads a PNG image with a sticker; returns the uploaded file.
 *
 * @param userId Sticker file owner; ignored for regular users.  
 * @param sticker Sticker file to upload.
 *
 * @return [File] Represents a file.
 */
suspend fun TelegramFlow.uploadStickerFile(userId: Long, sticker: InputSticker?): File =
    this.sendFunctionAsync(TdApi.UploadStickerFile(userId, sticker))

/**
 * Suspend function, which writes a part of a generated file. This method is intended to be used
 * only if the application has no direct access to TDLib's file system, because it is usually slower
 * than a direct write to the destination file.
 *
 * @param generationId The identifier of the generation process.  
 * @param offset The offset from which to write the data to the file.  
 * @param data The data to write.
 */
suspend fun TelegramFlow.writeGeneratedFilePart(
  generationId: Long,
  offset: Int,
  data: ByteArray?
) = this.sendFunctionLaunch(TdApi.WriteGeneratedFilePart(generationId, offset, data))
