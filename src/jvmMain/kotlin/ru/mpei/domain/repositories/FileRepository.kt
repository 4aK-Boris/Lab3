package ru.mpei.domain.repositories

import java.io.File
import java.security.PrivateKey
import java.security.cert.X509Certificate
import ru.mpei.domain.models.FileModel

interface FileRepository {

    suspend fun saveFile(file: File, nickName: String, data: ByteArray, privateKey: PrivateKey, certificate: X509Certificate)

    suspend fun readFile(file: File): Pair<FileModel, String>

    suspend fun chooseFile(): File
}