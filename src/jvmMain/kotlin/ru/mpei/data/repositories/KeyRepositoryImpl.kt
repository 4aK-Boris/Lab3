package ru.mpei.data.repositories

import java.security.KeyPairGenerator
import ru.mpei.domain.repositories.KeyRepository
import ru.mpei.domain.repositories.KeyStoreRepository


class KeyRepositoryImpl(
    private val keyStoreRepository: KeyStoreRepository,
    private val keyPairGenerator: KeyPairGenerator
) : KeyRepository {

    override suspend fun createKeyPair(nickName: String) {
        val keyPair = keyPairGenerator.generateKeyPair()
        keyStoreRepository.addKeyPair(keyPair = keyPair, nickName = nickName)
        keyStoreRepository.save()
    }

    override suspend fun deleteKeyPair(nickName: String) {
        keyStoreRepository.deleteKeyPair(nickName = nickName)
        keyStoreRepository.save()
    }

    override suspend fun chooseCertificate(): List<String> {
        return keyStoreRepository.chooseCertificate()
    }
}