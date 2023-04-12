package ru.mpei.domain.usecases

import ru.mpei.core.Lab3Exception
import ru.mpei.domain.repositories.KeyRepository

class KeyUseCases(
    private val keyRepository: KeyRepository
) {

    suspend fun chooseCertificate(): List<String> {
        return keyRepository.chooseCertificate()
    }

    suspend fun createKeyPair(nickName: String) {
        checkNickName(nickName = nickName)
        keyRepository.createKeyPair(nickName = nickName)
    }

    suspend fun deleteKeyPair(nickName: String) {
        checkNickName(nickName = nickName)
        keyRepository.deleteKeyPair(nickName = nickName)
    }

    private fun checkNickName(nickName: String) {
        if (nickName.isBlank()) throw Lab3Exception("Введите имя пользователя!")
    }
}