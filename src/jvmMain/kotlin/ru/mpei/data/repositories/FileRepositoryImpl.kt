package ru.mpei.data.repositories

import java.io.File
import java.security.PrivateKey
import java.security.cert.X509Certificate
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import ru.mpei.core.Lab3Exception
import ru.mpei.data.dto.FileDTO
import ru.mpei.data.mappers.FileMapper
import ru.mpei.domain.models.FileModel
import ru.mpei.domain.repositories.CryptoRepository
import ru.mpei.domain.repositories.FileRepository

class FileRepositoryImpl(
    private val cryptoRepository: CryptoRepository,
    private val fileMapper: FileMapper
) : FileRepository {
    override suspend fun saveFile(file: File, nickName: String, data: ByteArray, privateKey: PrivateKey, certificate: X509Certificate) {
        val signature = cryptoRepository.createSignature(data = data, privateKey = privateKey)
        val fileModel = FileModel(data = data, signature = signature, certificate = certificate.encoded)
        val fileDTO = fileMapper.map(fileModel = fileModel)
        file.writeBytes(fileDTO.file)
    }

    override suspend fun readFile(file: File): Pair<FileModel, String> {
        if (!file.exists()) throw Lab3Exception(message = "Не найден файл с именем ${file.name}")
        val fileDTO = FileDTO(file = file.readBytes())
        val fileModel = fileMapper.map(fileDTO = fileDTO)
        val certificate = cryptoRepository.decodeCertificate(certificate = fileModel.certificate)
        val result = cryptoRepository.verifySignature(data = fileModel.data, sign = fileModel.signature, certificate = certificate)
        if (!result) throw Lab3Exception("Подпись файла не прошла проверку!")
        val nickName = certificate.subjectX500Principal.name.removePrefix("CN=")
        return fileModel to nickName
    }

    override suspend fun chooseFile(): File {
        val chooser = JFileChooser(path)
        chooser.fileSelectionMode = JFileChooser.FILES_ONLY
        chooser.fileFilter = FileNameExtensionFilter("SD файл", "sd")
        val result = chooser.showSaveDialog(null)
        if (result != JFileChooser.APPROVE_OPTION) throw Lab3Exception("Не удалось выбрать файл!")
        return chooser.selectedFile
    }

    companion object {
        private val path = System.getProperty("user.dir")
    }
}