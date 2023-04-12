package ru.mpei.domain.usecases

import ru.mpei.core.Lab3Exception
import ru.mpei.domain.models.FileModel
import ru.mpei.domain.repositories.FileRepository
import ru.mpei.domain.repositories.KeyStoreRepository

class FileUseCases(
    private val fileRepository: FileRepository,
    private val keyStoreRepository: KeyStoreRepository
) {

    suspend fun downloadUseCase(): Pair<FileModel, String> {
        val file = fileRepository.chooseFile()
        return fileRepository.readFile(file = file)
    }

    suspend fun saveFileUseCase(nickName: String, text: String) {
        checkNickName(nickName = nickName)
        val file = fileRepository.chooseFile()
        val privateKey = keyStoreRepository.getPrivateKey(nickName = nickName)
        val certificate = keyStoreRepository.getCertificate(nickName = nickName)
        fileRepository.saveFile(
            nickName = nickName,
            data = text.encodeToByteArray(),
            privateKey = privateKey,
            file = file,
            certificate = certificate
        )
    }

    private fun checkNickName(nickName: String) {
        if (nickName.isBlank()) throw Lab3Exception("Введите имя пользователя!")
    }
}